package com.musicsystem.entity;

import jakarta.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Playlist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public Long getId() {
		return id;
	}

	private String name;

    @ManyToMany
    @JoinTable(
        name = "playlist_songs",
        joinColumns = @JoinColumn(name = "playlist_id"),
        inverseJoinColumns = @JoinColumn(name = "song_id") // Correct column name
    )
    private List<Song> songs = new LinkedList<>();
    
    private Long currentSongIndex = (long) 0;
    
    private boolean isRandomMode = false;
    
    private boolean isLoopEnabled = false;
    
    
    
    // Getters and Setters
    public boolean isLoopEnabled() {
        return isLoopEnabled;
    }

    public void setLoopEnabled(boolean loopEnabled) {
        isLoopEnabled = loopEnabled;
    }

	public boolean isRandomMode() {
		return isRandomMode;
	}

	public void setRandomMode(boolean isRandomMode) {
		this.isRandomMode = isRandomMode;
	}

	public Long getCurrentSongIndex() {
		return currentSongIndex;
	}

	public void setCurrentSongIndex(Long songIndex) {
		this.currentSongIndex = songIndex;
	}

	
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}

    
    
}
