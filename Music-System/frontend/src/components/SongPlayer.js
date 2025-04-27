import React, { useEffect, useState } from 'react';
import {
  selectPlaylist,
  getCurrentSong,
  getNextSong,
  getPreviousSong,
  toggleLoop,
  enableRandomMode,
  disableRandomMode
} from '../services/playlistService';

const SongPlayer = () => {
  const [currentSong, setCurrentSong] = useState(null);
  const [playlistId, setPlaylistId] = useState('');
  const [loop, setLoop] = useState(false);
  const [random, setRandom] = useState(false);

  useEffect(() => {
    if (playlistId) {
      selectPlaylist(playlistId);
      fetchCurrentSong();
    }
  }, [playlistId]);

  const fetchCurrentSong = async () => {
    const response = await getCurrentSong();
    setCurrentSong(response.data);
  };

  const handleNextSong = async () => {
    const response = await getNextSong();
    setCurrentSong(response.data);
  };

  const handlePreviousSong = async () => {
    const response = await getPreviousSong();
    setCurrentSong(response.data);
  };

  const handleToggleLoop = async () => {
    await toggleLoop();
    setLoop(!loop);
  };

  const handleRandomMode = async () => {
    if (random) {
      await disableRandomMode(playlistId);
    } else {
      await enableRandomMode(playlistId);
    }
    setRandom(!random);
  };

  return (
    <div>
      <label>Select Playlist:</label>
      <input type="text" value={playlistId} onChange={(e) => setPlaylistId(e.target.value)} required />

      <div>
        {currentSong && (
          <div>
            <h3>Now Playing: {currentSong.title} by {currentSong.artist}</h3>
            <audio controls src={`data:audio/mp3;base64,${currentSong.audioFile.data}`} />
          </div>
        )}
      </div>

      <button onClick={handlePreviousSong}>Previous Song</button>
      <button onClick={handleNextSong}>Next Song</button>
      <button onClick={handleToggleLoop}>{loop ? 'Disable Loop' : 'Enable Loop'}</button>
      <button onClick={handleRandomMode}>{random ? 'Disable Random' : 'Enable Random'}</button>
    </div>
  );
};

export default SongPlayer;