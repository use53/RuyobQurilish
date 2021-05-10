package uz.ruyobqurilish.myruyobqurilish.splashui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import uz.fintech.uzbankcard.utils.PreferenceManager
import uz.mybiometric.firebasemikit.splash.first.FirstFragment
import uz.mybiometric.firebasemikit.splash.two.TwoFragment
import uz.ruyobqurilish.myruyobqurilish.R
import uz.ruyobqurilish.myruyobqurilish.databinding.VpFragmentBinding
import uz.ruyobqurilish.myruyobqurilish.registratsiya.RegistratsiyaActivity
import uz.ruyobqurilish.myruyobqurilish.splashui.three.ThreeFragment
import uz.ruyobqurilish.myruyobqurilish.ui.HomeActivity

class VpFragment:Fragment(R.layout.vp_fragment), TabLayoutMediator.TabConfigurationStrategy {

    private val preferense by lazy { PreferenceManager.instanse(requireContext()) }
    private val fragment by lazy { mutableListOf<Fragment>()
        .apply {
            this.add(FirstFragment())
            this.add(TwoFragment())
            this.add(ThreeFragment())
        }}
    private  var mediator:TabLayoutMediator?=null
    private val adapter by lazy { ViewPagerListAdapter(requireActivity(),fragment) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
         val binding= VpFragmentBinding.bind(view)

        if (preferense.isCardSaveBoolean){
            Intent(requireContext(),RegistratsiyaActivity::class.java)
                .apply { startActivity(this) }
            requireActivity().finish()

        }else{
            binding.viewPager.adapter=adapter
            mediator=TabLayoutMediator(binding.tabLayout,binding.viewPager,this)
            mediator!!.attach()
        }

    }

    override fun onConfigureTab(tab: TabLayout.Tab, position: Int) {

    }




}