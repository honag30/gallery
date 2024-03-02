package com.springbackendshoppingmall.controller;

import com.springbackendshoppingmall.entity.Account;
import com.springbackendshoppingmall.repository.AccountRepository;
import com.springbackendshoppingmall.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.parser.Entity;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountRepository accRepo;

    private final JwtService jwtService;

    @Autowired
    public AccountController(final AccountRepository accRepo, final JwtService jwtService) {
        this.accRepo = accRepo;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> params, HttpServletResponse res) {
        Account account = accRepo.findByEmailAndPassword(params.get("email"), params.get("password"));

        if (account != null) {
            int id = account.getId();
            String token = jwtService.getTokens("id", id);

            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setSecure(true);

            res.addCookie(cookie);

            return new ResponseEntity<>(id, HttpStatus.OK);
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response, HttpServletRequest request, @CookieValue(name = "token", required = false) String token) {

        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    String cookieValue = cookie.getValue();
//                    System.out.println("value [1]: " + cookieValue);
                    cookie.setValue("");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
//                    System.out.println("value [2]: " + cookieValue);
                }
            }
        } else {
            System.out.println("Not Found Token");
        }

        Cookie cookie = new Cookie("token", null);
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

//        System.out.println("test token: " + token);

        return new ResponseEntity<>(HttpStatus.OK);


    }

    @GetMapping("/check")
    public ResponseEntity<?> check(@CookieValue(name = "token", required = false) String token) {
        if (token == null) {
            return new ResponseEntity<>("Token is missing", HttpStatus.BAD_REQUEST);
        }

        Claims claims = jwtService.getClaims(token);

        if (claims != null) {
            int id = Integer.parseInt(claims.get("id").toString());

            Account account = accRepo.findAccountById(id);

            return new ResponseEntity<>(account, HttpStatus.OK);
        }

        return new ResponseEntity<>("Invalid token", HttpStatus.UNAUTHORIZED);
    }

}
