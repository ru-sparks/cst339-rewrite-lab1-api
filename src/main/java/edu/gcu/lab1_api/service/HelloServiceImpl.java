package edu.gcu.lab1_api.service;

import edu.gcu.lab1_api.dto.HelloResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public HelloResponse getHello() {
        return new HelloResponse("Hello, world!", Instant.now().toString());
    }
}
