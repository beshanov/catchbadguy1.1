package com.beshanov.catchbadguy.domain;

public class SearchOptions {
    private Integer postCount;
    private String groupId;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


    public void setPostCount(Integer postCount) {
        this.postCount = postCount;
    }

    public Integer getPostCount() {
        return postCount;
    }
}
