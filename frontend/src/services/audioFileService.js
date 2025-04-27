import axios from 'axios';

const apiUrl = 'http://localhost:8080/api/music/audio-files';

export const uploadAudioFile = (songId, formData) => axios.post(`${apiUrl}/upload/${songId}`, formData);