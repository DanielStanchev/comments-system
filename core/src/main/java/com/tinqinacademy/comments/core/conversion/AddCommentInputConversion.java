package com.tinqinacademy.comments.core.conversion;

import com.tinqinacademy.comments.api.operations.addcomment.AddCommentInput;
import com.tinqinacademy.comments.core.base.BaseConverter;
import com.tinqinacademy.comments.persistence.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class AddCommentInputConversion extends BaseConverter<AddCommentInput, Comment.CommentBuilder> {

    @Override
    public Comment.CommentBuilder convertObject(AddCommentInput input) {
        return Comment.builder()
            .roomId(input.getRoomId())
            .userId(input.getUserId())
            .lastEditedBy(input.getUserId())
            .content(input.getContent());
    }

}
