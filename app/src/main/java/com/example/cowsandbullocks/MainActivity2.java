package com.example.cowsandbullocks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity2 extends AppCompatActivity {

    EditText word;
    TextView textView;
    Button submit;
    Button giveUpButton;
    String savedWord;
    ListView listView;
    int flag = 1;
    ArrayList<String> history;
    ArrayAdapter arrayAdapter;
    String[] words;
    int ctr = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        word = findViewById(R.id.word);
        textView = findViewById(R.id.textView);
        submit = findViewById(R.id.submitButton);
        listView = findViewById(R.id.listView);
        history = new ArrayList<String>();
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,history);
        listView.setAdapter(arrayAdapter);
        giveUpButton = findViewById(R.id.giveUpButton);

        StringBuffer stringBuffer = new StringBuffer();

        InputStream inputStream = this.getResources().openRawResource(R.raw.words);

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String data="";

        if(inputStream!=null){
            try {
                data = bufferedReader.readLine();

                words = data.split(" ");
            }catch (Exception e){
                e.printStackTrace();
            }
        }

//        Log.i("Hello",savedWord);
        createWord();

    }

    public void createWord(){
        Random r = new Random();
        int randomNumber = r.nextInt(2513 - 1) + 1;

        savedWord = words[randomNumber];
    }

    public void giveUp(View view){
        textView.setText("The word was:" + savedWord + "!Better luck next time!!");
        submit.setText("Play Again");
//        giveUpButton.setVisibility(View.INVISIBLE);
        createWord();
        flag = 2;
        history.clear();
        arrayAdapter.notifyDataSetChanged();
//        saveWord(view);
    }

    public void saveWord(View view){

        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

         if(flag == 1){
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
            textView.setText("Start guessing");
            word.setText("");
            createWord();
            flag = 1;
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
