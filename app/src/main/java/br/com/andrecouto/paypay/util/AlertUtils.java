package br.com.andrecouto.paypay.util;

import android.content.Context;
import android.content.DialogInterface;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.TextView;

import br.com.andrecouto.paypay.R;


public final class AlertUtils {

    public static AlertDialog createAlertDialog(Context context, String title, String msg) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setCancelable(false);
        dialog.setTitle(title);
        dialog.setMessage(msg);
        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });


        final AlertDialog alert = dialog.create();
        return alert;
    }

    private AlertUtils() {
    }

    public static void showMessage(final View view, final int message) {
        showMessage(view, view.getResources().getString(message), false, false);
    }

    public static void showMessage(final View view, final String message) {
        showMessage(view, message, false, false);
    }

    public static void showMessage(final View view, final int message, boolean indefinite, boolean dismissAction) {
        showMessage(view, view.getResources().getString(message), indefinite, dismissAction);
    }

    public static Snackbar showMessage(final View view, final String message, boolean indefinite, boolean dismissAction) {

        final Snackbar snackbar = Snackbar.make(view, message,
                indefinite ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_LONG)
                .setActionTextColor(Color.WHITE);

        if (dismissAction) {
            snackbar.setAction(R.string.ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    snackbar.dismiss();
                }
            });
        }

        snackbar.setCallback(new Snackbar.Callback() {
            @Override
            public void onShown(Snackbar snackbar) {
                final View snackView = snackbar.getView();
                snackView.setContentDescription(message);
                snackView.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
            }
        });
        final View snackBarView = snackbar.getView();
        final TextView tvSnack = (TextView)
                snackBarView.findViewById(android.support.design.R.id.snackbar_text);
        final TextView tvSnackAction = (TextView) snackBarView.findViewById(android.support.design.R.id.snackbar_action);
        final Typeface robotoTypeface = TypefaceUtil.load(view.getContext().getAssets(), TypefaceUtil.MEDIUM);
        tvSnack.setTypeface(robotoTypeface);
        tvSnack.setMaxLines(3);
        tvSnackAction.setTypeface(robotoTypeface);
        snackbar.show();
        return snackbar;
    }
}
