package com.musicsystem.service;

import com.musicsystem.entity.AudioFile;
import com.musicsystem.entity.Playlist;
import com.musicsystem.entity.Song;
import com.musicsystem.repository.PlaylistRepository;
import com.musicsystem.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Random;

//@Service
//public class PlaylistService {
//	private static final Logger logger = LoggerFactory.getLogger(PlaylistService.class);
//
//	@Autowired
//	private PlaylistRepository playlistRepository;
//
//	@Autowired
//	private SongRepository songRepository;
//
//	private Long selectedPlaylistId = null;
//	private Long selectedSongIndex = null;
//
//	public Playlist createPlaylist(Playlist playlist) {
//		return playlistRepository.save(playlist);
//	}
//
//	@Transactional
//	public void addSongToPlaylist(Long playlistId, Long songId) {
//		try {
//			Playlist playlist = playlistRepository.findById(playlistId)
//					.orElseThrow(() -> new RuntimeException("Playlist not found"));
//			Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song not found"));
//			if (!playlist.getSongs().contains(song)) {
//				playlist.getSongs().add(song);
//				playlistRepository.save(playlist);
//				logger.info("Added song [{}] to playlist [{}]", songId, playlistId);
//			} else {
//				logger.info("Song [{}] is already in playlist [{}]", songId, playlistId);
//			}
//		} catch (Exception e) {
//			logger.error("Error adding song [{}] to playlist [{}]: {}", songId, playlistId, e);
//			throw e;
//		}
//	}
//
//	@Transactional
//	public void addSongsToPlaylist(Long playlistId, List<Long> songIds) {
//		try {
//			Playlist playlist = playlistRepository.findById(playlistId)
//					.orElseThrow(() -> new RuntimeException("Playlist not found"));
//			for (Long songId : songIds) {
//				Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song not found"));
//				if (!playlist.getSongs().contains(song)) {
//					playlist.getSongs().add(song);
//					logger.info("Added song [{}] to playlist [{}]", songId, playlistId);
//				} else {
//					logger.info("Song [{}] is already in playlist [{}]", songId, playlistId);
//				}
//			}
//			playlistRepository.save(playlist);
//		} catch (Exception e) {
//			logger.error("Error adding songs to playlist [{}]: {}", playlistId, e);
//			throw e;
//		}
//	}
//
//	@Transactional
//	public Song getCurrentSong() {
//		if (selectedPlaylistId == null) {
//			throw new RuntimeException("No playlist selected");
//		}
//		if (selectedSongIndex == null) {
//			throw new RuntimeException("No song selected");
//		}
//		Playlist playlist = playlistRepository.findById(selectedPlaylistId)
//				.orElseThrow(() -> new RuntimeException("Playlist not found"));
//		if (playlist.getSongs().isEmpty()) {
//			throw new RuntimeException("Playlist is empty");
//		}
//		logger.info("Current song index [{}] for playlist [{}]", selectedSongIndex, selectedPlaylistId);
//		return playlist.getSongs().get(Math.toIntExact(selectedSongIndex));
//	}
//
//	@Transactional
//	public Song selectSong(Long songIndex) {
//		if (selectedPlaylistId == null) {
//			throw new RuntimeException("No playlist selected");
//		}
//		Playlist playlist = playlistRepository.findById(selectedPlaylistId)
//				.orElseThrow(() -> new RuntimeException("Playlist not found"));
//		if (playlist.getSongs().isEmpty()) {
//			throw new RuntimeException("Playlist is empty");
//		}
//		if (songIndex < 0 || songIndex >= playlist.getSongs().size()) {
//			throw new RuntimeException("Invalid song index");
//		}
//		selectedSongIndex = songIndex;
//		playlist.setCurrentSongIndex(songIndex);
//		playlistRepository.save(playlist);
//		logger.info("Selected song [{}] in playlist [{}]", songIndex, selectedPlaylistId);
//
//		return playlist.getSongs().get(Math.toIntExact(songIndex));
//	}
//	
//	// PlaylistService.java
//
//	@Transactional
//	public void stopSong() {
//	    if (selectedPlaylistId == null || selectedSongIndex == null) {
//	        throw new RuntimeException("No song is currently playing");
//	    }
//	   
//	    // Reset the selected song index
//	    selectedSongIndex = null;
//	    logger.info("Stopped song in playlist [{}]", selectedPlaylistId);
//	}
//
//
//	@Transactional
//	public void selectPlaylist(Long playlistId) {
//		try {
//			Playlist playlist = playlistRepository.findById(playlistId)
//					.orElseThrow(() -> new RuntimeException("Playlist not found"));
//			if (playlist.getSongs().isEmpty()) {
//				throw new RuntimeException("Playlist is empty");
//			}
//			selectedPlaylistId = playlistId;
//			selectedSongIndex = null; // Reset the selected song index when selecting a new playlist
//			logger.info("Selected playlist [{}]", playlistId);
//		} catch (Exception e) {
//			logger.error("Error selecting playlist [{}]: {}", playlistId, e);
//			throw e;
//		}
//	}
//
//	public void deselectPlaylist() {
//		logger.info("Deselected playlist [{}]", selectedPlaylistId);
//		selectedPlaylistId = null;
//		selectedSongIndex = null; // Also reset the selected song index
//	}
//
//	@Transactional
//	public List<Song> getNextSong() {
//		if (selectedPlaylistId == null) {
//			throw new RuntimeException("No playlist selected");
//		}
//		Playlist playlist = playlistRepository.findById(selectedPlaylistId)
//				.orElseThrow(() -> new RuntimeException("Playlist not found"));
//		if (playlist.getSongs().isEmpty()) {
//			throw new RuntimeException("Playlist is empty");
//		}
//		if (playlist.isRandomMode()) {
//			Random random = new Random();
//			int nextIndex = random.nextInt(playlist.getSongs().size());
//			selectedSongIndex = (long) nextIndex;
//		} else {
//			if (selectedSongIndex == null) {
//				selectedSongIndex = (long) 0;
//			} else if (selectedSongIndex + 1 < playlist.getSongs().size()) {
//				selectedSongIndex++;
//			} else {
//				selectedSongIndex = (long) 0; // Loop back to the start
//			}
//		}
//		playlist.setCurrentSongIndex(selectedSongIndex);
//		playlistRepository.save(playlist);
//		logger.info("Next song [{}] in playlist [{}]", selectedSongIndex, selectedPlaylistId);
//		return Collections.singletonList(playlist.getSongs().get(Math.toIntExact(selectedSongIndex)));
//	}
//
//	@Transactional
//	public List<Song> getPreviousSong() {
//		if (selectedPlaylistId == null) {
//			throw new RuntimeException("No playlist selected");
//		}
//		Playlist playlist = playlistRepository.findById(selectedPlaylistId)
//				.orElseThrow(() -> new RuntimeException("Playlist not found"));
//		if (playlist.getSongs().isEmpty()) {
//			throw new RuntimeException("Playlist is empty");
//		}
//		if (selectedSongIndex == null) {
//			selectedSongIndex = (long) 0;
//		} else if (selectedSongIndex - 1 >= 0) {
//			selectedSongIndex--;
//		} else {
//			selectedSongIndex = (long) (playlist.getSongs().size() - 1); // Loop back to the end
//		}
//		playlist.setCurrentSongIndex(selectedSongIndex);
//		playlistRepository.save(playlist);
//		logger.info("Previous song [{}] in playlist [{}]", selectedSongIndex, selectedPlaylistId);
//		return Collections.singletonList(playlist.getSongs().get(Math.toIntExact(selectedSongIndex)));
//	}
//
//	public void enableRandomMode(Long playlistId) {
//		Playlist playlist = playlistRepository.findById(playlistId)
//				.orElseThrow(() -> new RuntimeException("Playlist not found"));
//		playlist.setRandomMode(true);
//		playlistRepository.save(playlist);
//		logger.info("Enabled random mode for playlist [{}]", playlistId);
//	}
//
//	public void disableRandomMode(Long playlistId) {
//		Playlist playlist = playlistRepository.findById(playlistId)
//				.orElseThrow(() -> new RuntimeException("Playlist not found"));
//		playlist.setRandomMode(false);
//		playlistRepository.save(playlist);
//		logger.info("Disabled random mode for playlist [{}]", playlistId);
//	}
//
//	@Scheduled(fixedRate = 15000)
//	@Transactional
//	public void playNextSongAutomatically() {
//		if (selectedPlaylistId != null && selectedSongIndex != null) {
//			Playlist playlist = playlistRepository.findById(selectedPlaylistId)
//					.orElseThrow(() -> new RuntimeException("Playlist not found"));
//			if (!playlist.getSongs().isEmpty()) {
//				if (playlist.isLoopEnabled()) {
//					
//					playlist.setCurrentSongIndex(playlist.getCurrentSongIndex());
//					playlistRepository.save(playlist);
//					
//					
//					
//					Song s = selectSong(playlist.getCurrentSongIndex());
//					
//					// return; // Continue playing the current song
//				} else {
//					getNextSong();
//				}
//			}
//		}
//	}
//
//	@Transactional
//	public void toggleLoop() {
//		if (selectedPlaylistId == null) {
//			throw new RuntimeException("No playlist selected");
//		}
//		Playlist playlist = playlistRepository.findById(selectedPlaylistId)
//				.orElseThrow(() -> new RuntimeException("Playlist not found"));
//		boolean currentLoopStatus = playlist.isLoopEnabled();
//		playlist.setLoopEnabled(!currentLoopStatus);
//		playlistRepository.save(playlist);
//		logger.info("{} loop mode for playlist [{}]", currentLoopStatus ? "Disabled" : "Enabled", selectedPlaylistId);
//	}
//
//}
@Service
public class PlaylistService {
    private static final Logger logger = LoggerFactory.getLogger(PlaylistService.class);

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AudioFileService audioFileService;

