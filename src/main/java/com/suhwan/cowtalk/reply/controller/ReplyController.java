package com.suhwan.cowtalk.reply.controller;

import com.suhwan.cowtalk.reply.model.DeleteReplyResponse;
import com.suhwan.cowtalk.reply.model.ReplyDto;
import com.suhwan.cowtalk.reply.model.UpdateReplyRequest;
import com.suhwan.cowtalk.reply.model.UpdateReplyResponse;
import com.suhwan.cowtalk.reply.model.WriteReplyRequest;
import com.suhwan.cowtalk.reply.model.WriteReplyResponse;
import com.suhwan.cowtalk.reply.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
}
