package br.com.andrecouto.paypay.dialog;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;

public class BaseDialogCompat extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = super.onCreateDialog(savedInstanceState);

        // solicita uma janela sem o titulo
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public BaseDialogCompat show(FragmentActivity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (!activity.isFinishing() && !activity.isDestroyed()) {
                this.show(activity.getSupportFragmentManager(), getClass().getSimpleName());
            }

            return this;
        }

        if (!activity.isFinishing()) {
            this.show(activity.getSupportFragmentManager(), getClass().getSimpleName());
        }

        return this;
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }
}

