package com.tinqinacademy.comments.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.comments.api.operations.addcomment.AddCommentInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentInput;
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

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY, connection = EmbeddedDatabaseConnection.H2)
public class HotelControllerTests {

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
            .publishDate(LocalDate.of(2024,12,12))
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
    void getCommentsOk() throws Exception {
        String mockRoomId = "566f33c6-a3f7-4b38-9a88-9e8729bfd723";

        GetRoomCommentsInput input = GetRoomCommentsInput.builder()
            .roomId(mockRoomId).build();

        String serializedInput = objectMapper.writeValueAsString(input);

        mvc.perform(get(RestApiRoutes.HOTEL_GET_COMMENTS,mockRoomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(jsonPath("$.comments").isArray())
            .andExpect(jsonPath("$.comments",hasSize(1)))
            .andExpect(status().isOk());
    }

    @Test
    void getCommentsNotFound() throws Exception {
        String mockRoomId = "566f33c6-a3f7-4b38-9a88-9e8729bfd723";
        String wrongRoomId = "776f33c6-a3f7-4b38-9a88-9e8729bfd723";

        GetRoomCommentsInput input = GetRoomCommentsInput.builder()
            .roomId(mockRoomId).build();

        String serializedInput = objectMapper.writeValueAsString(input);

        mvc.perform(get(RestApiRoutes.HOTEL_GET_COMMENTS,wrongRoomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isNotFound());
    }

    @Test
    void getCommentsBadRequest() throws Exception {
        String mockRoomId = "566f33c6-a3f7-4b38-9a88-9e8729bfd723";

        GetRoomCommentsInput input = GetRoomCommentsInput.builder()
            .roomId(mockRoomId).build();

        String serializedInput = objectMapper.writeValueAsString(input);

        mvc.perform(get(RestApiRoutes.HOTEL_GET_COMMENTS,"123"+mockRoomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isBadRequest());
    }

    @Test
    void addCommentCreated() throws Exception {
        String mockUserId = "756f33c6-a3f7-4b38-9a88-9e8729bfd723";
        String mockRoomId = "566f33c6-a3f7-4b38-9a88-9e8729bfd723";

        AddCommentInput input = AddCommentInput.builder()
            .roomId(mockRoomId)
            .userId(mockUserId)
            .content("Bad room!")
            .build();

        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(post(RestApiRoutes.HOTEL_POST_COMMENT,mockUserId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isCreated());
    }

    @Test
    void addCommentBadRequest() throws Exception {
        String mockUserId = "756f33c6-a3f7-4b38-9a88-9e8729bfd723";
        String mockRoomId = "776f33c6-a3f7-4b38-9a88-9e8729bfd723";

        AddCommentInput input = AddCommentInput.builder()
            .roomId(mockRoomId)
            .userId(mockUserId)
            .content("a")
            .build();

        String serializedInput = objectMapper.writeValueAsString(input);

        mvc.perform(post(RestApiRoutes.HOTEL_POST_COMMENT,mockRoomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isBadRequest());
    }

    @Test
    void addCommentNotFound() throws Exception {
        String mockUserId = "756f33c6-a3f7-4b38-9a88-9e8729bfd723";
        String mockRoomId = "566f33c6-a3f7-4b38-9a88-9e8729bfd723";

        AddCommentInput input = AddCommentInput.builder()
            .roomId(mockRoomId)
            .userId(mockUserId)
            .content("Bad room!")
            .build();

        String serializedInput = objectMapper.writeValueAsString(input);

        mvc.perform(post(RestApiRoutes.HOTEL_POST_COMMENT+"/wrongId",mockRoomId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isNotFound());
    }

    @Test
    void updateCommentByUserOK() throws Exception {
        String userId = commentRepository.findAll().get(0).getUserId();
        String commentId = commentRepository.findAll().get(0).getId().toString();

        UpdateCommentInput input = UpdateCommentInput.builder()
            .userId(userId)
            .commentId(commentId)
            .content("Wonderful room!")
            .build();

        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(patch(RestApiRoutes.HOTEL_UPDATE_COMMENT,commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isOk());
    }

    @Test
    void updateCommentByUserNotFound() throws Exception {
        String userId = commentRepository.findAll().get(0).getUserId();
        String commentId = commentRepository.findAll().get(0).getId().toString();

        UpdateCommentInput input = UpdateCommentInput.builder()
            .userId(userId)
            .commentId(commentId)
            .content("Wonderful room!")
            .build();

        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(patch(RestApiRoutes.HOTEL_UPDATE_COMMENT,"/wrong"+commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isNotFound());
    }

    @Test
    void updateCommentByUserBadRequest() throws Exception {
        String userId = commentRepository.findAll().get(0).getUserId();
        String commentId = commentRepository.findAll().get(0).getId().toString();

        UpdateCommentInput input = UpdateCommentInput.builder()
            .userId(userId)
            .commentId(commentId)
            .content("a")
            .build();

        String serializedInput = objectMapper.writeValueAsString(input);
        mvc.perform(patch(RestApiRoutes.HOTEL_UPDATE_COMMENT,commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(serializedInput))
            .andExpect(status().isBadRequest());
    }
}
