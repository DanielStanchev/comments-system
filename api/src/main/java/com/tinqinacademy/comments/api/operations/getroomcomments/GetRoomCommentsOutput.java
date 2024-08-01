package com.tinqinacademy.comments.api.operations.getroomcomments;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class GetRoomCommentsOutput implements OperationOutput {

    private List<GetRoomCommentOutputInfo> comments;
}
