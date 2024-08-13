package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.exceptionmodel.ErrorMessages;
import com.tinqinacademy.comments.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentInput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteCommentOutput;
import com.tinqinacademy.comments.api.operations.deletecomment.DeleteComment;
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
public class DeleteCommentOperationProcessor extends BaseOperationProcessor implements DeleteComment {

    private final CommentRepository commentRepository;


    public DeleteCommentOperationProcessor(Validator validator, ConversionService conversionService,
                                           ErrorMapper errorMapper, CommentRepository commentRepository) {
        super(validator, conversionService,errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorWrapper,DeleteCommentOutput> process(DeleteCommentInput input) {
        log.info("Start deleteRoomComment input {}", input);
        return validateInput(input).flatMap(validated->deleteComment(input));
    }

    private Either<ErrorWrapper,DeleteCommentOutput> deleteComment(DeleteCommentInput input) {
        return Try.of(()->{
        Comment commentToDelete = getComment(input);
        commentRepository.delete(commentToDelete);
        DeleteCommentOutput result = DeleteCommentOutput.builder().build();

        log.info("End deleteRoomComment output {}", result);
        return result;
        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(instanceOf(NotFoundException.class)), errorMapper.handleError(throwable, HttpStatus.NOT_FOUND)),
            Case($(), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }

    private Comment getComment(DeleteCommentInput input) {
        return commentRepository.findById(UUID.fromString(input.getCommentId()))
            .orElseThrow(()-> new NotFoundException(ErrorMessages.COMMENT_NOT_FOUND));
    }
}
