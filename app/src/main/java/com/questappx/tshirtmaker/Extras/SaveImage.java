package com.questappx.tshirtmaker.Extras;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;


import com.questappx.tshirtmaker.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SaveImage {
    Context context;
    ViewGroup layout;

    public SaveImage(Context context, ViewGroup layout) {
        this.context = context;
        this.layout = layout;
    }

    public Bitmap saveImageAsPng()
    {
//        pngManagementLayout.setVisibility(GONE);
//        new SaveAsPNG(EditorActivity.stickerPramsEditableLayout,context).execute();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Calendar.getInstance().getTime());


        File fileDir = new File(context.getFilesDir(), context.getResources().getString(R.string.fileName));

        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        Bitmap imageToSave = layout.getDrawingCache();

        File fileSaved = saveBitmapFile(fileDir, imageToSave, timeStamp, true, true);

        try {
            Uri imageuri = saveImage(context, imageToSave, context.getResources().getString(R.string.fileName), System.currentTimeMillis() + "logo.png");
            if(imageuri != null)
            {
                Toast.makeText(context, "Image saved!", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageToSave;



//        Intent intent = new Intent(context, ShareActivity.class);
//        intent.putExtra("fileuri", Uri.fromFile(fileSaved).toString());
//        context.startActivity(intent);
//        ((Activity)context).finish();

    }

    public Bitmap saveAsJPGwORK() {
//        new SaveAsJPG(EditorActivity.stickerPramsEditableLayout,context).execute();


        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(Calendar.getInstance().getTime());


        File fileDir = new File(context.getFilesDir(), context.getResources().getString(R.string.fileName));

        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        layout.setDrawingCacheEnabled(true);
        layout.buildDrawingCache();
        Bitmap imageToSave = layout.getDrawingCache();

        File fileSaved = saveBitmapFile(fileDir, imageToSave, timeStamp, true, false);

        try {
            Uri imageuri = saveImage(context, imageToSave, context.getResources().getString(R.string.fileName), System.currentTimeMillis() + "logo.jpg");
            if(imageuri != null)
            {
                Toast.makeText(context, "Image Saved!", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

//        Intent intent = new Intent(context, .class);
//        intent.putExtra("fileuri", Uri.fromFile(fileSaved).toString());
//        context.startActivity(intent);
//        ((Activity)context).finish();
        return imageToSave;
    }

    private File saveBitmapFile(File logosStorageDir, Bitmap bitmap, String name, boolean keepSize, boolean ispng) {


        if (!logosStorageDir.exists()) {
            logosStorageDir.mkdirs();
        }


//        if (keepSize) {
//            bitmap = Bitmap.createScaledBitmap(bitmap, 512, 512, false);
//        }

        String mCurrentPath;

        if (ispng) {
            mCurrentPath = logosStorageDir.getPath() + File.separator
                    + "IMG_" + name + ".png";

        } else {
            mCurrentPath = logosStorageDir.getPath() + File.separator
                    + "IMG_" + name + ".jpg";

        }

        File mediaFile = new File(mCurrentPath);

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(mediaFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, getQualityNumber(bitmap), fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mediaFile;
    }

    public static Uri saveImage(Context context, Bitmap bitmap, @NonNull String folderName, @NonNull String fileName) throws IOException {
        OutputStream fos = null;
        File imageFile = null;
        Uri imageUri = null;

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentResolver resolver = context.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                contentValues.put(
                        MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + folderName);
                imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

                if (imageUri == null)
                    throw new IOException("Failed to create new MediaStore record.");

                fos = resolver.openOutputStream(imageUri);
            } else {
                File imagesDir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES).toString() + File.separator + folderName);

                if (!imagesDir.exists())
                    imagesDir.mkdir();

                imageFile = new File(imagesDir, fileName + ".png");
                fos = new FileOutputStream(imageFile);
            }


            if (!bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos))
                throw new IOException("Failed to save bitmap.");
            fos.flush();
        } finally {
            if (fos != null)
                fos.close();
        }

        if (imageFile != null) {//pre Q
            MediaScannerConnection.scanFile(context, new String[]{imageFile.toString()}, null, null);
            imageUri = Uri.fromFile(imageFile);
        }
        return imageUri;
    }

    public static int getQualityNumber(Bitmap bitmap) {
        int size = bitmap.getByteCount();
        int percentage = 0;

        if (size > 500000 && size <= 800000) {
            percentage = 15;
        } else if (size > 800000 && size <= 1000000) {
            percentage = 20;
        } else if (size > 1000000 && size <= 1500000) {
            percentage = 25;
        } else if (size > 1500000 && size <= 2500000) {
            percentage = 27;
        } else if (size > 2500000 && size <= 3500000) {
            percentage = 30;
        } else if (size > 3500000 && size <= 4000000) {
            percentage = 40;
        } else if (size > 4000000 && size <= 5000000) {
            percentage = 50;
        } else if (size > 5000000) {
            percentage = 75;
        }

        return percentage;
    }

}
