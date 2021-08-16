package com.spitzer.district8news.ui.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.spitzer.district8news.core.BaseFragment
import com.spitzer.district8news.databinding.PostFragmentBinding

class PostFragment : BaseFragment() {

    private var _binding: PostFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel: PostViewModel by viewModels()
    private val args: PostFragmentArgs by navArgs()

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
    }

    private fun defineObservables() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            setupView()
        })
    }

    private fun setupView() {
        with(viewModel.post.value!!) {

        }

        viewModel.viewLoaded()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
