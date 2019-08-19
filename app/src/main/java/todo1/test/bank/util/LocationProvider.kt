package todo1.test.bank.util

import android.content.Context
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import todo1.test.bank.model.GpsCoordinates


/**
 * Location for NETWORK and GPS providers using @getLastKnownLocation and onNewLocationAvailable
 */
class LocationProvider {

    interface LocationCallback {
        fun onNewLocationAvailable(coordinates: GpsCoordinates)
    }

    companion object {
        fun requestSingleUpdate(context: Context, locationCallBack: LocationCallback) {
            val managerLocation = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGPSEnabled = managerLocation.isProviderEnabled(LocationManager.GPS_PROVIDER)
            val isNetworkEnabled = managerLocation.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (isGPSEnabled || isNetworkEnabled) {
                val criteria = Criteria()
                criteria.accuracy = Criteria.ACCURACY_FINE
                try {
                    var loc = managerLocation.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                    if (loc == null) {
                        loc = managerLocation.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    }
                    if (loc != null) {
                        locationCallBack.onNewLocationAvailable(GpsCoordinates(loc.latitude.toString(), loc.longitude.toString()))
                    } else {
                        managerLocation.requestSingleUpdate(criteria, object : LocationListener {
                            override fun onLocationChanged(location: Location) {
                                locationCallBack.onNewLocationAvailable(GpsCoordinates(location.latitude.toString(), location.longitude.toString()))
                            }

                            override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {

                            }

                            override fun onProviderEnabled(provider: String) {

                            }

                            override fun onProviderDisabled(provider: String) {

                            }

                        }, null)
                    }

                } catch (ex: SecurityException) {

                }
            }

        }
    }
}