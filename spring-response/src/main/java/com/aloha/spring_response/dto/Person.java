package com.aloha.spring_response.dto;

import lombok.Data;

@Data
public class Person {
    
    private String name;
    private int age;
    
    // ctrl + . : (quick fix, 추가 작업) 단축키
    public Person() {
        this.name = "gunwoo";
        this.age = 29;
    }


}
