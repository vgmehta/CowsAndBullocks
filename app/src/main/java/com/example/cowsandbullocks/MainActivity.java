package com.example.cowsandbullocks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnKeyListener{

    EditText word;
    TextView textView;
    Button submit;
    String savedWord;
    ListView listView;
    int flag = 0;
    ArrayList<String> history;
    ArrayAdapter arrayAdapter;
    int ctr = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        word = findViewById(R.id.word);
        textView = findViewById(R.id.textView);
        submit = findViewById(R.id.submitButton);
        listView = findViewById(R.id.listView);
        history = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,history);
        listView.setAdapter(arrayAdapter);
    }

    public void saveWord(View view){

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

        if(flag == 0) {
            savedWord = word.getText().toString();
            Toast.makeText(this, "Word Saved! It's time to start guessing", Toast.LENGTH_SHORT).show();
            textView.setText("Make a guess");
//            submit.setVisibility(View.INVISIBLE);
            word.setText("");
            flag = 1;
        }else if(flag == 1){
            String currentGuess = word.getText().toString();
            if(currentGuess.matches(savedWord)){
                ctr++;
                textView.setText("You have won and you took " + ctr + " turns");
                history.add(currentGuess + "        " + 4 + "C " + 0 + "B");
                arrayAdapter.notifyDataSetChanged();
                submit.setText("Play Again");
                flag = 2;
            }else{
                ctr++;
                int cow_ctr = 0;
                int bullock_ctr = 0;
                for(int i =0; i<currentGuess.length();i++){
                    if(currentGuess.charAt(i) == savedWord.charAt(i)){
                        cow_ctr++;
                    }else if(savedWord.indexOf(currentGuess.charAt(i)) != -1){
                        bullock_ctr++;
                    }
                }
                history.add(currentGuess + "        " + cow_ctr + "C " + bullock_ctr + "B");
                arrayAdapter.notifyDataSetChanged();
                word.setText("");
            }
        }else{
            savedWord = "";
            textView.setText("Enter a 4-letter word");
            word.setText("");
            flag = 0;
            history.clear();
            arrayAdapter.notifyDataSetChanged();
            submit.setText("Submit");
            ctr = 0;
        }

    }


    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            saveWord(v);
        }
        return false;
    }
}
