package com.satyajitghosh.mediclock;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class RingActivity extends AppCompatActivity {
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED  | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);;
        setContentView(R.layout.activity_ring);
        Intent intent=getIntent();
        String Medicine=intent.getStringExtra("MedicineName");
        String food=intent.getStringExtra("food");
        String text="I have already taken \n"+Medicine+" \n"+food;
        textView=findViewById(R.id.medicine_name_view);
        textView.setText(text);

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