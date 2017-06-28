package com.artiomlevchuk.yourbandsintown.api;

import com.artiomlevchuk.yourbandsintown.entities.Artist;
import com.artiomlevchuk.yourbandsintown.entities.Event;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BITClient {

    @Headers({"Accept: application/json"})
    @GET("artists/{username}")
    Call<Artist> searchUser(
            @Path("username") String username,
            @Query("app_id") String appId);

    @Headers({"Accept: application/json"})
    @GET("artists/{username}/events")
    Call<List<Event>> getArtistEvents(
            @Path("username") String username,
            @Query("app_id") String appId);
}
