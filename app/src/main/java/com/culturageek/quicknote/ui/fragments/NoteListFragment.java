package com.culturageek.quicknote.ui.fragments;


import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.culturageek.quicknote.R;
import com.culturageek.quicknote.databinding.FragmentNoteListBinding;
import com.culturageek.quicknote.db.entity.NoteEntity;
import com.culturageek.quicknote.ui.adapter.NoteListAdapter;
import com.culturageek.quicknote.ui.dialogs.CustomSimpleAlertDialog;
import com.culturageek.quicknote.ui.interfaces.INoteListener;
import com.culturageek.quicknote.viewmodel.NoteViewModel;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class NoteListFragment extends Fragment implements View.OnClickListener, INoteListener.IDialogListener {
    public static final String TAG = "NoteListFragment";
    private FragmentNoteListBinding mBinding;
    private NoteViewModel mNoteViewModel;
    private NoteListAdapter mAdapter;
    private ItemTouchHelper mItemTouchHelper;
    private INoteListener mListener;
    private Activity mActivity;
    public NoteListFragment() {
        // Required empty public constructor
    }

    public static NoteListFragment newInstance(){
        return new NoteListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof INoteListener) mListener = (INoteListener) context;
        else throw new ClassCastException("INoteListener");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_note_list, container, false);
        initView();
        conectToDatabase();
        startItemTocuh();
        return mBinding.getRoot();
    }

    private void initView() {
        mActivity = getActivity();
        mListener.showTitle(getString(R.string.app_name), TAG);
        mAdapter = new NoteListAdapter(getContext());
        mBinding.container.noteRecyclerView.setAdapter(mAdapter);
        mBinding.container.noteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mBinding.fabAddNote.setOnClickListener(this);
    }

    private void conectToDatabase() {
        mNoteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        mNoteViewModel.getAllNotes().observe(this, new Observer<List<NoteEntity>>() {
            @Override
            public void onChanged(@Nullable List<NoteEntity> noteEntities) {
                mAdapter.setNoteList(noteEntities);
            }
        });
    }


    private void startItemTocuh() {
        mItemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                NoteEntity noteEntity = mAdapter.getNoteAtPosition(position);
                mNoteViewModel.deleteNote(noteEntity);
            }
        });
        mItemTouchHelper.attachToRecyclerView(mBinding.container.noteRecyclerView);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.fab_add_note:
                mListener.navigateTo(NoteFragment.newInstance(), NoteFragment.TAG);
                break;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:
                //Go to Settings Fragment
                Toast.makeText(mActivity, "Ajustes", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_delete_all:
                showDialog();
                break;
        }
        return false;
    }

    private void showDialog() {
        CustomSimpleAlertDialog customSimpleAlertDialog = CustomSimpleAlertDialog.getInstance("Estas seguro de querer borrar todos las notas");
        customSimpleAlertDialog.setDialogListener(this);
        customSimpleAlertDialog.show(mActivity.getFragmentManager(), CustomSimpleAlertDialog.TAG);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void actionDialog(int actionDialog) {
        switch (actionDialog){
            case Activity.RESULT_OK:
                mNoteViewModel.deleteAllNote();
                break;
        }
    }
}
