package com.brandonbryant.carfax.db

import retrofit2.Call
import retrofit2.http.GET

interface CarfaxAPIHelper {
    @GET ("/assignment.json")
    fun getListings(): Call<CARFAXApiResponse>

}


class CARFAXApiResponse(val listings: List<CARFAXListingDataResponse>)
class CARFAXListingDataResponse (
    val vin: String,
    val images: CARFAXImagesResponse,
    val year: String,
    val make: String,
    val model: String,
    val trim: String,
    val currentPrice: String,
    val mileage: String,
    val dealer: CARFAXDealerResponse,
    val interiorColor: String,
    val exteriorColor: String,
    val drivetype: String,
    val transmission: String,
    val engine: String,
    val bodytype: String,
    val fuel: String
)
class CARFAXImagesResponse (val firstPhoto: CARFAXFirstPhotoResponse)
class CARFAXFirstPhotoResponse (val large: String)
class CARFAXDealerResponse (
    val phone: String,
    val city: String,
    val state: String
)