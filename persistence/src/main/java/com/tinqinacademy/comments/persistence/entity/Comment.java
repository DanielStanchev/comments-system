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

    // have to be User firstName
    @Column(name = "first_name", nullable = false)
    private String firstName;
    //have to be User lastName
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(name = "publish_date", nullable = false)
    @CreationTimestamp
    private LocalDate publishDate;

    @Column(name = "last_edited_date")
    @UpdateTimestamp
    private LocalDate lastEditedDate;

    // have to be User
    @Column(name = "last_edited_by")
    private String lastEditedBy;

    //have to be relation with Room
    @Column(name = "comment_room_id")
    private String commentRoomId;
}
