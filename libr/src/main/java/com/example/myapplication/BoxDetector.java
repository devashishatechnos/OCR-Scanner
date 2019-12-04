package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.util.Log;
import android.util.SparseArray;


import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.Frame;

import java.io.ByteArrayOutputStream;

public class BoxDetector extends Detector {
    private Detector mDelegate;
    private int mBoxWidth, mBoxHeight;
    //private int mWidth, mHeight;

    public BoxDetector(Detector delegate, int boxWidth, int boxHeight, int width, int height) {
        mDelegate = delegate;
        mBoxWidth = boxWidth;
        mBoxHeight = boxHeight;
        /*mWidth=width;
        mHeight=height;*/
    }


    public SparseArray detect(Frame frame) {
        int width = frame.getMetadata().getWidth();
        int height = frame.getMetadata().getHeight();

        int right = (width / 2) + (mBoxHeight / 2);
        int left = (width / 2) - (mBoxHeight / 2);
        int bottom = (height / 2) + (mBoxWidth / 2);
        int top = (height / 2) - (mBoxWidth / 2);

        Log.e("Test width", "" + width);
        Log.e("Test height", "" + height);
        Log.e("Test right", "" + right);
        Log.e("Test left", "" + left);
        Log.e("Test bottom", "" + bottom);
        Log.e("Test top", "" + top);

        YuvImage yuvImage = new YuvImage(frame.getGrayscaleImageData().array(), ImageFormat.NV21, width, height, null);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
      /*  if (width == height) {
            Log.e("Test Equal", "" + (top - width / 4));

            yuvImage.compressToJpeg(new Rect(left, top - width / 6, right, bottom + width / 6),
                    100, byteArrayOutputStream);
        } else {*/
            yuvImage.compressToJpeg(new Rect(left, top, right, bottom),
                    100, byteArrayOutputStream);
       // }


        byte[] jpegArray = byteArrayOutputStream.toByteArray();

        Bitmap bitmap = BitmapFactory.decodeByteArray(jpegArray, 0, jpegArray.length);

        Frame croppedFrame =
                new Frame.Builder()
                        .setBitmap(bitmap)
                        .setRotation(frame.getMetadata().getRotation())
                        .build();

        return mDelegate.detect(croppedFrame);
    }

    public boolean isOperational() {
        return mDelegate.isOperational();
    }

    public boolean setFocus(int id) {
        return mDelegate.setFocus(id);
    }


}