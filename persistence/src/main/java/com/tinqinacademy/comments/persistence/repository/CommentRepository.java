package com.tinqinacademy.comments.persistence.repository;

import com.tinqinacademy.comments.persistence.entity.Comment;
import org.hibernate.annotations.Comments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

    @Query("SELECT c FROM Comment c WHERE c.roomId =:roomId")
    List<Comment> findCommentsByRoomId(@Param("roomId")String roomId);
}
