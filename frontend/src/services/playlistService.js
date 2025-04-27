import axios from 'axios';

const apiUrl = 'http://localhost:8080/playlists';

export const createPlaylist = (playlist) => axios.post(apiUrl, playlist);
export const getPlaylists = () => axios.get(apiUrl);
export const addSongsToPlaylist = (playlistId, songIds) => axios.post(`${apiUrl}/${playlistId}/songs`, { songIds });

export const selectPlaylist = (playlistId) => axios.post(`${apiUrl}/select/${playlistId}`);
export const getCurrentSong = () => axios.get(`${apiUrl}/current`);
export const getNextSong = () => axios.get(`${apiUrl}/next`);
export const getPreviousSong = () => axios.get(`${apiUrl}/previous`);
export const toggleLoop = () => axios.post(`${apiUrl}/loop/toggle`);
export const enableRandomMode = (playlistId) => axios.post(`${apiUrl}/${playlistId}/random/enable`);
export const disableRandomMode = (playlistId) => axios.post(`${apiUrl}/${playlistId}/random/disable`);