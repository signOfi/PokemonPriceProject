import time
import re
import csv
from videogame import VideoGame
from selenium import webdriver
from bs4 import BeautifulSoup


# console names as they appear in pricecharting.com URLs
CONSOLES = ["nintendo-3ds", "nintendo-ds", "gameboy-advance", "gameboy", "gameboy-color", "nintendo-switch"]

# produce exception "Failed to decode response from marionette" in main()
TROUBLE_CONSOLES_MARIONETTE = ["psp", "nintendo-3ds", "atari-7800", "jaguar"]

# produce exception "invalid argument: can't kill an exited process" in main()
TROUBLE_CONSOLES_KILL = ["sega-cd", "gameboy-color", "playstation-vita", "atari-lynx"]


def create_csv_file(all_games, filename="pokemon_games_prices.csv"):
    with open(filename, mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(["Title", "Console", "Loose Price", "Complete Price", "New Price"])
        for game in all_games:
            writer.writerow([game.title, game.console, game.loosePrice, game.completePrice, game.newPrice])


def scrollBottom(console):
    """Scrolls to the bottom of webpage. pricecharting.com/console/<console-name> loads x number of videogames at a time, this loads all
        videogames for our console before we scrape values

    Args:
        console: (string) game system name as it appears in the pricecharting URL

    Returns:
        (browser) html for webpage with all values loaded
    """
    SCROLL_PAUSE_TIME = 1
    browser = webdriver.Firefox()

    browser.get('https://www.pricecharting.com/console/' + console)
    prevHeight = browser.execute_script("return document.body.scrollHeight")
    atBottom = False  # occasionally selenium lags, this ensures that we are truly at the bottom
    while True:
        time.sleep(SCROLL_PAUSE_TIME)
        browser.execute_script("window.scrollTo(0, document.body.scrollHeight);")
        currHeight = browser.execute_script("return document.body.scrollHeight")
        if prevHeight == currHeight:
            if atBottom:
                break
            atBottom = True
        else:
            atBottom = False
        prevHeight = currHeight

    return browser


def scrapeVals(console, browser):
    """Scrapes titles and values for each Pokémon videogame in our webpage

    Args:
        console: (string) game system name as it appears in the pricecharting URL
        browser: (browser) html for webpage with all values loaded

    Returns:
        (list) Pokémon videogame objects for our console
    """
    pokemon_games = []
    soup = BeautifulSoup(browser.page_source, 'html.parser')
    for EachPart in soup.select('tr[id*="product-"]'):
        title = re.search(r'>(.*?)</a>', str(EachPart.select('td[class="title"]'))).group(1)
        # Check if 'Pokemon' is in the game title
        if 'Pokemon' in title or 'Pokémon' in title:
            loosePrice = re.findall("\d+\.\d+", str(EachPart.select('td[class="price numeric used_price"]')))
            loosePrice = loosePrice[0] if loosePrice else "N/A"
            completePrice = re.findall("\d+\.\d+", str(EachPart.select('td[class="price numeric cib_price"]')))
            completePrice = completePrice[0] if completePrice else "N/A"
            newPrice = re.findall("\d+\.\d+", str(EachPart.select('td[class="price numeric new_price"]')))
            newPrice = newPrice[0] if newPrice else "N/A"
            newGame = VideoGame(title, console, loosePrice, completePrice, newPrice)
            pokemon_games.append(newGame)
    return pokemon_games


def pullVals(console):
    """Pulls values from pricecharting.com

    Args:
        console: (string) game system name as it appears in the pricecharting URL

    Returns:
        (list) videogame objects
    """
    print('Pulling values for %s console\n' % (console))
    browser = scrollBottom(console)
    return scrapeVals(console, browser)


def main():
    all_pokemon_games = []
    for console in CONSOLES:
        browser = scrollBottom(console)
        console_games = scrapeVals(console, browser)
        all_pokemon_games.extend(console_games)
        browser.quit()  # Make sure to close the browser after scraping

    # Create CSV after all games have been scraped
    create_csv_file(all_pokemon_games)

if __name__ == "__main__":
    print('Scraping Pokémon game prices...')
    main()
    print('Scraping completed. Data saved to CSV.')