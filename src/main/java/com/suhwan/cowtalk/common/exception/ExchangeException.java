package com.suhwan.cowtalk.common.exception;

import com.suhwan.cowtalk.common.type.ErrorCode;
import lombok.Getter;

@Getter
public class ExchangeException extends RuntimeException {

  private final ErrorCode errorCode;
  private final String errorMessage;

  public ExchangeException(ErrorCode errorCode) {
    this.errorCode = errorCode;
    this.errorMessage = errorCode.getDescription();
  }
}

