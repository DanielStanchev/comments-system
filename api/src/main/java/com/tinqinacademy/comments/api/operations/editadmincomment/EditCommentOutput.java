package com.tinqinacademy.comments.api.operations.editadmincomment;

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
public class EditCommentOutput implements OperationOutput {
    private String id;
}
