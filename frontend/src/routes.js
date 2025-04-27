import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import AddSongPage from './pages/AddSongPage';
import CreatePlaylistPage from './pages/CreatePlaylistPage';
import AddSongsToPlaylistPage from './pages/AddSongsToPlaylistPage';
import PlaylistPage from './pages/PlaylistPage';
import PlayerPage from './pages/PlayerPage';


const RoutesComponent = () => (
  <Router>
    <div>
      <Routes>
        <Route path="/add-song" element={<AddSongPage />} />
        <Route path="/create-playlist" element={<CreatePlaylistPage />} />
        <Route path="/add-songs-to-playlist" element={<AddSongsToPlaylistPage />} />
        <Route path="/playlist-manager" element={<PlaylistPage />} />
        <Route path="/player" element={<PlayerPage />} />
        
        <Route path="/" element={<AddSongPage />} />
      </Routes>
    </div>
  </Router>
);

export default RoutesComponent;