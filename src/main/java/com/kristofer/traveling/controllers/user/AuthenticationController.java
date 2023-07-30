package com.kristofer.traveling.controllers.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristofer.traveling.controllers.user.requests.AuthenticationRequest;
import com.kristofer.traveling.controllers.user.requests.RegisterRequest;
import com.kristofer.traveling.controllers.user.responses.AuthenticationResponse;
import com.kristofer.traveling.services.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody RegisterRequest request){
        System.out.println(authenticationService.findByEmail(request.getEmail()));
        if(!authenticationService.findByEmail(request.getEmail()).isPresent()){
            if(!authenticationService.findByAt(request.getAt()).isPresent()){
                return ResponseEntity.ok(authenticationService.register(request));
            }else{
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("At already exists");
            }
        }else{
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Email already exists");
        }
        
    }
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
