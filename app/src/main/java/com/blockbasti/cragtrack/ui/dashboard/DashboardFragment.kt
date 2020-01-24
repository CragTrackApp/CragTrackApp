package com.blockbasti.cragtrack.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blockbasti.cragtrack.LocationSelectorActivity
import com.blockbasti.cragtrack.R

class DashboardFragment : Fragment() {

    private lateinit var homeViewModel: DashboardViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(this, Observer {
            textView.text = it
        })

        val button = root.findViewById<Button>(R.id.button_maps)
        button.setOnClickListener { v -> run {
            val intent = Intent(context, LocationSelectorActivity::class.java)
            startActivity(intent)
        } }
        return root
    }
}