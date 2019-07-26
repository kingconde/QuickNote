package com.culturageek.quicknote.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.culturageek.quicknote.R;
import com.culturageek.quicknote.db.entity.NoteEntity;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    private LayoutInflater mInflater;
    private List<NoteEntity> mNoteList;

    public NoteListAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        if(mNoteList != null){
            NoteEntity noteEntity = mNoteList.get(position);
            holder.note.setText(noteEntity.getNote());
        }else {
            holder.note.setText("Empty");
        }
    }

    @Override
    public int getItemCount() {
        if(mNoteList != null)
            return mNoteList.size();
        else return 0;
    }

    public NoteEntity getNoteAtPosition(int position){
        return mNoteList.get(position);
    }

    public void setNoteList(List<NoteEntity> noteList){
        mNoteList = noteList;
        notifyDataSetChanged();
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        private TextView note;
        public NoteViewHolder(View itemView) {
            super(itemView);
            note = itemView.findViewById(R.id.note_textview);
        }
    }
}
