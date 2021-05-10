package uz.ruyobqurilish.myruyobqurilish.registratsiya.entry

import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import uz.ruyobqurilish.myruyobqurilish.R
import uz.ruyobqurilish.myruyobqurilish.databinding.RigisentryFragmentBinding

class RigisterEntryFragment:Fragment(R.layout.rigisentry_fragment){


    private val navController by lazy {
        Navigation.findNavController(requireActivity(),R.id.splash_nav)
    }
    private var rregbinding:RigisentryFragmentBinding?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=RigisentryFragmentBinding.bind(view)
        rregbinding=binding
        itemAnimation()
        setupOnClick()

    }

    private fun setupOnClick() {
        rregbinding!!.gbRig.setOnClickListener {
            navController.navigate(R.id.singup_navigation)
        }
        rregbinding!!.gbOpen.setOnClickListener {
            navController.navigate(R.id.singin_navigation)
        }
    }

    private fun itemAnimation() {
        val anim=AnimationUtils.loadAnimation(requireContext(),R.anim.anim)
        rregbinding!!.gbOpen.animation=anim
        rregbinding!!.gbRig.animation=anim

        requireActivity()
            .onBackPressedDispatcher
            .addCallback(viewLifecycleOwner,object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().finish()
                }

            })
    }
}