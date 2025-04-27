// import React, { useState, useEffect, useRef } from 'react';
// import axios from 'axios';

// const PlaylistPlayer = () => {
//   const [playlists, setPlaylists] = useState([]);
//   const [selectedPlaylist, setSelectedPlaylist] = useState(null);
//   const [songs, setSongs] = useState([]);
//   const [currentSongIndex, setCurrentSongIndex] = useState(null);
//   const [currentSong, setCurrentSong] = useState(null);
//   const [randomMode, setRandomMode] = useState(false);
//   const [loopMode, setLoopMode] = useState(false);
//   const audioRef = useRef(null);

//   useEffect(() => {
//     const fetchPlaylists = async () => {
//       try {
//         const response = await axios.get('http://localhost:8080/playlists/allplaylist');
//         console.log('Fetched playlists:', response.data);
//         setPlaylists(response.data);
//       } catch (error) {
//         console.error('Error fetching playlists:', error);
//       }
//     };

//     fetchPlaylists();
//   }, []);

//   const handlePlaylistChange = async (event) => {
//     const playlistId = event.target.value;
//     setSelectedPlaylist(playlistId);
//     setSongs([]);
//     setCurrentSongIndex(null);
//     setCurrentSong(null);
//     if (playlistId) {
//       try {
//         const response = await axios.get(`http://localhost:8080/playlists/${playlistId}`);
//         console.log('Fetched songs for playlist:', response.data.songs);
//         setSongs(response.data.songs);
//       } catch (error) {
//         console.error('Error fetching songs for playlist:', error);
//       }
//     }
//   };

//   const handleSongSelect = async (index) => {
//     if (selectedPlaylist && songs.length > 0) {
//       setCurrentSongIndex(index);
//       try {
//         const response = await axios.get(`http://localhost:8080/playlists/${selectedPlaylist}/select/${index}`);
//         console.log('Selected song:', response.data);
//         setCurrentSong(response.data);
//       } catch (error) {
//         console.error('Error selecting song:', error);
//       }
//     }
//   };

//   const handleSongEnd = async () => {
//     if (selectedPlaylist) {
//       if (loopMode) {
//         handleSongSelect(currentSongIndex);
//       } else {
//         let nextIndex;
//         if (randomMode) {
//           nextIndex = Math.floor(Math.random() * songs.length);
//         } else {
//           nextIndex = (currentSongIndex + 1) % songs.length;
//         }
//         handleSongSelect(nextIndex);
//       }
//     }
//   };

//   const handlePreviousSong = async () => {
//     if (selectedPlaylist) {
//       let prevIndex = currentSongIndex - 1;
//       if (prevIndex < 0) {
//         prevIndex = songs.length - 1;
//       }
//       handleSongSelect(prevIndex);
//     }
//   };

//   const toggleRandomMode = async () => {
//     if (selectedPlaylist) {
//       setRandomMode(!randomMode);
//       const endpoint = randomMode ? '/random/disable' : '/random/enable';
//       try {
//         await axios.post(`http://localhost:8080/playlists/${selectedPlaylist}${endpoint}`);
//         console.log('Random mode toggled');
//       } catch (error) {
//         console.error('Error toggling random mode:', error);
//       }
//     }
//   };

//   const toggleLoopMode = async () => {
//     if (selectedPlaylist) {
//       setLoopMode(!loopMode);
//       try {
//         await axios.post('http://localhost:8080/playlists/loop/toggle');
//         console.log('Loop mode toggled');
//       } catch (error) {
//         console.error('Error toggling loop mode:', error);
//       }
//     }
//   };

//   const handleStopSong = async () => {
//     try {
//       await axios.post('http://localhost:8080/playlists/stop');
//       setCurrentSongIndex(null);
//       setCurrentSong(null);
//       if (audioRef.current) {
//         audioRef.current.pause();
//         audioRef.current.currentTime = 0;
//       }
//       console.log('Song stopped');
//     } catch (error) {
//       console.error('Error stopping song:', error);
//     }
//   };

//   return (
//     <div>
//       <h1>Playlist Player</h1>
//       <label>Select Playlist:</label>
//       <select onChange={handlePlaylistChange} required>
//         <option value="">Select a playlist</option>
//         {playlists.map(playlist => (
//           <option key={playlist.id} value={playlist.id}>{playlist.name}</option>
//         ))}
//       </select>

//       {songs.length > 0 && (
//         <div>
//           <h3>Songs in Playlist:</h3>
//           <ul>
//             {songs.map((song, index) => (
//               <li key={index} onClick={() => handleSongSelect(index)}>
//                 {song.title}
//               </li>
//             ))}
//           </ul>
//         </div>
//       )}

//       {currentSong && currentSong.data && (
//         <div key={currentSong.song.id}>
//           <h3>Now Playing:</h3>
//           <p>{currentSong.song.title} by {currentSong.song.artist}</p>
//           <audio 
//             ref={audioRef} 
//             controls 
//             onEnded={handleSongEnd} 
//             autoPlay
//           >
//             <source src={`data:${currentSong.fileType};base64,${currentSong.data}`} type={currentSong.fileType} />
//             Your browser does not support the audio element.
//           </audio>
//           <button onClick={handlePreviousSong}>Previous Song</button>
//           <button onClick={() => handleSongEnd()}>Next Song</button>
//           <button onClick={toggleRandomMode}>{randomMode ? 'Disable' : 'Enable'} Random Mode</button>
//           <button onClick={toggleLoopMode}>{loopMode ? 'Disable' : 'Enable'} Loop Mode</button>
//           <button onClick={handleStopSong}>Stop</button>
//         </div>
//       )}
//     </div>
//   );
// };

