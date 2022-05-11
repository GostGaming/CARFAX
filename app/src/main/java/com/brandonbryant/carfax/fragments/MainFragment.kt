package com.brandonbryant.carfax.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.brandonbryant.carfax.R
import com.brandonbryant.carfax.activities.DetailsActivity
import com.brandonbryant.carfax.databinding.MainFragmentBinding
import com.brandonbryant.carfax.viewmodels.EXTRA_ID
import com.brandonbryant.carfax.viewmodels.MainAdapter
import com.brandonbryant.carfax.viewmodels.MainViewModel
import com.google.android.material.snackbar.Snackbar
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers


class MainFragment : Fragment() {
    private var subscriptions:MutableList<Disposable> = mutableListOf()

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var mainFragmentBinding: MainFragmentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainFragmentBinding = MainFragmentBinding.bind(view)
        val mainRv = mainFragmentBinding?.main ?: return
        val ctx = requireContext()
        mainRv.setHasFixedSize(true)
        mainRv.layoutManager = LinearLayoutManager(context)
        val vehicleList = viewModel.retrieveVehicleList(ctx)
        if (mainRv.adapter == null) {
            val adapter = MainAdapter(vehicleList, ctx) { navigateToDetails(it) }
            mainRv.adapter = adapter
            if (vehicleList.isEmpty()) {
                setupListingSub(adapter)
            }
        }
        if (savedInstanceState != null) return

    }

    private fun navigateToDetails(vehicleId: String) {
        Log.d("MainFragment", "Attempting to navigate to details..")
        val intent = Intent(activity, DetailsActivity::class.java).putExtra(
            EXTRA_ID, vehicleId
        )
        requireActivity().startActivity(intent)
    }

    override fun onResume() {
        super.onResume()
        subscriptions = mutableListOf()
    }

    override fun onPause() {
        super.onPause()
        subscriptions.clear()
    }

    private fun setupListingSub(adapter: MainAdapter) {
        val sub = viewModel.requestVehiclesFromJSon()
            .subscribeOn(Schedulers.io())
            .subscribe(
                {
                    listing ->
                    adapter.addListings(listing)
                    Log.d("MainFragment", "Added listings $listing")
                },
                {error ->
                    Snackbar.make(mainFragmentBinding!!.main, error.message ?: "Unknown error", Snackbar.LENGTH_LONG).show()
                    Log.e("MainFragment", "Failed to setup listing sub", error)
                }
            )
        subscriptions.add(sub)
    }



}