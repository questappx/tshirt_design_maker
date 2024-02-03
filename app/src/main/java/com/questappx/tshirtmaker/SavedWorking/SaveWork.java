package com.questappx.tshirtmaker.SavedWorking;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.questappx.tshirtmaker.Extras.Utility;
import com.questappx.tshirtmaker.R;

import java.io.File;
import java.util.ArrayList;

public class SaveWork extends AppCompatActivity{

    public static ArrayList<File> files;
    public static int pos;
    RecyclerView recyclerView;
    private final String TAG = SaveWork.class.getSimpleName();
    SaveAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_work);




        PermissionWorking();

        recyclerView = (RecyclerView) findViewById(R.id.recylerView);
        GridLayoutManager manager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(manager);

        File file = new File(getFilesDir(), getResources().getString(R.string.fileName));

        if (!file.exists()) {
            file.mkdir();
        }


        if (file.exists()) {
            files = new ArrayList<>();
            for (File file1 : file.listFiles()) {
                files.add(file1);
            }
            if (files != null) {
                adapter = new SaveAdapter(files, this);
                recyclerView.setAdapter(adapter);
            }
        }
    }



    private void PermissionWorking() {
        Utility utility = new Utility();
        if (ContextCompat.checkSelfPermission(SaveWork.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {

            if (Utility.checkPermissionContects(SaveWork.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                if (ContextCompat.checkSelfPermission(SaveWork.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {


                    if (Utility.checkPermissionContects(SaveWork.this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

                    }
                }
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(adapter!=null){
            adapter.notifyDataSetChanged();
        }
    }
}
