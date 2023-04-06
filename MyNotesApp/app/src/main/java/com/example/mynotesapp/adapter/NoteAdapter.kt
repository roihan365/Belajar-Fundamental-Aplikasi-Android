package com.example.mynotesapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotesapp.R
import com.example.mynotesapp.databinding.ItemNoteBinding
import com.example.mynotesapp.note.Note

class NoteAdapter(private val onItemClickCallback: OnItemClickCallback) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {
    var listNote = ArrayList<Note>()
    set(listNote) {
        if (listNote.size > 0) {
            this.listNote.clear()
        }
        this.listNote.addAll(listNote)
    }


    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val  binding = ItemNoteBinding.bind(itemView)
        fun bind(note: Note) {
            binding.tvItemDate.text = note.date
            binding.tvItemTitle.text = note.title
            binding.tvItemDescription.text = note.description
            binding.cvItemNote.setOnClickListener {
                onItemClickCallback.onItemClicked(note, adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int =
        this.listNote.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNote[position])
    }

    interface OnItemClickCallback {
        fun onItemClicked(selectedNote: Note?, position: Int)
    }

    fun addItem(note: Note) {
        this.listNote.add(note)
        notifyItemInserted(this.listNote.size - 1)
    }

    fun updateItem(position: Int, note: Note) {
        this.listNote[position] = note
        notifyItemChanged(position, note)
    }

    fun removeItem(position: Int) {
        this.listNote.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.listNote.size)
    }

}