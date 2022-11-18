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

import com.example.roomdatabase.database.DatabaseClass;
import com.example.roomdatabase.EntityClass.UserModel;
import com.example.roomdatabase.R;

import java.util.Calendar;
import java.util.Date;

public class VehicleCreateFormFragment extends Fragment {
    EditText name, count;
    Button save;
    private int templatekey;

    public VehicleCreateFormFragment(int templatekey) {
        this.templatekey = templatekey;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.vechicle_create_form, container, false);
        save = view.findViewById(R.id.save);
        name = view.findViewById(R.id.vehicleName);
        count = view.findViewById(R.id.vehicleCount);
        save.setOnClickListener(view1 -> saveData());
        return view;
    }

    private void saveData() {
        UserModel model = new UserModel();
        if (!name.getText().toString().isEmpty()) {
            String name_txt = name.getText().toString().trim();
            model.setVehicleName(name_txt);
        } else  {
            name.setError("Enter Vehicle Name");
        }

        if (!count.getText().toString().isEmpty()) {
            int vehicleCount = Integer.parseInt(count.getText().toString());
            model.setCount(vehicleCount);
        } else {
            count.setError("Enter Count");
        }

        Date startTime = Calendar.getInstance().getTime();
        Date lastTime = Calendar.getInstance().getTime();
        model.setStartTime(startTime);
        model.setLastTime(lastTime);
        model.setTemplateParentId(templatekey);
        DatabaseClass.getDatabase(requireContext()).getDao().insertAllData(model);
        Toast.makeText(requireContext(), "Data Successfully Saved", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStackImmediate();

    }
}
