package com.musicsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.musicsystem.entity.Song;
import com.musicsystem.repository.SongRepository;

import jakarta.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TrieService {
	
	 @Autowired
	 private SongRepository songRepository;
   
	 private TrieNode root = new TrieNode();
	 
	 @PostConstruct
	    public void init() {
	        loadExistingSongs();
	    }

	    @Async
	    public void loadExistingSongs() {
	        List<Song> allSongs = songRepository.findAll();
	        for (Song song : allSongs) {
	            insert(song.getTitle());
	        }
	    }

    public void insert(String title) {
        title = title.toLowerCase();
        TrieNode current = root;
        for (char ch : title.toCharArray()) {
            current.getChildren().computeIfAbsent(ch, k -> new TrieNode());
            current = current.getChildren().get(ch);
        }
        current.setEndOfWord(true);
        current.setTitle(title);
    }

    public List<Song> search(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toLowerCase().toCharArray()) {
            current = current.getChildren().get(ch);
            if (current == null) {
                return Collections.emptyList();
            }
        }
        List<Song> results = new ArrayList<>();
        collectAllTitles(current, results);
        return results;
    }

    private void collectAllTitles(TrieNode node, List<Song> results) {
        if (node.isEndOfWord()) {
            results.add(songRepository.findByTitle(node.getTitle()));
        }
        for (Map.Entry<Character, TrieNode> entry : node.getChildren().entrySet()) {
            collectAllTitles(entry.getValue(), results);
        }
    }
}
