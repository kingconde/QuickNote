package com.culturageek.quicknote.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.culturageek.quicknote.db.dao.NoteDao;
import com.culturageek.quicknote.db.entity.NoteEntity;

import java.util.ArrayList;

@Database(entities = {NoteEntity.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDatabase extends RoomDatabase{
    public abstract NoteDao noteDao();

    private static NoteRoomDatabase INSTANCE;

    public static NoteRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (NoteRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class, "note_datebase")
                            // Wipes and rebuilds instead of migrating.
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            //TODO: Demo insert in DB
                            //.addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback =
            new RoomDatabase.Callback(){
                @Override
                public void onOpen(@NonNull SupportSQLiteDatabase db) {
                    super.onOpen(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {
        private NoteDao mNoteDao;

        private ArrayList<NoteEntity> noteEntities = new ArrayList<>();

        public PopulateDbAsync(NoteRoomDatabase noteRoomDatabase) {
            mNoteDao = noteRoomDatabase.noteDao();
            noteEntities.add(new NoteEntity("No olvidar comprar para el desayuno"));
            noteEntities.add(new NoteEntity("Tarea de Fisica y resolver problemas de matematicas"));
            noteEntities.add(new NoteEntity("Bañar a los perros"));
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //TODO: Delete all in DB
            //mNoteDao.deleteAllNotes();
            for (NoteEntity entity : noteEntities) {
                mNoteDao.insertNote(entity);
            }
            return null;
        }
    }
}
