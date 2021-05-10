package uz.fintech.uzbankcard.ui.singin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.BiFunction
import uz.fintech.uzbankcard.utils.PreferenceManager
import uz.ruyobqurilish.myruyobqurilish.R
import uz.ruyobqurilish.myruyobqurilish.databinding.SinginFragmentBinding
import uz.ruyobqurilish.myruyobqurilish.ui.HomeActivity

class SinginFragment :
    Fragment(R.layout.singin_fragment){

    private val preference by lazy { PreferenceManager.instanse(requireContext()) }
    private var anim: Animation? = null
    private var sininBinding:SinginFragmentBinding?=null
    private val navController by lazy { Navigation.findNavController(requireActivity(),R.id.splash_nav) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val binding=SinginFragmentBinding.bind(view)
        sininBinding=binding

        anim = AnimationUtils.loadAnimation(requireContext(), R.anim.anim)
           singInAnimation()

        sininBinding!!.toolbarSingin.setNavigationOnClickListener {
            requireActivity().finish()

        }
        sininBinding!!.gbNextSingin.setOnClickListener {

           Observable.combineLatest(
                sininBinding!!.gbNameSingin.textChanges(),
                sininBinding!!.gbPassSingin.textChanges(),
                BiFunction< CharSequence, CharSequence,Boolean>(this::isValidet)
           ).subscribe()
            if (sininBinding!!.gbNameSingin.text.toString().equals(preference.isSingupName) &&
                sininBinding!!.gbPassSingin.text.toString().equals(preference.isSingupPassword)){
               if (!sininBinding!!.gbNameSingin.text.isNullOrEmpty() &&
                   !sininBinding!!.gbPassSingin.text.isNullOrEmpty() ){
                   Intent(requireContext(),HomeActivity::class.java).apply {
                       startActivity(this)
                   }
                   requireActivity().finish()
               }else{
                   Toast.makeText(requireContext(), "parolni kiriting", Toast.LENGTH_SHORT).show()
               }
            }else{
                Toast.makeText(requireContext(), "Ism yoki parol xato", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun singInAnimation() {
        sininBinding!!.gbNameSingin.animation=anim
        sininBinding!!.gbNextSingin.animation=anim
        sininBinding!!.gbPassSingin.animation=anim
    }

    private fun isValidet(
        charSequenceName: CharSequence,
        charSequencePass: CharSequence
        ): Boolean {
        if (charSequenceName.toString().equals(preference.isSingupName)) sininBinding!!.tilSinginName.error=null else sininBinding!!.tilSinginName.error=getString(R.string.error_singin_name)
        if (charSequencePass.toString().equals(preference.isSingupPassword))   sininBinding!!.tilSinginPass.error=null  else  sininBinding!!.tilSinginPass.error=getString(R.string.error_singin_pass)
        return charSequenceName.isNotEmpty()&&charSequencePass.isNotEmpty()
    }


}