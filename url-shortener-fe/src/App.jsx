import React, {useState} from "react";
import './App.css'

function App() {
  const [longUrl, setLongUrl] = useState('');
  const [shortUrl, setShortUrl] = useState('');
  const [generateShortUrl, setGenerateShortUrl] = useState('');
  const [redirectUrl, setRedirectUrl] = useState('');

  const handleShortenUrl = async () => {
      try {
          const response = await fetch('http://localhost:8080/api/v1/data/shorten', {
              method: 'PUT',
              headers: {
                  'Content-Type': 'application/json',
              },
              body: JSON.stringify({longUrl: longUrl}),
          });

          if (response.ok) {
              const result = await response.text();
              setGenerateShortUrl(result);
          } else {
              console.error('Failed to shorten URL');
          }
      } catch (e) {
          console.error('Error shortening URL:', e);
      }
  }

  const handleRedirect = async () => {
      try {
          const code = shortUrl.startsWith('http') ? shortUrl.split('/').pop() : shortUrl;
          const response = await fetch(`http://localhost:8080/api/v1/${code}`, {
              method: 'GET',
              redirect: 'manual',
          });

          if (response.status === 301 || response.status === 302) {
              setRedirectUrl(response.headers.get('Location'));
          } else {
              console.error('No redirection found');
          }
      } catch (e) {
          console.error('Error fetching long URL:', e);
      }
  }

  return (
    <div className="App">
        <h1>URL Shortener</h1>
        <div>
            <input
            type="text"
            value={longUrl}
            onChange={(e) => setLongUrl(e.target.value)}
            placeholder="Enter a long URL"/>
            <button onClick={handleShortenUrl}>Shorten URL</button>
        </div>

        {generateShortUrl && (
            <div>
                <h3>Shortened URL:</h3>
                <p>{generateShortUrl}</p>
            </div>
        )}

        <hr/>

        <div>
            <input
                type="text"
                value={shortUrl}
                onChange={(e) => setShortUrl(e.target.value)}
                placeholder="Enter a short URL"
            />
            <button onClick={handleRedirect}>Get Long URL</button>
        </div>

        {redirectUrl && (
            <div>
                <h3>Redirect URL:</h3>
                <a href={redirectUrl} target="_blank" rel="noopener noreferrer">
                    {redirectUrl}
                </a>
            </div>

        )}
    </div>
  )
}

export default App
