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
import com.santimattius.template.ui.components.navigateToDeeplink
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
                    val deepLink = "app://movie/${it.id}"
                    requireContext().navigateToDeeplink(deepLink)
                },
                navigationUp = {
                    requireActivity().finish()
                }
            )
        }
    }

    private fun Fragment.composeView(
        content: @Composable () -> Unit,
    ): ComposeView {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(
                strategy = ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
            )
            setContent {
                MdcTheme(content = content)
            }
        }
    }
}