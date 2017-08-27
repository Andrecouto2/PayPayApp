package br.com.andrecouto.paypay.accessibility;

import android.view.View;
import android.view.accessibility.AccessibilityEvent;

public class ForceAccessibilityFocusRunnable implements Runnable {

    private final View viewToFocus;

    /**
     * Construtor padrão.
     *
     * @param viewToFocus View a ser considerada para o foco.
     */
    public ForceAccessibilityFocusRunnable(final View viewToFocus) {
        this.viewToFocus = viewToFocus;
    }

    @Override
    public void run() {
        forceAccessibilityFocus(viewToFocus);
    }

    /**
     * Realizar o foco de acessibilidade para uma determinada view.
     *
     * @param viewToFocus View a ser considerada para o foco.
     */
    protected void forceAccessibilityFocus(final View viewToFocus) {
        if (viewToFocus != null) {
            viewToFocus.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_FOCUSED);
        }
    }
}
