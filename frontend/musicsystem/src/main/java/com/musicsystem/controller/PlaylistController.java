//package com.musicsystem.controller;
//
//import java.net.http.HttpHeaders;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.http.*;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.ResponseEntity;
//
//import com.musicsystem.entity.AudioFile;
//import com.musicsystem.entity.Playlist;
//import com.musicsystem.entity.Song;
//import com.musicsystem.service.PlaylistService;
//
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.tags.Tag;
//
//@Tag(name = "Playlist Management", description = "Endpoints for managing playlists")
//@RestController
//@RequestMapping("/api/music/playlists")
//public class PlaylistController {
//
//    @Autowired
//    private PlaylistService playlistService;
//
//    @Operation(summary = "Create a new playlist", description = "Create a new playlist with the specified name.")
//    @PostMapping
//    public ResponseEntity<Playlist> createPlaylist(
//            @Parameter(description = "Playlist data", required = true) @RequestBody Playlist playlist) {
//        Playlist savedPlaylist = playlistService.createPlaylist(playlist);
//        return ResponseEntity.ok(savedPlaylist);
//    }
//
//    @Operation(summary = "Add a song to a playlist by song ID", description = "Add a specific song to a playlist by its ID.")
//    @PostMapping("/{playlistId}/songs/{songId}")
//    public ResponseEntity<Void> addSongToPlaylist(
//            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId,
//            @Parameter(description = "Song ID", required = true) @PathVariable Long songId) {
//        playlistService.addSongToPlaylist(playlistId, songId);
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "Add multiple songs to a playlist", description = "Add multiple songs to a playlist by their IDs.")
//    @PostMapping("/{playlistId}/songs")
//    public ResponseEntity<Void> addSongsToPlaylist(
//            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId,
//            @Parameter(description = "List of Song IDs", required = true) @RequestBody List<Long> songIds) {
//        playlistService.addSongsToPlaylist(playlistId, songIds);
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "Get the current song of the selected playlist", description = "Retrieve the current song being played from the selected playlist.")
//    @GetMapping("/current")
//    public ResponseEntity<Song> getCurrentSong() {
//        return ResponseEntity.ok(playlistService.getCurrentSong());
//    }
//
//    @Operation(summary = "Select a playlist", description = "Select a playlist to manage its songs.")
//    @PutMapping("/select/{playlistId}")
//    public ResponseEntity<Void> selectPlaylist(
//            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId) {
//        playlistService.selectPlaylist(playlistId);
//        return ResponseEntity.ok().build();
//    }
//
//    
//   
//
////    @Operation(summary = "Select a song from the selected playlist by song index", description = "Select a specific song from the selected playlist using its index.")
////    @PutMapping("/select/song/{songIndex}")
////    public ResponseEntity<Song> selectSong(
////            @Parameter(description = "Song index in the playlist", required = true) @PathVariable int songIndex) {
////        return ResponseEntity.ok(playlistService.selectSong((long) songIndex));
////    }
//    @Operation(summary = "Select a song from the selected playlist by song index", description = "Select a specific song from the selected playlist using its index.")
//    @PutMapping("/select/song/{songIndex}")
//    public ResponseEntity<byte[]> selectSong(
//            @Parameter(description = "Song index in the playlist", required = true) @PathVariable int songIndex) {
//        AudioFile audioFile = playlistService.selectSong((long) songIndex);
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + audioFile.getFileName() + "\"")
//                .body(audioFile.getData());
//    }
//    @Operation(summary = "Deselect the currently selected playlist", description = "Deselect the currently selected playlist.")
//    @PutMapping("/deselect")
//    public ResponseEntity<Void> deselectPlaylist() {
//        playlistService.deselectPlaylist();
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "Enable random play mode for a playlist", description = "Enable random play mode for a specific playlist.")
//    @PutMapping("/{playlistId}/random/enable")
//    public ResponseEntity<Void> enableRandomMode(
//            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId) {
//        playlistService.enableRandomMode(playlistId);
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "Disable random play mode for a playlist", description = "Disable random play mode for a specific playlist.")
//    @PutMapping("/{playlistId}/random/disable")
//    public ResponseEntity<Void> disableRandomMode(
//            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId) {
//        playlistService.disableRandomMode(playlistId);
//        return ResponseEntity.ok().build();
//    }
//
//    @Operation(summary = "Get the next song in the selected playlist", description = "Retrieve the next song in the selected playlist.")
//    @GetMapping("/next")
//    public ResponseEntity<Song> getNextSong() {
//        return ResponseEntity.ok(playlistService.getNextSong().get(0));
//    }
//
//    @Operation(summary = "Get the previous song in the selected playlist", description = "Retrieve the previous song in the selected playlist.")
//    @GetMapping("/previous")
//    public ResponseEntity<Song> getPreviousSong() {
//        return ResponseEntity.ok(playlistService.getPreviousSong().get(0));
//    }
//    
//    @Operation(summary = "audio loop mode for the selected playlist", description = "audio loop mode for the currently selected playlist.")
//    @PutMapping("/audio-loop")
//    public ResponseEntity<Void> audioLoop() {
//        playlistService.toggleLoop();
//        return ResponseEntity.ok().build();
//    }
//    
//    @Operation(summary = "Toggle loop mode for the selected playlist", description = "Toggle loop mode for the currently selected playlist.")
//    @PutMapping("/toggle-loop")
//    public ResponseEntity<Void> toggleLoop() {
//        playlistService.toggleLoop();
//        return ResponseEntity.ok().build();
//    }
//    
//    @PostMapping("/stop")
//    public ResponseEntity<String> stopSong() {
//        try {
//            playlistService.stopSong();
//            return ResponseEntity.ok("Song stopped successfully");
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error stopping song: " + e.getMessage());
//        }
//    }
//
//
//    
//    
//    
//}

