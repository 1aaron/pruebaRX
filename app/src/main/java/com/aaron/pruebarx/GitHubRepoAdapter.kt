package com.aaron.pruebarx

import android.support.annotation.Nullable
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by aaron on 10/26/17.
 */
class GitHubRepoAdapter : BaseAdapter() {

    private var gitHubRepos = ArrayList<GitHubRepo>()
    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        var view: View
        if(p1 != null){
            view = p1
        }else{
            view = createView(p2!!)
        }
        val viewHolder: GitHubReopoViewHolder = view.getTag() as GitHubReopoViewHolder
        viewHolder.setGitHubRepo(getItem(p0)!!)
        return view
    }

    private fun createView(parent: ViewGroup): View{
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_github_repo,parent,false)
        val viewHolder = GitHubReopoViewHolder(view)
        view.setTag(viewHolder)
        return view
    }

    override fun getItem(p0: Int): GitHubRepo? {
        if(p0 < 0 || p0 >= gitHubRepos.size){
            return null
        }else{
            return gitHubRepos.get(p0)
        }
    }

    fun setGitHubRepos(@Nullable repos: List<GitHubRepo>?) {
        if (repos == null) {
            return
        }
        gitHubRepos.clear()
        gitHubRepos.addAll(repos)
        notifyDataSetChanged()
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return gitHubRepos.size
    }
    companion object {
        public class GitHubReopoViewHolder{

            private var textRepoName: TextView
            private var textRepoDescription:TextView
            private var textLanguage:TextView
            private var textStars:TextView

            constructor(view: View){
                textLanguage = view.findViewById(R.id.text_language)
                textRepoName = view.findViewById(R.id.text_repo_name)
                textRepoDescription = view.findViewById(R.id.text_repo_description)
                textStars = view.findViewById(R.id.text_stars)
            }
            public fun setGitHubRepo(gitHubRepo: GitHubRepo){
                textLanguage.text = "language: "+gitHubRepo.language
                textStars.text = "stars: "+gitHubRepo.stargazersCount
                textRepoDescription.text = gitHubRepo.description
                textRepoName.text = gitHubRepo.name
            }

        }
    }
}