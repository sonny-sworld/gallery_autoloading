package com.paperkite.test.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paperkite.test.data.OperationCallback
import com.paperkite.test.model.Cat
import com.paperkite.test.model.Repository

class MainViewModel (private val repository: Repository): ViewModel(){

    private val _catList = MutableLiveData<List<Cat>>().apply { value = emptyList() }
    val catList: LiveData<List<Cat>> = _catList

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError // TODO SHOW ERROR

    init { loadCat() }

    fun loadCat() {
        _isViewLoading.value = true
        repository.fetchCat(object: OperationCallback<Cat>{
            override fun onSuccess(data: List<Cat>?) {
                _isViewLoading.value = false
                val catArray = data as ArrayList<Cat>
                // ADD EMPTY DATA AT FRONT AND END
                catArray.add(data.size,Cat(null,null,null,null))
                catArray.add(0,Cat(null,null,null,null))
                _catList.value = catArray
            }
            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error!!
            }
        })
    }
}