package com.example.roomdatabase;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roomdatabase.EntityClass.UserModel;
import com.example.roomdatabase.fragments.SaveDailogFragment;
import com.example.roomdatabase.fragments.TemplateListFragment;
import com.example.roomdatabase.fragments.VehiclesListFragment;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity  {

    FrameLayout frameLayout;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        frameLayout = findViewById(R.id.container);
       // toolbar = findViewById(R.id.toolbar);

        Fragment fragment = new TemplateListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.container, fragment);
        ft.commit();
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu_item,menu);
        return true;
    }
}