package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDetailDto;
import com.fastcampus.programming.dmaker.dto.DeveloperDto;
import com.fastcampus.programming.dmaker.dto.EditDeveloper;
import com.fastcampus.programming.dmaker.service.DMakerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// 빈을 추가하는 것은, 이미 완성된 레고 판 위의 부품(컴포넌트)을 올리는 행위와 유사.
@Slf4j
@RestController
// DMakerController 클래스를 'RestController' 라는 타입의 빈으로 등록하는 것. = @ResponseBody + @Controller. JSON 형태의 요청/응답 API를 만들 때 사용.
@RequiredArgsConstructor // @RequiredArgsConstructor + private final 의 형태로 @Autowired를 대체 가능.
public class DMakerController {
    private final DMakerService dMakerService;

    @GetMapping("/developers")
    public List<DeveloperDto> getAllDevelopers() {
        // GET /developers HTTP/1.1
        log.info("GET /developers HTTP/1.1");
        return dMakerService.getAllDevelopers();
    }

    @GetMapping("/developer/{memberId}")
    public DeveloperDetailDto getDeveloperDetail(
            @PathVariable String memberId
    ){
        log.info("GET /developers HTTP/1.1");
        return dMakerService.getDeveloperDetail(memberId);
    }

    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            @Valid @RequestBody CreateDeveloper.Request request
            ) {
        log.info("request: {}", request);
        return dMakerService.createDeveloper(request);
    }
    // PUT은 전체 수정, PATCH는 일부 수정
    @PutMapping("/developer/{memberId}")
    public DeveloperDetailDto editDeveloper(
            @PathVariable String memberId,
            @Valid @RequestBody EditDeveloper.Request request
    ){
        log.info("PUT /developers HTTP/1.1");
        return dMakerService.editDeveloper(memberId, request);
    }

}
