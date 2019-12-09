package com.example.lab9memoriestracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.lab9memoriestracker.Adapters.MemoryAdapter;
import com.example.lab9memoriestracker.DB.MemoryDB;
import com.example.lab9memoriestracker.Model.MemoryModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView rvMain;
    FloatingActionButton fabAdd;
    List<MemoryModel> memoryModelList;
    MemoryDB memoryDB=new MemoryDB(MainActivity.this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvMain=findViewById(R.id.rvMain);
        fabAdd=findViewById(R.id.fabAdd);
getSupportActionBar().setTitle("Home Page");

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,AddMemory.class);
                startActivity(i);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        memoryModelList=memoryDB.GetMemories();
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(MainActivity.this,RecyclerView.VERTICAL,true);
        linearLayoutManager.setStackFromEnd(true);
        rvMain.setLayoutManager(linearLayoutManager);

        rvMain.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL);
        rvMain.addItemDecoration(dividerItemDecoration);
        rvMain.setAdapter(new MemoryAdapter(memoryModelList));
    }
}
