// import React, { useState } from 'react';
// import axios from 'axios';

// const SongForm = ({ onSongAdded }) => {
//   console.log("Rendering SongForm"); // Debug output

//   const [title, setTitle] = useState('');
//   const [artist, setArtist] = useState('');
//   const [album, setAlbum] = useState('');

//   const handleSubmit = async (event) => {
//     event.preventDefault();
//     console.log("Submitting form"); // Debug output
//     const newSong = { title, artist, album };
//     console.log("New Song:", newSong);
//     try {
//       const response = await axios.post('http://localhost:8080/api/music/songs', newSong);
//       console.log("Song added:", response.data); // Handle success/failure response
//       if (onSongAdded) {
//         onSongAdded(response.data.id); // Notify parent component
//       }
//     } catch (error) {
//       console.error("Error adding song:", error); // Output error to console
//     }
//   };

//   return (
//     <form onSubmit={handleSubmit}>
//       <label>Title:</label>
//       <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required />
//       <label>Artist:</label>
//       <input type="text" value={artist} onChange={(e) => setArtist(e.target.value)} required />
//       <label>Album:</label>
//       <input type="text" value={album} onChange={(e) => setAlbum(e.target.value)} required />
//       <button type="submit">Add Song</button>
//     </form>
//   );
// };

// export default SongForm;
import React, { useState } from 'react';
import axios from 'axios';

const SongForm = ({ onSongAdded }) => {
  const [title, setTitle] = useState('');
  const [artist, setArtist] = useState('');
  const [album, setAlbum] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const handleSubmit = async (event) => {
    event.preventDefault();
    setIsSubmitting(true);

    const newSong = { title, artist, album };
    try {
      const response = await axios.post('http://localhost:8080/api/music/songs', newSong);
      console.log("Song added:", response.data);
      if (onSongAdded) {
        onSongAdded(response.data.id);
      }
      setTitle('');
      setArtist('');
      setAlbum('');
      setIsSubmitting(false);
    } catch (error) {
      console.error("Error adding song:", error);
      setIsSubmitting(false);
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <label>Title:</label>
      <input type="text" value={title} onChange={(e) => setTitle(e.target.value)} required />
      <label>Artist:</label>
      <input type="text" value={artist} onChange={(e) => setArtist(e.target.value)} required />
      <label>Album:</label>
      <input type="text" value={album} onChange={(e) => setAlbum(e.target.value)} required />
      <button type="submit" disabled={isSubmitting} style={{ display: isSubmitting ? 'none' : 'block' }}>
        Add Song
      </button>
    </form>
  );
};

export default SongForm;