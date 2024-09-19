import React, {useState} from 'react';
import axios from 'axios';

function App() {
    const [uniqueId, setUniqueId] = useState(null);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState(null);

    const fetchUniqueId = async () => {
        setLoading(true);
        setError(null);
        try {
            const response = await axios.get('http://localhost:8080/unique-id');
            setUniqueId(response.data);
        } catch (error) {
            setError(error);
        } finally {
            setLoading(false);
        }
    };

    return (
        <div className="App">
            <h1>Unique Id Generator</h1>
            <button onClick={fetchUniqueId} disabled={loading}>
                {loading ? 'Loading...' : 'Unique Id Generator'}
            </button>
            {error && <p style={{color:'red'}}>{error}</p>}
            {uniqueId && <p>Generate Unique Id: {uniqueId}</p>}
        </div>
    );
}
export default App;