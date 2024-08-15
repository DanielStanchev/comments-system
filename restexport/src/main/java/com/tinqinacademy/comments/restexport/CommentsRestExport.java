package com.tinqinacademy.comments.restexport;

import com.tinqinacademy.comments.api.operations.addcomment.AddCommentInput;
import com.tinqinacademy.comments.api.operations.addcomment.AddCommentOutput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentOutput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentInput;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentOutput;
import com.tinqinacademy.comments.api.restapiroutes.RestApiRoutes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "comments")
public interface CommentsRestExport {

    @GetMapping(RestApiRoutes.HOTEL_GET_COMMENTS)
    GetRoomCommentsOutput getComments(@PathVariable("roomId") String roomId);

    @PostMapping(RestApiRoutes.HOTEL_POST_COMMENT)
    AddCommentOutput addComment(@PathVariable("roomId") String roomId,
                                @RequestBody AddCommentInput addCommentInput);

    @PatchMapping(RestApiRoutes.HOTEL_UPDATE_COMMENT)
    UpdateCommentOutput updateCommentByUser(@PathVariable("commentId") String commentId,
                                            @RequestBody UpdateCommentInput updateCommentInput);

    @PutMapping(RestApiRoutes.SYSTEM_EDIT_COMMENT)
    EditCommentOutput editCommentByAdmin(@PathVariable("commentId") String commentId,
                                         @RequestBody EditCommentInput editCommentInput);

    @DeleteMapping(RestApiRoutes.SYSTEM_DELETE_COMMENT)
    DeleteCommentOutput deleteComment(@PathVariable("commentId") String commentId,
                                      @RequestBody DeleteCommentInput deleteCommentInput);

}
