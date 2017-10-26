package com.aaron.pruebarx

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


/**
 * Created by aaron on 10/26/17.
 */
interface GitHubService {
    @GET("users/{user}/starred")
    fun getStarredRepositories(@Path("user") userName: String): Observable<List<GitHubRepo>>

}