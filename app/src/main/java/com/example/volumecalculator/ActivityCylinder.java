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

public class ActivityCylinder extends AppCompatActivity {
    private EditText et_cylinderRadius, et_cylinderHeight;
    private TextView tv_cylinderVol, tv_cylinderArea;
    private Button bt_submit;
    private ImageButton ib_cylinderSoundA, ib_cylinderSoundV;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cylinder);
        getSupportActionBar().setTitle("Cylinder");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_cylinderRadius = findViewById(R.id.et_cylinderRadius);
        et_cylinderHeight = findViewById(R.id.et_cylinderHeight);
        tv_cylinderArea = findViewById(R.id.tv_cylinderArea);
        tv_cylinderVol = findViewById(R.id.tv_cylinderVol);
        bt_submit = findViewById(R.id.bt_submit);
        ib_cylinderSoundA = findViewById(R.id.ib_cylinderSoundA);
        ib_cylinderSoundV = findViewById(R.id.ib_cylinderSoundV);

        bt_submit.setOnClickListener(view -> {

            if(et_cylinderRadius.getText().toString().equals("") || et_cylinderHeight.getText().toString().equals("")){
                displayToastIfEmptyRadius();
            }else {
                String cylinderRadius = ActivityRhino.rhinoLibrary(et_cylinderRadius.getText().toString());
                String cylinderHeight = ActivityRhino.rhinoLibrary(et_cylinderHeight.getText().toString());

                et_cylinderRadius.setText(cylinderRadius);
                et_cylinderHeight.setText(cylinderHeight);

                double radius = Double.parseDouble(et_cylinderRadius.getText().toString());
                double height = Double.parseDouble(et_cylinderHeight.getText().toString());

                if (radius > 0 && height>0) {
                    DecimalFormat df = new DecimalFormat("#.####");
                    df.setRoundingMode(RoundingMode.CEILING);

                    double area = (Math.PI * 2 * radius * (radius + height));
                    tv_cylinderArea.setText(df.format(area));

                    double volume = (Math.PI * radius * radius * height);
                    tv_cylinderVol.setText(df.format(volume));
                } else {
                    Toast.makeText(ActivityCylinder.this , "Incorrect Entry" , Toast.LENGTH_SHORT).show();
                }
            }

            closeKeyboard();
        });

        textToSpeech = new TextToSpeech(getApplicationContext() , i -> {
            if(i == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        ib_cylinderSoundA.setOnClickListener(view -> {
            String st = "Surface area = " + tv_cylinderArea.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });

        ib_cylinderSoundV.setOnClickListener(view -> {
            String st = "Volume = " + tv_cylinderVol.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void displayToastIfEmptyRadius() {
        Toast.makeText( ActivityCylinder.this , "Enter the Value", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_cylinderRadius.setText(result.get(0));
                }
                break;
            case 2:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_cylinderHeight.setText(result.get(0));
                }
                break;
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void speakCylinderRadius(View view) {
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
    public void speakCylinderHeight(View view) {
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

