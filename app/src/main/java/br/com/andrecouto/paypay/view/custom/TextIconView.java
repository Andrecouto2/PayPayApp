package br.com.andrecouto.paypay.view.custom;

import android.content.Context;
import android.util.AttributeSet;

import br.com.andrecouto.paypay.util.TypefaceUtil;

public class TextIconView extends android.support.v7.widget.AppCompatTextView {
        /**
         * Instantiates a new Text icon view.
         *
         * @param context contexto da aplicação
         * @param attrs   atributo de tamanho
         */
        public TextIconView(Context context, AttributeSet attrs) {
            super(context, attrs);
            if (!isInEditMode()) {
                TypefaceUtil.setTypeface(this, TypefaceUtil.ICONS);
            }
        }

        /**
         * Instantiates a new Text icon view.
         *
         * @param context  contexto da aplicação
         * @param attrs    atributo de tamanho
         * @param defStyle the def style
         */
        public TextIconView(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
            TypefaceUtil.setTypeface(this, TypefaceUtil.ICONS);
        }

        /**
         * Instantiates a new Text icon view.
         *
         * @param context contexto da aplicação
         */
        public TextIconView(Context context) {
            super(context);
            TypefaceUtil.setTypeface(this, TypefaceUtil.ICONS);
        }

}
