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

import com.example.roomdatabase.EntityClass.TemplateEntity;
import com.example.roomdatabase.R;
import com.example.roomdatabase.database.DatabaseClass;

public class TemplateCreateFragment extends Fragment {
    private EditText templateName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.create_template, container, false);
        templateName = view.findViewById(R.id.templateName);
        Button templateButton = view.findViewById(R.id.templateButton);
        templateButton.setOnClickListener(view1 -> {
            String tn = templateName.getText().toString();
            if (tn != null && !tn.isEmpty()) {
                saveData(tn);
            } else {
                templateName.setError("enter value");
            }
        });

        return view;
    }

    private void saveData(String tn) {
        TemplateEntity templateEntity = new TemplateEntity();
        templateEntity.setTemplateName(tn);
        DatabaseClass.getDatabase(requireContext()).getDao().insertTemplateName(templateEntity);
        Toast.makeText(requireContext(), "Data Successfully Saved", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStackImmediate();

    }
}

