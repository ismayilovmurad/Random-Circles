package com.martiandeveloper.randomcircles.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.martiandeveloper.randomcircles.utils.Event

class MainViewModel : ViewModel() {

    //########## Number EditText text
    val number: MutableLiveData<String> by lazy { MutableLiveData<String>() }


    //########## Draw MaterialButton click
    private var _drawMBTNClick = MutableLiveData<Event<Boolean>>()
    val drawMBTNClick: LiveData<Event<Boolean>>
        get() = _drawMBTNClick

    fun onDrawMBTNClick() {
        _drawMBTNClick.value = Event(true)
    }

}
