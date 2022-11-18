package com.example.roomdatabase;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.ArrayList;

@SuppressLint("CustomSplashScreen")
public class SplashScreen extends AppCompatActivity {
    private String[] permission;
    private boolean isSecondTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  secureScreen(SplashScreen.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        if (getIntent() != null && getIntent().getExtras() != null) {
            Bundle bundle = getIntent().getExtras();
            bundle.putString("isFromFCM", "true");
            //        Utils.convertBundleToJson(this, bundle);
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    int waited = 0;
                    while (waited < 3000) {
                        sleep(100);
                        waited += 100;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (checkPermissions()) {
                        requestPermission();
                    } else {
                        startActivity();
                    }
                }
            }
        };
        thread.start();
    }

    private boolean checkPermissions() {
        ArrayList<String> list = new ArrayList<>();
        if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            list.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (list.size() == 0) {
            return false;
        } else {
            permission = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                permission[i] = list.get(i);
            }
            return true;
        }
    }

    private boolean checkPermission(String permission1) {
        return ActivityCompat.checkSelfPermission(getApplicationContext(), permission1) != PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            requestPermissions(permission, 100);
        }
    }

    private void startActivity() {
        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
        startActivity(intent);

        finish();
    }


    @SuppressLint("InflateParams")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (!checkPermissions() && !isSecondTime) {
            isSecondTime = true;
            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View headerView = inflater.inflate(R.layout.dialog_header, null);
            TextView header_tv = headerView.findViewById(R.id.header_tv);
            header_tv.setText(getResources().getString(R.string.app_name));

            AlertDialog.Builder builder = new AlertDialog.Builder(SplashScreen.this);
            builder.setMessage(getString(R.string.permissions_req));
            builder.setPositiveButton("OK", (dialog, which) -> {
                if (checkPermissions()) {
                    requestPermission();
                } else {
                    startActivity();
                }
            });
            builder.setCustomTitle(headerView);
            builder.show();
        } else {
            startActivity();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu_item,menu);
        return true;
    }
}