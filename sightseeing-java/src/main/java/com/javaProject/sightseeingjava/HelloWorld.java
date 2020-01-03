package com.javaProject.sightseeingjava;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld { // nazwa klasy
 
// wskazanie pod jakim adresem dostępna jest metoda
@RequestMapping("/hello")
// sygnatura metody
public String hello() {
// zwracana wartość przez przeglądarkę
return "Hello World! :)";
}
}