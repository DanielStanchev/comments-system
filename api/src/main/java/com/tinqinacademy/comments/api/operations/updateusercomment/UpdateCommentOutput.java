package com.tinqinacademy.comments.api.operations.updateusercomment;

import com.tinqinacademy.comments.api.base.OperationOutput;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UpdateCommentOutput implements OperationOutput {
    private String id;
}
