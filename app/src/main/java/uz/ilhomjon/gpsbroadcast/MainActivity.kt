package uz.ilhomjon.gpsbroadcast

import android.content.Intent
import android.content.IntentFilter
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import uz.ilhomjon.gpsbroadcast.broadcast.MyGpsBroadcast
import uz.ilhomjon.gpsbroadcast.databinding.ActivityMainBinding
import uz.ilhomjon.gpsbroadcast.utils.MyData.gpsOnOffLiveData


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //broadcast gps
        gpsBroadcast()

        //gps ni yoqish va o'chirish
        binding.tvGpsBtn.setOnClickListener {
            gpsOnOff()
        }
    }

    private fun gpsBroadcast() {
        val myGpsBroadcast = MyGpsBroadcast()
        val filter = IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION)
        filter.addAction(Intent.ACTION_PROVIDER_CHANGED)
        this.registerReceiver(myGpsBroadcast, filter)

        binding.tvGpsBtn.isClickable = false
        gpsOnOffLiveData.observe(this){
            binding.switchGps.isChecked = it
        }
    }

    fun gpsOnOff(){
        val provider: String =
            Settings.Secure.getString(contentResolver, Settings.Secure.LOCATION_PROVIDERS_ALLOWED)

        if (provider.contains("gps") == false) { //if gps is disabled
            val poke = Intent()
            poke.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider")
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE)
            poke.data = Uri.parse("3")
            sendBroadcast(poke)
        }
    }


}