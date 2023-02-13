package com.example.memes.ui.memeslist

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels

import com.example.memes.R
import com.example.memes.databinding.FragmentMemesListBinding
import com.example.memes.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MemesListFragment : Fragment(R.layout.fragment_memes_list) {
    private var _binding: FragmentMemesListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MemesListViewModel>()

    @Inject
    lateinit var adapter: AdapterMemes

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMemesListBinding.bind(view)
        binding.apply {
            memesListView.adapter = adapter
            cancelBtn.setOnClickListener { hideRadioGroupLayout() }
            retryBtn.setOnClickListener {
                errorLayout.visibility = View.GONE
                viewModel.loadMemesList()
            }
            showBtnRadioGroub.setOnClickListener {
                layoutRadioBtn.visibility = View.VISIBLE
                cancelBtn.visibility = View.VISIBLE
                showBtnRadioGroub.visibility = View.GONE

            }
            oneMemeRadio.setOnClickListener {
                hideRadioGroupLayout()
                viewModel.setOneMeme()
            }
            multiMemeRadio.setOnClickListener {
                hideRadioGroupLayout()
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

    private fun hideRadioGroupLayout() {
        binding.apply {
            layoutRadioBtn.visibility = View.GONE
            cancelBtn.visibility = View.GONE
            showBtnRadioGroub.visibility = View.VISIBLE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null

    }
}