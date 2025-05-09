package com.foresys.app1.sample.controller;

import com.foresys.app1.sample.model.SamplePagingDTO;
import com.foresys.app1.sample.service.SampleService;
import com.foresys.core.model.jwt.TokenUser;
import com.github.pagehelper.PageInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sample")
public class SampleController {

    private final SampleService sampleService;

    @PostMapping("/jasypt/encoding")
    public String jasyptEncoding(@RequestBody String params) {
        return sampleService.jasyptEncoding(params);
    }

    @PostMapping("/jasypt/decoding")
    public String jasyptDecoding(@RequestBody String params) {
        return sampleService.jasyptDecoding(params);
    }

    @PostMapping("/jwt/createToken")
    public String createToken(@RequestBody TokenUser params) {
        return sampleService.createToken(params);
    }

    @PostMapping("/jwt/decoding")
    public TokenUser jwtDecoding(@RequestBody String params) {
        return sampleService.jwtDecoding(params);
    }

    @PostMapping("/select")
    public List<Map<String, Object>> select() {
        return sampleService.select();
    }

    @PostMapping("/paging")
    public PageInfo<Map<String, Object>> paging(@RequestBody @Valid SamplePagingDTO params) {
        return sampleService.paging(params);
    }

}
