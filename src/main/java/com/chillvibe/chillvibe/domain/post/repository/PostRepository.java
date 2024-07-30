package com.chillvibe.chillvibe.domain.post.repository;

import com.chillvibe.chillvibe.domain.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

//  List<Post> findByPostAndIsDeletedFalse(Post post);
//
//  Post findByUserIdAndIsDeletedFalse(Long id);
//
//  Post findByPlaylistAndIsDeletedFalse(Long id);
//
//  Page<Post> getByUserId(Long user_Id, Pageable pageable);
//

}