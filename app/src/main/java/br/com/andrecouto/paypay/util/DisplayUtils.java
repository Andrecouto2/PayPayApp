package br.com.andrecouto.paypay.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import static android.os.Build.VERSION.SDK_INT;
import static android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1;

public class DisplayUtils {

    private DisplayUtils() {
        throw new AssertionError();
    }

    @SuppressLint("NewApi")
    private static Point getDisplayPixelSize(final Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        if (SDK_INT >= ICE_CREAM_SANDWICH_MR1) {
            Point size = new Point();
            display.getSize(size);
            return size;
        } else {
            return new Point(display.getWidth(), display.getHeight());
        }
    }

    public static int getDisplayPixelWidth(final Context context) {
        Point size = getDisplayPixelSize(context);
        return size.x;
    }

    public static int getDisplayPixelHeight(final Context context) {
        Point size = getDisplayPixelSize(context);
        return size.y;
    }

    public static int dpToPx(final Context context, final int dp) {
        float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                context.getResources().getDisplayMetrics());
        return (int) px;
    }

    public static float dipToPixels(final Context context, final float dipValue) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dipValue, metrics);
    }

    @SuppressLint("NewApi")
    public static boolean isEqualOrMoreThanXxxHdpi(final Context context) {
        return context.getResources().getDisplayMetrics().densityDpi
                >= DisplayMetrics.DENSITY_XXXHIGH;
    }

    public static boolean isMdpi(final Context context) {
        return context.getResources().getDisplayMetrics().densityDpi ==
                DisplayMetrics.DENSITY_MEDIUM;
    }

    public static boolean isHdpi(final Context context) {
        return context.getResources().getDisplayMetrics().densityDpi
                == DisplayMetrics.DENSITY_HIGH;
    }

    public static String getDensity(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        switch (metrics.densityDpi) {
            case DisplayMetrics.DENSITY_LOW:
                return "ldpi/";
            case DisplayMetrics.DENSITY_MEDIUM:
                return "mdpi/";
            case DisplayMetrics.DENSITY_HIGH:
                return "hdpi/";
            case DisplayMetrics.DENSITY_XHIGH:
                return "xhdpi/";
            case DisplayMetrics.DENSITY_XXHIGH:
                return "xxhdpi/";
            default:
                return "xxxhdpi/";
        }
    }
}
