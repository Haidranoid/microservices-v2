package io._2connect.authenticationservice.controllers;

import io._2connect.authenticationservice.beans.LoginRequest;
import io._2connect.authenticationservice.beans.LoginResponse;
import io._2connect.authenticationservice.beans.SignupRequest;
import io._2connect.authenticationservice.beans.SignupResponse;
import io._2connect.authenticationservice.services.AuthenticationService;
import io._2connect.commons.entities.token.Token;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;


    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> register(
            @RequestBody SignupRequest request
    ) {
        SignupResponse signupResponse = authenticationService.signup(request);

        return ResponseEntity.ok(signupResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(
            @RequestBody LoginRequest request
    ) {
        LoginResponse loginResponse = authenticationService.login(request);

        return ResponseEntity.ok(loginResponse);
    }

    @PostMapping("/refresh-token")
    public void authenticate(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        authenticationService.refreshToken(request, response);
    }
}
