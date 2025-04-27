package com.musicsystem.controller;

import com.musicsystem.entity.AudioFile;
import com.musicsystem.service.AudioFileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "AudioFile Management", description = "Endpoints for managing audio files")
@RestController
@RequestMapping("/api/music/audio-files")
public class AudioFileController {

    @Autowired
    private AudioFileService audioFileService;

    @Operation(summary = "Upload an audio file for a song", description = "Upload an audio file for a specific song.")
    @PostMapping("/upload/{songId}")
    public ResponseEntity<AudioFile> uploadAudioFile(
            @Parameter(description = "Song ID", required = true) @PathVariable Long songId,
            @Parameter(description = "Audio file", required = true) @RequestParam("file") MultipartFile file) {
        try {
            AudioFile audioFile = audioFileService.uploadAudioFile(songId, file);
            return ResponseEntity.ok(audioFile);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Get the audio file for a song", description = "Retrieve the audio file for a specific song.")
    @GetMapping("/download/{songId}")
    public ResponseEntity<byte[]> getAudioFile(
            @Parameter(description = "Song ID", required = true) @PathVariable Long songId) {
        AudioFile audioFile = audioFileService.getAudioFile(songId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + audioFile.getFileName() + "\"")
                .body(audioFile.getData());
    }
}