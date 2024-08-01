package com.tinqinacademy.comments.api.base;

import com.tinqinacademy.comments.api.exceptionmodel.ErrorWrapper;
import io.vavr.control.Either;

public interface OperationProcessor<O extends OperationOutput, I extends OperationInput> {
    Either<ErrorWrapper, O> process(I input);
}
