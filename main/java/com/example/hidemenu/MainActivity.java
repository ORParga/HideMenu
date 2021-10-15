package com.example.hidemenu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    static int height, width;
    static int WorkAreaDesviation;

    int FolderContinerDestination, WorkAreaDestination;
    LinearLayout MainFolderContainer;
    RelativeLayout WorkArea;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Inicializa las variables globales;
        MainFolderContainer = findViewById(R.id.MainFolderContainer);
        WorkArea = findViewById(R.id.workArea);
        floatingActionButton = findViewById(R.id.fab);
        PassingData.ViewWorkArea = WorkArea;

        InicializeUI();
    }

    private void InicializeUI() {

        //Calcular el tamañode los contenedores de listado y visualizacion
        MainFolderContainer.post(new Runnable() {
            @Override
            public void run() {
                height = MainFolderContainer.getHeight(); //height is ready in "post()"
                width = MainFolderContainer.getWidth(); //width is ready in "post()"
                if (height > width) {
                    PassingData.Orientation = PassingData.ORIENTATION.PORTRAIT;
                    MainFolderContainer.setLayoutParams(new ConstraintLayout.LayoutParams(width, height / 2));
                } else {
                    PassingData.Orientation = PassingData.ORIENTATION.LANDSCAPE;
                    MainFolderContainer.setLayoutParams(new ConstraintLayout.LayoutParams(width / 2, height));
                }

            }
        });
        WorkArea.post(new Runnable() {
            @Override
            public void run() {
                height = WorkArea.getHeight(); //height is ready
                width = WorkArea.getWidth(); //width is ready
                WorkAreaDesviation = (int) WorkArea.getY();
                if (height > width) {
                    PassingData.Orientation = PassingData.ORIENTATION.PORTRAIT;
                    //WorkArea.setLayoutParams(new ConstraintLayout.LayoutParams(width, height /2));
                    WorkAreaDesviation = height / 2;
                    WorkArea.setY((PassingData.FolderContainerIsShowing) ? WorkAreaDesviation : 0);
                } else {
                    PassingData.Orientation = PassingData.ORIENTATION.LANDSCAPE;
                    //WorkArea.setLayoutParams(new ConstraintLayout.LayoutParams(width , height/2));
                    WorkAreaDesviation = width / 2;
                    WorkArea.setX((PassingData.FolderContainerIsShowing) ? WorkAreaDesviation : 0);

                }

            }
        });
        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ObjectAnimator animation1;
                ObjectAnimator animation2;

                if (PassingData.FolderContainerIsShowing) {
                    FolderContinerDestination = -WorkAreaDesviation;
                    WorkAreaDestination = 0;
                    PassingData.FolderContainerIsShowing = false;
                } else {
                    FolderContinerDestination = 0;
                    WorkAreaDestination = WorkAreaDesviation;
                    PassingData.FolderContainerIsShowing = true;
                }

                if (PassingData.Orientation == PassingData.ORIENTATION.LANDSCAPE) {
                    animation1 = ObjectAnimator.ofFloat(MainFolderContainer, "translationX", FolderContinerDestination);
                    animation2 = ObjectAnimator.ofFloat(WorkArea, "translationX", WorkAreaDestination);
                    fab.setImageResource((PassingData.FolderContainerIsShowing) ? R.drawable.arrowleft32x32 : R.drawable.arrowright32x32);
                } else {
                    animation1 = ObjectAnimator.ofFloat(MainFolderContainer, "translationY", FolderContinerDestination);
                    animation2 = ObjectAnimator.ofFloat(WorkArea, "translationY", WorkAreaDestination);
                    fab.setImageResource((PassingData.FolderContainerIsShowing) ? R.drawable.arrowup32x32 : R.drawable.arrowdown32x32);
                }
                animation1.setDuration(250);
                animation1.start();

                animation2.setDuration(250);
                animation2.start();

            }
        });
    }

}