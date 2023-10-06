package com.suhwan.cowtalk.common.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

  // 시스템
  INTERNAL_SERVER_ERROR("내부 서버에 오류가 발생했습니다."),
  INVALID_REQUEST("잘못된 요청입니다."),

  // 토큰
  INVALID_REFRESH_TOKEN("찾을 수 없는 리프레쉬 토큰입니다."),

  // API
  API_CLIENT_ERROR("클라이언트 에러가 발생했습니다."),
  API_SERVER_ERROR("CoinGecko API 서버에서 에러가 발생했습니다."),

  // 거래소
  INVALID_EXCHANGE_NAME("찾을 수 없는 거래소 이름입니다."),
  INVALID_EXCHANGE_ID("찾을 수 없는 거래소 번호입니다."),
  ALREADY_EXIST_EXCHANGE("이미 존재하는 거래소입니다."),

  // 코인
  INVALID_COIN_ID("찾을 수 없는 코인 번호입니다."),
  ALREADY_DELETE_COIN_ID("이미 삭제된 코인 번호입니다."),

  // 회원
  INVALID_MEMBER_EMAIL("찾을 수 없는 회원 이메일입니다."),
  INVALID_MEMBER_ID("찾을 수 없는 회원 번호입니다."),
  USING_MEMBER_EMAIL("이미 사용중인 이메일입니다."),
  USING_MEMBER_NICKNAME("이미 사용중인 닉네임입니다."),
  NOT_REGISTERED_EMAIL("가입된 이메일이 아닙니다."),
  PASSWORD_MISMATCH("비밀번호가 일치하지 않습니다."),
  UNVERIFIED_MEMBER("인증된 회원이 아닙니다."),
  INVALID_MEMBER_UUID("찾을 수 없는 UUID입니다."),
  NICKNAME_CHANGE_UNAVAILABLE("닉네임을 아직 변경할 수 없습니다."),
  ALREADY_DELETE_MEMBER_ID("이미 삭제된 회원 번호입니다."),


  // 카테고리
  ALREADY_EXIST_CATEGORY("이미 존재하는 카테고리입니다."),
  INVALID_CATEGORY_ID("찾을 수 없는 카테고리 번호입니다."),
  ALREADY_DELETE_CATEGORY_ID("이미 삭제된 카테고리 번호입니다."),
  READ_ONLY_CATEGORY("읽기 전용 카테고리입니다."),

  // 게시글
  INVALID_POST_ID("찾을 수 없는 게시글 번호입니다."),
  CANNOT_GOOD_BAD_OWN_POST("자신이 작성한 게시글에 좋아요/싫어요를 누를 수 없습니다."),
  ALREADY_GOOD_BAD_POST("이미 좋아요 또는 싫어요를 누르셨습니다."),
  NOT_OWN_POST("본인이 작성한 게시글이 아닙니다."),
  ALREADY_DELETE_POST("이미 삭제된 게시글입니다."),

  // 댓글
  INVALID_COMMENT_ID("찾을 수 없는 댓글 번호입니다."),
  CANNOT_GOOD_BAD_OWN_COMMENT("자신이 작성한 댓글에 좋아요/싫어요를 누를 수 없습니다."),
  ALREADY_GOOD_BAD_COMMENT("이미 좋아요 또는 싫어요를 누르셨습니다."),
  NOT_OWN_COMMENT("본인이 작성한 댓글이 아닙니다."),
  ALREADY_DELETE_COMMENT("이미 삭제된 댓글입니다."),

  // 대댓글
  INVALID_REPLY_ID("찾을 수 없는 대댓글 번호입니다."),
  CANNOT_GOOD_BAD_OWN_REPLY("자신이 작성한 대댓글에 좋아요/싫어요를 누를 수 없습니다."),
  ALREADY_GOOD_BAD_REPLY("이미 좋아요 또는 싫어요를 누르셨습니다."),
  NOT_OWN_REPLY("본인이 작성한 대댓글이 아닙니다."),
  ALREADY_DELETE_REPLY("이미 삭제된 대댓글입니다."),
  ;

  private final String description;
}