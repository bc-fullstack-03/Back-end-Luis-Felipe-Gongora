package com.sysmap.showus.domain.embedded;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Followers {
    private List<Author> followersList = new ArrayList<>();
    private List<Author> followingList = new ArrayList<>();
    private Integer followersCount;
    private Integer followingCount;

    public Followers(){
        this.followersCount = 0;
        this.followingCount = 0;
    }
}
