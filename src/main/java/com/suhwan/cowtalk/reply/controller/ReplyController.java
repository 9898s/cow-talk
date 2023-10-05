package com.suhwan.cowtalk.reply.controller;

import com.suhwan.cowtalk.reply.model.ReplyDto;
import com.suhwan.cowtalk.reply.model.WriteReplyRequest;
import com.suhwan.cowtalk.reply.model.WriteReplyResponse;
import com.suhwan.cowtalk.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/reply")
@RestController
public class ReplyController {

  private final ReplyService replyService;

  @PostMapping
  public ResponseEntity<?> writeReply(@RequestBody WriteReplyRequest request) {
    ReplyDto replyDto = replyService.writeReply(request);

    return ResponseEntity.ok().body(WriteReplyResponse.from(replyDto));
  }
}
