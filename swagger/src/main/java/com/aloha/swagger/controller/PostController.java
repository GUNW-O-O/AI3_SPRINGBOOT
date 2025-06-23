package com.aloha.swagger.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aloha.swagger.dto.Posts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/posts")
public class PostController {
    
    // sp-crud : CRUD 를 위한 모든 컨트롤러 메소드 자동완성
    
    @GetMapping()
    public ResponseEntity<?> getAll() {
        try {
            List<Posts> list = new ArrayList<>();
            Posts post1 = Posts.builder().no(1L)
                                        .title("t1")
                                        .writer("w1")
                                        .content("c1")
                                        .build();
            Posts post2 = Posts.builder().no(2L)
                                        .title("t2")
                                        .writer("w2")
                                        .content("c2")
                                        .build();
            Posts post3 = Posts.builder().no(3L)
                                        .title("t3")
                                        .writer("w3")
                                        .content("c3")
                                        .build();
            list.add(post1);
            list.add(post2);
            list.add(post3);
            return new ResponseEntity<>(list, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping("/{no}")
    public ResponseEntity<?> getOne(
        @Parameter(name ="no", description ="조회할 글 번호를 입력해주세요", required = true)
        @PathVariable("no") Long no) {
        try {
            Posts post = Posts.builder()
                            .no(1L)
                            .title("t1")
                            .content("c1")
                            .writer("w1")
                            .build();
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping()
    @Operation(summary = "POST 요청", description = "POST 방식 요청 테스트를 합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "데이터 등록 성공",
                    content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = Posts.class)
                                        )
                    )
    })
    public ResponseEntity<?> create(@RequestBody Posts post) {
        try {
            log.info("post : {}",post);
            post.setId( UUID.randomUUID().toString() );
            return new ResponseEntity<>(post, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Posts post) {
        try {
            log.info("post : {}",post);
            return new ResponseEntity<>("Update Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @DeleteMapping("/{no}")
    public ResponseEntity<?> destroy(@PathVariable("no") Long no) {
        try {
            log.info("no : {}",no);
            return new ResponseEntity<>("Destroy Result", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
