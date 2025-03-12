package com.macielzeferino.authenticationsystem.controller;

import com.macielzeferino.authenticationsystem.entity.AuthenticationDto;
import com.macielzeferino.authenticationsystem.entity.LoginResponseDTO;
import com.macielzeferino.authenticationsystem.entity.RegisterDto;
import com.macielzeferino.authenticationsystem.entity.User;
import com.macielzeferino.authenticationsystem.infra.security.TokenService;
import com.macielzeferino.authenticationsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;
    @Autowired
    private TokenService tokenService;
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Validated AuthenticationDto data){
        var userNamePassword = new UsernamePasswordAuthenticationToken(data.userLogin(), data.userPassword());
        var auth = this.authenticationManager.authenticate(userNamePassword);
        var token = tokenService.generateToken((User)auth.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register (@RequestBody @Validated RegisterDto data) {
        if (this.repository.findByUserLogin(data.userLogin()) != null) return ResponseEntity.badRequest().build();
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.userPassword());
        User newUser = new User(data.userLogin(), encryptedPassword,data.userRole());

        this.repository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
