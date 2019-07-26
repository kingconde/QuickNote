package com.culturageek.quicknote.ui.interfaces;


import android.support.v4.app.Fragment;

public interface INoteListener {
    void showTitle(String title, String fragmentTag);
    void navigateTo(Fragment fragment, String fragmentTag);
    void closeFragment(Fragment fragment, String fragmentTag);
}
