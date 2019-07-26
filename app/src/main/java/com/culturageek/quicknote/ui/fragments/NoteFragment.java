package com.culturageek.quicknote.ui.fragments;


import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.culturageek.quicknote.R;
import com.culturageek.quicknote.databinding.FragmentNoteBinding;
import com.culturageek.quicknote.db.entity.NoteEntity;
import com.culturageek.quicknote.ui.interfaces.INoteListener;
import com.culturageek.quicknote.viewmodel.NoteViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteFragment extends Fragment implements View.OnClickListener{
    public static final String TAG = "NoteFragment";
    private FragmentNoteBinding mBinding;
    private NoteViewModel mNoteViewModel;
    private INoteListener mListener;

    public NoteFragment() {
        // Required empty public constructor
    }

    public static NoteFragment newInstance(){
        return new NoteFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof INoteListener) mListener = (INoteListener) context;
        else throw new ClassCastException("INoteListener");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_note, container, false);
        initView();
        conectToDatabase();
        return mBinding.getRoot();
    }

    private void conectToDatabase() {
        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
    }

    private void initView() {
        mListener.showTitle("Nueva nota", TAG);
        mBinding.fabSaveNote.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_save_note:
                if(!validateIsEmpty()){
                    saveNote();
                    getFragmentManager().popBackStackImmediate();
                }
                break;
        }
    }


    private boolean validateIsEmpty() {
        return TextUtils.isEmpty(mBinding.container.noteTextView.getText());
    }

    private void saveNote() {
        NoteEntity noteEntity = new NoteEntity(mBinding.container.noteTextView.getText().toString());
        mNoteViewModel.insertNote(noteEntity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
