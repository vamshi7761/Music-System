package com.musicsystem.repository;

import com.musicsystem.entity.AudioFile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AudioFileRepository extends JpaRepository<AudioFile, Long> {
    Optional<AudioFile> findBySongId(Long songId);
}