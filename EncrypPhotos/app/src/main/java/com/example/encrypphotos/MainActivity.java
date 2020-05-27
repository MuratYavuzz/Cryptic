package com.example.encrypphotos;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;


public class MainActivity extends AppCompatActivity {
    GridView gridView;
    private File [] fileImages;
    private String[] filesPath;
    private String[] filesName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        File dirImage = Environment.getExternalStoragePublicDirectory("/Cryptic");
        if(dirImage.isDirectory()){
            fileImages = dirImage.listFiles();
            filesPath = new String[fileImages.length];
            filesName = new String[fileImages.length];

            for (int i = 0; i < fileImages.length; i++) {
                filesPath[i] = fileImages[i].getAbsolutePath();
                filesName[i] = fileImages[i].getName();
            }
        }
        gridView = (GridView) findViewById(R.id.gridview);
        ImageAdapter adapter = new ImageAdapter(this,fileImages,filesName,filesPath);
        if(adapter.fileImages!=null)
            gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FullScreenActivity.class);
                intent.putExtra("id",position);
                intent.putExtra("fileImages",fileImages[position]);
                startActivity(intent);
            }
        });
    }

    public void pick_image(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode ==RESULT_OK && data != null)
        {
            Uri uri = data.getData();
            String path = getRealPathFromUri(this,uri);
            String name = getFileName(uri);


            try {
                InsertInPrivateStorage(name,path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    private void InsertInPrivateStorage(String name, String path) throws IOException {

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        File file = new File(Environment.getExternalStorageState()+"/Cryptic");
        if(!file.exists())
        {
            File Directory = new File("/sdcard/Cryptic/");
            Directory.mkdir();
        }
        File file1 = new File("/sdcard/Cryptic/",name);
        if(file1.exists())
        {
            file1.delete();
        }try
        {
            FileOutputStream out = new FileOutputStream(file1);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            Intent intent = new Intent(MainActivity.this, MainActivity.class);
            startActivity(intent);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private String getFileName(Uri uri) {
        String result = null;
        if(uri.getScheme().equals("content")){
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            try{
                if(cursor != null && cursor.moveToFirst()){
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }

            } finally {
                cursor.close();
            }
        }
        if(result == null){
            result = uri.getPath();
            int cut = result.lastIndexOf('/');
            if(cut != -1){
                result = result.substring(cut +1);
            }
        }
        return result;
    }

    private String getRealPathFromUri(Context context, Uri uri) {

        String [] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri,proj,null,null,null);
        if(cursor != null)
        {
            int coloumn_Index= cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(coloumn_Index);
        }
        return null;
    }
}
