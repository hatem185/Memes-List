package com.example.memes.ui.memeslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.memes.model.Meme
import com.example.memes.model.MemeApi
import com.example.memes.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MemesListViewModel @Inject constructor(private val memeApi: MemeApi) : ViewModel() {
    private var _memesList: MutableLiveData<Resource<List<Meme>>> =
        MutableLiveData(Resource.Loading())
    val memesList: LiveData<Resource<List<Meme>>> get() = _memesList

    init {
        loadMemesList()
    }

    fun loadMemesList() {
        viewModelScope.launch { _memesList.value = memeApi.getMemesList((5..50).random()) }
    }

    fun setOneMeme() {
        viewModelScope.launch { _memesList.value = memeApi.getMemesList(1) }
    }


}