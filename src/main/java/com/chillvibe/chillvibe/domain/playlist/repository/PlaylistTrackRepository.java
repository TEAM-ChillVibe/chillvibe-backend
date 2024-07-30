package com.chillvibe.chillvibe.domain.playlist.repository;

import com.chillvibe.chillvibe.domain.playlist.entity.PlaylistTrack;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {
  Optional<PlaylistTrack> findByPlaylistIdAndId(Long playlistId, Long id);
}
