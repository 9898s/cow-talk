package com.suhwan.cowtalk.post.controller;

import com.suhwan.cowtalk.common.type.GoodBad;
import com.suhwan.cowtalk.post.model.DeletePostResponse;
import com.suhwan.cowtalk.post.model.PagePostResponse;
import com.suhwan.cowtalk.post.model.PostDto;
import com.suhwan.cowtalk.post.model.PostResponse;
import com.suhwan.cowtalk.post.model.UpdatePostRequest;
import com.suhwan.cowtalk.post.model.UpdatePostResponse;
import com.suhwan.cowtalk.post.model.WritePostRequest;
import com.suhwan.cowtalk.post.model.WritePostResponse;
import com.suhwan.cowtalk.post.model.goodbad.GoodBadPostResponse;
import com.suhwan.cowtalk.post.model.goodbad.PostGoodBadDto;
import com.suhwan.cowtalk.post.service.PostGoodBadService;
import com.suhwan.cowtalk.post.service.PostService;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
@RequestMapping("/api/post")
@RestController
public class PostController {

  private final PostService postService;
  private final PostGoodBadService postGoodBadService;

  @PostMapping
  public ResponseEntity<?> writePost(@RequestBody WritePostRequest request) {
    PostDto postDto = postService.writePost(request);

    return ResponseEntity.ok().body(WritePostResponse.from(postDto));
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> getPost(@PathVariable Long id, HttpServletRequest request) {
    PostDto postDto = postService.getPost(id, request);

    Long goodCount = postGoodBadService.countGoodBad(id, GoodBad.GOOD);
    Long badCount = postGoodBadService.countGoodBad(id, GoodBad.BAD);

    return ResponseEntity.ok().body(PostResponse.from(postDto, goodCount, badCount));
  }

  @GetMapping("/all")
  public ResponseEntity<?> getAllPost(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    List<PostDto> postDtoList = postService.getAllPost(page, size);
    List<PostResponse> postResponseList = new ArrayList<>();

    for (PostDto postDto : postDtoList) {
      Long goodCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.GOOD);
      Long badCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.BAD);

      postResponseList.add(PostResponse.from(postDto, goodCount, badCount));
    }

    return ResponseEntity.ok().body(
        PagePostResponse.of(postResponseList.size(), page, size, postResponseList));
  }

  @PutMapping("/{id}")
  public ResponseEntity<?> updatePost(@PathVariable Long id,
      @RequestBody UpdatePostRequest request) {
    PostDto postDto = postService.updatePost(id, request);

    return ResponseEntity.ok().body(UpdatePostResponse.from(postDto));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deletePost(@PathVariable Long id) {
    PostDto postDto = postService.deletePost(id);

    return ResponseEntity.ok().body(DeletePostResponse.from(postDto));
  }

  @PostMapping("/{id}/good")
  public ResponseEntity<?> goodPost(@PathVariable Long id) {
    PostGoodBadDto postGoodBadDto = postGoodBadService.goodBadPost(id, GoodBad.GOOD);

    return ResponseEntity.ok().body(GoodBadPostResponse.from(postGoodBadDto));
  }

  @PostMapping("/{id}/bad")
  public ResponseEntity<?> badPost(@PathVariable Long id) {
    PostGoodBadDto postGoodBadDto = postGoodBadService.goodBadPost(id, GoodBad.BAD);

    return ResponseEntity.ok().body(GoodBadPostResponse.from(postGoodBadDto));
  }

  @GetMapping("/hot/today")
  public ResponseEntity<?> hotTodayPost(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {

    LocalDateTime startDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT);
    LocalDateTime endDateTime = startDateTime.plusDays(1).minusNanos(1);

    List<PostDto> postDtoList = postService.hotPost(page, size, startDateTime, endDateTime);
    List<PostResponse> postResponseList = new ArrayList<>();

    for (PostDto postDto : postDtoList) {
      Long goodCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.GOOD);
      Long badCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.BAD);

      postResponseList.add(PostResponse.from(postDto, goodCount, badCount));
    }

    return ResponseEntity.ok().body(
        PagePostResponse.of(postResponseList.size(), page, size, postResponseList));
  }

  @GetMapping("/hot/week")
  public ResponseEntity<?> hotWeekPost(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {

    LocalDateTime now = LocalDateTime.now();

    // 이번 주의 시작 날짜(월요일)
    LocalDateTime startDateTime = now.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
        .withHour(0).withMinute(0).withSecond(0);

    // 이번 주의 끝 날짜(일요일)
    LocalDateTime endDateTime = now.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
        .withHour(23).withMinute(59).withSecond(59);

    List<PostDto> postDtoList = postService.hotPost(page, size, startDateTime, endDateTime);
    List<PostResponse> postResponseList = new ArrayList<>();

    for (PostDto postDto : postDtoList) {
      Long goodCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.GOOD);
      Long badCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.BAD);

      postResponseList.add(PostResponse.from(postDto, goodCount, badCount));
    }

    return ResponseEntity.ok().body(
        PagePostResponse.of(postResponseList.size(), page, size, postResponseList));
  }

  @GetMapping("/hot/month")
  public ResponseEntity<?> hotMonthPost(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {

    LocalDateTime now = LocalDateTime.now();

    // 이번 달의 시작 날짜와 시간(1일 00:00:00)
    LocalDateTime startDateTime = LocalDateTime.of(now.toLocalDate().withDayOfMonth(1),
        LocalTime.MIN);

    // 이번 달의 마지막 날짜와 시간(마지막일 23:59:59)
    LocalDateTime endDateTime = LocalDateTime.of(
        now.toLocalDate().with(TemporalAdjusters.lastDayOfMonth()), LocalTime.MAX);

    List<PostDto> postDtoList = postService.hotPost(page, size, startDateTime, endDateTime);
    List<PostResponse> postResponseList = new ArrayList<>();

    for (PostDto postDto : postDtoList) {
      Long goodCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.GOOD);
      Long badCount = postGoodBadService.countGoodBad(postDto.getId(), GoodBad.BAD);

      postResponseList.add(PostResponse.from(postDto, goodCount, badCount));
    }

    return ResponseEntity.ok().body(
        PagePostResponse.of(postResponseList.size(), page, size, postResponseList));
  }
}
