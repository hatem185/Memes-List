package com.example.memes.ui.memessubreditlist

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.viewModels
import com.example.memes.R
import com.example.memes.databinding.FragmentMemesSubreditListBinding
import com.example.memes.ui.memeslist.AdapterMemes
import com.example.memes.util.Resource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MemesSubreditList : Fragment(R.layout.fragment_memes_subredit_list) {
    private var _binding: FragmentMemesSubreditListBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<MemesSubreditListViewModel>()

    @Inject
    lateinit var adapter: AdapterMemes

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMemesSubreditListBinding.bind(view)
        val options = arrayOf("dankmemes", "me_irl", "memes", "wholesomememes")
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, options)
        binding.apply {
            subreditDropMenu.adapter = spinnerAdapter
            subreditDropMenu.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, v: View?, pos: Int, id: Long) {
                    val selectedItem = parent.getItemAtPosition(pos) as String
                    viewModel.loadSubreditMemesList(selectedItem)
                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
            retryBtn.setOnClickListener {
                errorLayout.visibility = View.GONE
                viewModel.loadSubreditMemesList(subreditDropMenu.selectedItem.toString())
            }
            memeSubreditListView.adapter = adapter
            viewModel.memesSubreditList.observe(viewLifecycleOwner) { resource ->
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}