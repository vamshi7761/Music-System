import React, { useState } from 'react';
import axios from 'axios';

const CreatePlaylistForm = () => {
  const [name, setName] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/playlists', { name });
      console.log('Playlist created:', response.data); // Handle success/failure response
      alert('Playlist created successfully');
    } catch (error) {
      console.error('Error creating playlist:', error.response ? error.response.data : error); // Output error to console
      alert('Failed to create playlist. Check console for details.');
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>Playlist Name:</label>
      <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
      <button type="submit">Create Playlist</button>
    </form>
  );
};

export default CreatePlaylistForm;