package com.example.entry

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class EntryViewModel @Inject constructor(

): ViewModel() {
    private val _emailAddress = MutableStateFlow("")
    val emailAddress = _emailAddress.asStateFlow()

    private val _emailIsValid = MutableStateFlow(true)
    val emailIsValid = _emailIsValid.asStateFlow()

    private val _shouldTheScreenAppear = MutableStateFlow(false)
    val shouldTheScreenAppear = _shouldTheScreenAppear.asStateFlow()

    private val _password: MutableStateFlow<String> = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun enterEmail(str: String) {
        _emailAddress.value = str
    }

    fun changeEmailIsValid(boolean: Boolean) {
        _emailIsValid.value = boolean
    }

    fun changeScreenAppear(boolean: Boolean) {
        _shouldTheScreenAppear.value = boolean
    }

    fun enterPassword(number: String) {
        _password.value = number
    }
}