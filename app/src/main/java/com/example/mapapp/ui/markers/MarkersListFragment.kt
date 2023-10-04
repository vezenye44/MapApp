package com.example.mapapp.ui.markers

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mapapp.databinding.FragmentMarkersListBinding
import com.example.mapapp.ui.markers.recycler_view.MarkersListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MarkersListFragment : Fragment() {

    private var _binding: FragmentMarkersListBinding? = null
    private val binding get() = _binding!!

    private lateinit var nameChangedClickListener: (String, Long) -> Unit
    private lateinit var descriptionChangedClickListener: (String, Long) -> Unit

    private val markersViewModel: MarkersListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMarkersListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameChangedClickListener = markersViewModel::updateMarkerName
        descriptionChangedClickListener = markersViewModel::updateMarkerDescription
        initRecyclerView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initRecyclerView() {
        binding.markersListRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.markersListRecyclerView.adapter = MarkersListAdapter(
            nameChangedClickListener,
            descriptionChangedClickListener
        ).apply {
            markersViewModel.markerLiveData().observe(this@MarkersListFragment.viewLifecycleOwner) {
                this.setListOFMarkers(it)
                this.notifyDataSetChanged()
            }
        }
    }

    companion object {

        fun newInstance(): MarkersListFragment {
            return MarkersListFragment()
        }
    }
}