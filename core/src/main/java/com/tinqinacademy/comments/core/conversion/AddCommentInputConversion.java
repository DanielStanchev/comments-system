package com.tinqinacademy.comments.core.conversion;
import com.tinqinacademy.comments.api.operations.addcomment.AddCommentInput;
import com.tinqinacademy.comments.persistence.entity.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddCommentInputConversion implements Converter<AddCommentInput, Comment.CommentBuilder>{

    @Override
    public Comment.CommentBuilder convert(AddCommentInput input) {
        return Comment.builder()
            .roomId(input.getRoomId())
            .userId(input.getUserId())
            .lastEditedBy(input.getUserId())
            .content(input.getContent());
    }
}
