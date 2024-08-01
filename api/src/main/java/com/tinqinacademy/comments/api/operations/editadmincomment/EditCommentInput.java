package com.tinqinacademy.comments.api.operations.editadmincomment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tinqinacademy.comments.api.base.OperationInput;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
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
public class EditCommentInput implements OperationInput {

    @JsonIgnore
    private String id;

    @JsonIgnore
    @Size(min = 1,max = 5,message = "Room number should be between 1 and 3 characters.")
    private String roomNo;

    @NotNull
    @Size(min = 2,max = 15,message = "First name should be between 2 and 15 letters.")
    private String firstName;

    @NotNull
    @Size(min = 2,max = 15,message = "Last name should be between 2 and 15 letters.")
    private String lastName;

    @NotNull
    @Size(min = 2,max = 150,message = "Comment content should be between 2 and 150 symbols.")
    private String content;
}
