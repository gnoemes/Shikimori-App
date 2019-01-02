package com.gnoemes.shikimoriapp.presentation.view.splash

import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.util.Log
import com.arellomobile.mvp.MvpAppCompatActivity
import com.gnoemes.shikimoriapp.BuildConfig
import com.gnoemes.shikimoriapp.R
import com.gnoemes.shikimoriapp.presentation.view.main.MainActivity
import com.gnoemes.shikimoriapp.utils.toUri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SplashActivity : MvpAppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = FirebaseDatabase.getInstance().reference

        val versionListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lastVersion = snapshot.value.toString()
                Log.i("DEVE", "last $lastVersion")
                if (!BuildConfig.VERSION_NAME.contains(lastVersion)) {
                    Log.i("DEVE", "lul")
                    createNewVersionPush(lastVersion)
                }
            }

            override fun onCancelled(p0: DatabaseError) {}
        }
        database.child("version").addListenerForSingleValueEvent(versionListener)

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun createNewVersionPush(lastVersion: String) {
        val intent = Intent(Intent.ACTION_VIEW, "https://yadi.sk/d/rBCO8kRaIwXouQ".toUri())
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val notification = NotificationCompat.Builder(this, "shikimori-channel")
                .setContentTitle("Обновление")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentText("Доступна новая версия $lastVersion.")
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        NotificationManagerCompat.from(this).notify(0, notification)
    }
}