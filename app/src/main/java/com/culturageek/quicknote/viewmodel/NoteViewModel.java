package com.culturageek.quicknote.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;


import com.culturageek.quicknote.NoteRepository;
import com.culturageek.quicknote.db.entity.NoteEntity;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    private NoteRepository mRepository;
    private LiveData<List<NoteEntity>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        mRepository = new NoteRepository(application);
        mAllNotes = mRepository.getAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes() {
        return mAllNotes;
    }

    public void insertNote(NoteEntity noteEntity){
        mRepository.insertNote(noteEntity);
    }

    public void deleteAllNote(){
        mRepository.deleteAllNotes();
    }

    public void deleteNote(NoteEntity noteEntity){
        mRepository.deleteNote(noteEntity);
    }
}
