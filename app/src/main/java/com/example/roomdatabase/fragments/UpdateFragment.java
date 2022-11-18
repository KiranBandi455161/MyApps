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

public class UpdateFragment extends Fragment implements View.OnClickListener {
    String vehicleName;
    int count;
    int key;
    EditText vehicleNameEt;
    EditText vehicleCountEt;
    Button update;

    public UpdateFragment(String vehicleName, int count ,int key) {
        this.vehicleName = vehicleName;
        this.count = count;
        this.key = key;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.update_fragment, container, false);
        vehicleNameEt = view.findViewById(R.id.vehicleName);
        vehicleCountEt = view.findViewById(R.id.vehicleCount);
        update = view.findViewById(R.id.update);
        vehicleNameEt.setText(vehicleName);
        vehicleCountEt.setText(String.valueOf(count));
        update.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
       String newVehicleName= vehicleNameEt.getText().toString();
       int newCount = Integer.parseInt(vehicleCountEt.getText().toString());
       DatabaseClass.getDatabase(requireContext()).getDao().updateList(newVehicleName,newCount,key);
        Toast.makeText(requireContext(), "Updated Successfully ", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStackImmediate();
    }
}
