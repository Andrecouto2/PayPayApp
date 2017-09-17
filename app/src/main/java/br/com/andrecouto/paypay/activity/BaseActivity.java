package br.com.andrecouto.paypay.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.dialog.LoadingDialog;
import br.com.andrecouto.paypay.listeners.KeyboardListener;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


abstract class BaseActivity extends AppCompatActivity implements KeyboardListener {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    Toolbar toolbar;
    TextView toolbarTitle;
    private DialogFragment dialog;
    OnbackPressedListener onBackPressedListener;
    boolean statementToolbarEnabled = false;
    private ViewGroup rootLayout;
    private boolean listeningKeyboard;
    private boolean keyboardAlive;
    private LoadingDialog ld = new LoadingDialog();

    private ViewTreeObserver.OnGlobalLayoutListener keyboardLayoutListener = new ViewTreeObserver
            .OnGlobalLayoutListener() {
        @Override
        public void onGlobalLayout() {

            if (br.com.andrecouto.paypay.util.ViewUtils.isSoftKeyboardOpenned(BaseActivity.this)) {
                if (!keyboardAlive) {
                    onShowKeyboard();
                }
            } else {
                if (keyboardAlive) {
                    onHideKeyboard();
                }
            }
        }
    };


    @Override
    public void onShowKeyboard() {
        keyboardAlive = true;
    }

    @Override
    public void onHideKeyboard() {
        keyboardAlive = false;
    }

    public void setBottomNavigation(BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener) {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public void setToolbarTitle(@android.support.annotation.StringRes int title) {
        toolbarTitle.setText(title);
    }

    public void setToolbarTitle(String title, String contentDescription) {
        if (!TextUtils.isEmpty(title)) {
            toolbarTitle.setText(title);
        }
        if (!TextUtils.isEmpty(contentDescription)) {
            toolbarTitle.setContentDescription(contentDescription);
        }
    }

    public void setToolbarTitle(@android.support.annotation.StringRes int title, @android.support.annotation.StringRes int contentDescription) {
        String strTitle = getString(title);
        String strContentDescription = getString(contentDescription);
        setToolbarTitle(strTitle, strContentDescription);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /*@Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (getParentActivityName() != null) {
                navigateUp();
            } else {
                navigateBack();
            }
            return true;
        }
        return false;
    }*/

    String getParentActivityName() {
        return NavUtils.getParentActivityName(this);
    }

    void navigateBack() {
        onBackPressed();
    }

    void navigateUp() {
        NavUtils.navigateUpFromSameTask(this);
    }

    public Toolbar getToolbar() {
        return toolbar;
    }

    @UiThread
    public void showLoadingDialog() {
        if (!isFinishing() && (this.dialog == null || !this.dialog.isVisible())) {
            this.dialog = ld.show(this);
        }
    }

    @UiThread
    public void hideLoadingDialog() {
        if (this.dialog != null && !isFinishing()) {
            this.dialog.dismissAllowingStateLoss();
        }
    }

    public int getResourceFromAttr(final int attr) {
        final TypedValue typedvalueattr = new TypedValue();
        getTheme().resolveAttribute(attr, typedvalueattr, true);
        return typedvalueattr.resourceId;
    }

    public void setOnBackPressedListener(OnbackPressedListener onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }

    protected View getBaseViewToShowSnackBar() {
        return null;
    }

    @Override
    public void onBackPressed() {
         if (onBackPressedListener != null) {
             onBackPressedListener.onBackPressed();
         }
    }

    /**
     * Interface de escuta do botão voltar.
     */
    public interface OnbackPressedListener {

        /**
         * Voltar pressionado.
         */
        void onBackPressed();
    }
}
