package com.tinqinacademy.comments.core.conversion;

import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentOutputInfo;
import com.tinqinacademy.comments.core.base.BaseConverter;
import com.tinqinacademy.comments.persistence.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class GetAllRoomCommentsOutputConverter extends BaseConverter<Comment, GetRoomCommentOutputInfo.GetRoomCommentOutputInfoBuilder> {
    @Override
    public GetRoomCommentOutputInfo.GetRoomCommentOutputInfoBuilder convertObject(Comment source) {

        return GetRoomCommentOutputInfo.builder()
            .id(String.valueOf(source.getId()))
            .userId(source.getUserId())
            .content(source.getContent())
            .publishDate(source.getPublishDate())
            .lastEditedBy(source.getLastEditedBy())
            .lastEditedDate(source.getLastEditedDate());
    }
}
