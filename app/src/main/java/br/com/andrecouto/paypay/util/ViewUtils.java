package br.com.andrecouto.paypay.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.andrecouto.paypay.R;


public final class ViewUtils {

    private static int screenWidth;

    private ViewUtils() {
    }

    public static boolean isVisible(View view) {
        return view.getVisibility() == View.VISIBLE;
    }

    public static boolean isExpanded(View view) {
        return view.getHeight() != 0;
    }

    public static void hideSoftKeyboard(final Activity activity) {
        if (activity.getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager
                    .hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        }
    }

    public static void showSoftKeyboard(final Context context, final View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(view.getApplicationWindowToken(),
                InputMethodManager.SHOW_FORCED, 0);
    }

    public static boolean isSoftKeyboardOpenned(final Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        return inputMethodManager.isAcceptingText();
    }

    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }

    static TransitionDrawable proccessBlur(Context context, Drawable bg) {
        Bitmap bitmap = fastblur(((BitmapDrawable) bg).getBitmap(), 20);
        Drawable drawableBlurred = new BitmapDrawable(context.getResources(), bitmap);

        return new TransitionDrawable(new Drawable[]{
                bg,
                drawableBlurred
        });
    }

    private static Bitmap fastblur(Bitmap sentBitmap, int radius) {

        // Stack Blur v1.0 from
        // http://www.quasimondo.com/StackBlurForCanvas/StackBlurDemo.html
        //
        // Java Author: Mario Klingemann <mario at quasimondo.com>
        // http://incubator.quasimondo.com
        // created Feburary 29, 2004
        // Android port : Yahel Bouaziz <yahel at kayenko.com>
        // http://www.kayenko.com
        // ported april 5th, 2012

        // This is a compromise between Gaussian Blur and Box blur
        // It creates much better looking blurs than Box Blur, but is
        // 7x faster than my Gaussian Blur implementation.
        //
        // I called it Stack Blur because this describes best how this
        // filterRelease works internally: it creates a kind of moving stack
        // of colors whilst scanning through the image. Thereby it
        // just has to add one new block of color to the right side
        // of the stack and remove the leftmost color. The remaining
        // colors on the topmost layer of the stack are either added on
        // or reduced by one, depending on if they are on the right or
        // on the left side of the stack.
        //
        // If you are using this algorithm in your code please add
        // the following line:
        //
        // Stack Blur Algorithm by Mario Klingemann <mario@quasimondo.com>

        Bitmap bitmap = sentBitmap.copy(sentBitmap.getConfig(), true);

        if (radius < 1) {
            return null;
        }

        int w = bitmap.getWidth();
        int h = bitmap.getHeight();

        int[] pix = new int[w * h];
        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.getPixels(pix, 0, w, 0, 0, w, h);

        int wm = w - 1;
        int hm = h - 1;
        int wh = w * h;
        int div = radius + radius + 1;

        int[] r = new int[wh];
        int[] g = new int[wh];
        int[] b = new int[wh];
        int rsum;
        int gsum;
        int bsum;
        int x;
        int y;
        int i;
        int p;
        int yp;
        int yi;
        int yw;
        int[] vmin = new int[Math.max(w, h)];

        int divsum = (div + 1) >> 1;
        divsum *= divsum;
        int[] dv = new int[256 * divsum];
        for (i = 0; i < 256 * divsum; i++) {
            dv[i] = i / divsum;
        }

        yw = yi = 0;

        int[][] stack = new int[div][3];
        int stackpointer;
        int stackstart;
        int[] sir;
        int rbs;
        int r1 = radius + 1;
        int routsum;
        int goutsum;
        int boutsum;
        int rinsum;
        int ginsum;
        int binsum;

        for (y = 0; y < h; y++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            for (i = -radius; i <= radius; i++) {
                p = pix[yi + Math.min(wm, Math.max(i, 0))];
                sir = stack[i + radius];
                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = p & 0x0000ff;
                rbs = r1 - Math.abs(i);
                rsum += sir[0] * rbs;
                gsum += sir[1] * rbs;
                bsum += sir[2] * rbs;
                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }
            }
            stackpointer = radius;

            for (x = 0; x < w; x++) {

                r[yi] = dv[rsum];
                g[yi] = dv[gsum];
                b[yi] = dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (y == 0) {
                    vmin[x] = Math.min(x + radius + 1, wm);
                }
                p = pix[yw + vmin[x]];

                sir[0] = (p & 0xff0000) >> 16;
                sir[1] = (p & 0x00ff00) >> 8;
                sir[2] = p & 0x0000ff;

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer % div];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi++;
            }
            yw += w;
        }
        for (x = 0; x < w; x++) {
            rinsum = ginsum = binsum = routsum = goutsum = boutsum = rsum = gsum = bsum = 0;
            yp = -radius * w;
            for (i = -radius; i <= radius; i++) {
                yi = Math.max(0, yp) + x;

                sir = stack[i + radius];

                sir[0] = r[yi];
                sir[1] = g[yi];
                sir[2] = b[yi];

                rbs = r1 - Math.abs(i);

                rsum += r[yi] * rbs;
                gsum += g[yi] * rbs;
                bsum += b[yi] * rbs;

                if (i > 0) {
                    rinsum += sir[0];
                    ginsum += sir[1];
                    binsum += sir[2];
                } else {
                    routsum += sir[0];
                    goutsum += sir[1];
                    boutsum += sir[2];
                }

                if (i < hm) {
                    yp += w;
                }
            }
            yi = x;
            stackpointer = radius;
            for (y = 0; y < h; y++) {
                // Preserve a channel: ( 0xff000000 & pix[yi] )
                pix[yi] = (0xff000000 & pix[yi]) | (dv[rsum] << 16) | (dv[gsum] << 8) | dv[bsum];

                rsum -= routsum;
                gsum -= goutsum;
                bsum -= boutsum;

                stackstart = stackpointer - radius + div;
                sir = stack[stackstart % div];

                routsum -= sir[0];
                goutsum -= sir[1];
                boutsum -= sir[2];

                if (x == 0) {
                    vmin[y] = Math.min(y + r1, hm) * w;
                }
                p = x + vmin[y];

                sir[0] = r[p];
                sir[1] = g[p];
                sir[2] = b[p];

                rinsum += sir[0];
                ginsum += sir[1];
                binsum += sir[2];

                rsum += rinsum;
                gsum += ginsum;
                bsum += binsum;

                stackpointer = (stackpointer + 1) % div;
                sir = stack[stackpointer];

                routsum += sir[0];
                goutsum += sir[1];
                boutsum += sir[2];

                rinsum -= sir[0];
                ginsum -= sir[1];
                binsum -= sir[2];

                yi += w;
            }
        }

        Log.e("pix", w + " " + h + " " + pix.length);
        bitmap.setPixels(pix, 0, w, 0, 0, w, h);

        return bitmap;
    }

    public static TransformationMethod getPasswordTransformationMethod() {
        return new CustomPasswordTransformationMethod();
    }

    public static Drawable getDrawableFromAttr(int attrCode, final Activity activity) {
        try {
            int[] attrs = new int[]{attrCode};

            TypedArray ta = activity.obtainStyledAttributes(attrs);

            final Drawable drawableToReturn = ta.getDrawable(0);

            ta.recycle();
            return drawableToReturn;
        } catch (NullPointerException e) {
            Log.e("", e.toString());
            return null;
        }
    }

    @SuppressLint("NewApi")
    public static void setStatuBarColor(final Activity activity, final int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(color);
        }
    }

    @SuppressLint("NewApi")
    public static void setStatuBarColorWithAnimation(final Activity activity, final int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            View view = window.findViewById(android.R.id.statusBarBackground);
            int actualStatusBarColor = window.getStatusBarColor();
            //AnimationUtils.animateBackgroundColorChange(view, actualStatusBarColor, color, 500);
        }
    }

    /*public static void setStep(final Activity activity, final Step step) {
        if (activity != null) {
            final ProgressStepView stepView = (ProgressStepView) activity.findViewById(R.id
                    .view_progress_steps);
            if (stepView != null) {
                stepView.setStep(step.getStep());
            }
        }
    }*/

    /*public static void setStep(ProgressStepView stepView, final Step step) {
        if (stepView != null) {
            stepView.setStep(step.getStep());
        }
    }*/

    /*public static void backStep(final Activity activity, final Step step) {
        final ProgressStepView stepView = (ProgressStepView) activity.findViewById(R.id
                .view_progress_steps);

        if (stepView != null && step.getStep() - 1 > 0) {
            stepView.setStep(step.getStep() - 1);
        }
    }*/

    /*public static void showSteps(final Activity activity, boolean isShow) {
        final ProgressStepView stepView =
                (ProgressStepView) activity.findViewById(R.id.view_progress_steps);

        if (stepView != null) {
            stepView.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
        }
    }*/

    public static int getResourceFromAttr(final Context context, final int attr) {
        if (context == null) {
            throw new IllegalArgumentException();
        }
        final TypedValue typedvalueattr = new TypedValue();
        context.getTheme().resolveAttribute(attr, typedvalueattr, true);
        return typedvalueattr.resourceId;
    }

    public static int getColorFromAttr(int attrCode, final Activity activity) {
        try {
            int[] attrs = new int[]{attrCode};

            TypedArray ta = activity.obtainStyledAttributes(attrs);

            final int color = ta.getColor(0, 0);

            ta.recycle();
            return color;
        } catch (NullPointerException e) {
            Log.e("", e.toString());
            return 0;
        }
    }

    public static int getColorFromAttr(int attrCode, final Context context) {
        return getColorFromAttr(attrCode, (Activity) context);
    }

    /*public static void showTooltip(final Context context, final View anchorView, final
    TooltipManager.Gravity gravity, final String title, final String description) {
        TooltipManager.getInstance()
                .create(context, 0)
                .anchor(anchorView, gravity)
                .closePolicy(TooltipManager.ClosePolicy.TouchOutside, 0)
                .title(title)
                .description(description)
                .fitToScreen(true)
                .withCustomView(R.layout.view_tooltip, true)
                .show();
    }*/

    /*static void showSteppedCoachmark(final Activity activity, final ArrayList<View> views,
                                     final float x,
                                     final float y) {
        if (activity == null) {
            return;
        }

        if (activity.getResources().getBoolean(R.bool.has_coachmark)) {
            final ViewGroup root = (ViewGroup) activity.getWindow().getDecorView()
                    .findViewById(android.R.id.content);

            CoachmarkView coachmarkView = CoachmarkView_.build(activity);
            coachmarkView.setId(R.id.view_coachmark);
            root.addView(coachmarkView);
            coachmarkView.bind(views, x, y);
        }
    }*/

    /*public static void showCoachmark(final Activity activity, final View view, final float x,
                                     final float y) {
        if (activity == null) {
            return;
        }

        if (activity.getResources().getBoolean(R.bool.has_coachmark)) {

            final ViewGroup root = (ViewGroup) activity.getWindow().getDecorView()
                    .findViewById(android.R.id.content);

            CoachmarkView coachmarkView = CoachmarkView_.build(activity);

            coachmarkView.setId(R.id.view_coachmark);
            root.addView(coachmarkView);
            coachmarkView.bind(view, x, y);
        }
    }*/

    public static Bitmap getBitmapByView(View view) {
        Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap
                .Config.ARGB_8888);
        Canvas canvas = new Canvas(returnedBitmap);
        Drawable backgroundDrawable = view.getBackground();
        if (backgroundDrawable != null) {
            backgroundDrawable.draw(canvas);
        } else {
            canvas.drawColor(Color.WHITE);
        }
        view.draw(canvas);
        return returnedBitmap;
    }

    public static void enableChatIcon(final Activity activity) {
        RelativeLayout relativeChatAction = (RelativeLayout) activity.findViewById(R.id
                .relative_chat_action);
        relativeChatAction.setVisibility(View.VISIBLE);
    }

    public static void disableChatIcon(final Activity activity) {
        RelativeLayout relativeChatAction = (RelativeLayout) activity.findViewById(R.id
                .relative_chat_action);
        relativeChatAction.setVisibility(View.GONE);
    }

    public static void showUnreadedMessagesNumberInChatIcon(final Activity
                                                                    activity, int
                                                                    numberOfMessages) {
        TextView textUnreadMessagesNumber = (TextView) activity.findViewById(R.id
                .text_unread_messages_number);
        textUnreadMessagesNumber.setText(String.valueOf(numberOfMessages));
        textUnreadMessagesNumber.setVisibility(View.VISIBLE);
    }

    public static void showErrorMessage(View view, boolean isValid, String message) {
        String errorMessage = !isValid ? message : null;
        if (view instanceof EditText) {
            ((EditText) view).setError(errorMessage);
        } else if (view instanceof Spinner) {
            //((Spinner) view).setError(errorMessage);
        }
    }

    public static void formatViews(Context context, TextView textView) {
        if (context != null && textView != null) {
            textView.setTypeface(TypefaceUtil.load(textView.getContext().getAssets(),
                    TypefaceUtil.MEDIUM));
        }
    }

    /*public static void investmentGraphFormat(GraphicView graphicView, Context context) {
        final ViewPager viewPager = (ViewPager) graphicView.findViewById(R.id.pager_details_list);
        viewPager.measure(viewPager.getMeasuredWidth(), viewPager.getMeasuredHeight());

        for (int i = 0; i < viewPager.getChildCount(); i++) {
            final View childAt = viewPager.getChildAt(i);
            if (childAt != null) {
                TextView txtName = (TextView) childAt.findViewById(R.id.text_detail_name);
                TextView txtCurrency = (TextView) childAt.findViewById(R.id.text_item_currency);
                TextView txtDescription = (TextView) childAt.findViewById(R.id
                        .text_detail_description);

                ViewUtils.formatViews(context, txtName);
                ViewUtils.formatViews(context, txtCurrency);
                ViewUtils.formatViews(context, txtDescription);

                final LinearLayout parent = (LinearLayout) txtCurrency.getParent();
                TextView txtCurrencySymbol = (TextView) parent.getChildAt(0);

                ViewUtils.formatViews(context, txtCurrencySymbol);
            }
        }
    }*/

    public static void setHeightList(ExpandableListView listView) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupView = listAdapter.getGroupView(i, true, null, listView);
            groupView.measure(0, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupView.getMeasuredHeight();

            if (listView.isGroupExpanded(i)) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null, listView);
                    listItem.measure(0, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setHeightList(ExpandableListView listView,
                                     int group) {
        ExpandableListAdapter listAdapter = listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += groupItem.getMeasuredHeight();

            if (i == group) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10) {
            height = 200;
        }
        params.height = height + 20;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private static class CustomPasswordTransformationMethod extends PasswordTransformationMethod {
        private char dot = '\u2022';

        @Override
        public CharSequence getTransformation(CharSequence source, View view) {
            return new PassCharSequence(source);
        }

        @Override
        public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int
                direction, Rect previouslyFocusedRect) {
            //unused
        }

        class PassCharSequence implements CharSequence {

            private final CharSequence charSequence;

            PassCharSequence(final CharSequence charSequence) {
                this.charSequence = charSequence;
            }

            @Override
            public char charAt(final int index) {
                return dot;
            }

            @Override
            public int length() {
                return charSequence.length();
            }

            @Override
            public CharSequence subSequence(final int start, final int end) {
                return new PassCharSequence(charSequence.subSequence(start, end));
            }
        }
    }

}
