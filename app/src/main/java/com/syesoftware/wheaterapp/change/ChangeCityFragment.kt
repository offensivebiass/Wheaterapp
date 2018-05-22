package com.syesoftware.wheaterapp.change

import android.content.ContentValues.TAG
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.AutocompleteFilter
import com.syesoftware.wheaterapp.R
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import kotlinx.android.synthetic.*


class ChangeCityFragment : Fragment() {

    lateinit var autocompleteFragment : PlaceAutocompleteFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_city, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        autocompleteFragment = (activity!!.fragmentManager.findFragmentById(R.id.place_autocomplete_fragment) as? PlaceAutocompleteFragment)!!

        val typeFilter = AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES).build()
        autocompleteFragment.setFilter(typeFilter)

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {

            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: " + place.name)

                val latitude = place.latLng.latitude.toString()
                val longitude = place.latLng.longitude.toString()

                val bundle = Bundle()
                bundle.putString("LATITUDE", latitude)
                bundle.putString("LONGITUDE", longitude)

                NavHostFragment.findNavController(this@ChangeCityFragment).navigate(R.id.action_changeCityFragment_to_homeFragment, bundle)

            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        activity!!.fragmentManager.beginTransaction().remove(autocompleteFragment).commit();
    }

}
