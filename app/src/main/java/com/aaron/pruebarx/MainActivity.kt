package com.aaron.pruebarx

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.text.TextUtils
import android.util.Log
import android.widget.EditText
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import rx.Observer
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG: String = MainActivity.javaClass.simpleName
    }
    var adapter = GitHubRepoAdapter()

    private lateinit var subscription: Subscription
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView: ListView = findViewById(R.id.list_view_repos)
        listView.adapter = adapter

        val editTextUsername = edit_text_username

        val buttonSearch = button_search
        buttonSearch.setOnClickListener({
            val username = editTextUsername.text.toString()
            if(!TextUtils.isEmpty(username)){
                getStarredRepos(username)
            }
        })
        val permisos = arrayOf(android.Manifest.permission.INTERNET)
        ActivityCompat.requestPermissions(this,permisos,100)

    }

    fun getStarredRepos(username: String){

        subscription = GitHubClient.getInstance()
                .getStarredRepos(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<GitHubRepo>> {
                    override fun onCompleted() {
                        Log.d(TAG, "In onCompleted()")
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        Log.d(TAG, "In onError()")
                    }

                    override fun onNext(gitHubRepos: List<GitHubRepo>) {
                        Log.d(TAG, "In onNext()")
                        Log.e(TAG,gitHubRepos.toString())
                        adapter.setGitHubRepos(gitHubRepos)
                    }
                })
    }

    override fun onDestroy() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe()
        }
        super.onDestroy()
    }
}
