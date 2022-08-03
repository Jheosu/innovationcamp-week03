package com.innovation.myblog.controller;

import com.innovation.myblog.dto.MemberRequestDto;
import com.innovation.myblog.dto.MemberResponseDto;
import com.innovation.myblog.dto.TokenDto;
import com.innovation.myblog.dto.TokenRequestDto;
import com.innovation.myblog.exception.RestApiException;
import com.innovation.myblog.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<MemberResponseDto> signup(@Valid @RequestBody MemberRequestDto memberRequestDto) {
        try {
            return ResponseEntity.ok(authService.signup(memberRequestDto));
        }
        catch (IllegalArgumentException ex) {
            RestApiException restApiException = new RestApiException();
            restApiException.setHttpStatus(HttpStatus.CONFLICT);
            restApiException.setErrorMessage(ex.getMessage());
            return new ResponseEntity(restApiException, HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto memberRequestDto) {
            return ResponseEntity.ok(authService.login(memberRequestDto));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        return ResponseEntity.ok(authService.reissue(tokenRequestDto));
    }
}
