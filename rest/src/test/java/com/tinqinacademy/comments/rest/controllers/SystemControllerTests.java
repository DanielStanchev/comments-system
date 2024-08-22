package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentInput;
import com.tinqinacademy.comments.api.restapiroutes.RestApiRoutes;
import com.tinqinacademy.comments.persistence.entity.Comment;
import com.tinqinacademy.comments.persistence.repository.CommentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = EmbeddedDatabaseConnection.H2)
public class SystemControllerTests {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CommentRepository commentRepository;

    @BeforeEach
    public void setup() {

        String mockUserId = "756f33c6-a3f7-4b38-9a88-9e8729bfd723";
        String mockRoomId = "566f33c6-a3f7-4b38-9a88-9e8729bfd723";

        Comment comment = Comment.builder()
            .userId(mockUserId)
            .roomId(mockRoomId)
            .content("Good room!")
            .publishDate(LocalDate.of(2024, 12, 12))
            .lastEditedDate(LocalDate.of(2024,12,15))
            .lastEditedBy(mockUserId)
            .build();

        commentRepository.save(comment);
    }

    @AfterEach
    public void afterEach() {
        commentRepository.deleteAll();
    }

    @Test
    void editCommentByAdminOk() throws Exception {
        String commentId = commentRepository.findAll().get(0).getId().toString();
        String roomId = commentRepository.findAll().get(0).getRoomId();

        EditCommentInput input = EditCommentInput.builder()
            .commentId(commentId)
            .roomId(roomId)
            .content("Admin edited this comment")
            .build();

        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(put(RestApiRoutes.SYSTEM_EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isOk());
    }

    @Test
    void editCommentByAdminNotFound() throws Exception {
        String commentId = commentRepository.findAll().get(0).getId().toString();
        String roomId = commentRepository.findAll().get(0).getRoomId();

        EditCommentInput input = EditCommentInput.builder()
            .commentId(commentId)
            .roomId(roomId)
            .content("Admin edited this comment")
            .build();

        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(put(RestApiRoutes.SYSTEM_EDIT_COMMENT, "/wrong"+commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isNotFound());
    }

    @Test
    void editCommentByAdminBadRequest() throws Exception {
        String commentId = commentRepository.findAll().get(0).getId().toString();
        String roomId = commentRepository.findAll().get(0).getRoomId();

        EditCommentInput input = EditCommentInput.builder()
            .commentId(commentId)
            .roomId(roomId)
            .content("1")
            .build();

        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(put(RestApiRoutes.SYSTEM_EDIT_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isBadRequest());
    }

    @Test
    void deleteCommentOk() throws Exception {
        String commentId = commentRepository.findAll().get(0).getId().toString();
        DeleteCommentInput input = DeleteCommentInput.builder()
            .commentId(commentId)
            .build();
        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(delete(RestApiRoutes.SYSTEM_DELETE_COMMENT, commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isOk());
    }

    @Test
    void deleteCommentNotFound() throws Exception {
        String commentId = commentRepository.findAll().get(0).getId().toString();
        DeleteCommentInput input = DeleteCommentInput.builder()
            .commentId(commentId)
            .build();
        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(delete(RestApiRoutes.SYSTEM_DELETE_COMMENT, "/wrong"+commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isNotFound());
    }

    @Test
    void deleteCommentBadRequest() throws Exception {
        String commentId = commentRepository.findAll().get(0).getId().toString();
        DeleteCommentInput input = DeleteCommentInput.builder()
            .commentId(commentId)
            .build();
        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(delete(RestApiRoutes.SYSTEM_DELETE_COMMENT, "111"+commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isBadRequest());
    }
}
