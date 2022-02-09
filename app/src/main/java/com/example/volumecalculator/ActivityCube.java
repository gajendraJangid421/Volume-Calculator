package com.example.volumecalculator;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class ActivityCube extends AppCompatActivity {
    private EditText et_cubeSide;
    private TextView tv_cubeVol, tv_cubeArea;
    private Button bt_submit;
    private ImageButton ib_cubeSoundA, ib_cubeSoundV;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cube);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Cube");

        et_cubeSide = findViewById(R.id.et_cubeSide);
        tv_cubeArea = findViewById(R.id.tv_cubeArea);
        tv_cubeVol = findViewById(R.id.tv_cubeVol);
        bt_submit = findViewById(R.id.bt_submit);
        ib_cubeSoundA = findViewById(R.id.ib_cubeSoundA);
        ib_cubeSoundV = findViewById(R.id.ib_cubeSoundV);

        bt_submit.setOnClickListener(view -> {

            if(et_cubeSide.getText().toString().equals("")){
                displayToastIfEmptyRadius();
            }else {
                String cubeSide = ActivityRhino.rhinoLibrary(et_cubeSide.getText().toString());

                et_cubeSide.setText(cubeSide);

                double side = Double.parseDouble(et_cubeSide.getText().toString());

                if (side > 0) {
                    DecimalFormat df = new DecimalFormat("#.####");
                    df.setRoundingMode(RoundingMode.CEILING);

                    double area = (side * side * 6);
                    tv_cubeArea.setText(df.format(area));

                    double volume = (side * side * side);
                    tv_cubeVol.setText(df.format(volume));
                } else {
                    Toast.makeText(ActivityCube.this , "Incorrect Entry" , Toast.LENGTH_SHORT).show();
                }
            }

            closeKeyboard();
        });

        textToSpeech = new TextToSpeech(getApplicationContext() , i -> {
            if(i == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        ib_cubeSoundA.setOnClickListener(view -> {
            String st = "Surface area = " + tv_cubeArea.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });

        ib_cubeSoundV.setOnClickListener(view -> {
            String st = "Volume = " + tv_cubeVol.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void displayToastIfEmptyRadius() {
        Toast.makeText( ActivityCube.this , "Enter the Value", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_cubeSide.setText(result.get(0));
                }
                break;
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void speakCubeSide(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }else {
            Toast.makeText(this, "Not Supported", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager inputMM = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMM.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }
}
