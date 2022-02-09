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

public class ActivityCone extends AppCompatActivity {
    private EditText et_coneRadius, et_coneStLength, et_coneHeight;
    private TextView tv_coneVol, tv_coneArea;
    private Button bt_submit;
    private ImageButton ib_coneSoundA, ib_coneSoundV;
    private TextToSpeech textToSpeech;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cone);
        getSupportActionBar().setTitle("Cone");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_coneRadius = findViewById(R.id.et_coneRadius);
        et_coneStLength = findViewById(R.id.et_coneStLength);
        et_coneHeight = findViewById(R.id.et_coneHeight);
        tv_coneArea = findViewById(R.id.tv_coneArea);
        tv_coneVol = findViewById(R.id.tv_coneVol);
        bt_submit = findViewById(R.id.bt_submit);
        ib_coneSoundA = findViewById(R.id.ib_coneSoundA);
        ib_coneSoundV = findViewById(R.id.ib_coneSoundV);

        bt_submit.setOnClickListener(view -> {

            if(et_coneRadius.getText().toString().equals("") || et_coneStLength.getText().toString().equals("") || et_coneHeight.getText().toString().equals("")){
                displayToastIfEmptyRadius();
            }else {
                String coneRadius = ActivityRhino.rhinoLibrary(et_coneRadius.getText().toString());
                String coneLength = ActivityRhino.rhinoLibrary(et_coneStLength.getText().toString());
                String coneHeight = ActivityRhino.rhinoLibrary(et_coneHeight.getText().toString());

                et_coneRadius.setText(coneRadius);
                et_coneStLength.setText(coneLength);
                et_coneHeight.setText(coneHeight);

                double radius = Double.parseDouble(et_coneRadius.getText().toString());
                double length = Double.parseDouble(et_coneStLength.getText().toString());
                double height = Double.parseDouble(et_coneHeight.getText().toString());

                if (length > 0 && radius>0 && height>0) {
                    DecimalFormat df = new DecimalFormat("#.####");
                    df.setRoundingMode(RoundingMode.CEILING);

                    double area = (Math.PI * radius * (length + radius));
                    tv_coneArea.setText(df.format(area));

                    double volume = (Math.PI * Math.pow(radius,2) * height/3);
                    tv_coneVol.setText(df.format(volume));
                } else {
                    Toast.makeText(ActivityCone.this , "Incorrect Entry" , Toast.LENGTH_SHORT).show();
                }
            }

            closeKeyboard();
        });

        textToSpeech = new TextToSpeech(getApplicationContext() , i -> {
            if(i == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        ib_coneSoundA.setOnClickListener(view -> {
            String st = "Surface area = " + tv_coneArea.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });

        ib_coneSoundV.setOnClickListener(view -> {
            String st = "Volume = " + tv_coneVol.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void displayToastIfEmptyRadius() {
        Toast.makeText( ActivityCone.this , "Enter the Value", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_coneRadius.setText(result.get(0));
                }
                break;
            case 2:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_coneStLength.setText(result.get(0));
                }
                break;
            case 3:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_coneHeight.setText(result.get(0));
                }
                break;
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void speakConeRadius(View view) {
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
    public void speakConeLength(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 2);
        }else {
            Toast.makeText(this, "Not Supported", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void speakConeHeight(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent .putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, 3);
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

