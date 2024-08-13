package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.comments.api.operations.postcomment.PostComment;
import com.tinqinacademy.comments.api.operations.postcomment.PostCommentInput;
import com.tinqinacademy.comments.api.operations.postcomment.PostCommentOutput;
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

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;
import static io.vavr.Predicates.instanceOf;

@Slf4j
@Service
public class PostCommentOperationProcessor extends BaseOperationProcessor implements PostComment {
    private final CommentRepository commentRepository;

    public PostCommentOperationProcessor(Validator validator, ConversionService conversionService, ErrorMapper errorMapper,
                                         CommentRepository commentRepository) {
        super(validator, conversionService, errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorWrapper, PostCommentOutput> process(PostCommentInput input) {
        log.info("Start addRoomComment input {}", input);
        return validateInput(input).flatMap(validated -> postComment(input));
    }

    private Either<ErrorWrapper, PostCommentOutput> postComment(PostCommentInput input) {
        return Try.of(() -> {

                Comment commentToAdd = conversionService.convert(input, Comment.CommentBuilder.class)
                    .lastEditedBy(input.getUserId())
                    .build();

                Comment savedComment = commentRepository.save(commentToAdd);

                PostCommentOutput result = PostCommentOutput.builder()
                    .id(String.valueOf(savedComment.getId()))
                    .build();

                log.info("End addRoomComment output {}", result);
                return result;

            })
            .toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(NotFoundException.class)), errorMapper.handleError(throwable, HttpStatus.NOT_FOUND)),
                Case($(), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))));
    }
}
