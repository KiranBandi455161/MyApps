package com.example.roomdatabase.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.Adapter.TemplateAdapter;
import com.example.roomdatabase.EntityClass.TemplateEntity;
import com.example.roomdatabase.R;
import com.example.roomdatabase.database.DatabaseClass;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TemplateListFragment extends Fragment implements View.OnClickListener, TemplateAdapter.ItemClickListener {
    FloatingActionButton addtemplate;
    RecyclerView recyclerview;
    List<TemplateEntity> list;
    TextView tabName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.template_list, container, false);
        addtemplate = view.findViewById(R.id.addtemplate);
        recyclerview = view.findViewById(R.id.recyclerview);
        tabName = view.findViewById(R.id.tabName);

        getData();

        addtemplate.setOnClickListener(view1 -> {
            Fragment fragment = new TemplateCreateFragment();
            FragmentManager manager = requireActivity().getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            String backStackName = fragment.getClass().getName();
            transaction.addToBackStack(backStackName);
            fragment.setArguments(getArguments());
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        });
        return view;
    }

    @Override
    public void onClick(View view) {

    }

    private void getData() {

        list = new ArrayList<>();
        list = DatabaseClass.getDatabase(requireContext()).getDao().getTemplateList();
        if(list != null && !list.isEmpty()){
            TemplateAdapter adapter = new TemplateAdapter(requireContext(), list, templateEntity -> {
                Fragment fragment = new VehiclesListFragment(templateEntity.getKey());
                FragmentManager manager = requireActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                String backStackName = fragment.getClass().getName();
                transaction.addToBackStack(backStackName);
                fragment.setArguments(getArguments());
                transaction.replace(R.id.container, fragment);
                transaction.commit();

            });
            recyclerview.setLayoutManager(new LinearLayoutManager(requireContext()));
            recyclerview.setHasFixedSize(false);
            recyclerview.setAdapter(adapter);
        }else {
            tabName.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(TemplateEntity templateEntity) {

    }
}
