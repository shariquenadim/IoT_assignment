package com.example.iotassignment

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.iotassignment.ui.theme.IotAssignmentTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.TimeZone
import java.util.logging.Logger

class MainActivity : ComponentActivity() {
    private lateinit var tvChannelName: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvMl: TextView
    private lateinit var tvTmp: TextView
    private lateinit var tvWar: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TextViews
        tvChannelName = findViewById(R.id.tvChannelName)
        tvMl=findViewById(R.id.ml)
        tvTmp=findViewById(R.id.tmp)
        tvTime=findViewById(R.id.time)
        tvWar=findViewById(R.id.wn)

        // Initialize other TextViews similarly
        var count=1;
        // Start fetching data from API every 20 seconds
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                ApiCall().fetchData(applicationContext){data->
                    tvChannelName.text=data.channel.name
                    var soilMoist=data.feeds[0].field1
                    var tmp=data.feeds[0].field2
                    tvTime.text=timeFormat(data.channel.createdAt)
                    tvMl.text="Soil Moisture level: $soilMoist"
                    tvTmp.text="Temperature: $tmp"

                    if(soilMoist.toInt() < 35){
                        tvWar.visibility=View.VISIBLE
                        tvWar.text="Water Needed..!!!"
                    }
                    else if(soilMoist.toInt() > 90){
                        tvWar.visibility=View.VISIBLE
                        tvWar.text="Flood Alert..!!!"
                    }
                    else if(tmp.toInt() < 15){
                        tvWar.visibility=View.VISIBLE
                        tvWar.text="Too low Temperature..!!!"
                    }
                    else if(tmp.toInt() > 50){
                        tvWar.visibility=View.VISIBLE
                        tvWar.text="Too high Temperature..!!!"
                    }
                    //soil moisture < 35 -> warning water need
                    //> 90 -> flood
                    //tmp <15 -> warning - trmp down
                    //>50 -> red warning temp high
                    //count++
                }

                handler.postDelayed(this, 20000) // 10 seconds delay
            }
        })
    }

    fun timeFormat(time:String):String{
        val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")

        // Parse UTC time string to Date object
        val utcDate = utcFormat.parse(time)

        // Create a SimpleDateFormat object with Indian Standard Time (IST) time zone
        val istFormat = SimpleDateFormat("dd-MM-yyyy' 'HH:mm:ss")
        istFormat.timeZone = TimeZone.getTimeZone("Asia/Kolkata")

        // Format the Date object to IST time string
        val istTimeString = istFormat.format(utcDate)

        return istTimeString
    }

}
