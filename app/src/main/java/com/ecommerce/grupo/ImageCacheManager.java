package com.ecommerce.grupo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.ecommerce.grupo.Model.CategoryModel;
import com.ecommerce.grupo.Model.ProductModel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Format;

public class ImageCacheManager {
    public static Bitmap getBitmap(Context context, CategoryModel dataItem) {
        String fileName = context.getCacheDir() + "/" + dataItem.getImageUrl();
        File file = new File(fileName);
        if (file.exists()) {
            try {
                return BitmapFactory.decodeStream(new FileInputStream(file));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void putBitmap(Context context, CategoryModel dataItem, Bitmap bitmap) {
        String fileName = context.getCacheDir() + "/" + dataItem.getImageUrl();
        File file = new File(fileName);
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
