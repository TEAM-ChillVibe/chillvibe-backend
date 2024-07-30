package com.chillvibe.chillvibe.domain.playlist.repository;

import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

}
