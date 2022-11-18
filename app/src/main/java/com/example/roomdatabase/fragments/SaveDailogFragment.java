package com.example.roomdatabase.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roomdatabase.EntityClass.LatestRecords;
import com.example.roomdatabase.EntityClass.UserModel;
import com.example.roomdatabase.R;
import com.example.roomdatabase.database.DatabaseClass;

import java.util.ArrayList;
import java.util.List;

public class SaveDailogFragment extends AppCompatDialogFragment {
    private EditText savetime;
    private List<UserModel> list;

    private LatestRecords lastetRecords;
    private int key;

    public SaveDailogFragment(int key) {
        this.key = key;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dailog_item, null);

        builder.setView(view)
                .setTitle("Enter Time")
                .setNegativeButton("cancel", (dialogInterface, i) -> {

                })
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    String savingTime = savetime.getText().toString();
//                        String password = editTextPassword.getText().toString();
                    if (!savingTime.equals("")) {
                        list = new ArrayList<>();
                        list = DatabaseClass.getDatabase(requireContext()).getDao().getAllData(key);
                        lastetRecords = new LatestRecords();
                        lastetRecords.setSavingTime(savingTime);
                        lastetRecords.setUserModels(list);
                        lastetRecords.setUserkey(key);

                        DatabaseClass.getDatabase(requireContext()).getDao().insertLatestRecords(lastetRecords);
                        Toast.makeText(requireContext(), "Data Saved Successfully", Toast.LENGTH_LONG).show();
                        Fragment fragment = new AllSavedRecordsFragment(key);
                        FragmentManager manager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        String backStackName = fragment.getClass().getName();
                        transaction.addToBackStack(backStackName);
                        fragment.setArguments(getArguments());
                        transaction.replace(R.id.container, fragment);
                        transaction.commit();

                    } else {
                        savetime.setError("Enter Time Duration");
                        Toast.makeText(requireContext(), "Record is not saved \n please enter valid Time Duration", Toast.LENGTH_LONG).show();

                        //  builder.show();
                    }
                });

        savetime = view.findViewById(R.id.edit_username);
        //  editTextPassword = view.findViewById(R.id.edit_password);

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface SaveDialogListener {
        void applyTexts(String time);
    }
}

