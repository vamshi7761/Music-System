package com.musicsystem.controller;

import com.musicsystem.entity.AudioFile;
import com.musicsystem.entity.Playlist;
import com.musicsystem.entity.Song;
import com.musicsystem.service.PlaylistService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistService playlistService;

    @PostMapping
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return playlistService.createPlaylist(playlist);
    }
    
    @GetMapping("/allplaylist")
    public List<Playlist> getAllPlaylists() {
        return playlistService.getAllPlaylists();
    }
    
    @GetMapping("/{playlistId}")
    public Playlist getPlaylist(@PathVariable Long playlistId) {
        return playlistService.getPlaylist(playlistId);
    }


    @PostMapping("/{playlistId}/songs/{songId}")
    public void addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.addSongToPlaylist(playlistId, songId);
    }

    @PostMapping("/{playlistId}/songs")
    public void addSongsToPlaylist(@PathVariable Long playlistId, @RequestBody List<Long> songIds) {
        playlistService.addSongsToPlaylist(playlistId, songIds);
    }

    @GetMapping("/current")
    public Song getCurrentSong() {
        return playlistService.getCurrentSong();
    }

    @GetMapping("/{playlistId}/select/{songIndex}")
    public AudioFile selectSong(@PathVariable Long playlistId, @PathVariable Long songIndex) {
        playlistService.selectPlaylist(playlistId);
        return playlistService.selectSong(playlistId,songIndex);
    }
    
    @PostMapping("/stop")
    public void stopSong() {
        playlistService.stopSong();
    }

    @PostMapping("/{playlistId}/select")
    public void selectPlaylist(@PathVariable Long playlistId) {
        playlistService.selectPlaylist(playlistId);
    }

    @PostMapping("/deselect")
    public void deselectPlaylist() {
        playlistService.deselectPlaylist();
    }

    @GetMapping("/next")
    public AudioFile getNextSong() {
        return playlistService.getNextSong();
    }

    @GetMapping("/previous")
    public AudioFile getPreviousSong() {
        return playlistService.getPreviousSong();
    }

    @PostMapping("/{playlistId}/random/enable")
    public void enableRandomMode(@PathVariable Long playlistId) {
        playlistService.enableRandomMode(playlistId);
    }

    @PostMapping("/{playlistId}/random/disable")
    public void disableRandomMode(@PathVariable Long playlistId) {
        playlistService.disableRandomMode(playlistId);
    }

    @PostMapping("/loop/toggle")
    public void toggleLoop() {
        playlistService.toggleLoop();
    }
}