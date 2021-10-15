package com.example.hidemenu;

import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PassingData {

    public static ORIENTATION Orientation;
    public static boolean FolderContainerIsShowing;
    public static RelativeLayout ViewWorkArea;

    FloatingActionButton floatingActionButton;
    public enum ORIENTATION {LANDSCAPE, PORTRAIT}
}