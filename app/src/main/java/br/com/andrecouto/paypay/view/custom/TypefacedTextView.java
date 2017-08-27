package br.com.andrecouto.paypay.view.custom;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import br.com.andrecouto.paypay.util.TypefaceUtil;

public class TypefacedTextView extends AppCompatTextView {

    public TypefacedTextView(Context context) {
        super(context);
    }

    public TypefacedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        if (!isInEditMode()) {
            TypefaceUtil.setTypeface(attrs, this);
        }
    }

    public TypefacedTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        if (!isInEditMode()) {
            TypefaceUtil.setTypeface(attrs, this);
        }
    }
}