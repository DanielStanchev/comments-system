package com.tinqinacademy.comments.core.conversion;

import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentInput;
import com.tinqinacademy.comments.core.base.BaseConverter;
import com.tinqinacademy.comments.persistence.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class EditCommentInputConverter extends BaseConverter<EditCommentInput, Comment.CommentBuilder> {

    @Override
    public Comment.CommentBuilder convertObject(EditCommentInput input) {
        return Comment.builder()
            .roomId(input.getRoomId())
            //.userId(input.getUserId())
            .content(input.getContent());
    }
}