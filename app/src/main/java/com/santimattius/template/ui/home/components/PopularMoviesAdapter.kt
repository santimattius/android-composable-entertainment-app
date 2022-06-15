package com.santimattius.template.ui.home.components

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.santimattius.template.ui.home.components.diff.MovieDiffItem
import com.santimattius.template.ui.home.components.viewholders.MovieViewHolder
import com.santimattius.template.ui.home.models.MovieUiModel

class PopularMoviesAdapter(private val onItemClick: (MovieUiModel) -> Unit = {}) :
    ListAdapter<MovieUiModel, MovieViewHolder>(MovieDiffItem) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClick)
    }
}