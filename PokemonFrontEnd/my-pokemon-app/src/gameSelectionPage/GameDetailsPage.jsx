import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import './GameDetails.module.css';


const GameDetailsPage = () => {
    const { gameName } = useParams(); // Get game name from URL
    const [gameDetails, setGameDetails] = useState(null);

    useEffect(() => {
        const fetchGameDetails = async () => {
            try {
                const url = `http://localhost:5454/games/select/${encodeURIComponent(gameName)}`;
                const response = await fetch(url);
                if (!response.ok) throw new Error('Network response was not ok');
                const data = await response.json();
                setGameDetails(data);
            } catch (error) {
                console.error("Error fetching game details: ", error);
            }
        };

        fetchGameDetails();
    }, [gameName]);

    // Function to format price
    const formatPrice = (price) => {
        return price && price !== 0 ? `$${price}` : "No Sales Data Available";
    };

    if (!gameDetails) return <div>Loading...</div>;

    return (
        <div className="game-details-container">
            <h1 className="game-title">{gameDetails.title}</h1>
            <img src={`http://localhost:5454/${gameDetails.imageUrl}`} alt={gameDetails.title} className="game-image" />
            <p className="game-price">CIB Price: {formatPrice(gameDetails.cibPrice)}</p>
            <p className="game-price">New Price: {formatPrice(gameDetails.newPrice)}</p>
            <p className="game-price">Loose Price: {formatPrice(gameDetails.loosePrice)}</p>
        </div>
    );
};


export default GameDetailsPage;
