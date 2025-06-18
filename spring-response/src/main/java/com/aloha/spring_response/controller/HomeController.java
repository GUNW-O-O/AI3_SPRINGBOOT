package com.aloha.spring_response.controller;

import org.springframework.stereotype.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 *  @Controller
 *  Spring mvc 의 HTTP 요청에 응답하난 컨트롤러를 지정하는 어노테이션
 *  @Slf4j
 *  로그를 사용하기 위한 어노테이션
 */
@Slf4j
@Controller
public class HomeController {
    

    /**
     * void
     * - URL 과 동일한 뷰 페이지를 응답한다.
     * - /login -> login.html
     * @param param
     */
    @GetMapping("/login")
    public void login(@RequestParam(value = "param", required = false) String param) {
        log.info("[GET] - /login - 로그인 페이지");
        log.info("param : {}" , param);
    }
    
    /**
     * String
     * - 문자열로 응답할 뷰 페이지를 지정한다.
     * - localhost:8080/, localhost:8080 -> index.html
     * @return
     */
    @GetMapping({"/",""})
    public String home() {
        log.info("[GET] - / - 메인페이지");
        return "index";
    }

    /**
     * @ResponseBody
     * String
     * - 문자열 응답 메시지를 지정한다
     * @return
     */
    @ResponseBody
    @GetMapping("/hello")
    public String hello() {
        log.info("[GET] - /hello - 뷰가 아닌 문자열 본문을 응답");
        return "Hello Spring Boot~!";
    }
    

}
