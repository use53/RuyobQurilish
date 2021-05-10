package uz.mybiometric.firebasemikit.splash.tree

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class ThreeViewModel (val app:Application):AndroidViewModel(app){

    private  var _connect=MutableLiveData<Boolean>()
    fun Connection(){
        val connection=InternetConnection(app.applicationContext)
        connection.Connection()
        _connect=connection.connectionLd()
    }

    val connect:LiveData<Boolean>
    get() = _connect
}