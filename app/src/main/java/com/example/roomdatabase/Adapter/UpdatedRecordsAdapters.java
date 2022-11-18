package com.example.roomdatabase.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.EntityClass.LatestRecords;
import com.example.roomdatabase.R;
import com.example.roomdatabase.database.DatabaseClass;
import com.example.roomdatabase.fragments.AllSavedRecordsFragment;
import com.example.roomdatabase.fragments.TemplateListFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class UpdatedRecordsAdapters extends RecyclerView.Adapter<UpdatedRecordsAdapters.ViewHolder> {
    private final Context applicationContext;
    private final ItemClickListener itemClickListener;
    List<LatestRecords> upadatedRecords;

    public UpdatedRecordsAdapters(Context applicationContext, List<LatestRecords> upadatedRecords, ItemClickListener itemClickListener) {
        this.applicationContext = applicationContext;
        this.upadatedRecords = upadatedRecords;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LatestRecords records = upadatedRecords.get(position);
        holder.time.setText(records.getSavingTime());

        holder.deleteButton.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(applicationContext);
            builder.setMessage("Are you sure to delete the record ?");
            builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                DatabaseClass.getDatabase(applicationContext).getDao().deleteSingleItem(records.getKey());
                Toast.makeText(applicationContext ,"Record Deleted",Toast.LENGTH_LONG).show();
                Fragment fragment = new AllSavedRecordsFragment(records.getUserkey());
                FragmentManager manager = ((FragmentActivity) applicationContext).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                String fragmentName = fragment.getClass().getName();
                transaction.addToBackStack(fragmentName);
                transaction.replace(R.id.container, fragment);
                while(manager.getBackStackEntryCount() > 1) {
                    manager.popBackStackImmediate();
                }
                transaction.commit();

            });
            builder.setNegativeButton("No", (dialogInterface, i) -> {
            });
            builder.create();
            builder.show();
        });
        holder.itemView.setOnClickListener(v -> itemClickListener.onClick(upadatedRecords.get(position)));
    }

    @Override
    public int getItemCount() {
        return upadatedRecords.size();
    }


    public interface ItemClickListener {
        void onClick(LatestRecords lastetRecords);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        TextView count;
        LinearLayout layout;
        ImageView deleteButton;
        //   CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            time = itemView.findViewById(R.id.savedTime);
            layout = itemView.findViewById(R.id.linearItem);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            // layout = itemView.findViewById(R.id.layout);

        }
    }
}
