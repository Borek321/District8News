package com.spitzer.district8news.core

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.spitzer.district8news.MainActivity
import com.spitzer.district8news.core.extensions.setupSnackbar
import com.spitzer.district8news.core.navigation.NavigationCommand

abstract class BaseFragment : Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation(obtainViewModel())
        observeLoading(obtainViewModel())
        setupSnackbar(this, obtainViewModel().snackbarError, Snackbar.LENGTH_SHORT)
    }

    abstract fun obtainViewModel(): BaseViewModel

    private fun hideProgress() = (activity as MainActivity).hideProgressBar()
    private fun showProgress() = (activity as MainActivity).showProgressBar()

    private fun observeNavigation(viewModel: BaseViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { command ->
                when (command) {
                    is NavigationCommand.To -> {
                        findNavController().navigate(command.directions)
                    }
                    is NavigationCommand.Back -> {
                        findNavController().navigateUp()
                    }
                }
            }
        })
    }

    private fun observeLoading(viewModel: BaseViewModel) {
        viewModel.loading.observe(viewLifecycleOwner, {
            it?.getContentIfNotHandled()?.let { loading ->
                if (loading) {
                    showProgress()
                } else {
                    hideProgress()
                }
            }
        })
    }
}
