package com.musicsystem.service;

import com.musicsystem.entity.Song;
import com.musicsystem.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private TrieService trieService;

    public Song addSong(Song song) {
        Song savedSong = songRepository.save(song);
        trieService.insert(savedSong.getTitle());
        return savedSong;
    }

    public List<Song> addSongs(List<Song> songs) {
        List<Song> savedSongs = songRepository.saveAll(songs);
        savedSongs.forEach(song -> trieService.insert(song.getTitle()));
        return savedSongs;
    }

    public List<Song> searchSongs(String title) {
        return trieService.search(title);
    }
}
