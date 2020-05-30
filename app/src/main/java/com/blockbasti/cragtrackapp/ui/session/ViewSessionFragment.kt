package com.blockbasti.cragtrackapp.ui.session

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blockbasti.cragtrackapp.R

class ViewSessionFragment : Fragment() {

    companion object {
        fun newInstance() = ViewSessionFragment()
    }

    private lateinit var viewModel: SessionModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.view_session_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(SessionModel::class.java)
        // TODO: Use the ViewModel
    }

}