package com.musicsystem.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.musicsystem.entity.AudioFile;
import com.musicsystem.entity.Playlist;
import com.musicsystem.entity.Song;
import com.musicsystem.service.PlaylistService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Playlist Management", description = "Endpoints for managing playlists")
@RestController
@RequestMapping("/api/music/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @Operation(summary = "Create a new playlist", description = "Create a new playlist with the specified name.")
    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(
            @Parameter(description = "Playlist data", required = true) @RequestBody Playlist playlist) {
        Playlist savedPlaylist = playlistService.createPlaylist(playlist);
        return ResponseEntity.ok(savedPlaylist);
    }

    @Operation(summary = "Add a song to a playlist by song ID", description = "Add a specific song to a playlist by its ID.")
    @PostMapping("/{playlistId}/songs/{songId}")
    public ResponseEntity<Void> addSongToPlaylist(
            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId,
            @Parameter(description = "Song ID", required = true) @PathVariable Long songId) {
        playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Add multiple songs to a playlist", description = "Add multiple songs to a playlist by their IDs.")
    @PostMapping("/{playlistId}/songs")
    public ResponseEntity<Void> addSongsToPlaylist(
            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId,
            @Parameter(description = "List of Song IDs", required = true) @RequestBody List<Long> songIds) {
        playlistService.addSongsToPlaylist(playlistId, songIds);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get the current song of the selected playlist", description = "Retrieve the current song being played from the selected playlist.")
    @GetMapping("/current")
    public ResponseEntity<Song> getCurrentSong() {
        return ResponseEntity.ok(playlistService.getCurrentSong());
    }

    @Operation(summary = "Select a playlist", description = "Select a playlist to manage its songs.")
    @PutMapping("/select/{playlistId}")
    public ResponseEntity<Void> selectPlaylist(
            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId) {
        playlistService.selectPlaylist(playlistId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Select a song from the selected playlist by song index", description = "Select a specific song from the selected playlist using its index.")
    @PutMapping("/select/song/{songIndex}")
    public ResponseEntity<byte[]> selectSong(
            @Parameter(description = "Song index in the playlist", required = true) @PathVariable int songIndex) {
        AudioFile audioFile = playlistService.selectSong((long) songIndex);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + audioFile.getFileName() + "\"")
                .body(audioFile.getData());
    }

    @Operation(summary = "Deselect the currently selected playlist", description = "Deselect the currently selected playlist.")
    @PutMapping("/deselect")
    public ResponseEntity<Void> deselectPlaylist() {
        playlistService.deselectPlaylist();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Enable random play mode for a playlist", description = "Enable random play mode for a specific playlist.")
    @PutMapping("/{playlistId}/random/enable")
    public ResponseEntity<Void> enableRandomMode(
            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId) {
        playlistService.enableRandomMode(playlistId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Disable random play mode for a playlist", description = "Disable random play mode for a specific playlist.")
    @PutMapping("/{playlistId}/random/disable")
    public ResponseEntity<Void> disableRandomMode(
            @Parameter(description = "Playlist ID", required = true) @PathVariable Long playlistId) {
        playlistService.disableRandomMode(playlistId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Get the next song in the selected playlist", description = "Retrieve the next song in the selected playlist.")
    @GetMapping("/next")
    public ResponseEntity<Song> getNextSong() {
        return ResponseEntity.ok(playlistService.getNextSong().get(0));
    }

    @Operation(summary = "Get the previous song in the selected playlist", description = "Retrieve the previous song in the selected playlist.")
    @GetMapping("/previous")
    public ResponseEntity<Song> getPreviousSong() {
        return ResponseEntity.ok(playlistService.getPreviousSong().get(0));
    }

    @Operation(summary = "Audio loop mode for the selected playlist", description = "Toggle audio loop mode for the currently selected playlist.")
    @PutMapping("/audio-loop")
    public ResponseEntity<Void> audioLoop() {
        playlistService.toggleLoop();
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Toggle loop mode for the selected playlist", description = "Toggle loop mode for the currently selected playlist.")
    @PutMapping("/toggle-loop")
    public ResponseEntity<Void> toggleLoop() {
        playlistService.toggleLoop();
        return ResponseEntity.ok().build();
    }

    @PostMapping("/stop")
    public ResponseEntity<String> stopSong() {
        try {
            playlistService.stopSong();
            return ResponseEntity.ok("Song stopped successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error stopping song: " + e.getMessage());
        }
    }
}
