package br.com.andrecouto.paypay.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import br.com.andrecouto.paypay.R;
import butterknife.BindView;

public class LoadingDialog extends BaseDialogCompat {

    public ProgressBar progressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.dialog_loading,null);
        progressBar = (ProgressBar) v.findViewById(R.id.loading);
        setCancelable(false);
        getDialog().setCanceledOnTouchOutside(false);

        final Activity activity = getActivity();
        if (activity != null) {

            progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorAccent), PorterDuff.Mode.MULTIPLY);
        }
        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            //AccessibilityUtils.setRequestFocus(progressBar, getString(R.string.progressbar_description_wait));
        }
    }

}

