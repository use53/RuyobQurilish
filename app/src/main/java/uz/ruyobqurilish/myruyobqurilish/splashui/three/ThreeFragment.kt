package uz.ruyobqurilish.myruyobqurilish.splashui.three

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.mybiometric.firebasemikit.splash.tree.ThreeViewModel
import uz.ruyobqurilish.myruyobqurilish.R
import uz.ruyobqurilish.myruyobqurilish.databinding.ThreeFragmentBinding
import uz.ruyobqurilish.myruyobqurilish.registratsiya.RegistratsiyaActivity
import uz.ruyobqurilish.myruyobqurilish.ui.HomeActivity

class ThreeFragment :Fragment(R.layout.three_fragment){

     private val threeViewModel:ThreeViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding=ThreeFragmentBinding.bind(view)

        binding.gbReport.setOnClickListener {
               binding.tvTreeTitle.visibility=View.INVISIBLE
             binding.lottieSplash.visibility=View.VISIBLE
            threeViewModel.Connection()
            threeViewModel.connect.observe(viewLifecycleOwner, Observer {
                if (it){
                    Intent(requireContext(),RegistratsiyaActivity::class.java).apply {
                        startActivity(this)
                    }
                    requireActivity().finish()
                }else{
                    Toast.makeText(requireContext(), "Internet bilan aloqa mavjud emas", Toast.LENGTH_SHORT).show()
                }
            })


        }
    }
}