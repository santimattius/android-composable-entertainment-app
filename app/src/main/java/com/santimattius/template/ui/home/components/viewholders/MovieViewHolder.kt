package com.santimattius.template.ui.home.components.viewholders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.santimattius.template.core.presentation.load
import com.santimattius.template.databinding.ItemMovieBinding
import com.santimattius.template.ui.home.models.MovieUiModel

class MovieViewHolder(
    @VisibleForTesting internal val viewBinding: ItemMovieBinding,
) : RecyclerView.ViewHolder(viewBinding.root) {

    fun bind(item: MovieUiModel, onItemClick: (MovieUiModel) -> Unit = {}) {
        with(viewBinding) {
            imageLoader.isVisible = true
            imageMovie.load(item.imageUrl) {
                imageLoader.isVisible = false
            }
            imageMovie.contentDescription = item.title
            itemRootContainer.setOnClickListener { onItemClick(item) }
        }
    }

    companion object {

        fun from(parent: ViewGroup): MovieViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val viewBinding = ItemMovieBinding.inflate(
                inflater,
                parent,
                false
            )
            return MovieViewHolder(viewBinding)
        }
    }
}
