package com.example.mapapp.ui.markers.recycler_view

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mapapp.domain.models.MarkerEntity

class MarkersListAdapter(
    private val nameChangedClickListener: (String, Long) -> Unit,
    private val descriptionChangedClickListener: (String, Long) -> Unit,
) : RecyclerView.Adapter<MarkerListItemViewHolder>() {
    private var markerList: List<MarkerEntity> = listOf()

    fun setListOFMarkers(list: List<MarkerEntity>) {
        markerList = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarkerListItemViewHolder {
        return MarkerListItemViewHolder.create(
            parent,
            nameChangedClickListener,
            descriptionChangedClickListener
        )
    }

    override fun getItemCount(): Int {
        return markerList.size
    }

    override fun onBindViewHolder(holder: MarkerListItemViewHolder, position: Int) {
        holder.bind(markerList[position])
    }
}