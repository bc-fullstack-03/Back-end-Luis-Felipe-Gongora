package com.sysmap.showus.domain.embedded;

import com.sysmap.showus.domain.entities.User;
import com.sysmap.showus.services.user.dto.UserResponse;
import lombok.Data;

import java.util.UUID;
@Data
public class Author {
    private UUID id;
    private String name;

    public Author(){}
    public Author(User user){
        this.id = user.getId();
        this.name = user.getName();
    }
}
