import React, { useEffect, useState } from 'react';
import styles from './GameSelectionPage.module.css'; 
import { useNavigate } from 'react-router-dom';

const fetchGames = async () => {
    try {
        const response = await fetch('http://localhost:5454/games/select');
        if (!response.ok) throw new Error('Network response was not ok');
        return await response.json();
    } catch (error) {
        console.error("There was a problem with the fetch operation: ", error);
        throw error;
    }
};

const GameSelectionPage = () => {
    const [games, setGames] = useState({});
    const navigate = useNavigate(); 

    // Moved inside GameSelectionPage component
    const handleGameClick = (gameName) => {
        navigate(`/game-details/${encodeURIComponent(gameName)}`); 
    };

    useEffect(() => {
        const loadGames = async () => {
            try {
                let gamesData = await fetchGames();
                gamesData = gamesData.filter(game => game.imageUrl !== 'invalid.webp');

                const uniqueGames = [];
                const seenTitles = new Set();

                for (const game of gamesData) {
                    if (!seenTitles.has(game.title)) {
                        uniqueGames.push(game);
                        seenTitles.add(game.title);
                    }
                }

                // Categorize games based on the platform
                const categorizedGames = uniqueGames.reduce((acc, game) => {
                    const platformKey = game.gamePlatform;
                    (acc[platformKey] = acc[platformKey] || []).push(game);
                    return acc;
                }, {});

                setGames(categorizedGames);
            } catch (error) {
                console.error("Error fetching games: ", error);
            }
        };

        loadGames();
    }, []);

    const platforms = Object.keys(games);

    // Define the order of the platforms
    const platformOrder = [
        "GB_GBC",
        "GBA",
        "GAMECUBE",
        "DS",
        "DS3D",
        "SWITCH",
    ];

    // Sort the platforms array based on the predefined order
    const sortedPlatforms = platforms.sort((a, b) => {
        return platformOrder.indexOf(a) - platformOrder.indexOf(b);
    });

    return (
        <div className={styles.gameContainer}>
            {sortedPlatforms.map(platform => (
                <div key={platform} className={`${styles.platformRow} ${styles[platform.toLowerCase()]}`}>
                    <div className={styles.gameRow}>
                        {games[platform]?.map(game => (
                            <div key={game.title} className={styles.gameItem} onClick={() => handleGameClick(game.title)}>
                                <img src={`http://localhost:5454/${game.imageUrl}`} alt={game.title} className={styles.gameImage} />
                                <p className={styles.gameTitle}>{game.title}</p>
                            </div>
                        ))}
                    </div>
                </div>
            ))}
        </div>
    );
};

export default GameSelectionPage;
