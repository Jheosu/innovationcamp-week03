package com.innovation.myblog.controller;

import com.innovation.myblog.models.TargetType;
import com.innovation.myblog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/likes")
// 좋아요 관련 api를 다루는 컨트롤러
public class LikeController {
    private final LikeService likeService;

    @PostMapping("/{type}/{typeId}")
    public Object like(@PathVariable TargetType type,
                       @PathVariable Long typeId,
                       @RequestParam String param) {
        return likeService.like(type, typeId, param);
    }

//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler(HttpClientErrorException.class)
//    public String cather(Exception e) {
//        return "로그인이 필요합니다";
////        RestApiException restApiException = new RestApiException();
////        restApiException.setHttpStatus(HttpStatus.UNAUTHORIZED);
////        restApiException.setErrorMessage("로그인이 필요합니다.");
////        return new ResponseEntity<>(
////                // HTTP body
////                restApiException,
////                // HTTP status code
////                HttpStatus.OK
////        );
//    }
}
