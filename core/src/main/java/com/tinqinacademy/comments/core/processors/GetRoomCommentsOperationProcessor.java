package com.tinqinacademy.comments.core.processors;

import com.tinqinacademy.comments.api.exceptionmodel.ErrorWrapper;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomComment;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentOutputInfo;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsInput;
import com.tinqinacademy.comments.api.operations.getroomcomments.GetRoomCommentsOutput;
import com.tinqinacademy.comments.core.base.BaseOperationProcessor;
import com.tinqinacademy.comments.core.exception.ErrorMapper;
import com.tinqinacademy.comments.persistence.entity.Comment;
import com.tinqinacademy.comments.persistence.repository.CommentRepository;
import io.vavr.control.Either;
import io.vavr.control.Try;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static io.vavr.API.$;
import static io.vavr.API.Case;
import static io.vavr.API.Match;

@Slf4j
@Service
public class GetRoomCommentsOperationProcessor extends BaseOperationProcessor implements GetRoomComment {
    private final CommentRepository commentRepository;

    public GetRoomCommentsOperationProcessor(Validator validator, ConversionService conversionService,
                                             ErrorMapper errorMapper, CommentRepository commentRepository) {
        super(validator, conversionService,errorMapper);
        this.commentRepository = commentRepository;
    }

    @Override
    public Either<ErrorWrapper,GetRoomCommentsOutput> process (GetRoomCommentsInput input) {
        log.info("Start getAllRoomComments input {}",input);
        return validateInput(input).flatMap(validated-> editComment());
    }

    private Either<ErrorWrapper,GetRoomCommentsOutput> editComment() {
        return Try.of(()->{
        UUID roomMockId = UUID.fromString("45b0d31b-1d45-4e48-8e18-641574d2f406");
        List<Comment> allRoomComments = getRoomComments(roomMockId);
        List<GetRoomCommentOutputInfo> getRoomCommentOutputInfos = getRoomCommentsConvertedToOutput(allRoomComments);
        GetRoomCommentsOutput output = GetRoomCommentsOutput.builder()
            .comments(getRoomCommentOutputInfos)
            .build();
        log.info("End getAllRoomComments output {}",output);
        return output;
        }).toEither().mapLeft(throwable -> Match(throwable).of(
            Case($(), errorMapper.handleError(throwable, HttpStatus.BAD_REQUEST))
        ));
    }

    private List<GetRoomCommentOutputInfo> getRoomCommentsConvertedToOutput(List<Comment> allRoomComments) {
        return allRoomComments.stream()
            .map(comment -> conversionService.convert(comment, GetRoomCommentOutputInfo.GetRoomCommentOutputInfoBuilder.class).build())
            .toList();
    }

    private List<Comment> getRoomComments(UUID roomMockId) {
        return commentRepository.findCommentsByRoomId(String.valueOf(roomMockId));
    }
}
