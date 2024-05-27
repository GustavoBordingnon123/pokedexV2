package com.example.tst

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Room
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val db: AppDatabase = Room.databaseBuilder(
        application,
        AppDatabase::class.java, "user-database"
    ).build()

    fun login(username: String, password: String): LiveData<Boolean> {
        val loginResult = MutableLiveData<Boolean>()
        viewModelScope.launch(Dispatchers.IO) {
            val user = db.userDao().getUserByUsername(username)
            if (user != null && user.password == password) {
                loginResult.postValue(true)
            } else {
                loginResult.postValue(false)
            }
        }
        return loginResult
    }
}
