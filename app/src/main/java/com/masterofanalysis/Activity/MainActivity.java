package com.masterofanalysis.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.masterofanalysis.Fragment.fragment_homex;
import com.masterofanalysis.Function;
import com.masterofanalysis.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    FloatingActionButton homex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        homex = findViewById(R.id.homex);

        Function.Get_Permission_Notification(MainActivity.this);
        replaceFragmentx(new fragment_homex(),"home");

        homex.setOnClickListener(view -> {
            replaceFragmentx(new fragment_homex(), "home");
        });
    }

    public void replaceFragmentx(Fragment fragment, String tag){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, fragment, tag);
        transaction.commit();
    }
}
