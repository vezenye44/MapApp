package com.example.mapapp.ui.markers.recycler_view

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mapapp.databinding.MarkerListItemBinding
import com.example.mapapp.domain.models.MarkerEntity

class MarkerListItemViewHolder(
    view: View,
    private val nameChangedClickListener: (String, Long) -> Unit,
    private val descriptionChangedClickListener: (String, Long) -> Unit,
) : ViewHolder(view){

    private val binding = MarkerListItemBinding.bind(itemView)


    fun bind(markerEntity: MarkerEntity) {
        binding.markerNameEditText.apply {
            setText(markerEntity.name)
            addTextChangedListener(createTextWatcher(nameChangedClickListener, markerEntity.id))
        }
        binding.markerDescriptionEditText.apply {
            setText(markerEntity.description)
            addTextChangedListener(createTextWatcher(descriptionChangedClickListener, markerEntity.id))
        }
    }

    private fun createTextWatcher(textChangedClickListener: (String, Long) -> Unit, markerId: Long) =
        object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int,
            ) {
            }

            override fun onTextChanged(
                s: CharSequence?,
                start: Int,
                before: Int,
                count: Int,
            ) {
            }

            override fun afterTextChanged(s: Editable?) {
                textChangedClickListener(s.toString(), markerId)
            }

        }

    companion object {
        fun create(
            parent: ViewGroup,
            nameChangedClickListener: (String, Long) -> Unit,
            descriptionChangedClickListener: (String, Long) -> Unit
        ) : MarkerListItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val view = MarkerListItemBinding.inflate(inflater, parent, false).root
            return MarkerListItemViewHolder(view, nameChangedClickListener, descriptionChangedClickListener)
        }
    }
}