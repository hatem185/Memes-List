package com.example.memes.ui.memessubreditlist

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
class MemesSubreditListViewModel @Inject constructor(private val memesApi: MemeApi) : ViewModel() {
    private val _memesSubreditList: MutableLiveData<Resource<List<Meme>>> =
        MutableLiveData(Resource.Loading())
    val memesSubreditList: LiveData<Resource<List<Meme>>> get() = _memesSubreditList

    init {

    }

    fun loadSubreditMemesList(subredit: String) {
        viewModelScope.launch {
            _memesSubreditList.value = memesApi.getMemeListBySubredit(subredit, (5..50).random())
        }
    }
}