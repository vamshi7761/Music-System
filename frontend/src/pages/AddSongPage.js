import React, { useState } from 'react';
import SongForm from '../components/SongForm';
import UploadAudioFile from '../components/UploadAudioFile';

const AddSongPage = () => {
  const [songId, setSongId] = useState(null);

  const handleSongAdded = (newSongId) => {
    console.log("Song added with ID:", newSongId);
    setSongId(newSongId); // Set songId for file upload
  };

  return (
    <div>
      <h1>Add New Song</h1>
      <SongForm onSongAdded={handleSongAdded} />
      {songId && <UploadAudioFile songId={songId} />}  {/* Render upload form only after song added */}
    </div>
  );
};

export default AddSongPage;