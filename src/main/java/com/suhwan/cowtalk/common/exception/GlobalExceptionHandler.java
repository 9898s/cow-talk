package com.suhwan.cowtalk.common.exception;

import static com.suhwan.cowtalk.common.type.ErrorCode.INTERNAL_SERVER_ERROR;
import static com.suhwan.cowtalk.common.type.ErrorCode.INVALID_REQUEST;

import com.suhwan.cowtalk.common.exception.model.ErrorResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ApiException.class)
  public ResponseEntity<?> apiExceptionHandler(ApiException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ExchangeException.class)
  public ResponseEntity<?> exchangeExceptionHandler(ExchangeException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CoinException.class)
  public ResponseEntity<?> coinExceptionHandler(CoinException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MemberException.class)
  public ResponseEntity<?> memberExceptionHandler(MemberException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CategoryException.class)
  public ResponseEntity<?> categoryExceptionHandler(CategoryException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(PostException.class)
  public ResponseEntity<?> postExceptionHandler(PostException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(CommentException.class)
  public ResponseEntity<?> commentExceptionHandler(CommentException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ReplyException.class)
  public ResponseEntity<?> replyExceptionHandler(ReplyException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(TokenException.class)
  public ResponseEntity<?> tokenExceptionHandler(TokenException exception) {
    ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(),
        exception.getErrorMessage());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<?> handleDataIntegrityViolationException() {
    ErrorResponse errorResponse = new ErrorResponse(INVALID_REQUEST,
        INVALID_REQUEST.getDescription());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> exceptionHandler() {
    ErrorResponse errorResponse = new ErrorResponse(INTERNAL_SERVER_ERROR,
        INTERNAL_SERVER_ERROR.getDescription());

    return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
  }
}
