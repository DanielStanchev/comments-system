package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomComment;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.postcomment.PostComment;
import com.tinqinacademy.comments.api.operations.postcomment.PostCommentInput;
import com.tinqinacademy.comments.api.operations.postcomment.PostCommentOutput;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateComment;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentInput;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentOutput;
import com.tinqinacademy.comments.api.restapiroutes.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Hotel API", description = "Hotel room booking related functionality.")
@RestController
public class HotelController extends BaseController{


private final GetRoomComment getRoomComment;
private final PostComment postComment;
private final UpdateComment updateComment;

    public HotelController(GetRoomComment getRoomComment, PostComment postComment, UpdateComment updateComment) {
        this.getRoomComment = getRoomComment;
        this.postComment = postComment;
        this.updateComment = updateComment;
    }

    @Operation(summary = "Get comments", description = "Get all comments")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "404", description = "Not found")})
    @GetMapping(RestApiRoutes.HOTEL_GET_COMMENTS)
    public ResponseEntity<?> getComments(@PathVariable("roomId") String roomId){

        GetRoomCommentsInput input = GetRoomCommentsInput.builder()
            .id(roomId)
            .build();

        Either<ErrorWrapper,GetRoomCommentsOutput> output = getRoomComment.process(input);
        return handleResult(output, HttpStatus.OK);
    }

    @Operation(summary = "Post a comment.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "CREATED"),
        @ApiResponse(responseCode = "400", description = "Bad request")})
    @PostMapping(RestApiRoutes.HOTEL_POST_COMMENT)
    public ResponseEntity<?> postComment(@PathVariable("roomId") String roomId,
                                         @RequestBody PostCommentInput postCommentInput){

        PostCommentInput input = PostCommentInput.builder()
            .id(roomId)
            .content(postCommentInput.getContent())
            .firstName(postCommentInput.getFirstName())
            .lastName(postCommentInput.getLastName())
            .build();

        Either<ErrorWrapper,PostCommentOutput> output = postComment.process(input);
        return handleResult(output,HttpStatus.CREATED);
    }

    @Operation(summary = "User update a comment.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "OK"),
        @ApiResponse(responseCode = "400", description = "Bad request")})
    @PatchMapping(RestApiRoutes.HOTEL_UPDATE_COMMENT)
    public ResponseEntity<?> updateCommentByUser(@PathVariable("commentId") String commentId,
                                                 @RequestBody UpdateCommentInput updateCommentInput){
        UpdateCommentInput input = UpdateCommentInput.builder()
            .id(commentId)
            .content(updateCommentInput.getContent())
            .build();

        Either<ErrorWrapper,UpdateCommentOutput> output = updateComment.process(input);
        return handleResult(output,HttpStatus.OK);
    }
}
