package com.example.roomdatabase.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.roomdatabase.EntityClass.TitleEntity;
import com.example.roomdatabase.R;
import com.example.roomdatabase.database.DatabaseClass;

public class TitleDialogFragment extends AppCompatDialogFragment {
    EditText editTitle;
    private String titleName;
    private int templatekey;

    public TitleDialogFragment(String titleName,int templatekey) {
        this.titleName = titleName;
        this.templatekey = templatekey;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.title_dailog, null);
        editTitle = view.findViewById(R.id.edit_titleName);
        editTitle.setText(titleName);
        builder.setView(view)
                .setTitle("Enter Title")
                .setNegativeButton("cancel", (dialogInterface, i) -> {
                })
                .setPositiveButton("save", (dialogInterface, i) -> {
                    String titleName =editTitle.getText().toString();
                    TitleEntity titleEntity = new TitleEntity();
                    titleEntity.setTitleName(titleName);
                    titleEntity.setTemplateKey(templatekey);

                   TitleEntity entity = DatabaseClass.getDatabase(requireContext()).getDao().getTitle(templatekey);
                    if(entity == null){
                        DatabaseClass.getDatabase(requireContext()).getDao().insertTitleName(titleEntity);
                    }else {
                        DatabaseClass.getDatabase(requireContext()).getDao().updateTitleName(titleName, entity.getKey());
                        Fragment fragment = new VehiclesListFragment(templatekey);
                        FragmentManager manager = requireActivity().getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        String backStackName = fragment.getClass().getName();
                        transaction.addToBackStack(backStackName);
                        fragment.setArguments(getArguments());
                        transaction.replace(R.id.container, fragment);
                        while(manager.getBackStackEntryCount() > 0) {
                            manager.popBackStackImmediate();
                        }
                        transaction.commit();

                    }

                });

        return builder.create();
    }
}
