package com.brilgo.meanbookandroidapp.api;

import com.brilgo.meanbookandroidapp.api.request.UsernameRequest;
import com.brilgo.meanbookandroidapp.api.response.PostsListResponse;
import com.brilgo.meanbookandroidapp.api.response.User;
import com.brilgo.meanbookandroidapp.api.response.UsersCurrentResponse;
import com.brilgo.meanbookandroidapp.api.response.UsersListResponse;
import com.brilgo.meanbookandroidapp.api.response.UsersLogoutResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MeanBookApiClient {

	@GET("users/list")
	Call<UsersListResponse> list();

	@POST("users/current")
	Call<UsersCurrentResponse> getCurrent();

	@POST("users/login")
	Call<User> login(@Body UsernameRequest request);

	@POST("users/logout")
	Call<UsersLogoutResponse> logout(@Body UsernameRequest request);

	@GET("posts/list/{username}/{pageNumber}")
	Call<PostsListResponse> listPosts(
			@Path("username") String username,
			@Path("pageNumber") Integer pageNumber);
}
