import React from 'react';
import GameSelectionPage from "./gameSelectionPage/GameSelectionPage.jsx";
import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import GameDetailsPage from "./gameSelectionPage/GameDetailsPage.jsx";
import RefreshButton from "./gameSelectionPage/RefreshButton.jsx";

function App() {
    return (
        <>
            <RefreshButton />
            <Router>
                <Routes>
                    <Route path="/" element={<GameSelectionPage />} />
                    <Route path="/game-details/:gameName" element={<GameDetailsPage />} />
                </Routes>
            </Router>
        </>
    );
}
export default App;
