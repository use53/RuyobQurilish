package uz.ruyobqurilish.myruyobqurilish.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.launch
import uz.ruyobqurilish.myruyobqurilish.model.HomeModel

class HomeViewModel :ViewModel(){

    private val firebaseDb by lazy {
        FirebaseDatabase.getInstance().getReference("homeitem")
    }
    private val _fireabseLd=MutableLiveData<MutableList<HomeModel>>()
    private val _onclickLd=MutableLiveData<HomeModel>()
    private var corrent=false
    private val status=MutableLiveData<NetworkStatus>()
    fun readFirebase(){
        if (!corrent){
        viewModelScope.launch {
            status.postValue(NetworkStatus.Loading)
            val homemodel= mutableListOf<HomeModel>()
            firebaseDb.addValueEventListener(object :ValueEventListener{
                override fun onCancelled(error: DatabaseError) {
                    Log.d("TAG", "onCancelled: ${error.toException()}")
                    status.postValue(NetworkStatus.Error)
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val snp=it.getValue(HomeModel::class.java)
                       homemodel.add(snp!!)
                        status.postValue(NetworkStatus.Success)
                    }
                    _fireabseLd.postValue(homemodel)
                }
            })
        }
            corrent=true
        }
    }
    fun onClickItem(homeModel: HomeModel){
        _onclickLd.postValue(homeModel)
    }
    val onclickLd:LiveData<HomeModel>
    get() = _onclickLd

    val firebaseLd:LiveData<MutableList<HomeModel>>
    get() = _fireabseLd

    val networkstatus:LiveData<NetworkStatus>
    get() = status
}


sealed class NetworkStatus{
    object Error:NetworkStatus()
    object Success:NetworkStatus()
    object Loading:NetworkStatus()
}