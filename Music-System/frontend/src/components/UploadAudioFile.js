import React, { useState } from 'react';
import axios from 'axios';

const UploadAudioFile = ({ songId, onUploadComplete }) => {
  const [file, setFile] = useState(null);
  const [isUploading, setIsUploading] = useState(false);

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    console.log("Selected file:", e.target.files[0]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();
    if (!songId) {
      console.error("No songId provided");
      return;
    }
    setIsUploading(true);
    const formData = new FormData();
    formData.append('file', file);
    try {
      const response = await axios.post(`http://localhost:8080/api/music/audio-files/upload/${songId}`, formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
      });
      console.log("File uploaded:", response.data);
      setFile(null);
      setIsUploading(false);
      if (onUploadComplete) {
        onUploadComplete('File uploaded successfully');
      }
    } catch (error) {
      console.error("Error uploading file:", error);
      setIsUploading(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>Upload Audio File:</label>
      <input type="file" onChange={handleFileChange} required />
      <button type="submit" disabled={isUploading || !file}>
        {isUploading ? 'Uploading...' : 'Upload'}
      </button>
    </form>
  );
};

export default UploadAudioFile;