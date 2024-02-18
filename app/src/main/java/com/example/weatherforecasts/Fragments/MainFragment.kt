package com.example.weatherforecasts.Fragments

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.weatherforecasts.Extencion.isPermissionGranted
import com.example.weatherforecasts.Extencion.registPermission
import com.example.weatherforecasts.R
import com.example.weatherforecasts.ViewModels.MainViewModel
import com.example.weatherforecasts.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registPermission(Manifest.permission.ACCESS_FINE_LOCATION)

        with(binding) {
            butGPS.setOnClickListener {
               handlePermissions()
            }

            butCityName.setOnClickListener {
                mainViewModel.updateLocationFromAddress(
                    cityName.text.toString()
                )
                cityName.setText("")
                //Toast.makeText(activity, cityName.text, Toast.LENGTH_LONG).show()
            }

            butGoWeatList.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_weatherListFragment)
            }

            butGoChart.setOnClickListener {
                findNavController().navigate(R.id.action_mainFragment_to_weatherChartFragment)
            }
        }
    }

    private fun handlePermissions(){
        if (isPermissionGranted(Manifest.permission.ACCESS_FINE_LOCATION))
            mainViewModel.updateLocation()
        else
            Toast.makeText(activity, R.string.request_for_permission, Toast.LENGTH_LONG)
                .show()
    }
}