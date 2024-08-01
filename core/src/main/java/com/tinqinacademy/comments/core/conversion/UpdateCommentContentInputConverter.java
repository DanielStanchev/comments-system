package com.tinqinacademy.comments.core.conversion;

import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentInput;
import com.tinqinacademy.comments.persistence.entity.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UpdateCommentContentInputConverter implements Converter<UpdateCommentInput, Comment.CommentBuilder> {

    @Override
    public Comment.CommentBuilder convert(UpdateCommentInput input) {
        return Comment.builder()
            .content(input.getContent());
    }
}
