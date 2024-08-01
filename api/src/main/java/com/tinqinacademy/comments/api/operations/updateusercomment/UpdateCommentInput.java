package com.tinqinacademy.comments.api.operations.updateusercomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comments.api.base.OperationInput;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class UpdateCommentInput implements OperationInput {

    @JsonIgnore
    private String id;

    @NotNull
    @Size(min = 2, max = 150, message = "Comment content should be between 2 and 150 symbols.")
    private String content;
}
