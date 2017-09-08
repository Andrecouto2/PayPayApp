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

        /*if (!isSearchHidden() && editSearch != null) {

            CharSequence searchSequence = editSearch.getText();
            if (!TextUtils.isEmpty(searchSequence)) {
                postEvent(new TrackEvent.ClickEvent(Constants.GAEvents.Category.STATEMENT,
                        Constants.GAEvents.Action.SEARCH, searchSequence.toString()));
            }
        }*/

        keyboardAlive = false;
    }

    /*@Override
    public void onTrackingEvent(TokenEvent.Category category, TokenEvent.Action action) {

    }

    @Override
    public void onEnterScreen(TokenEvent.Screen screen) {
        if (screen == TokenEvent.Screen.PASSWORD) {
            TrackerUtils.enableGoogleAnalytics(this, ScreenName.PASSWORD.formatName());
        } else if (screen == TokenEvent.Screen.TOKEN) {
            TrackerUtils.enableGoogleAnalytics(this, ScreenName.ITOKEN.formatName());
        }
    }

    @Override
    public void onLeaveScreen(TokenEvent.Screen screen) {
        // não utilizado
    }*/

    /*public RelativeLayout getRelativeChatAction() {
        return relativeChatAction;
    }

    @AfterInject
    void afterInjectBaseActivity() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        BackendClient.getInstance().setTokenAppByPassValidation(this);
    }

    @AfterViews
    void afterViewsBaseActivity() {
        String screenName = screenName();
        if (screenName != null) {
            TrackerUtils.enableGoogleAnalytics(this, screenName);
        }
        BackendClient.getInstance().setTracker(this);
        setToolbarOverlayClickListener();
        rootLayout = (ViewGroup) findViewById(android.R.id.content);
        rootLayout.getViewTreeObserver().addOnGlobalLayoutListener(keyboardLayoutListener);
        listeningKeyboard = true;
    }*/

    /*private void setToolbarOverlayClickListener() {
        if (toolbarOverlay != null) {
            setToolbarOverlayVisible(false, 0);
            toolbarOverlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getVisibility() == View.VISIBLE && onToolbarOverlayTouchedListener !=
                            null) {
                        onToolbarOverlayTouchedListener.onToolbarOverlayTouched();
                    }
                }
            });
        }
    }*/

    /*public void setTheme() {
        if (!getResources().getBoolean(R.bool.is_personnalite)) {
            setTheme(R.style.NotLoggedBaseTheme);
        } else {
            setTheme(R.style.PersonnaliteNotLoggedBaseTheme);
        }
    }*/

    /*public void setToolbarOverlayVisible(boolean visible, int duration) {
        if (toolbarOverlay != null) {
            AnimationUtils.animateAlpha(toolbarOverlay, duration, visible);
        }
    }*/

    /*public View getToolbarOverlay() {
        return toolbarOverlay;
    }*/

    /*private void initToolbar() {

        if (isSearchHidden) {
            if (statementToolbarView != null && !statementToolbarEnabled) {
                statementToolbarView.setVisibility(View.GONE);
            }

            setupToolbar();
        }
    }*/

    private void setupToolbar() {
        if (toolbar != null) {
            // Desabilita a navegação do icone
            if (toolbar.getNavigationIcon() == null) {
                //toolbar.setNavigationIcon(getResourceFromAttr(R.attr.ic_seta_esquerda));
            }

            setSupportActionBar(toolbar);
            final ActionBar supportActionBar = getSupportActionBar();

            /*if (supportActionBar != null) {
                // Desabilita a navegação do icone
                if (!(this instanceof HomeActivity)) {
                    supportActionBar.setDisplayHomeAsUpEnabled(true);
                }
                supportActionBar.setDisplayShowTitleEnabled(false);
            }*/

            /*if (title != null) {
                setToolbarTitle(title);
            } else if (getToolBarTitle() != null) {
                setToolbarTitle(getToolBarTitle());
            }*/
        }
    }

    public void setBottomNavigation(BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener) {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    /*public void setToolbarTitle(String title) {
        this.title = title;
        if (!TextUtils.isEmpty(title)) {
            toolbarTitle.setText(title);
        }
    }*/

    public void setToolbarTitle(@android.support.annotation.StringRes int title) {
        toolbarTitle.setText(title);
    }

    public void setToolbarTitle(String title, String contentDescription) {
      //  this.title = title;
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

    /*@Override
    public void startActivity(Intent intent) {
        try {
            BackgroundExecutor.cancelAll("", true);

            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } finally {
            super.startActivity(intent);
        }
    }*/

    /*@Override
    protected void onResume() {
        super.onResume();

        BackendClient.getInstance().setTokenAppByPassValidation(this);

        initToolbar();

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

        checkForCrashes();

        EventBusUtil.INSTANCE.setPaused(false);
    }*/

    /*private void checkForCrashes() {
        if (getResources().getBoolean(R.bool.has_hockeyapp)) {
            if (!getResources().getBoolean(R.bool.is_personnalite)) {
                CrashManager.register(this, getResources().getString(R.string.id_hockeyapp),
                        new CrashManagerListener() {
                            @Override
                            public boolean shouldAutoUploadCrashes() {
                                return true;
                            }
                        });
            } else {
                CrashManager.register(this,
                        getResources().getString(R.string.id_hockeyapp_Personnalite), new
                                CrashManagerListener() {
                                    @Override
                                    public boolean shouldAutoUploadCrashes() {
                                        return true;
                                    }
                                });
            }
        }
    }*/

    /*protected void checkForUpdates() {
        if (getResources().getBoolean(R.bool.has_hockeyapp_update)) {
            if (!getResources().getBoolean(R.bool.is_personnalite)) {
                UpdateManager.register(this, getResources().getString(R.string.id_hockeyapp));
            } else {
                UpdateManager.register(this,
                        getResources().getString(R.string.id_hockeyapp_Personnalite));
            }
        }
    }*/

    /*@Override
    protected void onDestroy() {
        onBackPressedListener = null;
        onToolbarOverlayTouchedListener = null;
        onSearchHiddenListener = null;

        if (listeningKeyboard) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rootLayout.getViewTreeObserver()
                        .removeOnGlobalLayoutListener(keyboardLayoutListener);
            } else {
                rootLayout.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(keyboardLayoutListener);
            }
        }

        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } finally {
            super.onDestroy();
        }
    }*/

    @Override
    protected void onPause() {
       // EventBusUtil.INSTANCE.setPaused(true);
        super.onPause();
    }

    @Override
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
    }

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

    /*@Override
    public void postEvent(Object event) {
        EventBus.getDefault().post(event);
    }*/

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

    /*public void setStatementToolbarListener(
            ToolbarStatementView.StatementToolbarListener statementToolbarListener) {
        statementToolbarView.setStatementToolbarListener(statementToolbarListener);
    }*/



    protected View getBaseViewToShowSnackBar() {
        return null;
    }




    protected void showCoachmark(final View view, final float x, final float y) {
        //ViewUtils.showCoachmark(this, view, x, y);
    }

    @Override
    public void onBackPressed() {

        /*setToolbarOverlayVisible(false, 0);

        getSupportFragmentManager().executePendingTransactions();

        //CoachmarkView coachmarkView = (CoachmarkView) findViewById(R.id.view_coachmark);

        if (coachmarkView != null) {
            coachmarkView.dismiss();
            return;
        }

        try {
            ITokenValidationFragment iTokenValidationFragment =
                    (ITokenValidationFragment) getSupportFragmentManager()
                            .findFragmentByTag(new ITokenValidationFragment().tag());

            if (iTokenValidationFragment != null) {
                FragmentUtils.removeFragment(getSupportFragmentManager(), iTokenValidationFragment,
                        false);
                return;
            }
        } catch (ClassCastException e) {
            Timber.e(e, e.getMessage());
        }

        try {
            //Infelizmente esta exceção é lançada sem motivo.
            super.onBackPressed();
        } catch (IllegalStateException e) {
            Timber.e(e, e.getMessage());
        }*/
    }


    /*public String getToolBarTitle() {
        try {
            ActivityInfo activityInfo = getPackageManager()
                    .getActivityInfo(getComponentName(), PackageManager.GET_META_DATA);

            title = activityInfo.loadLabel(getPackageManager()).toString();
            return title;
        } catch (PackageManager.NameNotFoundException e) {
            Timber.e(e, e.getMessage());
        }
        return null;
    }*/

    /*@Override
    public void onStartedByPass() {
        showLoadingDialog();

        Fragment currentFragment =
                FragmentUtils.getCurrentFragment(getSupportFragmentManager());
        if (currentFragment instanceof BaseFragment) {
            ((BaseFragment) currentFragment).hideLoadingDialog();
        }
        ITokenValidationFragment iTokenValidationFragment =
                ITokenValidationFragment_.builder().build();
        if (iTokenValidationFragment != null) {
            FragmentUtils
                    .attachFragmentWithAnimation(getSupportFragmentManager(),
                            android.R.id.content, iTokenValidationFragment, true);
        }
    }

    @Override
    public void onFinishedByPass() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                hideLoadingDialog();

                getSupportFragmentManager()
                        .popBackStackImmediate(new ITokenValidationFragment().tag(),
                                FragmentManager.POP_BACK_STACK_INCLUSIVE);
            }
        });
    }

    @Override
    public void onWaitingForToken(int i, TokenWaiter tokenWaiter) {
        hideLoadingDialog();

        Fragment currentFragment = FragmentUtils.getCurrentFragment(getSupportFragmentManager());
        if (currentFragment instanceof BaseFragment) {
            ((BaseFragment) currentFragment).hideLoadingDialog();
        }

        ITokenValidationFragment iTokenValidationFragment =
                (ITokenValidationFragment) getSupportFragmentManager()
                        .findFragmentByTag(new ITokenValidationFragment().tag());
        if (iTokenValidationFragment != null) {
            FragmentUtils
                    .removeFragment(getSupportFragmentManager(), iTokenValidationFragment, false);
        } else {
            iTokenValidationFragment = ITokenValidationFragment_.builder().build();
            FragmentUtils
                    .attachFragmentWithAnimation(getSupportFragmentManager(),
                            android.R.id.content, iTokenValidationFragment, true);
        }
    }

    @Click(R.id.image_close_search_action)
    public void onClickCloseImageSearch() {
        hideSearchField(true);
    }

    @Click(R.id.image_clear_search_action)
    public void onClickClearImageSearch() {
        ViewUtils.hideSoftKeyboard(this);
        editSearch.setText("");
        editSearch.requestFocus();
        ViewUtils.showSoftKeyboard(this, editSearch);
    }

    public void showSearchField() {
        ((BaseNavigationDrawerActivity) this).hideNavigationDrawer();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
        }
        setToolbarTitleVisibility(View.GONE);
        relativeSearchAction.setVisibility(View.VISIBLE);
        editSearch.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf"));
        if (onSearchHiddenListener != null) {
            onSearchHiddenListener.onSearchInputVisibilityStateChange(true);
        }
        isSearchHidden = false;
        editSearch.requestFocus();

        ViewUtils.showSoftKeyboard(this, editSearch);
    }

    public void hideSearchField(boolean withTextWatcherEnabled) {

        ((BaseNavigationDrawerActivity) this).showNavigationDrawer();
        ActionBar actionBar = getSupportActionBar();

        // Desabilita a navegação do icone
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            toolbar.setNavigationIcon(getResourceFromAttr(R.attr.ic_seta_esquerda));
        }

        relativeSearchAction.setVisibility(View.GONE);
        setToolbarTitleVisibility(View.VISIBLE);

        if (!TextUtils.isEmpty(editSearch.getText())) {
            searchTextWatcherEnabled = withTextWatcherEnabled;
            editSearch.setText("");
        }

        ViewUtils.hideSoftKeyboard(this);

        if (onSearchHiddenListener != null) {
            onSearchHiddenListener.onSearchInputVisibilityStateChange(false);
        }

        isSearchHidden = true;
        showClearSearchIcon(false);
    }

    public boolean isSearchHidden() {
        return isSearchHidden;
    }

    public void showClearSearchIcon(boolean shouldShowIcon) {
        if (imageClearSearch != null) {
            if (shouldShowIcon) {
                imageClearSearch.setVisibility(View.VISIBLE);
            } else {
                imageClearSearch.setVisibility(View.GONE);
            }
        }
    }

    public boolean isSearchTextWatcherEnabled() {
        return searchTextWatcherEnabled;
    }

    public void showStatementToolbar() {
        statementToolbarEnabled = true;
        setSupportActionBar(statementToolbarView.getToolbar());
        toolbar.setVisibility(View.GONE);
        ((BaseNavigationDrawerActivity) this).getToolbarContainer().setVisibility(View.VISIBLE);
        statementToolbarView.setVisibility(View.VISIBLE);
    }

    public void hideStatementToolbar() {
        statementToolbarEnabled = false;
        setSupportActionBar(toolbar);
        toolbar.setVisibility(View.VISIBLE);
        statementToolbarView.setVisibility(View.GONE);
    }

    public void hideToolbarSendStatementSelected() {
        if (statementToolbarView != null) {
            statementToolbarView.setSendStatementSelectedVisibility(View.INVISIBLE);
        }
    }

    public void showToolbarSendStatementSelected() {
        if (statementToolbarView != null) {
            statementToolbarView.setSendStatementSelectedVisibility(View.VISIBLE);
        }
    }

    public void setTextStatementSelectedItems(String text) {
        statementToolbarView.setTittle(text);
    }

    public boolean isSearchBoxIsEmpty() {
        return TextUtils.isEmpty(editSearch.getText());
    }

    public TextView getSearchBox() {
        return editSearch;
    }

    public void configureSearchBox(TextView.OnEditorActionListener editorListener, InputFilter[]
            filters, TextWatcher watcher) {
        editSearch.setImeOptions(EditorInfo.IME_FLAG_NO_FULLSCREEN | EditorInfo.IME_ACTION_SEARCH);
        editSearch
                .setInputType(editSearch.getInputType() | EditorInfo.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        editSearch.setSingleLine();
        editSearch.setTextIsSelectable(false);

        editSearch.setFilters(filters);
        editSearch.addTextChangedListener(watcher);
    }

    public void setEditSearchActionListener(TextView.OnEditorActionListener editorActionListener) {
        editSearch.setOnEditorActionListener(editorActionListener);
    }

    public void lockNavigationDrawer() {
        ((BaseNavigationDrawerActivity) this).disableNavigationDrawer();
    }

    public void unlockNavigationDrawer() {
        ((BaseNavigationDrawerActivity) this).enableNavigationDrawer();
    }

    public void hideToolbar() {
        toolbarContainer.setVisibility(View.GONE);
        getToolbar().setVisibility(View.GONE);
    }

    public void showToolbar() {
        toolbarContainer.setVisibility(View.VISIBLE);
        getToolbar().setVisibility(View.VISIBLE);
    }

    @Override
    public void onShowKeyboard() {
        keyboardAlive = true;
    }

    @Override
    public void onHideKeyboard() {

        if (!isSearchHidden() && editSearch != null) {

            CharSequence searchSequence = editSearch.getText();
            if (!TextUtils.isEmpty(searchSequence)) {
                postEvent(new TrackEvent.ClickEvent(Constants.GAEvents.Category.STATEMENT,
                        Constants.GAEvents.Action.SEARCH, searchSequence.toString()));
            }
        }

        keyboardAlive = false;
    }

    public SharedStorageManager getSharedStorageManager() {

        return SharedStorageManager;
    }

    public void setSharedStorageManager(SharedStorageManager Shared) {
        this.SharedStorageManager = Shared;
    }

    public void setToolbarTitleVisibility(int visibility) {
        toolbarTitle.setVisibility(visibility);
    }

    public void showSimultaneousSessionError(String message) {
        AlertUtils.showMessage(getWindow().getDecorView().findViewById(android.R.id.content),
                message, false, false);
        hideLoadingDialog();
    }*/



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
