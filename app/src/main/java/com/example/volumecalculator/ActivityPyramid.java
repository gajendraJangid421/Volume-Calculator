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

public class ActivityPyramid extends AppCompatActivity {
    private EditText et_pyramidLength, et_pyramidHeight;
    private TextView tv_pyramidVol, tv_pyramidArea;
    private Button bt_submit;
    private ImageButton ib_pyramidSoundA, ib_pyramidSoundV;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramid);
        getSupportActionBar().setTitle("Square Pyramid");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_pyramidLength = findViewById(R.id.et_pyramidLength);
        et_pyramidHeight = findViewById(R.id.et_pyramidHeight);
        tv_pyramidArea = findViewById(R.id.tv_pyramidArea);
        tv_pyramidVol = findViewById(R.id.tv_pyramidVol);
        bt_submit = findViewById(R.id.bt_submit);
        ib_pyramidSoundA = findViewById(R.id.ib_pyramidSoundA);
        ib_pyramidSoundV = findViewById(R.id.ib_pyramidSoundV);

        bt_submit.setOnClickListener(view -> {

            if(et_pyramidLength.getText().toString().equals("") || et_pyramidHeight.getText().toString().equals("")){
                displayToastIfEmptyRadius();
            }else {
                String pyramidLength = ActivityRhino.rhinoLibrary(et_pyramidLength.getText().toString());
                String pyramidHeight = ActivityRhino.rhinoLibrary(et_pyramidHeight.getText().toString());

                et_pyramidLength.setText(pyramidLength);
                et_pyramidHeight.setText(pyramidHeight);

                double length = Double.parseDouble(et_pyramidLength.getText().toString());
                double height = Double.parseDouble(et_pyramidHeight.getText().toString());

                if (length > 0 && height>0) {
                    DecimalFormat df = new DecimalFormat("#.####");
                    df.setRoundingMode(RoundingMode.CEILING);

                    double area = (Math.pow(length,2) + 2 * length * Math.sqrt((Math.pow(length,2)/4) + Math.pow(height,2)));
                    tv_pyramidArea.setText(df.format(area));

                    double volume = (length * length * height/3);
                    tv_pyramidVol.setText(df.format(volume));
                } else {
                    Toast.makeText(ActivityPyramid.this , "Incorrect Entry" , Toast.LENGTH_SHORT).show();
                }
            }

            closeKeyboard();
        });

        textToSpeech = new TextToSpeech(getApplicationContext() , i -> {
            if(i == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        ib_pyramidSoundA.setOnClickListener(view -> {
            String st = "Surface area = " + tv_pyramidArea.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });

        ib_pyramidSoundV.setOnClickListener(view -> {
            String st = "Volume = " + tv_pyramidVol.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void displayToastIfEmptyRadius() {
        Toast.makeText( ActivityPyramid.this , "Enter the Value", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_pyramidLength.setText(result.get(0));
                }
                break;
            case 2:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_pyramidHeight.setText(result.get(0));
                }
                break;
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void speakPyramidLength(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 1);
        }else {
            Toast.makeText(this, "Not Supported", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void speakPyramidHeight(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 2);
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

