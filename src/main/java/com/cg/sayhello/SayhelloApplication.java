package com.cg.sayhello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SayhelloApplication {

  public static void main(String[] args) {

    SpringApplication.run(SayhelloApplication.class, args);
    System.out.println("Hello World!");
  }

}
