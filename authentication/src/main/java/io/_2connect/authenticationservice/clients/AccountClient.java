package io._2connect.authenticationservice.clients;

import io._2connect.commons.entities.account.Account;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@FeignClient(name = "account", url = "${application.feign-clients.account}")
public interface AccountClient {

    @PostMapping("/save")
    Account save(Account user);

    @GetMapping("/findByUsername/{username}")
    Optional<Account> findByUsername(@PathVariable String username);

    @GetMapping("/loadUserByUsername/{username}")
    UserDetails loadUserByUsername(@PathVariable String username);
}
