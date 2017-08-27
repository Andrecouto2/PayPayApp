package br.com.andrecouto.paypay.view.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;

import com.squareup.picasso.Transformation;

import br.com.andrecouto.paypay.util.DisplayUtils;

public class CircleTransformation implements Transformation {

    private int borderColor = -1;
    private int borderWidth = -1;
    private int fillColor = -1;

    public CircleTransformation() {}

    public CircleTransformation(Context context, @ColorRes int borderColorResource, int borderWidthInDp) {
        this.borderColor = ContextCompat.getColor(context, borderColorResource);
        this.borderWidth = DisplayUtils.dpToPx(context, borderWidthInDp);
    }

    public CircleTransformation(Context context, @ColorRes int borderColorResource, int borderWidthInDp, @ColorRes int fillColorResource) {
        this.borderColor = ContextCompat.getColor(context, borderColorResource);
        this.borderWidth = DisplayUtils.dpToPx(context, borderWidthInDp);
        this.fillColor = ContextCompat.getColor(context, fillColorResource);
    }

    @Override
    public Bitmap transform(Bitmap source) {
        final boolean shouldDrawBorder = borderWidth != -1;
        final boolean shouldDrawFillColor = fillColor != -1;

        float width;
        float height;
        float radius;

        Paint bitmapPaint = new Paint();
        Bitmap bitmap;
        BitmapShader shader = new BitmapShader(source, BitmapShader.TileMode.CLAMP,
                BitmapShader.TileMode.CLAMP);

        final int size = Math.min(source.getWidth(), source.getHeight());
        width = height = size / 2.0f;

        if (!shouldDrawBorder) {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            radius = size / 2.0f;
        } else {
            bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            radius = Math.min((source.getHeight() - borderWidth) / 2.0f, (source.getWidth() - borderWidth) / 2.0f);
        }

        Canvas canvas = new Canvas(bitmap);

        if (shouldDrawFillColor) {
            Paint fillPaint = new Paint();
            fillPaint.setStyle(Paint.Style.FILL);
            fillPaint.setAntiAlias(true);
            fillPaint.setColor(fillColor);

            canvas.drawCircle(width, height, radius, fillPaint);
        }

        bitmapPaint.setShader(shader);
        bitmapPaint.setAntiAlias(true);

        canvas.drawCircle(width, height, radius, bitmapPaint);

        if (source != bitmap) {
            source.recycle();
        }

        if (shouldDrawBorder) {
            Paint borderPaint = new Paint();
            borderPaint.setStyle(Paint.Style.STROKE);
            borderPaint.setStrokeWidth(borderWidth);
            borderPaint.setColor(borderColor);
            borderPaint.setAntiAlias(true);

            canvas.drawCircle(width, height, radius, borderPaint);
        }

        return bitmap;
    }

    @Override
    public String key() {
        return "CircleTransformation()";
    }
}