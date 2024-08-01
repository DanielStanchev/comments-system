package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.exceptionmodel.ErrorMessages;
import com.tinqinacademy.comments.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditComment;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentInput;
import com.tinqinacademy.comments.api.operations.editadmincomment.EditCommentOutput;
import com.tinqinacademy.comments.core.base.BaseOperationProcessor;
import com.tinqinacademy.comments.core.exception.ErrorMapper;
import com.tinqinacademy.comments.core.exception.exceptions.NotFoundException;
import com.tinqinacademy.comments.persistence.entity.Comment;
import com.tinqinacademy.comments.persistence.repository.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class EditCommentOperationProcessor extends BaseOperationProcessor implements EditComment{
    private final CommentRepository commentRepository;

    public EditCommentOperationProcessor(ConversionService conversionService, Validator validator,
                                         ErrorMapper errorMapper, CommentRepository commentRepository) {
        super(validator, conversionService,errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorWrapper,EditCommentOutput> process(EditCommentInput input) {
        log.info("Start EditRoomComment input {}", input);
        return validateInput(input).flatMap(validated->editComment(input));
    }

    private Either<ErrorWrapper,EditCommentOutput> editComment(EditCommentInput input) {
        return Try.of(()->{
        Comment commentToEdit = getComment(input);
        Comment editedComment = getConvertedCommentToInput(input, commentToEdit);
        commentRepository.save(editedComment);

        EditCommentOutput result = EditCommentOutput
            .builder()
            .id(input.getId())
            .build();

        log.info("End editRoomComment output {}", result);
        return result;
        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(NotFoundException.class)), errorMapper.handleError(throwable, HttpStatus.NOT_FOUND)),
            Case($(), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }

    private Comment getConvertedCommentToInput(EditCommentInput input, Comment commentToEdit) {
        Comment editedComment = conversionService.convert(input, Comment.CommentBuilder.class)
            .commentRoomId(commentToEdit.getCommentRoomId())
            .lastEditedBy(commentToEdit.getLastEditedBy())
            .lastEditedDate(commentToEdit.getLastEditedDate())
            .publishDate(commentToEdit.getPublishDate())
            .build();
        editedComment.setId(commentToEdit.getId());
        return editedComment;
    }

    private Comment getComment(EditCommentInput input) {
        //I do not use the RoomNo in the query because id is unique and sufficient
        // admin shouldn't be able to alter the roomNo from here either so is useless
        return commentRepository.findById(UUID.fromString(input.getId()))
            .orElseThrow(()-> new NotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
    }
}
