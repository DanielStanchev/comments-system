package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.exceptionmodel.ErrorMessages;
import com.tinqinacademy.comments.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateComment;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentInput;
import com.tinqinacademy.comments.api.operations.updateusercomment.UpdateCommentOutput;
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
public class UpdateCommentOperationProcessor extends BaseOperationProcessor implements UpdateComment {
    private final CommentRepository commentRepository;

    public UpdateCommentOperationProcessor(Validator validator, ConversionService conversionService, ErrorMapper errorMapper,
                                           CommentRepository commentRepository) {
        super(validator, conversionService, errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorWrapper, UpdateCommentOutput> process(UpdateCommentInput input) {
        log.info("Start updateRoomComment input {}", input);
        return validateInput(input).flatMap(validated -> updateComment(input));
    }

    private Either<ErrorWrapper, UpdateCommentOutput> updateComment(UpdateCommentInput input) {
        return Try.of(() -> {
                Comment commentToUpdate = getComment(input);
                Comment updatedComment = getConvertedCommentByInput(input, commentToUpdate);
                Comment savedComment = commentRepository.save(updatedComment);
                UpdateCommentOutput result = UpdateCommentOutput.builder()
                    .id(String.valueOf(savedComment.getId()))
                    .build();
                log.info("End updateRoomComment output {}", result);
                return result;

            })
            .toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(NotFoundException.class)), errorMapper.handleError(throwable, HttpStatus.NOT_FOUND)),
                Case($(), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))));
    }

    private Comment getConvertedCommentByInput(UpdateCommentInput input, Comment commentToUpdate) {
        Comment updatedComment = conversionService.convert(input, Comment.CommentBuilder.class)
            .publishDate(commentToUpdate.getPublishDate())
            .roomId(commentToUpdate.getRoomId())
            .build();
        updatedComment.setId(commentToUpdate.getId());
        return updatedComment;
    }

    private Comment getComment(UpdateCommentInput input) {
        return commentRepository.findById(UUID.fromString(input.getCommentId()))
            .orElseThrow(() -> new NotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
    }
}
