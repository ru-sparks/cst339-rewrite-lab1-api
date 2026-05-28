package edu.gcu.lab1_api.controller;

import edu.gcu.lab1_api.dto.HelloResponse;
import edu.gcu.lab1_api.service.HelloService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {
    private final HelloService helloService;
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HelloController.class);

    public HelloController(HelloService helloService) {
        this.helloService = helloService;
    }

    @GetMapping("/hello")
    public HelloResponse hello() {
        log.info("hello() endpoint was called");
        return helloService.getHello();
    }
}
