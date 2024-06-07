package com.masterofanalysis.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.masterofanalysis.R;

public class activity_Userban extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userban);
        TextView referencebaslik = findViewById(R.id.referencebaslik);
        TextView referenceaciklama = findViewById(R.id.referenceaciklama);
        LottieAnimationView anim = findViewById(R.id.profildetay);
        anim.setAnimation("noseckin.json");
        referencebaslik.setText(getString(R.string.bantitle));
        referenceaciklama.setText(getString(R.string.banbody));
    }
}