package com.tinqinacademy.comments.rest.controllers;

import com.tinqinacademy.comments.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteComment;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditComment;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentOutput;
import com.tinqinacademy.comments.api.restapiroutes.RestApiRoutes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Either;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "System API", description = "System related functionality.")
@RestController
public class SystemController extends BaseController{

private final EditComment editComment;
private final DeleteComment deleteComment;

    public SystemController(EditComment editComment, DeleteComment deleteComment) {
        this.editComment = editComment;
        this.deleteComment = deleteComment;
    }

    @Operation(summary = "Admin edit a comment.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Not found")})
    @PutMapping(RestApiRoutes.SYSTEM_EDIT_COMMENT)
    public ResponseEntity<?> editCommentByAdmin(@PathVariable("commentId") String commentId,
                                                @RequestBody EditCommentInput editCommentInput){
        EditCommentInput input = EditCommentInput.builder()
            .commentId(commentId)
            //.userId(editCommentInput.getUserId())
            .roomId(editCommentInput.getRoomId())
            .content(editCommentInput.getContent())
            .build();

        Either<ErrorWrapper,EditCommentOutput> output = editComment.process(input);
        return handleResult(output, HttpStatus.OK);
    }

    @Operation(summary = "Delete a comment")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "404", description = "Not found")})
    @DeleteMapping(RestApiRoutes.SYSTEM_DELETE_COMMENT)
    public ResponseEntity<?> deleteComment(@PathVariable("commentId") String commentId,
                                           @RequestBody DeleteCommentInput deleteCommentInput){
        DeleteCommentInput input = DeleteCommentInput
            .builder()
            .commentId(commentId)
            .build();

        Either<ErrorWrapper,DeleteCommentOutput> output = deleteComment.process(input);
        return handleResult(output,HttpStatus.OK);
    }
}
