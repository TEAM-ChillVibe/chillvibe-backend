package com.chillvibe.chillvibe.domain.comment.repository;

import com.chillvibe.chillvibe.domain.comment.entity.Comment;
import com.chillvibe.chillvibe.domain.post.entity.Post;
import com.chillvibe.chillvibe.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  List<Comment> findByPostOrderByCreatedAtDesc(Post post);

  List<Comment> findByUserOrderByCreatedAtDesc(User user);

}
