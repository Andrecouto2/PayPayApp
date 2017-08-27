package br.com.andrecouto.paypay.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.view.accessibility.AccessibilityEventCompat;
import android.support.v4.view.accessibility.AccessibilityRecordCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;

import java.util.ArrayList;
import java.util.List;

import br.com.andrecouto.paypay.accessibility.ForceAccessibilityFocusRunnable;


public final class AccessibilityUtils {

    private static final int POST_DELAYED_TIME = 500;

    private AccessibilityUtils() {
    }

    public static void requestFocusToView(@NonNull View view) {
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }

    public static void setRequestFocus(final View view, @StringRes final int message) {
        if (view == null) {
            return;
        }

        setRequestFocus(view, view.getResources().getString(message));
    }

    public static void setRequestFocus(final View view, final String message) {
        if (view == null) {
            return;
        }

        AccessibilityManager manager = (AccessibilityManager) view.getContext().getSystemService(Context.ACCESSIBILITY_SERVICE);
        if (isAccessibilityActive(view.getContext())) {
            AccessibilityEvent e = AccessibilityEvent.obtain();
            e.setEventType(AccessibilityEvent.TYPE_VIEW_FOCUSED);
            e.setClassName(view.getClass().getName());
            e.setPackageName(view.getContext().getPackageName());
            e.getText().add(message);
            manager.sendAccessibilityEvent(e);
        }
    }

    public static boolean isAccessibilityActive(final Context context) {
        AccessibilityManager am = (AccessibilityManager) context.getSystemService(Context.ACCESSIBILITY_SERVICE);

        boolean isAccessibilityEnabledFlag = am.isEnabled();
        boolean isExploreByTouchEnabledFlag = isScreenReaderActivePreAndPostAPI14(context);

        return isAccessibilityEnabledFlag && isExploreByTouchEnabledFlag;
    }

    private static boolean isScreenReaderActivePreAndPostAPI14(Context context) {

        Intent screenReaderIntent = new Intent("android.accessibilityservice.AccessibilityService");
        screenReaderIntent.addCategory("android.accessibilityservice.category.FEEDBACK_SPOKEN");

        List<ResolveInfo> screenReaders = context.getPackageManager().queryIntentServices(screenReaderIntent, 0);
        ContentResolver cr = context.getContentResolver();
        Cursor cursor;
        int status;

        List<String> runningServices = new ArrayList<>();

        android.app.ActivityManager manager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (android.app.ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            runningServices.add(service.service.getPackageName());
        }

        for (ResolveInfo screenReader : screenReaders) {
            cursor = cr.query(Uri.parse("content://" + screenReader.serviceInfo.packageName
                    + ".providers.StatusProvider"), null, null, null, null);
            //this part works for Android <4.1
            if (cursor != null && cursor.moveToFirst()) {
                status = cursor.getInt(0);
                cursor.close();
                return status == 1;
            } else {
                //this part works for Android 4.1+
                return runningServices.contains(screenReader.serviceInfo.packageName);
            }
        }

        return false;
    }

    public static void announceForAccessibilityCompat(final Context context,
                                                      final View view,
                                                      CharSequence text) {

        AccessibilityManager accessibilityManager = (AccessibilityManager) context
                .getSystemService(Context.ACCESSIBILITY_SERVICE);

        if (!accessibilityManager.isEnabled()) {
            return;
        }

        // Prior to SDK 16, announcements could only be made through FOCUSED
        // events. Jelly Bean (SDK 16) added support for speaking text verbatim
        // using the ANNOUNCEMENT event type.
        final int eventType;
        if (Build.VERSION.SDK_INT < 16) {
            eventType = AccessibilityEvent.TYPE_VIEW_FOCUSED;
        } else {
            eventType = AccessibilityEventCompat.TYPE_ANNOUNCEMENT;
        }

        // Construct an accessibility event with the minimum recommended
        // attributes. An event without a class name or package may be dropped.
        final AccessibilityEvent event = AccessibilityEvent.obtain(eventType);
        event.getText().add(text);
        event.setEnabled(view.isEnabled());
        event.setClassName(view.getClass().getName());
        event.setPackageName(context.getPackageName());

        // JellyBean MR1 requires a source view to set the window ID.
        final AccessibilityRecordCompat record = new AccessibilityRecordCompat(event);
        record.setSource(view);

        // Sends the event directly through the accessibility manager. If your
        // application only targets SDK 14+, you should just call
        accessibilityManager.sendAccessibilityEvent(event);
    }

    /**
     * Dispara a ação de foco após um determinado tempo de espera
     * para as renderizações dos elementos
     *
     * @param viewToFocus - view onde será inserido o foco
     */
    public static void forceAccessibilityFocusAfterPostDelay(final View viewToFocus) {
        forceAccessibilityFocusAfterPostDelay(viewToFocus, POST_DELAYED_TIME);
    }

    /**
     * Dispara a ação de foco após um determinado tempo de espera
     * para as renderizações dos elementos
     *
     * @param viewToFocus - view onde será inserido o foco
     * @param time - tempo de espera
     */
    public static void forceAccessibilityFocusAfterPostDelay(final View viewToFocus, int time) {
        viewToFocus.postDelayed(new ForceAccessibilityFocusRunnable(viewToFocus), time);
    }

    /**
     * Método para configurar acessibilidade para finais de telefone que contém mascaras, esse
     * método faz com que a acessbilidade fale apenas os últimos digitos do número.
     * @param context Contexto
     * @param view View que precisa do contentDescription
     * @param phoneNumber String com o número de telefone
     */

}
