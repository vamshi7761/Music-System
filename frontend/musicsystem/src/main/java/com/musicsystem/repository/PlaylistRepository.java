package com.musicsystem.repository;

import com.musicsystem.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Playlist findByName(String name);

	
}
