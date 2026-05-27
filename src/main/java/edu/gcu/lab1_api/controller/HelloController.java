package edu.gcu.lab1_api.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public Map<String, Object> hello() {
        Map<String, Object> resp = new HashMap<>();
        resp.put("message", "Hello, world!");
        resp.put("timestamp", Instant.now().toString());
        return resp;
    }
}
