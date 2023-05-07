package com.sysmap.showus.api;

import com.sysmap.showus.services.authentication.IAuthenticationService;
import com.sysmap.showus.services.authentication.dto.AuthenticateRequest;
import com.sysmap.showus.services.authentication.dto.AuthenticateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/authentication")
@Tag(name = "Authentication")
public class AuthenticationController {
    @Autowired
    private IAuthenticationService _authenticationService;
    @PostMapping
    @Operation(summary = "Login")
    public ResponseEntity<AuthenticateResponse> authenticate(@RequestBody AuthenticateRequest request){
        return ResponseEntity.ok().body(_authenticationService.authenticate(request));
    }
}
