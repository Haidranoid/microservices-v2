package io._2connect.account.controllers;

import io._2connect.account.beans.ChangePasswordRequest;
import io._2connect.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import io._2connect.commons.entities.account.Account;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/account") // TODO: change to /api/v1/users
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final UserDetailsService userDetails;

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(
            @RequestBody ChangePasswordRequest request
    ){
        accountService.changePassword(request);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/me")
    public ResponseEntity<Account> me(Principal connectedUser){
        var user = accountService.me(connectedUser);

        // TODO: fix bug with tokens
        user.setTokens(Collections.emptyList());
        user.setPassword(null);

        return ResponseEntity.ok(user);
    }


    @GetMapping("/loadUserByUsername/{username}")
    UserDetails loadUserByUsername(@PathVariable String username){
        return userDetails.loadUserByUsername(username);
    }
}
