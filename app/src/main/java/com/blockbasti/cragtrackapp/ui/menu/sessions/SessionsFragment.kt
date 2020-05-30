package com.blockbasti.cragtrackapp.ui.menu.sessions

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.blockbasti.cragtrackapp.ViewSessionActivity
import com.blockbasti.cragtrackapp.databinding.FragmentMenuSessionsBinding

class SessionsFragment : Fragment() {

    private var _binding: FragmentMenuSessionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMenuSessionsBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.floatingActionButton.setOnClickListener { _ ->
            run {
                val intent = Intent(context, ViewSessionActivity::class.java)
                startActivity(intent)
            }
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
