package uz.ruyobqurilish.myruyobqurilish.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapController
import org.osmdroid.views.overlay.Marker
import uz.ruyobqurilish.myruyobqurilish.R
import uz.ruyobqurilish.myruyobqurilish.databinding.HousePhotoFragmentBinding
import uz.ruyobqurilish.myruyobqurilish.ui.home.HomeViewModel


@Suppress("DEPRECATION")
class HousePhotoFragment :Fragment(R.layout.house_photo_fragment){


    private var houseBinding:HousePhotoFragmentBinding?=null
    private val homeViewModel:HomeViewModel by activityViewModels()
    private var mapController: MapController?=null
    private val navController by lazy { Navigation.findNavController(requireActivity(),R.id.home_fragment_navigation) }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding=HousePhotoFragmentBinding.bind(view)
        ZoomHelper.Companion.addZoomableView(binding.imageHouse)
        houseBinding=binding
        binding.tollbarHouse.setNavigationOnClickListener {
            navController.popBackStack()
        }
        homeViewModel.onclickLd.observe(viewLifecycleOwner, Observer {
            Glide.with(view)
                .load(it.image)
                .into(binding.imageHouse)
            binding.tvName.text=it.callnumber
            binding.tvTitle.text=it.title
            itemGrahic(it.lat,it.lon,it.name)
        })
    }

    private fun itemGrahic(lat:Long, lon: Long, name: String) {
        Configuration.getInstance().load(requireContext(),
            PreferenceManager.getDefaultSharedPreferences(requireContext()))

        houseBinding!!.mapViewHouse.setTileSource(TileSourceFactory.MAPNIK)
        houseBinding!!.mapViewHouse.setBuiltInZoomControls(true)
        houseBinding!!.mapViewHouse.setMultiTouchControls(true)
        houseBinding!!.mapViewHouse.invalidate()



        mapController = houseBinding!!.mapViewHouse.controller as MapController?

        mapController!!.setZoom(9)
        val list=ArrayList<GeoPoint>()
        val gPt = GeoPoint(lat.toDouble(),lon.toDouble())

        list.add(gPt)

        mapController!!.setCenter(gPt)
        houseBinding!!.mapViewHouse.mapOrientation=4F
        val startmarker= Marker(houseBinding!!.mapViewHouse)



        startmarker.position=gPt


        startmarker.title=name

        startmarker.isDraggable=true
        startmarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)


        houseBinding!!.mapViewHouse.overlays.add(startmarker)
    }


}