package ru.examples.bilimdonuz.ui.account

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import uz.ruyobqurilish.myruyobqurilish.R
import uz.ruyobqurilish.myruyobqurilish.databinding.AccountFragmentBinding

class AccountFragment ():Fragment(R.layout.account_fragment){

    private var accountbinding:AccountFragmentBinding?=null
    private val accountViewModel:AccountViewModel by activityViewModels()
    private val navController by lazy { Navigation.findNavController(requireActivity(),R.id.home_fragment_navigation) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                val binding=AccountFragmentBinding.bind(view)
        accountbinding=binding
        binding.accountToolbar.setNavigationOnClickListener {
            navController.popBackStack()
        }



        binding.btSave.setOnClickListener {
             val callnuber=binding.edAccountAge.text.toString()
             val name=binding.edAccountName.text.toString()
             val home=binding.edAccountSurname.text.toString()
            accountViewModel.accountRead(name,home,callnuber)
              navController.popBackStack()
        }

    }

}