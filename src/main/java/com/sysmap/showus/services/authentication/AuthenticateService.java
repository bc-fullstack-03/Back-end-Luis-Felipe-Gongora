package com.sysmap.showus.services.authentication;

import com.sysmap.showus.services.authentication.dto.AuthenticateRequest;
import com.sysmap.showus.services.authentication.dto.AuthenticateResponse;
import com.sysmap.showus.services.security.IJwtService;
import com.sysmap.showus.services.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
@Service
public class AuthenticateService implements IAuthenticationService {
    @Autowired
    private IUserService _userService;
    @Autowired
    private IJwtService _jwtService;
    @Autowired
    private PasswordEncoder _passwordEncoder;

    public AuthenticateResponse authenticate(AuthenticateRequest request){
        var user = _userService.getUserById(_userService.getUserByEmail(request.getEmail()).getId());

        if (!_passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Password invalid!");
        }

        var token = _jwtService.generateToken(user.getId());

        var response = new AuthenticateResponse();

        response.setUserId(user.getId());
        response.setToken(token);

        return response;
    }
}
