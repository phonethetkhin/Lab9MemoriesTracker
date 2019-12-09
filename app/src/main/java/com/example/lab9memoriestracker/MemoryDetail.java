package com.example.lab9memoriestracker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.lab9memoriestracker.DB.MemoryDB;
import com.example.lab9memoriestracker.Model.MemoryModel;

public class MemoryDetail extends AppCompatActivity {
private MemoryModel memoryModel;
private ImageView imgPhoto;
private EditText etTitle,etDate,etDescription,etLocation;
private Button btnShowonMap,btnSave,btnDelete;
private ImageButton imgbtnEdit;
    Bitmap photo=null;
    private static final int CAMERA_REQUEST = 1888;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
private MemoryDB memoryDB=new MemoryDB(MemoryDetail.this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memory_detail);
        imgPhoto=findViewById(R.id.imgPhoto);
        etTitle=findViewById(R.id.etTitle);
        etDate=findViewById(R.id.etDate);
        etDescription=findViewById(R.id.etDescription);
        etLocation=findViewById(R.id.etLocation);
        btnShowonMap=findViewById(R.id.btnShowonMap);
        btnSave=findViewById(R.id.btnSave);
        btnDelete=findViewById(R.id.btnDelete);
        imgbtnEdit=findViewById(R.id.imgbtnEdit);

        memoryModel=getIntent().getParcelableExtra("OldValue");
        getSupportActionBar().setTitle(memoryModel.getTitle());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        etTitle.setText(memoryModel.getTitle());
        etDescription.setText(memoryModel.getDescription());
        etDate.setText(memoryModel.getMemoryDate());
        etLocation.setText(memoryModel.getLocation());
        byte[] bytes=memoryModel.getImage();
         photo = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        imgPhoto.setImageBitmap(photo);


        imgbtnEdit.setOnClickListener(new View.OnClickListener() {
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
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etTitle.getText().toString().equals(memoryModel.getTitle()) &&
                etDescription.getText().toString().equals(memoryModel.getDescription()) &&
                etDate.getText().toString().equals(memoryModel.getMemoryDate()) &&
                etLocation.getText().toString().equals(memoryModel.getLocation()) &&
                photo.sameAs(BitmapFactory.decodeByteArray(memoryModel.getImage(),0,memoryModel.getImage().length))
                )
                {
                    Toast.makeText(MemoryDetail.this, "No Changes Detected !!", Toast.LENGTH_SHORT).show();
                }
                else {
                    memoryDB.UpdateBook(memoryModel.getMemoryID(),
                            etTitle.getText().toString(),
                            etDescription.getText().toString(),
                            etDate.getText().toString(),
                            etLocation.getText().toString(),
                            photo);
                    Toast.makeText(MemoryDetail.this, "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        btnShowonMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchkeyword=etLocation.getText().toString();
                String map = "http://maps.google.co.in/maps?q=" + searchkeyword;
                Intent i = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse(map));
                startActivity(i);
            }
        });
btnDelete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder alertdialogbuilder=new AlertDialog.Builder(MemoryDetail.this);
        alertdialogbuilder.setMessage("Are You Sure You Want to Delete This Memory!");
        alertdialogbuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                memoryDB.RemoveMemory(memoryModel.getMemoryID());
                Toast.makeText(MemoryDetail.this, "Deleted Successfully!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
        alertdialogbuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog=alertdialogbuilder.create();
        dialog.show();

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
            imgPhoto.setImageBitmap(photo);
        }
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
