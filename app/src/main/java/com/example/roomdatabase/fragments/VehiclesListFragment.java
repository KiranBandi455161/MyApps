package com.example.roomdatabase.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.Adapter.UserAdapter;
import com.example.roomdatabase.EntityClass.TitleEntity;
import com.example.roomdatabase.EntityClass.UserModel;
import com.example.roomdatabase.R;
import com.example.roomdatabase.database.DatabaseClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VehiclesListFragment extends Fragment implements View.OnClickListener, SaveDailogFragment.SaveDialogListener, UserAdapter.ItemClickListener {
    private final int key;
    RecyclerView recyclerview;
    ImageView gridButton;
    ImageView lineargrid;
    FloatingActionButton actionButton;
    Button resetAll;
    Button savedVehicles;
    Button saveData;
    Button homepage;
    TextView title;
    private List<UserModel> list;
    private TitleEntity entity;


    public VehiclesListFragment(int key) {
        this.key = key;


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_get_data, container, false);

        recyclerview = view.findViewById(R.id.recyclerview);
        gridButton = view.findViewById(R.id.gridButton);
        lineargrid = view.findViewById(R.id.lineargrid);
        actionButton = view.findViewById(R.id.addVehicle);
        resetAll = view.findViewById(R.id.reset);
        savedVehicles = view.findViewById(R.id.savedVehicleList);
        saveData = view.findViewById(R.id.saveData);
        title = view.findViewById(R.id.titleName);
        homepage = view.findViewById(R.id.homepage);
        getData();
        actionButton.setOnClickListener(this);
        savedVehicles.setOnClickListener(this);
        saveData.setOnClickListener(this);
        title.setOnClickListener(this);
        homepage.setOnClickListener(this);


        entity = new TitleEntity();

        entity = DatabaseClass.getDatabase(requireContext()).getDao().getTitle(key);
        try {
            if (entity == null) {
                entity = new TitleEntity();
                entity.setTitleName("Vehicles & Count");
                entity.setTemplateKey(key);
                DatabaseClass.getDatabase(requireContext()).getDao().insertTitleName(entity);

            } else {
                title.setText(entity.getTitleName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        resetAll.setOnClickListener(view13 -> {
            resetAllRecords();
        });


        gridButton.setOnClickListener(view12 -> {
            list = new ArrayList<>();
            list = DatabaseClass.getDatabase(requireContext()).getDao().getAllData(key);
            UserAdapter adapter = new UserAdapter(requireContext(), list, userModel -> {
                Fragment fragment = new UpdateFragment(userModel.getVehicleName(), userModel.getCount(), userModel.getKey());
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                String backStackName = fragment.getClass().getName();
                transaction.addToBackStack(backStackName);
                fragment.setArguments(getArguments());
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            });
            recyclerview.setLayoutManager(new GridLayoutManager(requireContext(), 3));
            recyclerview.setHasFixedSize(false);
            recyclerview.setAdapter(adapter);
            lineargrid.setVisibility(View.VISIBLE);
            gridButton.setVisibility(View.GONE);
        });
        lineargrid.setOnClickListener(view1 -> {
            gridButton.setVisibility(View.VISIBLE);
            lineargrid.setVisibility(View.GONE);
            getData();
        });
        return view;
    }

    private void getData() {

        list = new ArrayList<>();
        list = DatabaseClass.getDatabase(requireContext()).getDao().getAllData(key);
        UserAdapter adapter = new UserAdapter(requireContext(), list, userModel -> {
        });
        recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerview.setHasFixedSize(false);
        recyclerview.setAdapter(adapter);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.addVehicle) {
            Fragment fragment = new VehicleCreateFormFragment(key);
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            String backStackName = fragment.getClass().getName();
            transaction.addToBackStack(backStackName);
            fragment.setArguments(getArguments());
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        } else if (view.getId() == R.id.savedVehicleList) {
            Fragment fragment = new AllSavedRecordsFragment(key);
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            String backStackName = fragment.getClass().getName();
            transaction.addToBackStack(backStackName);
            fragment.setArguments(getArguments());
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        } else if (view.getId() == R.id.saveData) {
            saveRecords();
        } else if (view.getId() == R.id.titleName) {
            saveTitleName();
        } else if (view.getId() == R.id.homepage) {
            requireActivity().getSupportFragmentManager().popBackStackImmediate();
        }
    }

    private void saveTitleName() {

        TitleDialogFragment titleDialogFragment = new TitleDialogFragment(entity.getTitleName(), key);
        titleDialogFragment.show(getActivity().getSupportFragmentManager(), "save record Dialog");
    }
    // }

    public void resetAllRecords() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure to reset all records ?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            for (int j = 0; j < list.size(); j++) {
                int v = 0;
                DatabaseClass.getDatabase(requireContext()).getDao().restCountValue(v, list.get(j).getKey());
            }
            getData();
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
        });
        builder.create();
        builder.show();
    }

    public void saveRecords() {
        if (list != null && !list.isEmpty()) {
            SaveDailogFragment saveDailogFragment = new SaveDailogFragment(key);
            saveDailogFragment.show(getActivity().getSupportFragmentManager(), "save record Dialog");
        } else {
            Toast.makeText(requireContext(), "At least enter one record", Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void applyTexts(String time) {

    }


    @Override
    public void onClick(UserModel userModel) {

    }
}


