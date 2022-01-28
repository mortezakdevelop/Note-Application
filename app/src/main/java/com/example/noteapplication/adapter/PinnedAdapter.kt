package com.example.noteapplication.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.PrecomputedTextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.R
import com.example.noteapplication.databinding.PinnedRvItemsBinding
import com.example.noteapplication.room.entity.NoteEntity

class PinnedAdapter(private var data: ArrayList<NoteEntity>,private var listener:CardClickListener) :
    RecyclerView.Adapter<PinnedAdapter.PinnedViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PinnedViewHolder {
        val pinnedRvItemsBinding: PinnedRvItemsBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.pinned_rv_items, parent, false)
        return PinnedViewHolder(pinnedRvItemsBinding)
    }

    override fun onBindViewHolder(holder: PinnedViewHolder, position: Int) {
            holder.bind(data[position],listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class PinnedViewHolder(private var binding:PinnedRvItemsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(noteEntity: NoteEntity, listener: CardClickListener){
            // best practice for text view(bind textview)
            binding.textTitle.setTextFuture(
                PrecomputedTextCompat.getTextFuture(
                    noteEntity.noteModels.title,
                    binding.textTitle.textMetricsParamsCompat,
                    null
                )

            )
            binding.pinnedCardView.setCardBackgroundColor(Color.parseColor(noteEntity.noteModels.color))
            binding.textNote.text = noteEntity.noteModels.note
            binding.textNote.text = noteEntity.noteModels.note

            binding.pinnedCardView.setOnClickListener {
                listener.onItemRVClickListener(noteEntity)
            }
            binding.imageFilterButton.setOnClickListener {
                listener.onMenuItemRVClickListener(it,noteEntity)
            }

            binding.executePendingBindings()
            // normal bind without textViewCompat(without best practice)
           // binding.textTitle.text = noteModel.note
        }


    }
}