package com.spitzer.district8news.ui.main

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.spitzer.district8news.R
import com.spitzer.district8news.core.BaseFragment
import com.spitzer.district8news.core.repository.data.Post
import com.spitzer.district8news.databinding.MainFragmentBinding
import com.spitzer.district8news.ui.categoryfilteradapter.CategoryFilterAdapter
import com.spitzer.district8news.ui.postadapter.PostAdapter
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment() {

    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var postAdapter: PostAdapter
    private lateinit var fallbackDrawableImage: Drawable

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private val viewModel: MainViewModel by viewModels()

    override fun obtainViewModel() = viewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        setupView()
        defineObservables()
        return binding.root
    }

    private fun defineObservables() {
        viewModel.posts.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { posts ->
                if (posts.isEmpty()) {
                    // TODO show no content layout
                } else {
                    try {
                        Picasso.get()
                            .load(posts.first().featuredMedia.first().href)
                            .placeholder(R.drawable.ic_placeholder_image_24)
                            .error(R.drawable.ic_broken_image_24)
                            .into(binding.featuredPostImage)
                    } catch (e: Exception) {
                        binding.featuredPostImage.setImageDrawable(fallbackDrawableImage)
                    }
                    binding.featuredPostTitle.text = posts.first().title.rendered

                    binding.featuredPostImage.setOnClickListener {
                        viewModel.onPostClicked(posts.first())
                    }
                    binding.featuredPostTitle.setOnClickListener {
                        viewModel.onPostClicked(posts.first())
                    }
                    binding.featuredPostTitleBackground.setOnClickListener {
                        viewModel.onPostClicked(posts.first())
                    }

                    postAdapter.updateData(
                        posts.slice(
                            IntRange(
                                1,
                                posts.size - 1
                            )
                        ) as ArrayList<Post>
                    )
                }
            }
        })
    }

    private fun setupView() {
        fallbackDrawableImage = resources.getDrawable(R.drawable.ic_broken_image_24)

        postAdapter = PostAdapter(fallbackDrawableImage)
        postAdapter.onItemClickFunction { postClicked -> viewModel.onPostClicked(postClicked) }
        binding.postsRecyclerView.apply {
            layoutManager =
                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = postAdapter
        }

        bottomSheetDialog = BottomSheetDialog(requireContext())

        binding.toolbarFilterTextView.setOnClickListener {

            val view = layoutInflater.inflate(R.layout.categories_filter, null)
            val btnClose = view.findViewById<ImageView>(R.id.closeBottomsheet)
            btnClose.setOnClickListener {
                bottomSheetDialog.dismiss()
            }
            bottomSheetDialog.setCancelable(false)

            val categoriesAdapter = CategoryFilterAdapter(viewModel.categories.value!!)
            val categoriesRecyclerView = view.findViewById<RecyclerView>(R.id.categoriesRecyclerView)
            categoriesRecyclerView.apply {
                layoutManager =
                    LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                adapter = categoriesAdapter
            }

            bottomSheetDialog.setContentView(view)
            bottomSheetDialog.show()
        }

        viewModel.getInitialConfiguration()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
