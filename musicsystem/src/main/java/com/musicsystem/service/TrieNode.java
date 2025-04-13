package com.musicsystem.service;

import java.util.HashMap;
import java.util.Map;

import com.musicsystem.entity.Song;

public class TrieNode {
    Map<Character, TrieNode> children = new HashMap<>();
    boolean isEndOfWord;
    Song song;
}
