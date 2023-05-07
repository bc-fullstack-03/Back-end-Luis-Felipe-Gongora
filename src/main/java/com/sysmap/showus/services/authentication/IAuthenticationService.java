package com.sysmap.showus.services.authentication;

import com.sysmap.showus.services.authentication.dto.AuthenticateRequest;
import com.sysmap.showus.services.authentication.dto.AuthenticateResponse;

public interface IAuthenticationService {
    AuthenticateResponse authenticate(AuthenticateRequest request);
}
