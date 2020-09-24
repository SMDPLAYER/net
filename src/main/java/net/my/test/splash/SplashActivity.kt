package net.my.test.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.my.test.R
import net.my.test.activities.MainActivity


import java.util.concurrent.Executors

class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Executors.newSingleThreadExecutor().execute {
            Thread.sleep(5000)
            startActivity(Intent(this,MainActivity::class.java))
            finish()

        }
    }


}