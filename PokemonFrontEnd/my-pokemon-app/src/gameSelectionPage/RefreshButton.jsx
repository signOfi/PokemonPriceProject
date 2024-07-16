import React, { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSyncAlt } from '@fortawesome/free-solid-svg-icons';

const RefreshButton = () => {
    const [isDisabled, setIsDisabled] = useState(false);

    const handleRefresh = async () => {
        setIsDisabled(true); 
        try {
            const response = await fetch('http://localhost:5454/refresh', { method: 'GET' });
            if (!response.ok) throw new Error('Failed to refresh data');
            console.log('Data refresh initiated');
        } catch (error) {
            console.error('Error refreshing data:', error);
        }

        setTimeout(() => setIsDisabled(false), 180000);
    };

    return (
        <button onClick={handleRefresh} disabled={isDisabled} className="refresh-button">
            <FontAwesomeIcon icon={faSyncAlt} spin={isDisabled} />
        </button>
    );
};

export default RefreshButton;
