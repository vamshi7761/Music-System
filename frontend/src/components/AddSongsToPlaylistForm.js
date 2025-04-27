import React, { useState, useEffect } from 'react';
import axios from 'axios';

const AddSongsToPlaylistForm = () => {
  const [playlists, setPlaylists] = useState([]);
  const [selectedPlaylist, setSelectedPlaylist] = useState('');
  const [searchTerm, setSearchTerm] = useState('');
  const [songs, setSongs] = useState([]);
  const [addedSongs, setAddedSongs] = useState([]);
  const [currentPlaylistSongs, setCurrentPlaylistSongs] = useState([]);
  const [message, setMessage] = useState('');

  useEffect(() => {
    const fetchPlaylists = async () => {
      try {
        const response = await axios.get('http://localhost:8080/playlists/allplaylist');
        console.log('Fetched playlists:', response.data);
        setPlaylists(response.data);
      } catch (error) {
        console.error('Error fetching playlists:', error);
      }
    };
    fetchPlaylists();
  }, []);

  useEffect(() => {
    if (selectedPlaylist) {
      const fetchPlaylistSongs = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/playlists/${selectedPlaylist}`);
          console.log(`Fetched songs for playlist ${selectedPlaylist}:`, response.data.songs);
          setCurrentPlaylistSongs(response.data.songs);
        } catch (error) {
          console.error('Error fetching playlist songs:', error);
        }
      };
      fetchPlaylistSongs();
    } else {
      setCurrentPlaylistSongs([]);
    }
    setAddedSongs([]);
  }, [selectedPlaylist]);

  const handlePlaylistChange = (event) => {
    setSelectedPlaylist(event.target.value);
    setMessage('');
  };

  const handleSearchChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleSearchSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.get(`http://localhost:8080/api/music/songs/search/${searchTerm}`);
      console.log('Fetched songs:', response.data);
      const filteredSongs = response.data.filter(song => song !== null);
      setSongs(filteredSongs);
    } catch (error) {
      console.error('Error fetching songs:', error);
      setSongs([]); // Ensure songs state is reset on error
    }
  };

  const handleAddSong = async (songId) => {
    if (!selectedPlaylist || !songId) {
      setMessage('Please select a playlist and a song');
      return;
    }

    if (currentPlaylistSongs.some(song => song.id === songId) || addedSongs.some(song => song.id === songId)) {
      setMessage('Song is already in the playlist');
      return;
    }

    try {
      await axios.post(`http://localhost:8080/playlists/${selectedPlaylist}/songs/${songId}`);
      
      // Add song title to the addedSongs and currentPlaylistSongs array for displaying below
      const songTitle = songs.find(song => song.id === parseInt(songId)).title;
      setAddedSongs([...addedSongs, { id: songId, title: songTitle }]);
      setCurrentPlaylistSongs([...currentPlaylistSongs, { id: songId, title: songTitle }]);
      setMessage(`Song "${songTitle}" added to playlist successfully`);
    } catch (error) {
      console.error('Error adding song to playlist:', error);
      setMessage('Error adding song to playlist');
    }
  };

  return (
    <div>
      {message && <p>{message}</p>}
      
      <form>
        <label>Select Playlist:</label>
        <select value={selectedPlaylist} onChange={handlePlaylistChange} required>
          <option value="">Select a playlist</option>
          {playlists.map((playlist) => (
            <option key={playlist.id} value={playlist.id}>{playlist.name}</option>
          ))}
        </select>
      </form>

      <form onSubmit={handleSearchSubmit}>
        <label>Search Songs:</label>
        <input type="text" value={searchTerm} onChange={handleSearchChange} required />
        <button type="submit">Search</button>
      </form>

      {songs.length > 0 ? (
        <div>
          <h3>Search Results:</h3>
          <ul>
            {songs.map((song) => (
              <li key={song.id} onClick={() => handleAddSong(song.id)} style={{ cursor: 'pointer', color: 'blue' }}>
                {song.title}
              </li>
            ))}
          </ul>
        </div>
      ) : (
        searchTerm && <p>No songs found</p>
      )}

      {addedSongs.length > 0 && (
        <div>
          <h3>Added Songs:</h3>
          <ul>
            {addedSongs.map((song, index) => (
              <li key={index}>{song.title}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default AddSongsToPlaylistForm;