package com.example.notesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.models.NoteResponse
import com.example.notesapp.utils.NetworkUtils
import javax.inject.Inject

class NoteAdapter(
    private val onNoteClicked: (NoteResponse) -> Unit,
    private val shareNote: (NoteResponse) -> Unit) :
    ListAdapter<NoteResponse, NoteAdapter.NoteViewHolder>(ComparatorDiffUtil()) {

    @Inject
    lateinit var networkUtils: NetworkUtils
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = getItem(position)
        note?.let{
            holder.bind(it)
        }
    }

    inner class NoteViewHolder(private val binding: NoteItemBinding):
    RecyclerView.ViewHolder(binding.root){

        fun bind(noteResponse: NoteResponse){
            binding.title.text = noteResponse.title
            binding.desc.text = noteResponse.discription
            binding.root.setOnClickListener{
                onNoteClicked(noteResponse)
            }
            binding.btnShare.setOnClickListener {
                shareNote(noteResponse)
            }
        }
    }

    class ComparatorDiffUtil: DiffUtil.ItemCallback<NoteResponse>(){
        override fun areItemsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: NoteResponse, newItem: NoteResponse): Boolean {
            return oldItem == newItem
        }
    }
}
