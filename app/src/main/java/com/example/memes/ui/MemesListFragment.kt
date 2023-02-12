package com.example.memes.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope

import com.example.memes.R
import com.example.memes.databinding.FragmentMemesListBinding
import com.example.memes.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ViewWithFragmentComponent

@AndroidEntryPoint
class MemesListFragment : Fragment(R.layout.fragment_memes_list) {
    private var _binding: FragmentMemesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MemesListViewModel>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMemesListBinding.bind(view)
        val adapter = AdapterMemes()

        binding.apply {
            memesListView.adapter = adapter
            retryBtn.setOnClickListener {
                errorLayout.visibility = View.GONE
                viewModel.loadMemesList()
            }
            rgShowBtn.setOnClickListener {
                if (rgMemes.isVisible) {
                    rgShowBtn.visibility = View.GONE
                } else {
                    rgMemes.visibility = View.VISIBLE
                    rgShowBtn.visibility = View.GONE
                }
            }
            oneMemeRadio.setOnClickListener {
                hideBtnOnChoice()
                viewModel.setOneMeme()
            }
            multiMemeRadio.setOnClickListener {
                hideBtnOnChoice()
                viewModel.loadMemesList()
            }
            viewModel.memesList.observe(viewLifecycleOwner) { resource ->
                when (resource) {
                    is Resource.Error -> {
                        errorLayout.visibility = View.VISIBLE
                        errorMsg.text = resource.message
                    }
                    is Resource.Loading -> {
                        errorLayout.visibility = View.GONE
                    }
                    is Resource.Success -> {
                        errorLayout.visibility = View.GONE
                        adapter.submitList(resource.data)
                        adapter.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun hideBtnOnChoice() {
        binding.apply {
            rgMemes.visibility = View.GONE
            rgShowBtn.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}