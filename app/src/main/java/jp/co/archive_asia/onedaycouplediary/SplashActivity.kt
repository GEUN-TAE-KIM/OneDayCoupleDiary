package jp.co.archive_asia.onedaycouplediary

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.co.archive_asia.onedaycouplediary.util.startActivity
import jp.co.archive_asia.onedaycouplediary.view.auth.IntroActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity () {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(IntroActivity::class.java)
        finish()
    }
}