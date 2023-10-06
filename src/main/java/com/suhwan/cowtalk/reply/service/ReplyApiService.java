package com.suhwan.cowtalk.reply.service;

import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.reply.model.ReplyDto;
import com.suhwan.cowtalk.reply.model.ReplyResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReplyApiService {

  private final ReplyService replyService;
  private final ReplyGoodBadService replyGoodBadService;

  public List<ReplyResponse> getReplyResponseList(Long commentId) {
    List<ReplyDto> replyDtoList = replyService.getCommentReply(commentId);

    return replyDtoList.stream()
        .map(replyDto -> {
          Long goodCount = replyGoodBadService.countGoodBad(replyDto.getId(), GoodBad.GOOD);
          Long badCount = replyGoodBadService.countGoodBad(replyDto.getId(), GoodBad.BAD);
          return ReplyResponse.from(replyDto, goodCount, badCount);
        })
        .collect(Collectors.toList());
  }
}
