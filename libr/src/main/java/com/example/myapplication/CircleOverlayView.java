package com.example.myapplication;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class CircleOverlayView extends LinearLayout {
    private Bitmap bitmap;

    public CircleOverlayView(Context context) {
        super(context);
    }

    public CircleOverlayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleOverlayView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleOverlayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);

        if (bitmap == null) {
            createWindowFrame();
        }
        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    protected void createWindowFrame() {
        bitmap = Bitmap.createBitmap(getMeasuredWidth(), getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas osCanvas = new Canvas(bitmap);

        Rect outerRectangle = new Rect(0, 0, getWidth(), getHeight());
        final int color = 0xff424242;
        Paint paint = new Paint();
       // paint.setColor(getResources().getColor(R.color.overlay_color));
        paint.setColor(getResources().getColor(R.color.overlay_color));

        paint.setAlpha(80);
        osCanvas.drawRect(outerRectangle, paint);

        osCanvas.drawPaint(paint);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setTextSize(40);
        osCanvas.drawText("", 100, getHeight() / 2 - 100, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(6);
        osCanvas.drawRect(outerRectangle, paint);

        paint.setColor(Color.WHITE);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));

        RectF outerRectangle2 = new RectF(24,
                getHeight() / 2 - 80,
                getWidth() - 24,
                getHeight() / 2 + 80);


        osCanvas.drawRoundRect(outerRectangle2, 30, 30, paint);
    }

    @Override
    public boolean isInEditMode() {
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        bitmap = null;
    }
}