    private Long selectedPlaylistId = null;
    private Long selectedSongIndex = null;

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    @Transactional
    public void addSongToPlaylist(Long playlistId, Long songId) {
        try {
            Playlist playlist = playlistRepository.findById(playlistId)
                    .orElseThrow(() -> new RuntimeException("Playlist not found"));
            Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song not found"));
            if (!playlist.getSongs().contains(song)) {
                playlist.getSongs().add(song);
                playlistRepository.save(playlist);
                logger.info("Added song [{}] to playlist [{}]", songId, playlistId);
            } else {
                logger.info("Song [{}] is already in playlist [{}]", songId, playlistId);
            }
        } catch (Exception e) {
            logger.error("Error adding song [{}] to playlist [{}]: {}", songId, playlistId, e);
            throw e;
        }
    }

    @Transactional
    public void addSongsToPlaylist(Long playlistId, List<Long> songIds) {
        try {
            Playlist playlist = playlistRepository.findById(playlistId)
                    .orElseThrow(() -> new RuntimeException("Playlist not found"));
            for (Long songId : songIds) {
                Song song = songRepository.findById(songId).orElseThrow(() -> new RuntimeException("Song not found"));
                if (!playlist.getSongs().contains(song)) {
                    playlist.getSongs().add(song);
                    logger.info("Added song [{}] to playlist [{}]", songId, playlistId);
                } else {
                    logger.info("Song [{}] is already in playlist [{}]", songId, playlistId);
                }
            }
            playlistRepository.save(playlist);
        } catch (Exception e) {
            logger.error("Error adding songs to playlist [{}]: {}", playlistId, e);
            throw e;
        }
    }

