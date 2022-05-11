package com.brandonbryant.carfax.viewmodels



import android.content.Context
import androidx.lifecycle.ViewModel
import com.brandonbryant.carfax.db.RestApi
import com.brandonbryant.carfax.db.VehicleDbTable
import com.brandonbryant.carfax.models.Vehicle
import io.reactivex.rxjava3.core.Observable

lateinit var vehicleDbTable: VehicleDbTable
class MainViewModel : ViewModel() {


    fun retrieveVehicleList (context: Context): MutableList<Vehicle> {
        vehicleDbTable = VehicleDbTable(context)
        return vehicleDbTable.retrieveVehiclesList() as MutableList
    }

    fun requestVehiclesFromJSon (): Observable<List<Vehicle>> {
        val api = RestApi()
        return Observable.create {
            subscriber ->
            val callResponse = api.getListings()
            val response = callResponse.execute()
            if (response.isSuccessful) {
                val listings = response.body()!!.listings.map {
                    with (it) {
                        Vehicle(
                            id = vin,
                            photo = images.firstPhoto.large,
                            year = year,
                            make = make,
                            model = model,
                            trim = trim,
                            price = currentPrice,
                            mileage = mileage,
                            dealerNumber = dealer.phone,
                            location = "${dealer.city}, ${dealer.state}",
                            interiorColor = interiorColor,
                            exteriorColor = exteriorColor,
                            driveType = drivetype,
                            transmission = transmission,
                            engine = engine,
                            bodyStyle = bodytype,
                            fuelType = fuel
                        )
                    }
                }
                saveVehiclesToDb(listings)
                subscriber.onNext(listings)
                subscriber.onComplete()
            } else {
                subscriber.onError(Throwable(response.message()))
            }
        }
    }

    private fun saveVehiclesToDb(vehicles: List<Vehicle>) {
        vehicleDbTable.saveVehicles(vehicles)

    }
}
