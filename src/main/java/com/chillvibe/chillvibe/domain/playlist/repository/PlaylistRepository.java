package com.chillvibe.chillvibe.domain.playlist.repository;

import com.chillvibe.chillvibe.domain.playlist.entity.Playlist;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
  Page<Playlist> findByUserId(Long userId, Pageable pageable);
  List<Playlist> findByUserId(Long userId);
  // Playlist 엔티티의 posts 필드(List<Post>)에 있는 각 Post 객체의 id를 참조
  // 게시글마다, 플레이리스트가 하나이므로 Posts여도 실제로 단일 Post의 id와 매칭된다.
  Optional<Playlist> findByPostsId(Long postId);
}