    @Transactional
    public Song getCurrentSong() {
        if (selectedPlaylistId == null) {
            throw new RuntimeException("No playlist selected");
        }
        if (selectedSongIndex == null) {
            throw new RuntimeException("No song selected");
        }
        Playlist playlist = playlistRepository.findById(selectedPlaylistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        if (playlist.getSongs().isEmpty()) {
            throw new RuntimeException("Playlist is empty");
        }
        logger.info("Current song index [{}] for playlist [{}]", selectedSongIndex, selectedPlaylistId);
        return playlist.getSongs().get(Math.toIntExact(selectedSongIndex));
    }

    @Transactional
    public AudioFile selectSong(Long songIndex) {
        if (selectedPlaylistId == null) {
            throw new RuntimeException("No playlist selected");
        }
        Playlist playlist = playlistRepository.findById(selectedPlaylistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        if (playlist.getSongs().isEmpty()) {
            throw new RuntimeException("Playlist is empty");
        }
        if (songIndex < 0 || songIndex >= playlist.getSongs().size()) {
            throw new RuntimeException("Invalid song index");
        }
        selectedSongIndex = songIndex;
        playlist.setCurrentSongIndex(songIndex);
        playlistRepository.save(playlist);
        logger.info("Selected song [{}] in playlist [{}]", songIndex, selectedPlaylistId);

        Song selectedSong = playlist.getSongs().get(Math.toIntExact(songIndex));
        AudioFile audioFile = audioFileService.getAudioFile(selectedSong.getId());
        
        return audioFile;
    }

    @Transactional
    public void stopSong() {
        if (selectedPlaylistId == null || selectedSongIndex == null) {
            throw new RuntimeException("No song is currently playing");
        }

        // Reset the selected song index
        selectedSongIndex = null;
        logger.info("Stopped song in playlist [{}]", selectedPlaylistId);
    }

    @Transactional
    public void selectPlaylist(Long playlistId) {
        try {
            Playlist playlist = playlistRepository.findById(playlistId)
                    .orElseThrow(() -> new RuntimeException("Playlist not found"));
            if (playlist.getSongs().isEmpty()) {
                throw new RuntimeException("Playlist is empty");
            }
            selectedPlaylistId = playlistId;
            selectedSongIndex = null; // Reset the selected song index when selecting a new playlist
            logger.info("Selected playlist [{}]", playlistId);
        } catch (Exception e) {
            logger.error("Error selecting playlist [{}]: {}", playlistId, e);
            throw e;
        }
    }

    public void deselectPlaylist() {
        logger.info("Deselected playlist [{}]", selectedPlaylistId);
        selectedPlaylistId = null;
        selectedSongIndex = null; // Also reset the selected song index
    }

    @Transactional
    public List<Song> getNextSong() {
        if (selectedPlaylistId == null) {
            throw new RuntimeException("No playlist selected");
        }
        Playlist playlist = playlistRepository.findById(selectedPlaylistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        if (playlist.getSongs().isEmpty()) {
            throw new RuntimeException("Playlist is empty");
        }
        if (playlist.isRandomMode()) {
            Random random = new Random();
            int nextIndex = random.nextInt(playlist.getSongs().size());
            selectedSongIndex = (long) nextIndex;
        } else {
            if (selectedSongIndex == null) {
                selectedSongIndex = (long) 0;
            } else if (selectedSongIndex + 1 < playlist.getSongs().size()) {
                selectedSongIndex++;
            } else {
                selectedSongIndex = (long) 0; // Loop back to the start
            }
        }
        playlist.setCurrentSongIndex(selectedSongIndex);
        playlistRepository.save(playlist);
        logger.info("Next song [{}] in playlist [{}]", selectedSongIndex, selectedPlaylistId);
        return Collections.singletonList(playlist.getSongs().get(Math.toIntExact(selectedSongIndex)));
    }

    @Transactional
    public List<Song> getPreviousSong() {
        if (selectedPlaylistId == null) {
            throw new RuntimeException("No playlist selected");
        }
        Playlist playlist = playlistRepository.findById(selectedPlaylistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        if (playlist.getSongs().isEmpty()) {
            throw new RuntimeException("Playlist is empty");
        }
        if (selectedSongIndex == null) {
            selectedSongIndex = (long) 0;
        } else if (selectedSongIndex - 1 >= 0) {
            selectedSongIndex--;
        } else {
            selectedSongIndex = (long) (playlist.getSongs().size() - 1); // Loop back to the end
        }
        playlist.setCurrentSongIndex(selectedSongIndex);
        playlistRepository.save(playlist);
        logger.info("Previous song [{}] in playlist [{}]", selectedSongIndex, selectedPlaylistId);
        return Collections.singletonList(playlist.getSongs().get(Math.toIntExact(selectedSongIndex)));
    }

    public void enableRandomMode(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        playlist.setRandomMode(true);
        playlistRepository.save(playlist);
        logger.info("Enabled random mode for playlist [{}]", playlistId);
    }

    public void disableRandomMode(Long playlistId) {
        Playlist playlist = playlistRepository.findById(playlistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        playlist.setRandomMode(false);
        playlistRepository.save(playlist);
        logger.info("Disabled random mode for playlist [{}]", playlistId);
    }

    @Scheduled(fixedRate = 15000)
    @Transactional
    public void playNextSongAutomatically() {
        if (selectedPlaylistId != null && selectedSongIndex != null) {
            Playlist playlist = playlistRepository.findById(selectedPlaylistId)
                    .orElseThrow(() -> new RuntimeException("Playlist not found"));
            if (!playlist.getSongs().isEmpty()) {
                if (playlist.isLoopEnabled()) {
                    
                    playlist.setCurrentSongIndex(playlist.getCurrentSongIndex());
                    playlistRepository.save(playlist);
                    
                    AudioFile s = selectSong(playlist.getCurrentSongIndex());
                    
                } else {
                    getNextSong();
                }
            }
        }
    }

    @Transactional
    public void toggleLoop() {
        if (selectedPlaylistId == null) {
            throw new RuntimeException("No playlist selected");
        }
        Playlist playlist = playlistRepository.findById(selectedPlaylistId)
                .orElseThrow(() -> new RuntimeException("Playlist not found"));
        boolean currentLoopStatus = playlist.isLoopEnabled();
        playlist.setLoopEnabled(!currentLoopStatus);
        playlistRepository.save(playlist);
        logger.info("{} loop mode for playlist [{}]", currentLoopStatus ? "Disabled" : "Enabled", selectedPlaylistId);
    }
}