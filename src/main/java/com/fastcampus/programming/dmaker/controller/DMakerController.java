package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.*;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.service.DMakerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return dMakerService.getAllEmployedDevelopers();
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

    @DeleteMapping("/developer/{memberId}")
    public DeveloperDetailDto deleteDeveloper(
            @PathVariable String memberId
    ){
        return dMakerService.deleteDeveloper(memberId);
    }

    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(DMakerException e,
                                               HttpServletRequest request){
        log.error("errorCode: {}, url: {}, message: {}",
                e.getDMakerErrorCode(), request.getRequestURI(), e.getDetailMessage());
        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }

}
