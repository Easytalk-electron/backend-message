package com.example.UserManagement.entity;

import java.util.List;

public class Group {
    private String gid;
    private String new_uid;
    private List<Integer> uids;

    public List<Integer> getUids() {
        return uids;
    }

    public String getGid() {
        return gid;
    }

    public String getNew_uid() {
        return new_uid;
    }

    public void setGid(String gid) {
        this.gid = gid;
    }

    public void setUid(List<Integer> uids) {
        this.uids = uids;
    }

    public void setNew_uid(String new_uid) {
        this.new_uid = new_uid;
    }
}
