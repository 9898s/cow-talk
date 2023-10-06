package com.suhwan.cowtalk.common.exception;

import com.suhwan.cowtalk.common.type.ErrorCode;
import lombok.Getter;

@Getter
public class PostException extends RuntimeException {

  private final ErrorCode errorCode;
  private final String errorMessage;

  public PostException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getDescription();
  }
}

