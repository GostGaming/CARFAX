package com.brandonbryant.carfax.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.brandonbryant.carfax.databinding.DetailsFragmentBinding
import com.brandonbryant.carfax.models.Vehicle
import com.brandonbryant.carfax.viewmodels.CarfaxUtils
import com.brandonbryant.carfax.viewmodels.DetailsViewModel
import com.brandonbryant.carfax.viewmodels.EXTRA_ID
import com.brandonbryant.carfax.viewmodels.setImage
import com.brandonbryant.carfax.viewmodels.vehicleDbTable

class DetailsFragment: Fragment() {
    companion object {
        fun newInstance() = DetailsFragment()
    }

    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailsViewModel
    private lateinit var vehicleId: String
    private lateinit var vehicle: Vehicle
    private lateinit var utils: CarfaxUtils
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Log.d("DetailsFragment", "Creating details fragment view")
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[DetailsViewModel::class.java]
        vehicleId = activity?.intent?.extras?.getString(EXTRA_ID) ?: ""
        Log.d("DetailsFragment", "Details fragment created")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        utils = CarfaxUtils(requireContext())
        populateVehicleInfo()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun populateVehicleInfo () {
        try {
            vehicle = vehicleDbTable.getSingleVehicle(vehicleId)
            with(binding) {
                vehiclePhoto.setImage(vehicle.photo)
                yearMakeModelTrim .text = vehicle.toString()
                priceMileage.text = utils.formatPriceMileage(vehicle.price, vehicle.mileage)
                locationText.text = vehicle.location
                extColorText.text = vehicle.exteriorColor
                intColorText.text = vehicle.interiorColor
                driveTypeText.text = vehicle.driveType
                transmissionText.text = vehicle.transmission
                bodyStyleText.text = vehicle.bodyStyle
                engineText.text = vehicle.engine
                fuelText.text = vehicle.fuelType
                detailDealerButton.setOnClickListener { makeCall(vehicle.dealerNumber) }
            }
        } catch (ex: Exception) {
            Toast.makeText(context, "Exception populating vehicle info..", Toast.LENGTH_LONG).show()
            Log.e("DetailsFragment", "Exception populating vehicle info:", ex)
        }
    }

    private fun makeCall (number: String) {
        utils.makePhoneCall(number)
    }


}

