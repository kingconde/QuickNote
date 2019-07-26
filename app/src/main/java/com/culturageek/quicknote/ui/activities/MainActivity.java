package com.culturageek.quicknote.ui.activities;

import android.databinding.DataBindingUtil;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.culturageek.quicknote.R;
import com.culturageek.quicknote.databinding.ActivityMainBinding;
import com.culturageek.quicknote.ui.fragments.NoteFragment;
import com.culturageek.quicknote.ui.fragments.NoteListFragment;
import com.culturageek.quicknote.ui.interfaces.INoteListener;

public class MainActivity extends AppCompatActivity implements INoteListener {
    public static final String TAG = MainActivity.class.getName();
    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(mBinding.toolbar);
        displayFragment();
    }

    private void displayFragment() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.framelayout_content, NoteListFragment.newInstance())
                .commit();
    }

    @Override
    public void showTitle(String title, String fragmentTag) {
        mBinding.toolbar.setTitle(title);
        /*
        switch (fragmentTag){
            case NoteFragment.TAG:
                break;
            case NoteListFragment.TAG:
                break;
        }
        */
    }

    @Override
    public void navigateTo(Fragment fragment, String fragmentTag) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.framelayout_content, fragment, fragmentTag)
                    .addToBackStack(null)
                    .commit();
        }
    }

    @Override
    public void closeFragment(Fragment fragment, String fragmentTag) {
        if(fragment != null){
            getSupportFragmentManager().beginTransaction()
                    .remove(fragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:

                break;
            case R.id.action_delete_all:

                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
