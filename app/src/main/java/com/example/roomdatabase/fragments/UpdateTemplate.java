package com.example.roomdatabase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roomdatabase.R;
import com.example.roomdatabase.database.DatabaseClass;

public class UpdateTemplate extends Fragment implements View.OnClickListener {
    String tabName;
    int count;
    int key;
    EditText tabNameEt;
    EditText vehicleCountEt;
    Button update;

    public UpdateTemplate(String tabName, int key) {
        this.tabName = tabName;

        this.key = key;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.update_template, container, false);
        tabNameEt = view.findViewById(R.id.tabName);
        vehicleCountEt = view.findViewById(R.id.vehicleCount);
        update = view.findViewById(R.id.update);
        tabNameEt.setText(tabName);
        update.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        String newVehicleName = tabNameEt.getText().toString();
        DatabaseClass.getDatabase(requireContext()).getDao().updateTempList(newVehicleName, key);
        Toast.makeText(requireContext(), "Updated Successfully ", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}
