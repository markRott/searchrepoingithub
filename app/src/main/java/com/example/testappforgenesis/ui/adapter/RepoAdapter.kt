package com.example.testappforgenesis.ui.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testappforgenesis.R
import com.example.testappforgenesis.entity.ui.RepoItemUI
import com.example.testappforgenesis.utils.inflate

class RepoAdapter : RecyclerView.Adapter<RepoViewHolder>() {

    private val items = mutableListOf<RepoItemUI>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder =
            RepoViewHolder(parent.inflate(R.layout.item_repo))

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val repo = items[position]
        holder.bind(repo)
    }

    override fun getItemCount(): Int = items.size

    fun setItems(list: List<RepoItemUI>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }
}