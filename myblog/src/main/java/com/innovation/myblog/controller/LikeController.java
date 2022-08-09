package com.innovation.myblog.controller;

import com.innovation.myblog.exception.RestApiException;
import com.innovation.myblog.models.TargetType;
import com.innovation.myblog.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpSessionRequiredException;
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
                       @RequestParam String param) throws HttpSessionRequiredException {
        return likeService.like(type, typeId, param);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestApiException> cather(Exception e) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.BAD_REQUEST);
        restApiException.setErrorMessage(
                e.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                restApiException.getHttpStatus()
        );
    }

    @ExceptionHandler(HttpSessionRequiredException.class)
    public ResponseEntity<RestApiException> HttpSessionRequiredExceptionHandler(Exception e) {
        RestApiException restApiException = new RestApiException();
        restApiException.setHttpStatus(HttpStatus.OK);
        restApiException.setErrorMessage(
                e.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                restApiException.getHttpStatus()
        );
    }
}
