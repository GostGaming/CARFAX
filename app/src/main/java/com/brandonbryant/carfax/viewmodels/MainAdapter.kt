package com.brandonbryant.carfax.viewmodels

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.brandonbryant.carfax.R
import com.brandonbryant.carfax.databinding.VehicleCardBinding
import com.brandonbryant.carfax.models.Vehicle
import com.squareup.picasso.Picasso

const val EXTRA_ID = "package com.brandonbryant.carfax.EXTRA"

class MainAdapter (private var vehicles: MutableList<Vehicle>, private val context: Context,
private var onItemClicked: ((vehicle:String) -> Unit)): RecyclerView.Adapter<MainAdapter.VehicleViewHolder>() {
    inner class VehicleViewHolder(private val vehicleCardBinding: VehicleCardBinding): RecyclerView.ViewHolder(vehicleCardBinding.root) {
        fun bind(vehicle: Vehicle) = vehicleCardBinding.apply {
            vehiclePhoto.setImage(vehicle.photo)
            yearMakeModelTrim.text = vehicle.toString()
            priceMileage.text = utils.formatPriceMileage(vehicle.price, vehicle.mileage)
            location.text = vehicle.location
            root.setOnClickListener { onItemClicked(vehicle.id) }
            callDealerButton.setOnClickListener { makeCall(vehicle.dealerNumber)}
        }
    }
    lateinit var utils: CarfaxUtils
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val binder = VehicleCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        utils = CarfaxUtils(context)
        return VehicleViewHolder(binder)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        holder.bind(vehicles[position])

    }

    fun makeCall(number: String) {
        utils.makePhoneCall(number)
    }

    fun addListings(listings: List<Vehicle>) {
        vehicles.addAll(listings)
        notifyItemRangeInserted(0, vehicles.size)
    }

    override fun getItemCount() = vehicles.size
}
fun ImageView.setImage(url: String) {
    Picasso.get()
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .error(R.drawable.ic_error)
        .into(this)
}

