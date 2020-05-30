package com.blockbasti.cragtrackapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blockbasti.cragtrackapp.ui.session.ViewSessionFragment

class ViewSessionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.view_session_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ViewSessionFragment.newInstance())
                .commitNow()
        }
    }
}