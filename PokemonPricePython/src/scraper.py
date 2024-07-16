import time
import re
import csv
from videogame import VideoGame
from selenium import webdriver
from bs4 import BeautifulSoup

CONSOLES = ["nintendo-3ds", "nintendo-ds", "gameboy-advance", "gameboy", "gameboy-color", "nintendo-switch"]
TROUBLE_CONSOLES_MARIONETTE = ["psp", "nintendo-3ds", "atari-7800", "jaguar"]
TROUBLE_CONSOLES_KILL = ["sega-cd", "gameboy-color", "playstation-vita", "atari-lynx"]

def create_csv_file(all_games, filename="pokemon_games_prices.csv"):
    with open(filename, mode='w', newline='', encoding='utf-8') as file:
        writer = csv.writer(file)
        writer.writerow(["Title", "Console", "Loose Price", "Complete Price", "New Price"])
        for game in all_games:
            writer.writerow([game.title, game.console, game.loosePrice, game.completePrice, game.newPrice])


def scrollBottom(console):
    SCROLL_PAUSE_TIME = 1
    browser = webdriver.Firefox()

    browser.get('https://www.pricecharting.com/console/' + console)
    prevHeight = browser.execute_script("return document.body.scrollHeight")
    atBottom = False  
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
