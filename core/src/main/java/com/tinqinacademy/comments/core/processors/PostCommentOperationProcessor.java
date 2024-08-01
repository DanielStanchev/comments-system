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

import java.util.UUID;

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
        super(validator, conversionService,errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorWrapper,PostCommentOutput> process(PostCommentInput input) {
        log.info("Start addRoomComment input {}",input);
        return validateInput(input).flatMap(validated->postComment(input));
    }

    private Either<ErrorWrapper,PostCommentOutput> postComment(PostCommentInput input) {
       return Try.of(()->{

        UUID roomMockId = UUID.fromString("45b0d31b-1d45-4e48-8e18-641574d2f406");
        UUID mockUserId = UUID.fromString("65b0d31b-1d45-4e48-8e18-641574d2f406");
        String mockUserFirstName = "Daniel";
        String mockUserLastName = "Stanchev";

        //mockedInput
        PostCommentInput mockInput = PostCommentInput.builder()
            .firstName(mockUserFirstName)
            .lastName(mockUserLastName)
            .content(input.getContent())
            .build();

        Comment commentToAdd = getConvertedCommentByInput(mockInput, mockUserId, roomMockId);

        commentRepository.save(commentToAdd);

        //getting to mockRoomId here
        PostCommentOutput result = PostCommentOutput.builder()
            .id(String.valueOf(roomMockId))
            .build();

        log.info("End addRoomComment output {}",result);
        return result;
            })
            .toEither()
            .mapLeft(throwable -> Match(throwable).of(
                Case($(instanceOf(NotFoundException.class)), errorMapper.handleError(throwable, HttpStatus.NOT_FOUND)),
                Case($(), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))));
    }

    private Comment getConvertedCommentByInput(PostCommentInput mockInput, UUID mockUserId, UUID roomMockId) {
        return conversionService.convert(mockInput, Comment.CommentBuilder.class)
            .lastEditedBy(String.valueOf(mockUserId))
            .commentRoomId(String.valueOf(roomMockId))
            .build();
    }
}
