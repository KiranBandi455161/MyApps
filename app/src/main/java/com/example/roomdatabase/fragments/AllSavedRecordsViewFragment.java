package com.example.roomdatabase.fragments;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.roomdatabase.EntityClass.UserModel;
import com.example.roomdatabase.R;

import java.text.MessageFormat;
import java.util.List;

public class AllSavedRecordsViewFragment extends Fragment {
    private LayoutInflater layoutInflater;
    private LinearLayout llView;
    private final List<UserModel> userModels;

    public AllSavedRecordsViewFragment(List<UserModel> userModels) {
        this.userModels = userModels;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_list_view_records, container, false);
        this.layoutInflater = inflater;
        llView = view.findViewById(R.id.ll_view);
        if(userModels != null && !userModels.isEmpty()){
            getData(userModels);
        }
        return view;
    }

    public void getData(List<UserModel> userModels) {
        View footerView = layoutInflater.inflate(R.layout.count_list_footer, null);
        llView.addView(footerView);
        LinearLayout provisional_ll = footerView.findViewById(R.id.provision_ll);

        for (int i = 0; i < userModels.size(); i++) {
            View provisionView = layoutInflater.inflate(R.layout.provision_item, null);
            provisional_ll.addView(provisionView);
            TextView name_tv = provisionView.findViewById(R.id.name_tv);
            TextView value_tv = provisionView.findViewById(R.id.value_tv);
            name_tv.setTextColor(getResources().getColor(R.color.black));
            name_tv.setTypeface(Typeface.DEFAULT);
            value_tv.setTypeface(Typeface.DEFAULT);
            name_tv.setText(MessageFormat.format("{0}", userModels.get(i).getVehicleName()));
            value_tv.setText(String.valueOf(userModels.get(i).getCount()));
        }
    }
}
