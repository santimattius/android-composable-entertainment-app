package com.santimattius.template.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import com.google.android.material.composethemeadapter.MdcTheme
import com.santimattius.template.ui.components.openLink
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalLifecycleComposeApi
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        return composeView {
            HomeRoute(
                homeViewModel = viewModel,
                onMovieClick = {
                    openLink(it.imageUrl)
                },
                onBack = {
                    requireActivity().finish()
                }
            )
        }
    }

    private fun Fragment.composeView(content: @Composable () -> Unit): ComposeView {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MdcTheme(content = content)
            }
        }
    }
}