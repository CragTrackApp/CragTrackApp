package com.blockbasti.cragtrackapp.ui.menu.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.blockbasti.cragtrackapp.R

class StatisticsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_menu_statistics, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        return root
    }
}
