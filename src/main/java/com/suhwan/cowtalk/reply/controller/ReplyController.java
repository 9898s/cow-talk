package com.suhwan.cowtalk.reply.controller;

import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.reply.model.DeleteReplyResponse;
import com.suhwan.cowtalk.reply.model.ReplyDto;
import com.suhwan.cowtalk.reply.model.ReplyResponse;
import com.suhwan.cowtalk.reply.model.ReplyResponseList;
import com.suhwan.cowtalk.reply.model.UpdateReplyRequest;
import com.suhwan.cowtalk.reply.model.UpdateReplyResponse;
import com.suhwan.cowtalk.reply.model.WriteReplyRequest;
import com.suhwan.cowtalk.reply.model.WriteReplyResponse;
import com.suhwan.cowtalk.reply.model.goodbad.GoodBadReplyResponse;
import com.suhwan.cowtalk.reply.model.goodbad.ReplyGoodBadDto;
import com.suhwan.cowtalk.reply.service.ReplyApiService;
import com.suhwan.cowtalk.reply.service.ReplyGoodBadService;
import com.suhwan.cowtalk.reply.service.ReplyService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/reply")
@RestController
public class ReplyController {

  private final ReplyService replyService;
  private final ReplyGoodBadService replyGoodBadService;
  private final ReplyApiService replyApiService;

  @PostMapping
  public ResponseEntity<?> writeReply(@RequestBody WriteReplyRequest request) {
    ReplyDto replyDto = replyService.writeReply(request);

    return ResponseEntity.ok().body(WriteReplyResponse.from(replyDto));
  }

  @GetMapping
  public ResponseEntity<?> getCommentReply(@RequestParam Long commentId) {
    List<ReplyResponse> replyResponseList = replyApiService.getReplyResponseList(commentId);

    return ResponseEntity.ok()
        .body(ReplyResponseList.of(replyResponseList.size(), replyResponseList));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updateReply(@PathVariable Long id,
      @RequestBody UpdateReplyRequest request) {
    ReplyDto replyDto = replyService.updateReply(id, request);

    return ResponseEntity.ok().body(UpdateReplyResponse.from(replyDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteReply(@PathVariable Long id) {
    ReplyDto replyDto = replyService.deleteReply(id);

    return ResponseEntity.ok().body(DeleteReplyResponse.from(replyDto));
  }

  @PostMapping("/{id}/good")
  public ResponseEntity<?> goodReply(@PathVariable Long id) {
    ReplyGoodBadDto replyGoodBadDto = replyGoodBadService.goodBadReply(id, GoodBad.GOOD);

    return ResponseEntity.ok().body(GoodBadReplyResponse.from(replyGoodBadDto));
  }

  @PostMapping("/{id}/bad")
  public ResponseEntity<?> badComment(@PathVariable Long id) {
    ReplyGoodBadDto replyGoodBadDto = replyGoodBadService.goodBadReply(id, GoodBad.BAD);

    return ResponseEntity.ok().body(GoodBadReplyResponse.from(replyGoodBadDto));
  }
}
