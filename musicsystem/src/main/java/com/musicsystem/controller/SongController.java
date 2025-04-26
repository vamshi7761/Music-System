package com.musicsystem.controller;

import com.musicsystem.entity.Song;
import com.musicsystem.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Song Management", description = "Endpoints for managing songs")
@RestController
@RequestMapping("/api/music/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Operation(summary = "Add a new song", description = "Add a new song to the system.")
    @PostMapping
    public ResponseEntity<Song> addSong(
            @Parameter(description = "Song data", required = true) @RequestBody Song song) {
        Song savedSong = songService.addSong(song);
        return ResponseEntity.ok(savedSong);
    }

    @Operation(summary = "Add multiple songs", description = "Add multiple songs to the system.")
    @PostMapping("/batch")
    public ResponseEntity<List<Song>> addSongs(
            @Parameter(description = "List of songs", required = true) @RequestBody List<Song> songs) {
        List<Song> savedSongs = songService.addSongs(songs);
        return ResponseEntity.ok(savedSongs);
    }

    @Operation(summary = "Search for songs by title", description = "Search for songs in the system by their title.")
    @GetMapping("/search/{title}")
    public ResponseEntity<List<Song>> searchSongs(
            @Parameter(description = "Song title", required = true) @PathVariable String title) {
        return ResponseEntity.ok(songService.searchSongs(title));
    }
}