// export default PlaylistPlayer;
import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';

const PlaylistPlayer = () => {
  const [playlists, setPlaylists] = useState([]);
  const [selectedPlaylist, setSelectedPlaylist] = useState(null);
  const [songs, setSongs] = useState([]);
  const [currentSongIndex, setCurrentSongIndex] = useState(null);
  const [currentSong, setCurrentSong] = useState(null);
  const [randomMode, setRandomMode] = useState(false);
  const [loopMode, setLoopMode] = useState(false);
  const audioRef = useRef(null);

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
      const fetchSongsForPlaylist = async () => {
        try {
          const response = await axios.get(`http://localhost:8080/playlists/${selectedPlaylist}`);
          console.log('Fetched songs for playlist:', response.data.songs);
          setSongs(response.data.songs);
        } catch (error) {
          console.error('Error fetching songs for playlist:', error);
        }
      };
      fetchSongsForPlaylist();
    }
  }, [selectedPlaylist]);

  const handlePlaylistChange = async (event) => {
    const playlistId = event.target.value;
    setSelectedPlaylist(playlistId);
    setSongs([]);
    setCurrentSongIndex(null);
    setCurrentSong(null);
    if (playlistId) {
      try {
        const response = await axios.get(`http://localhost:8080/playlists/${playlistId}`);
        console.log('Fetched songs for playlist:', response.data.songs);
        setSongs(response.data.songs);
      } catch (error) {
        console.error('Error fetching songs for playlist:', error);
      }
    }
  };

  const handleSongSelect = async (index) => {
    if (selectedPlaylist && songs.length > 0) {
      setCurrentSongIndex(index);
      try {
        const response = await axios.get(`http://localhost:8080/playlists/${selectedPlaylist}/select/${index}`);
        console.log('Selected song:', response.data);
        setCurrentSong(response.data);
        // Play the song immediately when it is selected
        if (audioRef.current) {
          audioRef.current.load();
          audioRef.current.play();
        }
      } catch (error) {
        console.error('Error selecting song:', error);
      }
    }
  };

  const handleSongEnd = async () => {
    if (songs.length > 0) {
      if (loopMode) {
        // Rewind the current song to the beginning and play it again
        if (audioRef.current) {
          audioRef.current.currentTime = 0;
          audioRef.current.play();
        }
      } else {
        let nextIndex;
        if (randomMode) {
          nextIndex = Math.floor(Math.random() * songs.length);
        } else {
          nextIndex = (currentSongIndex + 1) % songs.length;
        }
        handleSongSelect(nextIndex);
      }
    }
  };

  const handlePreviousSong = () => {
    if (selectedPlaylist && currentSongIndex !== null) {
      let prevIndex = currentSongIndex - 1;
      if (prevIndex < 0) {
        prevIndex = songs.length - 1;
      }
      handleSongSelect(prevIndex);
    }
  };

  const toggleRandomMode = async () => {
    if (selectedPlaylist) {
      setRandomMode(!randomMode);
      try {
        const endpoint = randomMode 
          ? `http://localhost:8080/playlists/${selectedPlaylist}/random/disable`
          : `http://localhost:8080/playlists/${selectedPlaylist}/random/enable`;
        await axios.post(endpoint);
        console.log('Random mode toggled');
      } catch (error) {
        console.error('Error toggling random mode:', error);
      }
    }
  };

  const toggleLoopMode = async () => {
    if (selectedPlaylist) {
      setLoopMode(!loopMode);
      try {
        await axios.post(`http://localhost:8080/playlists/loop/toggle`);
        console.log('Loop mode toggled');
      } catch (error) {
        console.error('Error toggling loop mode:', error);
      }
    }
  };

  return (
    <div>
      <h1>Playlist Player</h1>
      <label>Select Playlist:</label>
      <select onChange={handlePlaylistChange} required>
        <option value="">Select a playlist</option>
        {playlists.map((playlist) => (
          <option key={playlist.id} value={playlist.id}>{playlist.name}</option>
        ))}
      </select>

      {songs.length > 0 && (
        <div>
          <h3>Songs in Playlist:</h3>
          <ul>
            {songs.map((song, index) => (
              <li key={song.id} onClick={() => handleSongSelect(index)} style={{ cursor: 'pointer' }}>
                {song.title}
              </li>
            ))}
          </ul>
        </div>
      )}

      {currentSong && currentSong.data && (
        <div key={currentSong.song.id}>
          <h3>Now Playing:</h3>
          <p>{currentSong.song.title} by {currentSong.song.artist}</p>
          <audio
            ref={audioRef}
            controls
            onEnded={handleSongEnd}
            autoPlay
          >
            <source src={`data:${currentSong.fileType};base64,${currentSong.data}`} type={currentSong.fileType} />
            Your browser does not support the audio element.
          </audio>
          <button onClick={handlePreviousSong}>Previous Song</button>
          <button onClick={handleSongEnd}>Next Song</button>
          <button onClick={toggleRandomMode}>{randomMode ? 'Disable' : 'Enable'} Random Mode</button>
          <button onClick={toggleLoopMode}>{loopMode ? 'Disable' : 'Enable'} Loop Mode</button>
        </div>
      )}
    </div>
  );
};

export default PlaylistPlayer;