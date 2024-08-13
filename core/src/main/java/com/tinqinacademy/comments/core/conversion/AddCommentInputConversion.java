package com.tinqinacademy.comments.core.conversion;
import com.tinqinacademy.comments.api.operations.postcomment.PostCommentInput;
import com.tinqinacademy.comments.persistence.entity.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class AddCommentInputConversion implements Converter<PostCommentInput, Comment.CommentBuilder>{

    @Override
    public Comment.CommentBuilder convert(PostCommentInput input) {
        return Comment.builder()
            .userId(input.getUserId())
            .content(input.getContent());
    }
}
