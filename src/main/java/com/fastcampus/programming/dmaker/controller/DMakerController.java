package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.service.DMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

// 빈을 추가하는 것은, 이미 완성된 레고 판 위의 부품(컴포넌트)을 올리는 행위와 유사.
@Slf4j
@RestController
// DMakerController 클래스를 'RestController' 라는 타입의 빈으로 등록하는 것. = @ResponseBody + @Controller. JSON 형태의 요청/응답 API를 만들 때 사용.
@RequiredArgsConstructor // @RequiredArgsConstructor + private final 의 형태로 @Autowired를 대체 가능.
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<String> getAllDevelopers() {
        // GET /developers HTTP/1.1
        log.info("GET /developers HTTP/1.1");
        return Arrays.asList("Snow", "Elsa", "Olaf");
    }

    @PostMapping("/create-developer")
    public List<String> createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
            ) {
        log.info("request: {}", request);
        dMakerService.createDeveloper(request);
        return List.of("Olaf");
    }

}
