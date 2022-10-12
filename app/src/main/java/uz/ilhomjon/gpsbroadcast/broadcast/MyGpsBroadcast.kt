package uz.ilhomjon.gpsbroadcast.broadcast

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.location.LocationManager
import uz.ilhomjon.gpsbroadcast.utils.MyData.gpsOnOffLiveData

class MyGpsBroadcast : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        if (LocationManager.PROVIDERS_CHANGED_ACTION.equals(intent.getAction())) {

            val locationManager =
                context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            val isNetworkEnabled =
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isGpsEnabled || isNetworkEnabled) {
                //location is enabled
                gpsOnOffLiveData.postValue(true)
            } else {
                //location is disabled
                gpsOnOffLiveData.postValue(false)
            }
        }
    }

}