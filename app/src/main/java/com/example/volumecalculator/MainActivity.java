package com.example.volumecalculator;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    FrameLayout fl_sphereImg,fl_coneImg, fl_cubeImg, fl_cuboidImg, fl_cylImg, fl_prismImg, fl_pyraImg;
    TextView tv_sphere, tv_cone, tv_cube, tv_cuboid, tv_cylinder, tv_prism, tv_pyramid;
    ImageButton ib_sphere, ib_cone, ib_cube, ib_cuboid, ib_cylinder, ib_prism, ib_pyramid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv_sphere = findViewById(R.id.tv_sphere);
        tv_cone = findViewById(R.id.tv_cone);
        tv_cube = findViewById(R.id.tv_cube);
        tv_cuboid = findViewById(R.id.tv_cuboid);
        tv_cylinder = findViewById(R.id.tv_cylinder);
        tv_prism = findViewById(R.id.tv_prism);
        tv_pyramid = findViewById(R.id.tv_pyramid);
        ib_sphere = findViewById(R.id.ib_sphere);
        ib_cone = findViewById(R.id.ib_cone);
        ib_cube = findViewById(R.id.ib_cube);
        ib_cuboid = findViewById(R.id.ib_cuboid);
        ib_cylinder = findViewById(R.id.ib_cylinder);
        ib_prism = findViewById(R.id.ib_prism);
        ib_pyramid = findViewById(R.id.ib_pyramid);
        fl_sphereImg = findViewById(R.id.fl_sphereImg);
        fl_coneImg = findViewById(R.id.fl_coneImg);
        fl_cubeImg = findViewById(R.id.fl_cubeImg);
        fl_cuboidImg = findViewById(R.id.fl_cuboidImg);
        fl_cylImg = findViewById(R.id.fl_cylImg);
        fl_prismImg = findViewById(R.id.fl_prismImg);
        fl_pyraImg = findViewById(R.id.fl_pyraImg);

        tv_sphere.setOnClickListener(view -> {
            Intent intent = new Intent(com.example.volumecalculator.MainActivity.this , ActivitySphere.class);
            startActivity(intent);
        });
        tv_cone.setOnClickListener(view -> {
            Intent intent = new Intent(com.example.volumecalculator.MainActivity.this , ActivityCone.class);
            startActivity(intent);
        });
        tv_cube.setOnClickListener(view -> {
            Intent intent = new Intent(com.example.volumecalculator.MainActivity.this , ActivityCube.class);
            startActivity(intent);
        });
        tv_cuboid.setOnClickListener(view -> {
            Intent intent = new Intent(com.example.volumecalculator.MainActivity.this , ActivityCuboid.class);
            startActivity(intent);
        });
        tv_cylinder.setOnClickListener(view -> {
            Intent intent = new Intent(com.example.volumecalculator.MainActivity.this , ActivityCylinder.class);
            startActivity(intent);
        });
        tv_prism.setOnClickListener(view -> {
            Intent intent = new Intent(com.example.volumecalculator.MainActivity.this , ActivityPrism.class);
            startActivity(intent);
        });
        tv_pyramid.setOnClickListener(view -> {
            Intent intent = new Intent(com.example.volumecalculator.MainActivity.this , ActivityPyramid.class);
            startActivity(intent);
        });

        ib_sphere.setOnClickListener(view -> {
            fl_sphereImg.setVisibility(View.VISIBLE);
            makeDisable();
        });
        ib_cone.setOnClickListener(view -> {
            fl_coneImg.setVisibility(View.VISIBLE);
            makeDisable();
        });
        ib_cube.setOnClickListener(view -> {
            fl_cubeImg.setVisibility(View.VISIBLE);
            makeDisable();
        });
        ib_cuboid.setOnClickListener(view -> {
            fl_cuboidImg.setVisibility(View.VISIBLE);
            makeDisable();
        });
        ib_cylinder.setOnClickListener(view -> {
            fl_cylImg.setVisibility(View.VISIBLE);
            makeDisable();
        });
        ib_prism.setOnClickListener(view -> {
            fl_prismImg.setVisibility(View.VISIBLE);
            makeDisable();
        });
        ib_pyramid.setOnClickListener(view -> {
            fl_pyraImg.setVisibility(View.VISIBLE);
            makeDisable();
        });
    }

    void makeDisable() {
        tv_sphere.setEnabled(false);
        tv_cone.setEnabled(false);
        tv_cube.setEnabled(false);
        tv_cuboid.setEnabled(false);
        tv_cylinder.setEnabled(false);
        tv_prism.setEnabled(false);
        tv_pyramid.setEnabled(false);
//        ib_sphere.setEnabled(false);
//        ib_cone.setEnabled(false);
//        ib_cube.setEnabled(false);
//        ib_cuboid.setEnabled(false);
//        ib_cylinder.setEnabled(false);
//        ib_prism.setEnabled(false);
//        ib_pyramid.setEnabled(false);
    }

    public void makeAbled(View view) {
        fl_sphereImg.setVisibility(View.GONE);
        fl_coneImg.setVisibility(View.GONE);
        fl_cubeImg.setVisibility(View.GONE);
        fl_cuboidImg.setVisibility(View.GONE);
        fl_cylImg.setVisibility(View.GONE);
        fl_prismImg.setVisibility(View.GONE);
        fl_pyraImg.setVisibility(View.GONE);
        tv_sphere.setEnabled(true);
        tv_cone.setEnabled(true);
        tv_cube.setEnabled(true);
        tv_cuboid.setEnabled(true);
        tv_cylinder.setEnabled(true);
        tv_prism.setEnabled(true);
        tv_pyramid.setEnabled(true);
//        ib_sphere.setEnabled(true);
//        ib_cone.setEnabled(true);
//        ib_cube.setEnabled(true);
//        ib_cuboid.setEnabled(true);
//        ib_cylinder.setEnabled(true);
//        ib_prism.setEnabled(true);
//        ib_pyramid.setEnabled(true);
    }
}