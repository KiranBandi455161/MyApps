package com.example.roomdatabase.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.EntityClass.LatestRecords;
import com.example.roomdatabase.database.DatabaseClass;
import com.example.roomdatabase.DateTimeUtils.DateAndTimeFormat;
import com.example.roomdatabase.EntityClass.UserModel;
import com.example.roomdatabase.R;
import com.example.roomdatabase.fragments.UpdateFragment;
import com.example.roomdatabase.fragments.VehiclesListFragment;

import java.util.Calendar;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    Context context;
    List<UserModel> list;
    private final ItemClickListener itemClickListener;

    public UserAdapter(Context context, List<UserModel> list, ItemClickListener itemClickListener) {
        this.context = context;
        this.list = list;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserModel userModel = (list.get(position));
        holder.vehicleName.setText(userModel.getVehicleName());
        holder.count.setText(String.valueOf(userModel.getCount()));
        holder.startTime.setText(DateAndTimeFormat.getFormattedDateTime(userModel.getStartTime()));
        holder.lastUpdate.setText(DateAndTimeFormat.getFormattedDateTime(userModel.getStartTime()));

        holder.addition.setOnClickListener(view -> {
            int countnu = getIncrement(userModel.getCount());
        userModel.setCount(countnu);
      //  userModel.setLastTime(Calendar.getInstance().getTime());
        holder.count.setText(String.valueOf(countnu));
       // holder.lastUpdate.setText(DateAndTimeFormat.getFormattedDateTime(userModel.getLastTime()));
        DatabaseClass.getDatabase(context).getDao().upDateCount(userModel);

        });
        holder.sub.setOnClickListener(view -> {
            int countnu = getSub(userModel.getCount());
            userModel.setCount(countnu);
         //   userModel.setLastTime(Calendar.getInstance().getTime());
            holder.count.setText(String.valueOf(countnu));
          //  holder.lastUpdate.setText(DateAndTimeFormat.getFormattedDateTime(userModel.getLastTime()));
            DatabaseClass.getDatabase(context).getDao().upDateCount(userModel);
        });

        holder.vehicleName.setOnClickListener(view -> resetOrUpdate(userModel.getVehicleName(), userModel.getCount(), userModel.getKey()));
     //   holder.count.setOnClickListener(view -> resetOrUpdate(userModel.getVehicleName(), userModel.getCount(), userModel.getKey()));
    //    holder.itemView.setOnClickListener(v -> itemClickListener.onClick(list.get(position)));

    }
    private int getIncrement(int count){
        int cre = count;
        cre++;
        return cre;

    }

    private int getSub(int count){
        int cre = count;
        cre--;
        return cre;
    }

    public void resetOrUpdate(String vehicleName, int count, int key) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("You can Update or delete the Records...");
        builder.setIcon(R.drawable.add);
        builder.setPositiveButton("Update", (dialogInterface, i) -> {
            Fragment fragment = new UpdateFragment(vehicleName, count, key);
            FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            String backStackName = fragment.getClass().getName();
            transaction.addToBackStack(backStackName);
          //  fragment.setArguments(getArguments());
            transaction.replace(R.id.container, fragment);
            transaction.commit();
        });
        builder.setNegativeButton("Delete", (dialogInterface, i) -> {
            DatabaseClass.getDatabase(context).getDao().deleteUserRecord(key);
            Fragment fragment = new VehiclesListFragment(key);
            FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            String backStackName = fragment.getClass().getName();
            transaction.addToBackStack(backStackName);
          //  fragment.setArguments(getArguments());
            transaction.replace(R.id.container, fragment);
            transaction.commit();

        });
        builder.create();
        builder.show();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener {
        void onClick(UserModel userModel);

    }

    class ViewHolder extends RecyclerView.ViewHolder {

        Button update, delete;
        ImageView addition, sub;

        TextView count;
        TextView date;
        TextView startTime;
        TextView lastUpdate;
        TextView vehicleName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicleName = itemView.findViewById(R.id.vehicleName);
            count = itemView.findViewById(R.id.count);
            startTime = itemView.findViewById(R.id.startTime);
            lastUpdate = itemView.findViewById(R.id.lastUpdate);
            addition = itemView.findViewById(R.id.addition);
            sub = itemView.findViewById(R.id.subtraction);


        }
    }
}
