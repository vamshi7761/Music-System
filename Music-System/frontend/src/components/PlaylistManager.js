import React, { useState, useEffect } from 'react';
import { getPlaylists, addSongsToPlaylist } from '../services/playlistService';
import { searchSongs } from '../services/songService';

const PlaylistManager = () => {
  const [playlists, setPlaylists] = useState([]);
  const [selectedPlaylist, setSelectedPlaylist] = useState(null);
  const [songs, setSongs] = useState([]);
  const [selectedSongs, setSelectedSongs] = useState([]);

  useEffect(() => {
    const fetchPlaylists = async () => {
      const response = await getPlaylists();
      setPlaylists(response.data);
    };
    fetchPlaylists();
  }, []);

  const handlePlaylistSelect = (e) => setSelectedPlaylist(e.target.value);

  const handleSearch = async (e) => {
    const response = await searchSongs(e.target.value);
    setSongs(response.data);
  };

  const handleSongSelect = (songId) => {
    setSelectedSongs((prevSelected) => [...prevSelected, songId]);
  };

  const handleAddSongs = async () => {
    if (selectedPlaylist && selectedSongs.length > 0) {
      await addSongsToPlaylist(selectedPlaylist, selectedSongs);
      setSelectedSongs([]);
      alert("Songs added to playlist!");
    }
  };

  return (
    <div>
      <label>Select Playlist:</label>
      <select onChange={handlePlaylistSelect}>
        <option value="">Select Playlist</option>
        {playlists.map((playlist) => (
          <option key={playlist.id} value={playlist.id}>{playlist.name}</option>
        ))}
      </select>

      <label>Search Song:</label>
      <input type="text" onChange={handleSearch} />

      <div>
        <h3>Search Results:</h3>
        {songs.map((song) => (
          <div key={song.id}>
            <input type="checkbox" onChange={() => handleSongSelect(song.id)} />
            {song.title}
          </div>
        ))}
      </div>

      <button onClick={handleAddSongs}>Add Songs to Playlist</button>
    </div>
  );
};

export default PlaylistManager;