package io._2connect.account.service;

import io._2connect.account.beans.ChangePasswordRequest;
import io._2connect.account.repositories.AccountRepository;
import io._2connect.commons.entities.account.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.security.Principal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;

    public void changePassword(ChangePasswordRequest request) {
        var accountOptional = accountRepository.findById(request.getAccountId());

        if (accountOptional.isEmpty()){
            throw new IllegalStateException("Account not found");
        }

        // get the account
        var account = accountOptional.get();

        // check if the current password is correct
        if (!passwordEncoder.matches(request.getCurrentPassword(), account.getPassword())){
            throw new IllegalStateException("Wrong password");
        }

        // check if the two new passwords are the same
        if (!request.getNewPassword().equals(request.getConfirmationPassword())){
            throw new IllegalStateException("Passwords are not the same");
        }

        // update the password
        account.setPassword(passwordEncoder.encode(request.getNewPassword()));

        // save the new password
        accountRepository.save(account);
    }

    public Account me(Principal connectedUser){
        return ((Account) ((UsernamePasswordAuthenticationToken) connectedUser).getPrincipal());
    }

    public Optional<Account> findByUsername(@PathVariable String username){
        return accountRepository.findByUsername(username);
    }
}
