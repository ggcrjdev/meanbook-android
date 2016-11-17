package com.brilgo.meanbookandroidapp.api.response;

public class UsersCurrentResponse {

    public final boolean authenticated;
    public final String username;

    public UsersCurrentResponse(boolean authenticated, String username) {
        this.authenticated = authenticated;
        this.username = username;
    }

    public static UsersCurrentResponse nullObject() {
        return new UsersCurrentResponse(false, "");
    }
}
