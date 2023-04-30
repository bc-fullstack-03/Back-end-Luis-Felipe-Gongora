package com.sysmap.showus.services.user;

import com.sysmap.showus.domain.User;

public interface IUserService {
    String createUser(UserRequest request);
    User updateData(User newUser, User user);
}
