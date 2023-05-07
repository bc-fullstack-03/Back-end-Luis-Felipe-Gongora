package com.sysmap.showus.domain.embedded;

import com.sysmap.showus.domain.entities.User;
import lombok.Data;

import java.util.UUID;
@Data
public class Likes {
    private UUID id;
    private String name;

    public Likes(){};
    public Likes(User user){
        this.id = user.getId();
        this.name = user.getName();
    }
}
