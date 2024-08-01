package com.tinqinacademy.comments.core.conversion;

import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentInput;
import com.tinqinacademy.comments.persistence.entity.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class EditCommentInputConverter implements Converter<EditCommentInput, Comment.CommentBuilder> {

    @Override
    public Comment.CommentBuilder convert(EditCommentInput input) {
        return Comment.builder()
            .firstName(input.getFirstName())
            .lastName(input.getLastName())
            .content(input.getContent());
    }
}