package jp.co.archive_asia.onedaycouplediary

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.co.archive_asia.onedaycouplediary.auth.IntroActivity

class SplashActivity: AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, IntroActivity::class.java))
        finish()
    }
}