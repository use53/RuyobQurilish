package uz.fintech.uzbankcard.ui.singup

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.jakewharton.rxbinding4.widget.checkedChanges
import com.jakewharton.rxbinding4.widget.textChanges
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.functions.Function4

import uz.fintech.uzbankcard.utils.PhoneNumberTextWatcher
import uz.ruyobqurilish.myruyobqurilish.R
import uz.ruyobqurilish.myruyobqurilish.databinding.SingupFragmentBinding
import uz.ruyobqurilish.myruyobqurilish.ui.HomeActivity

class SingUpFragment : Fragment(R.layout.singup_fragment), View.OnClickListener {

    private val navController by lazy { Navigation.findNavController(requireActivity(), R.id.splash_nav) }
    private val singUpViewModel: SingUpViewModel by activityViewModels()
    private var anim: Animation? = null
    private var callnum: String? = null
    private var name: String? = null
    private var password: String? = null
    private var singBinding:SingupFragmentBinding?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=SingupFragmentBinding.bind(view)
        singBinding=binding
        anim = AnimationUtils.loadAnimation(requireContext(), R.anim.anim)
        itemGetEditText()
        singupAimation()
        rxBindingEditText()
        itemMask()
    }

    private fun itemGetEditText() {
        singBinding!!.gbNextSingup.setOnClickListener(this)
    }

    private fun rxBindingEditText() {
        Observable.combineLatest(
            singBinding!!.gbCallSingup.textChanges(),
            singBinding!!.gbNameSingup.textChanges(),
            singBinding!!.gbPassSingup.textChanges(),
            singBinding!!.mchbTerms.checkedChanges(),
            Function4<CharSequence, CharSequence, CharSequence, Boolean, Boolean>
            { phone, name, password, isChecked ->
                return@Function4 phone.isNotEmpty()
                        && name.isNotEmpty() && password.isNotEmpty() && isChecked
            }
        )
            .doOnNext {
                singBinding!!.gbNextSingup.isEnabled = it
            }
            .subscribe()
    }

    private fun isValidate(
        phone: CharSequence,
        name: CharSequence,
        password: CharSequence,
        isChecked: Boolean
    ): Boolean {
        if (phone.length < 12) singBinding!!.tilSingupCall.error =
            getString(R.string.error_phone_req) else singBinding!!.tilSingupCall.error = null
        if (name.length < 4) singBinding!!.tilSingupName.error =
            getString(R.string.error_name_req) else singBinding!!.tilSingupName.error = null
        if (password.length < 8) singBinding!!.tilSinupPass.error =
            getString(R.string.error_password_req) else singBinding!!.tilSinupPass.error = null
        if (!isChecked) singBinding!!.mchbTerms.error =
            getString(R.string.error_terms_of_use_agreement) else singBinding!!.mchbTerms.error = null
        return true
    }

    private fun itemMask() {
        val phone = PhoneNumberTextWatcher(singBinding!!.gbCallSingup)
        singBinding!!.gbCallSingup.addTextChangedListener(phone)
    }

    private fun singupAimation() {
        singBinding!!.gbNameSingup.animation = anim
        singBinding!!.gbCallSingup.animation = anim
        singBinding!!.gbPassSingup.animation = anim
        singBinding!!.toolbarSingup.setNavigationOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onClick(v: View) {
        Observable.combineLatest(
            singBinding!!.gbCallSingup.textChanges(),
            singBinding!!.gbNameSingup.textChanges(),
            singBinding!!.gbPassSingup.textChanges(),
            singBinding!!.mchbTerms.checkedChanges(),
            Function4(this::isValidate)
        ).subscribe()

        callnum = singBinding!!.gbCallSingup.text.toString()
        name = singBinding!!.gbNameSingup.text.toString()
        password = singBinding!!.gbPassSingup.text.toString()


        if (callnum!!.length > 8 && name!!.length > 4 && password!!.length > 7) {
            singUpViewModel.singUpSave(
                callnum.toString(),
                name.toString(), password.toString()
            )
            Intent(requireContext(),HomeActivity::class.java)
                .apply { startActivity(this) }

        }
    }
}