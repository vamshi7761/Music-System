import React, { useState } from 'react';
import SongForm from './SongForm';
import UploadAudioFile from './UploadAudioFile';
import CreatePlaylistForm from './CreatePlaylistForm';
import AddSongsToPlaylistForm from './AddSongsToPlaylistForm';
import PlaylistPlayer from './PlaylistPlayer';

const MainNavigation = () => {
  const [activePage, setActivePage] = useState('addSong');
  const [songId, setSongId] = useState(null);
  const [songAdded, setSongAdded] = useState(false);
  const [fileUploaded, setFileUploaded] = useState(false);
  const [songMessage, setSongMessage] = useState('');
  const [uploadMessage, setUploadMessage] = useState('');

  const handleSongAdded = (newSongId) => {
    setSongId(newSongId);
    setSongAdded(true);
    setSongMessage('Song added successfully!');
  };

  const handleUploadComplete = (message) => {
    setUploadMessage(message);
    setFileUploaded(true);
  };

  const resetForm = () => {
    setSongId(null);
    setSongAdded(false);
    setFileUploaded(false);
    setSongMessage('');
    setUploadMessage('');
  };

  const renderPage = () => {
    switch (activePage) {
      case 'addSong':
        return (
          <div>
            <h1>Add New Song</h1>
            {!songAdded ? (
              <SongForm onSongAdded={handleSongAdded} />
            ) : (
              <div>
                {songMessage && <p>{songMessage}</p>}
                {!fileUploaded && (
                  <UploadAudioFile songId={songId} onUploadComplete={handleUploadComplete} />
                )}
                {fileUploaded && (
                  <div>
                    {uploadMessage && <p>{uploadMessage}</p>}
                    <button onClick={resetForm}>Add Another Song</button>
                  </div>
                )}
              </div>
            )}
          </div>
        );
      case 'createPlaylist':
        return (
          <div>
            <h1>Create New Playlist</h1>
            <CreatePlaylistForm />
          </div>
        );
      case 'addSongsToPlaylist':
        return (
          <div>
            <h1>Add Songs to Playlist</h1>
            <AddSongsToPlaylistForm />
          </div>
        );
      case 'player':
        return (
          <div>
            <h1>Playlist Player</h1>
            <PlaylistPlayer />
          </div>
        );
      default:
        return (
          <div>
            <h1>Add New Song</h1>
            {!songAdded ? (
              <SongForm onSongAdded={handleSongAdded} />
            ) : (
              <div>
                {songMessage && <p>{songMessage}</p>}
                {!fileUploaded && (
                  <UploadAudioFile songId={songId} onUploadComplete={handleUploadComplete} />
                )}
                {fileUploaded && (
                  <div>
                    {uploadMessage && <p>{uploadMessage}</p>}
                    <button onClick={resetForm}>Add Another Song</button>
                  </div>
                )}
              </div>
            )}
          </div>
        );
    }
  };

  return (
    <div>
      <nav>
        <button onClick={() => setActivePage('addSong')}>Add Song</button>
        <button onClick={() => setActivePage('createPlaylist')}>Create Playlist</button>
        <button onClick={() => setActivePage('addSongsToPlaylist')}>Add Songs to Playlist</button>
        <button onClick={() => setActivePage('player')}>Player</button>
      </nav>
      {renderPage()}
    </div>
  );
};

export default MainNavigation;