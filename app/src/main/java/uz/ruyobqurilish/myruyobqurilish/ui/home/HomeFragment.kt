package uz.ruyobqurilish.myruyobqurilish.ui.home

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline
import uz.ruyobqurilish.myruyobqurilish.R
import uz.ruyobqurilish.myruyobqurilish.adapter.HomeAdapter
import uz.ruyobqurilish.myruyobqurilish.databinding.HomeFragmentBinding
import uz.ruyobqurilish.myruyobqurilish.model.HomeModel
import uz.ruyobqurilish.myruyobqurilish.onclick.IOnClickListener

@Suppress("DEPRECATION")
class HomeFragment ():Fragment(R.layout.home_fragment), IOnClickListener,
    Toolbar.OnMenuItemClickListener {

    private val obsevable= Observer<NetworkStatus>{
        when(it){
            NetworkStatus.Loading->showLoading()
            NetworkStatus.Success->showSuccess()
            NetworkStatus.Error->showError()
        }
    }

    private fun showError() {

    }

    private fun showSuccess() {
        homeBinding!!.homeMotion.lottieNews.visibility=View.INVISIBLE
    }

    private fun showLoading() {
     homeBinding!!.homeMotion.lottieNews.visibility=View.VISIBLE
    }

    private var homeBinding:HomeFragmentBinding?=null
    private var mapController: MapController?=null
    private val homeAdapter by lazy { HomeAdapter(this) }
    private val homeViewModel:HomeViewModel by activityViewModels()
    private val navController by lazy {
        Navigation.findNavController(requireActivity(), R.id.home_fragment_navigation)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding= HomeFragmentBinding.bind(view)
        homeBinding=binding
         graphicItem()
        recHomeItem()
        tollbarItem()

    }

    private fun tollbarItem() {
        homeBinding!!.homeTollbar.inflateMenu(R.menu.menu)
        homeBinding!!.homeTollbar.setOnMenuItemClickListener(this)
    }

    private fun recHomeItem() {
        homeBinding!!.homeMotion.homeRec.adapter=homeAdapter
        homeViewModel.readFirebase()
        homeViewModel.networkstatus.observe(viewLifecycleOwner,obsevable)
        homeViewModel.firebaseLd.observe(viewLifecycleOwner, Observer {
            homeAdapter!!.submitList(it)
        })
    }

    private fun graphicItem() {
        Configuration.getInstance().load(requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext()))

        homeBinding!!.graphic.mapView.setTileSource(TileSourceFactory.MAPNIK)
        homeBinding!!.graphic.mapView.setBuiltInZoomControls(true)
        homeBinding!!.graphic.mapView.setMultiTouchControls(true)
        homeBinding!!.graphic.mapView.invalidate()



        mapController = homeBinding!!.graphic.mapView.controller as MapController?

        mapController!!.setZoom(9)
        val list=ArrayList<GeoPoint>()
        val gPt = GeoPoint(41.30779, 69.28067)

        list.add(gPt)

        mapController!!.setCenter(gPt)
        homeBinding!!.graphic.mapView.mapOrientation=4F
        val startmarker= Marker(homeBinding!!.graphic.mapView)



        startmarker.position=gPt


        startmarker.title="Ro'yob qurilish tashkiloti"

        startmarker.isDraggable=true
        startmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)


        homeBinding!!.graphic.mapView.overlays.add(startmarker)

    }

    override fun onClick(homeModel: HomeModel) {
        homeViewModel.onClickItem(homeModel)
          navController.navigate(R.id.house_photo_navigation)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if (item!!.itemId==R.id.menu_account){
           navController.navigate(R.id.account_navigation)
            return true
        }else{
            if(item.itemId==R.id.menu_help){
               navController.navigate(R.id.help_navigation)
            }
        }
       return true
    }
}