package com.aaron.pruebarx

import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import android.support.annotation.NonNull
import rx.Observable


/**
 * Created by aaron on 10/26/17.
 */
class GitHubClient {



    companion object {
        val GITHUB_BASE_URL = "https://api.github.com/"
        lateinit var gitHubService: GitHubService
        var instancia: GitHubClient? = null
        fun getInstance(): GitHubClient {
            if (instancia == null) {
                instancia = GitHubClient()
            }
            return instancia!!
        }
    }


    private constructor(){
        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create()
        val retrofit = Retrofit.Builder().baseUrl(GITHUB_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        gitHubService = retrofit.create(GitHubService::class.java)
    }



    fun getStarredRepos(userName: String): Observable<List<GitHubRepo>> {
        return gitHubService.getStarredRepositories(userName)
    }
}