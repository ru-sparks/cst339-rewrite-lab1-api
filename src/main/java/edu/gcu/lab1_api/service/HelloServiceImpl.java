package edu.gcu.lab1_api.service;

import edu.gcu.lab1_api.dto.HelloDto;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class HelloServiceImpl implements HelloService {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(HelloService.class);

    @Override
    public HelloDto getHello() {
        log.info("getHello() service method executed");

        return new HelloDto("Hello, world!", Instant.now().toString());
    }
}
