package com.example.homemade;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BakerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baker_profile);
    }

    public void Next1(View view) {
        Intent intent= new Intent(BakerProfile.this,BakerMapActivity.class);
        startActivity(intent);
    }
}