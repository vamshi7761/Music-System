import React, { useState } from 'react';
import { createPlaylist } from '../services/playlistService';

const PlaylistForm = () => {
  console.log("Rendering PlaylistForm"); // Debug output

  const [name, setName] = useState('');

  const handleSubmit = async (event) => {
    event.preventDefault();
    const newPlaylist = { name };
    try {
      const response = await createPlaylist(newPlaylist);
      console.log(response.data); // Handle response
    } catch (error) {
      console.error(error); // Output error to console
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

export default PlaylistForm;