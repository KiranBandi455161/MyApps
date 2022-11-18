package com.example.roomdatabase.Adapter;

import android.app.AlertDialog;
import android.content.Context;
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

import com.example.roomdatabase.EntityClass.TemplateEntity;
import com.example.roomdatabase.R;
import com.example.roomdatabase.database.DatabaseClass;
import com.example.roomdatabase.fragments.TemplateListFragment;
import com.example.roomdatabase.fragments.UpdateTemplate;

import java.util.List;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.ViewHolder> {

    private final ItemClickListener itemClickListener;
    private final Context context;
    List<TemplateEntity> templateEntities;

    public TemplateAdapter(Context context, List<TemplateEntity> templateEntities, ItemClickListener itemClickListener) {
        this.context = context;
        this.templateEntities = templateEntities;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.template_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TemplateEntity templateEntity = templateEntities.get(position);
        holder.templateName.setText(templateEntity.getTemplateName());

        holder.itemView.setOnClickListener(v -> itemClickListener.onClick(templateEntities.get(position)));
        holder.delete.setOnClickListener(view -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setMessage("You can update or delete the Tabs..");
            builder.setPositiveButton("Delete", (dialogInterface, i) -> {
                delete(templateEntity);

            });
            builder.setNegativeButton("Update", (dialogInterface, i) -> {

                Fragment fragment = new UpdateTemplate(templateEntity.getTemplateName(), templateEntity.getKey());
                FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                String fragmentName = fragment.getClass().getName();
                transaction.addToBackStack(fragmentName);
                transaction.replace(R.id.container, fragment);
                transaction.commit();
            });
            builder.create();
            builder.show();

        });

    }

    @Override
    public int getItemCount() {
        return templateEntities.size();
    }

    public void delete(TemplateEntity templateEntity) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure to delete the Tab....?");
        builder.setPositiveButton("YES", (dialogInterface, i) -> {

            DatabaseClass.getDatabase(context).getDao().deleteTemplate(templateEntity.getKey());
            DatabaseClass.getDatabase(context).getDao().deleteTempSavedRecords(templateEntity.getKey());
            DatabaseClass.getDatabase(context).getDao().deleteTempUserRecords(templateEntity.getKey());
            DatabaseClass.getDatabase(context).getDao().deleteTempTitleRecords(templateEntity.getKey());
            Toast.makeText(context, "Record Deleted", Toast.LENGTH_LONG).show();


            Fragment fragment = new TemplateListFragment();
            FragmentManager manager = ((FragmentActivity) context).getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            String fragmentName = fragment.getClass().getName();
            transaction.addToBackStack(fragmentName);
            transaction.replace(R.id.container, fragment);
            transaction.commit();
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
        });
        builder.create();
        builder.show();

    }

    public interface ItemClickListener {
        void onClick(TemplateEntity templateEntity);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView templateName;
        ImageView delete;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            templateName = itemView.findViewById(R.id.savedTime);
            delete = itemView.findViewById(R.id.deleteButton);
            linearLayout = itemView.findViewById(R.id.colorLinear);
        }
    }
}
