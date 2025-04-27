import axios from 'axios';

const apiUrl = 'http://localhost:8080/api/music/songs';

export const addSong = (song) => axios.post(apiUrl, song);
export const searchSongs = (title) => axios.get(`${apiUrl}/search/${title}`);