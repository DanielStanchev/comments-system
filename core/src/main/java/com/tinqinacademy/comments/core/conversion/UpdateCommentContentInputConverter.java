package com.tinqinacademy.comments.core.conversion;

import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentInput;
import com.tinqinacademy.comments.core.base.BaseConverter;
import com.tinqinacademy.comments.persistence.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommentContentInputConverter extends BaseConverter<UpdateCommentInput, Comment.CommentBuilder> {

    @Override
    public Comment.CommentBuilder convertObject(UpdateCommentInput input) {
        return Comment.builder()
            .lastEditedBy(input.getUserId())
            .userId(input.getUserId())
            .content(input.getContent());
    }
}
