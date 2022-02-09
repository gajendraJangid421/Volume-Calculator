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

public class ActivityCuboid extends AppCompatActivity {
    private EditText et_cuboidLength, et_cuboidWidth, et_cuboidHeight;
    private TextView tv_cuboidVol, tv_cuboidArea;
    private Button bt_submit;
    private ImageButton ib_cuboidSoundA, ib_cuboidSoundV;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuboid);
        getSupportActionBar().setTitle("Cuboid");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        et_cuboidLength = findViewById(R.id.et_cuboidLength);
        et_cuboidWidth = findViewById(R.id.et_cuboidWidth);
        et_cuboidHeight = findViewById(R.id.et_cuboidHeight);
        tv_cuboidArea = findViewById(R.id.tv_cuboidArea);
        tv_cuboidVol = findViewById(R.id.tv_cuboidVol);
        bt_submit = findViewById(R.id.bt_submit);
        ib_cuboidSoundA = findViewById(R.id.ib_cuboidSoundA);
        ib_cuboidSoundV = findViewById(R.id.ib_cuboidSoundV);

        bt_submit.setOnClickListener(view -> {

            if(et_cuboidLength.getText().toString().equals("") || et_cuboidWidth.getText().toString().equals("") || et_cuboidHeight.getText().toString().equals("")){
                displayToastIfEmptyRadius();
            }else {
                String cuboidLength = ActivityRhino.rhinoLibrary(et_cuboidLength.getText().toString());
                String cuboidWidth = ActivityRhino.rhinoLibrary(et_cuboidWidth.getText().toString());
                String cuboidHeight = ActivityRhino.rhinoLibrary(et_cuboidHeight.getText().toString());

                et_cuboidLength.setText(cuboidLength);
                et_cuboidWidth.setText(cuboidWidth);
                et_cuboidHeight.setText(cuboidHeight);

                double length = Double.parseDouble(et_cuboidLength.getText().toString());
                double width = Double.parseDouble(et_cuboidWidth.getText().toString());
                double height = Double.parseDouble(et_cuboidHeight.getText().toString());

                if (length > 0 && width>0 && height>0) {
                    DecimalFormat df = new DecimalFormat("#.####");
                    df.setRoundingMode(RoundingMode.CEILING);

                    double area = (((length * width) + (width * height) + (length * height)) * 2);
                    tv_cuboidArea.setText(df.format(area));

                    double volume = (length * width * height);
                    tv_cuboidVol.setText(df.format(volume));
                } else {
                    Toast.makeText(ActivityCuboid.this , "Incorrect Entry" , Toast.LENGTH_SHORT).show();
                }
            }

            closeKeyboard();
        });

        textToSpeech = new TextToSpeech(getApplicationContext() , i -> {
            if(i == TextToSpeech.SUCCESS) {
                textToSpeech.setLanguage(Locale.ENGLISH);
            }
        });

        ib_cuboidSoundA.setOnClickListener(view -> {
            String st = "Surface area = " + tv_cuboidArea.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });

        ib_cuboidSoundV.setOnClickListener(view -> {
            String st = "Volume = " + tv_cuboidVol.getText().toString();

            textToSpeech.speak(st, TextToSpeech.QUEUE_FLUSH, null);
        });
    }

    private void displayToastIfEmptyRadius() {
        Toast.makeText( ActivityCuboid.this , "Enter the Value", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
        super.onActivityResult(requestCode , resultCode , data);
        switch (requestCode){
            case 1:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_cuboidLength.setText(result.get(0));
                }
                break;
            case 2:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_cuboidWidth.setText(result.get(0));
                }
                break;
            case 3:
                if(resultCode == RESULT_OK && data != null){
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    et_cuboidHeight.setText(result.get(0));
                }
                break;
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    public void speakCuboidLength(View view) {
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
    public void speakCuboidWidth(View view) {
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
    public void speakCuboidHeight(View view) {
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

