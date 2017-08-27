package br.com.andrecouto.paypay.view.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import br.com.andrecouto.paypay.R;

public class ProgressBarView extends RelativeLayout {

    private ProgressBar progressBar;

    public ProgressBarView(Context context) {
        super(context);
        init();
    }

    public ProgressBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ProgressBarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }
    private void init() {
        inflate(getContext(), R.layout.view_progress_bar, this);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryLight), PorterDuff.Mode.MULTIPLY);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getContext(), R.color.colorPrimaryDark), PorterDuff.Mode.MULTIPLY);
    }

}

