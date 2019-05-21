package com.example.flashlight;

import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private  boolean Check = true;
    private ImageButton imgbutton;
    private  boolean Flash= true;
    private Camera camera;
    private Camera.Parameters parameters;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgbutton = (ImageButton) findViewById(R.id.imgbutton);
        textView=(TextView)findViewById(R.id.textView);
        Check = getApplicationContext()
                .getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (Check == false) {
            AlertDialog.Builder alertdialog = new AlertDialog.Builder(MainActivity.this);
            alertdialog.create();
            alertdialog.setTitle("Lỗi!");
            alertdialog.setMessage("Thiết bị của bạn không hỗ trợ Flash!");
            alertdialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            alertdialog.show();
        }
       getCamera();
        imgbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Flash == true) {
                    OpenFlash();
                } else {
                    StopFlash();
                }
            }
        });
        }


    private void getCamera() {
        if (camera == null && parameters == null) {
            camera = Camera.open();
            parameters = camera.getParameters();
        }
    }
    private void OpenFlash() {
        if (Flash == true) {
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
            camera.setParameters(parameters);
            camera.startPreview();
            Flash = false;
            StatusFlash();
        }
    }
    private void StopFlash() {
        if (Flash == false) {
            parameters = camera.getParameters();
            parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
            camera.setParameters(parameters);
            camera.stopPreview();
            Flash = true;
            StatusFlash();

        }
    }
    private void StatusFlash() {
        if (Flash == true) {
            imgbutton.setImageResource(R.drawable.btn_off);
            Toast.makeText(this, "Đèn Flash đã tắt ", Toast.LENGTH_SHORT).show();

        } else {
            imgbutton.setImageResource(R.drawable.btn_on);

            Toast.makeText(this, "Đèn Flash đang mở", Toast.LENGTH_SHORT).show();
        }
    }
    }
