package com.musicsystem.service;

import com.musicsystem.entity.AudioFile;
import com.musicsystem.entity.Song;
import com.musicsystem.repository.AudioFileRepository;
import com.musicsystem.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class AudioFileService {
    @Autowired
    private AudioFileRepository audioFileRepository;

    @Autowired
    private SongRepository songRepository;

    public AudioFile uploadAudioFile(Long songId, MultipartFile file) throws IOException {
        Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song not found"));

        AudioFile audioFile = new AudioFile();
        audioFile.setFileName(file.getOriginalFilename());
        audioFile.setFileType(file.getContentType());
        audioFile.setData(file.getBytes());
        audioFile.setSong(song);

        return audioFileRepository.save(audioFile);
    }

    public AudioFile getAudioFile(Long songId) {
        return audioFileRepository.findBySongId(songId).orElseThrow(() -> new RuntimeException("Audio file not found for song id " + songId));
    }
}