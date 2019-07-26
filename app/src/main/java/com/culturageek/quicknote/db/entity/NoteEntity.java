package com.culturageek.quicknote.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "note_table")
public class NoteEntity {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id_note")
    private int idNote;
    @NonNull
    @ColumnInfo(name = "note")
    private String note;

    public NoteEntity(@NonNull String note) {
        this.note = note;
    }

    @NonNull
    public int getIdNote() {
        return idNote;
    }

    public void setIdNote(@NonNull int idNote) {
        this.idNote = idNote;
    }

    @NonNull
    public String getNote() {
        return note;
    }

    public void setNote(@NonNull String note) {
        this.note = note;
    }
}
