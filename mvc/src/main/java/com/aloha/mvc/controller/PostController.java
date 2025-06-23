package com.aloha.mvc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.aloha.mvc.dto.Posts;
import com.aloha.mvc.service.PostService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;





/**
 * /posts 요청 경로 매핑
 * [GET]        -/posts/list            : 목록
 * [GET]        -/posts/read/{no}       : 조회
 * 
 * [GET]        -/posts/create          : 등록 화면
 * [POST]       -/posts/create          : 등록 처리 (FORM)
 * [POST]       -/posts                 : 등록 처리 (JSON)
 * 
 * [GET]        -/posts/update          : 수정 화면
 * [POST]       -/posts/update          : 수정 처리 (FORM)
 * [PUT]        -/posts                 : 수정 처리 (JSON)
 * 
 * [POST]       -/posts/delete/{no}     : 삭제 처리 (FORM)
 * [DELETE]     -/posts/{no}            : 삭제 처리 (JSON)
 */
@Slf4j                      // 로그 어노테이션
@Controller                 // 컨트롤러 스프링 빈으로 등록
@RequestMapping("/posts")   // 클래스 레벨 요청 경로 매핑
public class PostController {

    // ⭐ MVC
    // Controller -> Service (데이터 요청)
    // Controller <- Service (데이터 전달)
    // Controller -> Model   (모델 등록)
    // View       <- Model   (데이터 출력)

    @Autowired          // 의존성 자동 주입
    private PostService postService;

    /**
     * 게시글 목록 화면
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/list")
    public String list(Model model) throws Exception {
        // 데이터 요청
        List<Posts> list = postService.list();
        // 모델 등록
        model.addAttribute("list", list);
        // 뷰 지정
        return "posts/list";    // resources/templates/board/list.html
    }
    
    /**
     * 게시글 조회 화면
     * @param no
     * @param model
     * @return
     * @throws Exception 
     */
    @GetMapping("/read/{no}")
    public String read(@PathVariable("no") Integer no, Model model) throws Exception {
        // 데이터 요청
        Posts post = postService.select(no);
        // 모델 등록
        model.addAttribute("post", post);
        // 뷰지정
        return "posts/read";
    }
    
    /**
     * 게시글 등록 화면
     * @return
     */
    @GetMapping("/create")
    public String create(@ModelAttribute(value = "post") Posts post) {
        return "posts/create";
    }

    /**
     * 게시글 등록 처리
     * @param post
     * @return
     * @throws Exception 
     * @Content-Type multipart/form-data
     */
    @PostMapping(path = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String createPostForm(Posts post) throws Exception {
        // 데이터 요청
        boolean result = postService.insert(post);
        // 리다이렉트
        // ✔ 데이터 처리 성공
        if(result)
            return "redirect:/posts/list";
        // 😢 데이터 처리 실패
        return "redirect:/posts/create?error=true";
    }
    
    /**
     * 게시글 등록 처리
     * @param post
     * @return
     * @throws Exception 
     * @Content-Type application/json
     */
    @PostMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPostJSON(@RequestBody Posts post) throws Exception {
        // 데이터 요청
        boolean result = postService.insert(post);
        // 리다이렉트
        // 데이터 처리 성공
        if(result)
            return new ResponseEntity<>("SUCCESS", HttpStatus.CREATED);
        // 데이터 처리 실패
        return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
    }

    /**
     * 게시글 수정 화면
     * @param no
     * @param model
     * @return
     * @throws Exception
     */
    @GetMapping("/update/{no}")
    public String update(@PathVariable("no") Integer no, Model model) throws Exception {
        // 데이터 요청
        Posts post = postService.select(no);
        // 모델 등록
        model.addAttribute("post",post);
        // 뷰 지정
        return "posts/update";
    }
    
    /**
     * 게시글 수정 처리
     * @param post
     * @return
     * @throws Exception 
     */
    @PostMapping("/update")
    public String updatePostForm(Posts post) throws Exception {
        // 데이터 요청
        boolean result = postService.update(post);
        // 리다이렉트
        // 데이터 처리 성공
        if(result)
            return "redirect:/posts/list";
        // 데이터 처리 실패
        return "redirect:/posts/update?error=true";
    }
    
    /**
     * 게시글 수정 처리
     * @param post
     * @return
     * @throws Exception 
     * @Content-Type application/json
     */
    @PutMapping(path = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePutJSON(@RequestBody Posts post) throws Exception {
        log.info("post : {}", post);
        // 데이터 요청
        boolean result = postService.update(post);
        // 리다이렉트
        // 데이터 처리 성공
        if(result)
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        // 데이터 처리 실패
        return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
    }
    
    /**
     * 게시글 삭제 처리
     * @param no
     * @return
     * @throws Exception 
     */
    @PostMapping("/delete/{no}")
    public String postDelete(@PathVariable("no") Integer no) throws Exception {
        // 데이터 요청
        boolean result = postService.delete(no);
        // 리다이렉트
        // 데이터 처리 성공
        if(result)
            return "redirect:/posts/list";
        // 데이터 처리 실패
        return "redirect:/posts/update?error=true";
    }
    
    /**
     * 게시글 삭제 처리
     * @param no
     * @return
     * @throws Exception 
     */
    @DeleteMapping("/{no}")
    public ResponseEntity<?> postDeleteJSON(@PathVariable("no") Integer no) throws Exception {
        // 데이터 요청
        boolean result = postService.delete(no);
        // 리다이렉트
        // 데이터 처리 성공
        if(result)
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        // 데이터 처리 실패
        return new ResponseEntity<>("FAIL", HttpStatus.BAD_REQUEST);
    }


    
    
}















