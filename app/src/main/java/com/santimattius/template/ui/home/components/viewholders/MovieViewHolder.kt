package com.santimattius.template.ui.home.components.viewholders

import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.RecyclerView
import com.santimattius.template.ui.home.components.MovieView
import com.santimattius.template.ui.home.models.MovieUiModel

class MovieViewHolder(
    internal val composeView: ComposeView,
) : RecyclerView.ViewHolder(composeView) {

    init {
        composeView.setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
    }

    fun bind(item: MovieUiModel, onItemClick: (MovieUiModel) -> Unit = {}) {
        composeView.setContent {
            MovieView(
                movie = item,
                modifier = Modifier.clickable {
                    onItemClick(item)
                }
            )
        }
    }

    companion object {

        fun from(parent: ViewGroup): MovieViewHolder {
            val composeView = ComposeView(parent.context)
            return MovieViewHolder(composeView)
        }
    }
}
