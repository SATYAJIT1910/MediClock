package com.satyajitghosh.mediclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class RingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ring);

        findViewById(R.id.button_dismiss).setAnimation(AnimationUtils.loadAnimation(this,R.anim.fade));

        findViewById(R.id.button_dismiss).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intentService = new Intent(getApplicationContext(), MyAlarmService.class);
                getApplicationContext().stopService(intentService);
                finish();
            }
        });
    }
}