package com.example.cfx;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainInerface {
    @GET("api/user.status?&")
    Call<String> STRING_CALL(
            @Query("from")int from,
            @Query("count")int count,
           @Query("handle")String username
    );
    @GET("api/user.status?&from=1&")
    Call<String> STRING_CALL2(
           @Query("handle")String username
    );
@GET("api/user.info?")
    Call<String> STRING_CALL3(
           @Query("handles")String username
    );
@GET("api/user.rating?")
    Call<String> STRING_CALL4(
           @Query("handle")String username
    );
@GET("api/contest.list?gym=false")
    Call<String> STRING_CALL5(
    );
@GET("api/problemset.problems?")
    Call<String> STRING_CALL6(
        @Query("tags")String username
    );

@GET("api/user.friends?&apiKey=0384f3b840aa97c9a69e7c577c5c5bd7f36eae12")
    Call<String> STRING_CALL7(
        @Query("time")String time,
        @Query("apiSig")String apiSig
    );
@GET("api/contest.standings?&from=1&showUnofficial=true")
    Call<String> STRING_CALL8(
        @Query("handles")String handle,
        @Query("contestId")String contestid
    );

}

