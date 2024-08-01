package com.tinqinacademy.comments.core.conversion;

import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentOutputInfo;
import com.tinqinacademy.comments.persistence.entity.Comment;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GetAllRoomCommentsOutputConverter implements Converter<Comment, GetRoomCommentOutputInfo.GetRoomCommentOutputInfoBuilder>{
    @Override
    public GetRoomCommentOutputInfo.GetRoomCommentOutputInfoBuilder convert(Comment source) {

        return GetRoomCommentOutputInfo.builder()
            .id(String.valueOf(source.getId()))
            .firstName(source.getFirstName())
            .lastName(source.getLastName())
            .content(source.getContent())
            .publishDate(source.getPublishDate())
            .lastEditedBy(source.getLastEditedBy())
            .lastEditedDate(source.getLastEditedDate());
    }
}
