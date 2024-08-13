package com.tinqinacademy.comments.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

@Entity
@Table(name = "comments")
public class Comment extends BaseEntity {

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "room_id",nullable = false)
    private String roomId;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "publish_date", nullable = false)
    @CreationTimestamp
    private LocalDate publishDate;

    @Column(name = "last_edited_date")
    @UpdateTimestamp
    private LocalDate lastEditedDate;

    // have to be the User id
    @Column(name = "last_edited_by")
    private String lastEditedBy;

}
