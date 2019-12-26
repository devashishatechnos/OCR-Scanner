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

       /* int width = 1280;
        int height = 960;*/
        Log.e("Test width", "" + width);
        Log.e("Test height", "" + height);


        YuvImage yuvImage;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //  if (width > height) {
        try {
            int right = (width / 2) + (mBoxHeight / 2);
            int left = (width / 2) - (mBoxHeight / 2);
            int bottom = (height / 2) + (mBoxWidth / 2);
            int top = (height / 2) - (mBoxWidth / 2);
            Log.e("Test right", "" + right);
            Log.e("Test left", "" + left);
            Log.e("Test bottom", "" + bottom);
            Log.e("Test top", "" + top);
            yuvImage = new YuvImage(frame.getGrayscaleImageData().array(), ImageFormat.NV21, width, height, null);
            byteArrayOutputStream = new ByteArrayOutputStream();

            yuvImage.compressToJpeg(new Rect(left, top, right, bottom),
                    100, byteArrayOutputStream);
        } catch (IllegalArgumentException e) {

        }
       /* } else {
            try {
                int right = (width / 2) + (mBoxHeight / 2);
                int left = (width / 2) - (mBoxHeight / 2);
                int bottom = (height / 2) + (mBoxWidth / 2);
                int top = (height / 2) - (mBoxWidth / 2);
                Log.e("Test right2", "" + right);
                Log.e("Test left2", "" + left);
                Log.e("Test bottom2", "" + bottom);
                Log.e("Test top2", "" + top);
                yuvImage = new YuvImage(frame.getGrayscaleImageData().array(), ImageFormat.NV21, width, height, null);

                yuvImage.compressToJpeg(new Rect(left, top, right, bottom),
                        100, byteArrayOutputStream);
            } catch (IllegalArgumentException e) {

            }
        }*/
      /*  if (width == height) {
            Log.e("Test Equal", "" + (top - width / 4));

            yuvImage.compressToJpeg(new Rect(left, top - width / 6, right, bottom + width / 6),
                    100, byteArrayOutputStream);
        } else {*/


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