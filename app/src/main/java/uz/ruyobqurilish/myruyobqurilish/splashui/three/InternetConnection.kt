package uz.mybiometric.firebasemikit.splash.tree

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class InternetConnection (ctx:Context):CoroutineScope{

    private val job= SupervisorJob()
    override val coroutineContext: CoroutineContext=job

    private val firabaseDb by lazy { FirebaseDatabase.getInstance().getReference(".info/connected") }
     private val _connectionLd=MutableLiveData<Boolean>()


    fun Connection(){
       launch {
           firabaseDb.addValueEventListener(object :ValueEventListener{
               override fun onCancelled(error: DatabaseError) {
                   Log.d("TAG", "onCancelled: ${error.toException()}")
               }

               override fun onDataChange(snapshot: DataSnapshot) {
                   val connect=snapshot.getValue(Boolean::class.java)?:false
                   _connectionLd.postValue(connect)
               }
           })

       }
    }

    fun connectionLd(): MutableLiveData<Boolean> {
        return _connectionLd
    }

}