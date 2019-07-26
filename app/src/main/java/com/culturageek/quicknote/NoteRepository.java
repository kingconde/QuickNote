package com.culturageek.quicknote;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.culturageek.quicknote.db.NoteRoomDatabase;
import com.culturageek.quicknote.db.dao.NoteDao;
import com.culturageek.quicknote.db.entity.NoteEntity;

import java.util.List;

public class NoteRepository {
    private NoteDao mNoteDao;
    private LiveData<List<NoteEntity>> mAllNotes;

    public NoteRepository(Application application) {
        NoteRoomDatabase database = NoteRoomDatabase.getDatabase(application);
        mNoteDao = database.noteDao();
        mAllNotes = mNoteDao.getAllNotes();
    }

    public LiveData<List<NoteEntity>> getAllNotes(){
        return mAllNotes;
    }

    public void deleteAllNotes(){
        new deleteAllAsyncTask(mNoteDao).execute();
    }

    public void insertNote(NoteEntity note){
        new insertAsyncTask(mNoteDao).execute(note);
    }

    public void deleteNote(NoteEntity noteEntity){
        new deleteNoteAsyncTask(mNoteDao).execute(noteEntity);
    }

    private static class insertAsyncTask extends AsyncTask<NoteEntity, Void, Void>{
        private NoteDao mAsyncTaskDao;

        public insertAsyncTask(NoteDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... notes) {
            mAsyncTaskDao.insertNote(notes[0]);
            return null;
        }
    }

    public static class deleteAllAsyncTask extends AsyncTask<Void, Void, Void>{
        private NoteDao mAsyncTaskDao;

        public deleteAllAsyncTask(NoteDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mAsyncTaskDao.deleteAllNotes();
            return null;
        }
    }

    public static class deleteNoteAsyncTask extends AsyncTask<NoteEntity, Void, Void>{
        private NoteDao mAsyncTaskDao;

        public deleteNoteAsyncTask(NoteDao mAsyncTaskDao) {
            this.mAsyncTaskDao = mAsyncTaskDao;
        }

        @Override
        protected Void doInBackground(NoteEntity... noteEntities) {
            mAsyncTaskDao.deleteNote(noteEntities[0]);
            return null;
        }
    }
}
