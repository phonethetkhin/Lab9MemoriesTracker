package com.example.lab9memoriestracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lab9memoriestracker.DB.MemoryDB;
import com.google.android.material.textfield.TextInputEditText;

public class AddMemory extends AppCompatActivity {
    TextInputEditText tietTitle,tietDescription,tietLocation,tietMemoryDate;
    ImageButton imgbCamera;
    ImageView imgSelectedPhoto;
    Button btnAddMemory;
    Bitmap photo=null;
    MemoryDB memoryDB=new MemoryDB(AddMemory.this);
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memory);
        tietTitle=findViewById(R.id.tietTitle);
        tietDescription=findViewById(R.id.tietDescription);
        tietLocation=findViewById(R.id.tietLocation);
        tietMemoryDate=findViewById(R.id.tietDate);
        imgbCamera=findViewById(R.id.imgbCamera);
        imgSelectedPhoto=findViewById(R.id.imgSelectedPhoto);
        btnAddMemory=findViewById(R.id.btnAdd);

        getSupportActionBar().setTitle("Add Memory");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgbCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
                    {
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_CAMERA_PERMISSION_CODE);
                    }
                    else
                    {
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent, CAMERA_REQUEST);
                    }
                }
            }
        });
        btnAddMemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tietTitle.getText().toString().isEmpty() || tietDescription.getText().toString().isEmpty() || tietLocation.getText().toString().isEmpty() || tietMemoryDate.getText().toString().isEmpty())
                {
                    Toast.makeText(AddMemory.this, "Fil All Information !", Toast.LENGTH_SHORT).show();
                }
                else if(photo==null)
                {
                    Toast.makeText(AddMemory.this, "Choose Image", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    memoryDB.AddMemory(tietTitle.getText().toString(), tietDescription.getText().toString(), tietLocation.getText().toString(), tietMemoryDate.getText().toString(), photo);
                    Toast.makeText(AddMemory.this, "Memory Added Successful !", Toast.LENGTH_SHORT).show();
                    Clear();

                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
            else
            {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = (Bitmap) data.getExtras().get("data");
            imgSelectedPhoto.setImageBitmap(photo);
        }
    }
private void Clear()
{
    tietTitle.setText("");
    tietDescription.setText("");
    tietLocation.setText("");
    tietMemoryDate.setText("");
    this.finish();
}

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
