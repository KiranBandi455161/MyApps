package com.example.roomdatabase.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roomdatabase.Adapter.UpdatedRecordsAdapters;
import com.example.roomdatabase.EntityClass.LatestRecords;
import com.example.roomdatabase.EntityClass.TitleEntity;
import com.example.roomdatabase.EntityClass.UserModel;
import com.example.roomdatabase.R;
import com.example.roomdatabase.database.DatabaseClass;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class AllSavedRecordsFragment extends Fragment implements UpdatedRecordsAdapters.ItemClickListener, View.OnClickListener {

    private final int key;
    RecyclerView recyclerview;
    Button delete;
    Button export;
    private List<LatestRecords> upadatedRecords;
    private List<UserModel> userModelList;
    private TitleEntity entity;
    private String fileName;
    private LinearLayout noDataFountTv;
    private ImageView gridButton;
    private ImageView linearGrid;

    public AllSavedRecordsFragment(int key) {
        this.key = key;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.activity_upadated_records, container, false);
        recyclerview = view.findViewById(R.id.recyclerview);
        delete = view.findViewById(R.id.delete);
        export = view.findViewById(R.id.export);
        noDataFountTv = view.findViewById(R.id.noDataFountTv);
        gridButton = view.findViewById(R.id.gridButton);
        linearGrid = view.findViewById(R.id.lineargrid);
        delete.setOnClickListener(this);
        export.setOnClickListener(this);

        entity = DatabaseClass.getDatabase(requireContext()).getDao().getTitle(key);
        fileName = entity.getTitleName();

        gridButton.setOnClickListener(view1 -> {
            upadatedRecords = new ArrayList<>();
            upadatedRecords = DatabaseClass.getDatabase(requireContext()).getDao().getUpdatedRecords(key);
            if (upadatedRecords != null && !upadatedRecords.isEmpty()) {
                UpdatedRecordsAdapters adapter = new UpdatedRecordsAdapters(requireContext(), upadatedRecords, lastetRecords -> {
                    Fragment fragment = new AllSavedRecordsViewFragment(lastetRecords.getUserModels());
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

                linearGrid.setVisibility(View.VISIBLE);
                gridButton.setVisibility(View.GONE);
            } else {
                noDataFountTv.setVisibility(View.VISIBLE);
            }
        });
        linearGrid.setOnClickListener(view12 -> {
            gridButton.setVisibility(View.VISIBLE);
            linearGrid.setVisibility(View.GONE);
            getData();
        });

        getData();
        return view;
    }

    public void getData() {
        upadatedRecords = new ArrayList<>();
        upadatedRecords = DatabaseClass.getDatabase(requireContext()).getDao().getUpdatedRecords(key);
        if(upadatedRecords.size() == 0){
            noDataFountTv.setVisibility(View.VISIBLE);
        }

            UpdatedRecordsAdapters adapter = new UpdatedRecordsAdapters(requireContext(), upadatedRecords, lastetRecords -> {
                Fragment fragment = new AllSavedRecordsViewFragment(lastetRecords.getUserModels());
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



    }

    @Override
    public void onClick(LatestRecords lastetRecords) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.export) {
            if (upadatedRecords != null && !upadatedRecords.isEmpty()) {
                createExcelSheet();
            } else {
                Toast.makeText(requireContext(), " There is No records to Export", Toast.LENGTH_LONG).show();
            }
        } else if (view.getId() == R.id.delete) {
            deleteAllRecords();
        }
    }


    private void createExcelSheet() {

        userModelList = new ArrayList<>();
        userModelList = DatabaseClass.getDatabase(requireContext()).getDao().getAllData(key);

        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> countList = new ArrayList<>();
        arrayList.add(0, "Time");
        for (int j = 0; j < userModelList.size(); j++) {
            countList.add(0, String.valueOf(userModelList.get(j).getCount()));
            arrayList.add(userModelList.get(j).getVehicleName());
        }

        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File filePath = new File(folder, fileName + ".xls");

        ActivityCompat.requestPermissions((Activity) requireContext(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE}, PackageManager.PERMISSION_GRANTED);

        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        HSSFSheet hssfSheet = hssfWorkbook.createSheet("Custom Sheet");
        hssfSheet.setHorizontallyCenter(true);

        for (int i = 0; i < upadatedRecords.size(); i++) {
            HSSFRow hssfRow = hssfSheet.createRow(i + 1);
            for (int j = 0; j < upadatedRecords.get(i).getUserModels().size(); j++) {
                HSSFCell hssfCell1 = hssfRow.createCell(0);
                hssfCell1.setCellValue(upadatedRecords.get(i).getSavingTime());
                HSSFCell hssfCell = hssfRow.createCell(j + 1);
                hssfCell.setCellValue(String.valueOf(upadatedRecords.get(i).getUserModels().get(j).getCount()));

            }


        }
        HSSFRow hssfRow = hssfSheet.createRow(0);
        for (int i = 0; i < arrayList.size(); i++) {
            HSSFCell hssfCell = hssfRow.createCell(i);
            hssfCell.setCellValue(arrayList.get(i));
        }

        try {
            int increase = 0;
            while (filePath.exists()) {

                increase++;
                filePath = new File(folder + "/" + fileName + "(" + increase + ")" + ".xls");
            }
            filePath.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
            hssfWorkbook.write(fileOutputStream);
            Toast.makeText(requireContext(), "Saved in Downloads", Toast.LENGTH_SHORT).show();
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (filePath.exists()) {
          /*  Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
            startActivity(intent);*/
            Intent intentShare = new Intent(Intent.ACTION_SEND);
            intentShare.setType("application/vnd.ms-excel");
            intentShare.putExtra(Intent.EXTRA_STREAM, Uri.parse("files://" + filePath));
            startActivity(Intent.createChooser(intentShare, "Share this File"));

        }
       /* Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        startActivity(intent);*/
    }

    public void deleteAllRecords() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setMessage("Are you sure to delete all records ?");
        builder.setPositiveButton("Yes", (dialogInterface, i) -> {
            DatabaseClass.getDatabase(requireContext()).getDao().deleteData();
            getData();
            noDataFountTv.setVisibility(View.VISIBLE);
        });
        builder.setNegativeButton("No", (dialogInterface, i) -> {
        });
        builder.create();
        builder.show();

    }
}
