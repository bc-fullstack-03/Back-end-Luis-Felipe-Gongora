package com.sysmap.showus.data;

import java.util.ArrayList;
import java.util.List;

public class FollowersDTO {
    private List<AuthorDTO> followersList = new ArrayList<>();
    private Integer countFollowers;

    public FollowersDTO(){
        this.countFollowers = 0;
    }

    public FollowersDTO(AuthorDTO user){
        this.followersList.add(user);
    }

    public List<AuthorDTO> getFollowersList() {
        return followersList;
    }

    public void setFollowersList(List<AuthorDTO> followersList) {
        this.followersList = followersList;
    }

    public Integer getCountFollowers() {
        return countFollowers;
    }

    public void setCountFollowers(Integer countFollowers) {
        this.countFollowers = countFollowers;
    }
}
