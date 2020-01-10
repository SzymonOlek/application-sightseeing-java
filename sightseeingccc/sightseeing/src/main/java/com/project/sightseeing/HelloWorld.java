package com.project.sightseeing;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 

@RestController
public class HelloWorld { 
 
 @RequestMapping("/hello")
 public String hello() {
 return "glowny.jsp" ;	// zwracana wartość przez przeglądarkę         http://localhost:8080/sightseeing/hello
 }
}