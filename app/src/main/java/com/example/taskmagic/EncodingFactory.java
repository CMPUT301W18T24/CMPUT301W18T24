package com.example.taskmagic;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;

/**
 * Created by Harrold on 2018-03-15.
 */

public class EncodingFactory {

    public byte[] takeBitmap(Bitmap bitmap) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 25, outputStream);
        byte[] byteArray = outputStream .toByteArray();

        return byteArray;
    }
}
