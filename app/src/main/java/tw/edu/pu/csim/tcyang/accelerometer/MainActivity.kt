package tw.edu.pu.csim.tcyang.accelerometer

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MainActivity : AppCompatActivity(), SensorEventListener {

    lateinit var sm : SensorManager
    lateinit var sr : Sensor
    lateinit var txv : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sr = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sm.registerListener(this, sr, SensorManager.SENSOR_DELAY_NORMAL)
        txv = findViewById(R.id.txv)
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x: Float = event.values.get(0)
        val y: Float = event.values.get(1)
        val z: Float = event.values.get(2)
        //txv.setText(String.format("x軸: %1.2f \ny軸: %1.2f \nz軸: %1.2f", x, y, z))
        if (Math.abs(x)<1 && Math.abs(y)<1 && z<-9) {
            txv.setText("朝下平放")
        }
        else if (Math.abs(x) + Math.abs(y) + Math.abs(z) > 32) {
            txv.setText("手機搖晃")
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }

    override fun onResume() {
        super.onResume()
        sm.registerListener(this, sr, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this)
    }
}