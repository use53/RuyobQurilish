package ru.examples.bilimdonuz.ui.account

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.google.firebase.database.FirebaseDatabase
import uz.ruyobqurilish.myruyobqurilish.ui.account.Account


class AccountViewModel(app:Application):AndroidViewModel(app){

    private val firebase by lazy {
       FirebaseDatabase.getInstance().getReference("account") }

    fun accountRead(name:String,home:String,callnumber:String){
        val account=Account(name,home,callnumber)
        firebase.child("${name}${callnumber}")
            .setValue(account)
    }


}