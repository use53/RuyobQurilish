package uz.ruyobqurilish.myruyobqurilish.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import uz.ruyobqurilish.myruyobqurilish.R
import uz.ruyobqurilish.myruyobqurilish.databinding.HelpLayoutBinding

class HelpFragment:Fragment(R.layout.help_layout){
    private val navController by lazy {
        Navigation.findNavController(requireActivity(), R.id.home_fragment_navigation)
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=HelpLayoutBinding.bind(view)

        binding.toolbarHelp.setNavigationOnClickListener {
           navController.popBackStack()
        }
    }
}