package com.brilgo.meanbookandroidapp.api.response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersListResponse {

    public final List<User> users;

    public UsersListResponse(List<User> users) {
        this.users = Collections.unmodifiableList(users);
    }

    public static UsersListResponse nullObject() {
        return new UsersListResponse(new ArrayList<User>(0));
    }
}
