package com.example.testappforgenesis.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.testappforgenesis.entity.ui.RepoItemUI
import kotlinx.android.synthetic.main.item_repo.view.*

class RepoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(repo: RepoItemUI){
        itemView.tv_repo_name.text = repo.fullName
        itemView.tv_description.text = repo.description
        itemView.tv_language.text = repo.language
        itemView.tv_stars.text = repo.stars.toString()
    }
}