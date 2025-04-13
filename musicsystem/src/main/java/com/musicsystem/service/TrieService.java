package com.musicsystem.service;

import com.musicsystem.entity.Song;
import org.springframework.stereotype.Service;

import java.util.*;



@Service
public class TrieService {
    private TrieNode root = new TrieNode();

    public void insert(Song song) {
        String title = song.getTitle().toLowerCase();
        TrieNode current = root;
        for (char ch : title.toCharArray()) {
            current = current.children.computeIfAbsent(ch, c -> new TrieNode());
        }
        current.isEndOfWord = true;
        current.song = song;
    }

    public List<Song> search(String prefix) {
        TrieNode current = root;
        for (char ch : prefix.toLowerCase().toCharArray()) {
            current = current.children.get(ch);
            if (current == null) {
                return Collections.emptyList();
            }
        }
        return getAllSongs(current);
    }

    private List<Song> getAllSongs(TrieNode node) {
        List<Song> songs = new ArrayList<>();
        if (node.isEndOfWord) {
            songs.add(node.song);
        }
        for (TrieNode childNode : node.children.values()) {
            songs.addAll(getAllSongs(childNode));
        }
        return songs;
    }
}
