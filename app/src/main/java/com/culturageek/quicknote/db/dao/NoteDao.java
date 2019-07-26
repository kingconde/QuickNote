package com.culturageek.quicknote.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.culturageek.quicknote.db.entity.NoteEntity;

import java.util.List;

@Dao
public interface NoteDao {
    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertNote(NoteEntity note);

    @Update
    void updateNote(NoteEntity note);

    @Delete
    void deleteNote(NoteEntity note);

    @Query("DELETE FROM note_table WHERE id_note = :idNote")
    void deleteNoteByIdNote(int idNote);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    @Query("SELECT * FROM note_table ORDER BY id_note DESC")
    LiveData<List<NoteEntity>> getAllNotes();

    @Query("SELECT * FROM note_table WHERE id_note = :idNote")
    NoteEntity getNoteByIdNote(int idNote);
}
