package com.example.cowsandbullocks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ModeSelectionScreen extends AppCompatActivity {

    Button onePlayer;
    Button twoPlayers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_selection_screen);
        onePlayer = findViewById(R.id.onePlayer);
        twoPlayers = findViewById(R.id.twoPlayer);
    }

    public void goTo1PlayerMode(View view){
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void goTo2PlayerMode(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
