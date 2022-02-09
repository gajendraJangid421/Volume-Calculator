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

public class ActivitySphere extends AppCompatActivity {
    private EditText et_sphereRadius;
    private TextView tv_sphereVol, tv_sphereArea;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sphere);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Sphere");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_sphereRadius = findViewById(R.id.et_sphereRadius);
        tv_sphereArea = findViewById(R.id.tv_sphereArea);
        tv_sphereVol = findViewById(R.id.tv_sphereVol);
        Button bt_submit = findViewById(R.id.bt_submit);
        ImageButton ib_sphereSoundA = findViewById(R.id.ib_sphereSoundA);
        ImageButton ib_sphereSoundV = findViewById(R.id.ib_sphereSoundV);

        bt_submit.setOnClickListener(view -> {

            if(et_sphereRadius.getText().toString().equals("")){
            displayToastIfEmptyRadius();
            }else {
                String sphereRadius = ActivityRhino.rhinoLibrary(et_sphereRadius.getText().toString());

                et_sphereRadius.setText(sphereRadius);

                double radius = Double.parseDouble(et_sphereRadius.getText().toString());

                if (radius > 0) {
                    DecimalFormat df = new DecimalFormat("#.####");
                    df.setRoundingMode(RoundingMode.CEILING);

                    double area = (4 * Math.PI * Math.pow(radius , 2));
                    tv_sphereArea.setText(df.format(area));

                    double volume = ((double) (4 / 3) * Math.PI * Math.pow(radius , 3));
                    tv_sphereVol.setText(df.format(volume));
                } else {
                    Toast.makeText(ActivitySphere.this , "Incorrect Entry" , Toast.LENGTH_SHORT).show();
                }
            }
            closeKeyboard();
        });

        textToSpeech = new TextToSpeech(getApplicationContext() , i -> {
            if(i == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        ib_sphereSoundA.setOnClickListener(view -> {
            String st = "Surface area = " + tv_sphereArea.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });

        ib_sphereSoundV.setOnClickListener(view -> {
            String st = "Volume = " + tv_sphereVol.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });

        }

    private void displayToastIfEmptyRadius() {
        Toast.makeText( ActivitySphere.this , "Enter the Value", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK && data != null) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                et_sphereRadius.setText(result.get(0));
            }
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void speakSphereRadius(View view) {
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
