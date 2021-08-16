package com.spitzer.district8news.ui.post

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.spitzer.district8news.R
import com.spitzer.district8news.core.BaseFragment
import com.spitzer.district8news.databinding.PostFragmentBinding
import com.squareup.picasso.Picasso

class PostFragment : BaseFragment() {

    private var _binding: PostFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostViewModel by viewModels()
    private val args: PostFragmentArgs by navArgs()
    private lateinit var fallbackDrawableImage: Drawable

    override fun obtainViewModel() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PostFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        defineObservables()
        viewModel.setPostModel(args.post)
        fallbackDrawableImage = resources.getDrawable(R.drawable.ic_broken_image_24)
    }

    private fun defineObservables() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            setupView()
        })
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setupView() {
        binding.toolbarBackIcon.setOnClickListener {
            viewModel.navigateBack()
        }

        with(viewModel.post.value!!) {
            try {
                Picasso.get()
                    .load(this.featuredMedia.first().href)
                    .placeholder(R.drawable.ic_placeholder_image_24)
                    .error(R.drawable.ic_broken_image_24)
                    .into(binding.featuredPostImage)
            } catch (e: Exception) {
                binding.featuredPostImage.setImageDrawable(fallbackDrawableImage)
            }
            binding.postWebContent.apply {
                webViewClient = WebViewClient()
                settings.javaScriptEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                loadData(
                    postContent.rendered,
                    "text/html",
                    "UTF-8"
                )
            }
        }

        viewModel.viewLoaded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
