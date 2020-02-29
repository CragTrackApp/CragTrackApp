package com.blockbasti.cragtrackapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.blockbasti.cragtrackapp.LocationSelectorActivity
import com.blockbasti.cragtrackapp.R
import com.blockbasti.cragtrackapp.databinding.FragmentDashboardBinding
import com.google.firebase.auth.FirebaseAuth

class DashboardFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth

    private lateinit var homeViewModel: DashboardViewModel
    private var _binding: FragmentDashboardBinding? = null
    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val view = binding.root

        mAuth = FirebaseAuth.getInstance()

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        val button = binding.buttonMaps
        button.setOnClickListener { v -> run {
            val intent = Intent(context, LocationSelectorActivity::class.java)
            startActivity(intent)
        } }

        binding.buttonLogout.setOnClickListener {
            mAuth.signOut()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}