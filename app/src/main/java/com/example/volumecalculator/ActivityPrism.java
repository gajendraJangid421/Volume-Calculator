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

public class ActivityPrism extends AppCompatActivity {
    private EditText et_prismLength, et_prismWidth, et_prismHeight;
    private TextView tv_prismVol, tv_prismArea;
    private Button bt_submit;
    private ImageButton ib_prismSoundA, ib_prismSoundV;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prism);
        getSupportActionBar().setTitle("Isosceles Triangular Prism");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_prismLength = findViewById(R.id.et_prismLength);
        et_prismWidth = findViewById(R.id.et_prismWidth);
        et_prismHeight = findViewById(R.id.et_prismHeight);
        tv_prismArea = findViewById(R.id.tv_prismArea);
        tv_prismVol = findViewById(R.id.tv_prismVol);
        bt_submit = findViewById(R.id.bt_submit);
        ib_prismSoundA = findViewById(R.id.ib_prismSoundA);
        ib_prismSoundV = findViewById(R.id.ib_prismSoundV);

        bt_submit.setOnClickListener(view -> {

            if(et_prismLength.getText().toString().equals("") || et_prismWidth.getText().toString().equals("") || et_prismHeight.getText().toString().equals("")){
                displayToastIfEmptyRadius();
            }else {
                String prismLength = ActivityRhino.rhinoLibrary(et_prismLength.getText().toString());
                String prismWidth = ActivityRhino.rhinoLibrary(et_prismWidth.getText().toString());
                String prismHeight = ActivityRhino.rhinoLibrary(et_prismHeight.getText().toString());

                et_prismLength.setText(prismLength);
                et_prismWidth.setText(prismWidth);
                et_prismHeight.setText(prismHeight);

                double length = Double.parseDouble(et_prismLength.getText().toString());
                double width = Double.parseDouble(et_prismWidth.getText().toString());
                double height = Double.parseDouble(et_prismHeight.getText().toString());

                if (length > 0 && width>0 && height>0) {
                    DecimalFormat df = new DecimalFormat("#.####");
                    df.setRoundingMode(RoundingMode.CEILING);

                    double area = ((length * height) + (length * width) + (2 * width * Math.sqrt(Math.pow(height,2) + Math.pow(length/2,2))));
                    tv_prismArea.setText(df.format(area));

                    double volume = (0.5 * length * width * height);
                    tv_prismVol.setText(df.format(volume));
                } else {
                    Toast.makeText(ActivityPrism.this , "Incorrect Entry" , Toast.LENGTH_SHORT).show();
                }
            }

            closeKeyboard();
        });

        textToSpeech = new TextToSpeech(getApplicationContext() , i -> {
            if(i == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        ib_prismSoundA.setOnClickListener(view -> {
            String st = "Surface area = " + tv_prismArea.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });

        ib_prismSoundV.setOnClickListener(view -> {
            String st = "Volume = " + tv_prismVol.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void displayToastIfEmptyRadius() {
        Toast.makeText( ActivityPrism.this , "Enter the Value", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_prismLength.setText(result.get(0));
                }
                break;
            case 2:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_prismWidth.setText(result.get(0));
                }
                break;
            case 3:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_prismHeight.setText(result.get(0));
                }
                break;
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void speakPrismLength(View view) {
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
    public void speakPrismWidth(View view) {
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
    public void speakPrismHeight(View view) {
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

