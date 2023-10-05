package com.suhwan.cowtalk.common.exception.model;

import com.suhwan.cowtalk.common.type.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ErrorResponse {

  private ErrorCode errorCode;
  private String errorMessage;
}
