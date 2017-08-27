package br.com.andrecouto.paypay.fragment;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.ViewUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.util.FragmentUtils;


public abstract class BaseFragment extends Fragment {

    protected static final String ERROR_BACKEND = "Erro ao comunicar com o Backend. %s";

    /*public BaseActivity baseActivity() {
        return (BaseActivity) getActivity();
    }*/

    @Override
    public void onStart() {
        super.onStart();
       /* if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
        String screenName = screenName();
        if (screenName != null) {
            TrackerUtils.enableGoogleAnalytics(this, screenName);
        }*/


    }

    public void setTransitionAnimationListener(Animation.AnimationListener animationListener) {
        //this.animationListener = animationListener;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {

        /*if (nextAnim != 0) {
            Animation anim = AnimationUtils.loadAnimation(getActivity(), nextAnim);

            if (anim != null && animationListener != null) {
                anim.setAnimationListener(animationListener);
            }

            return anim;
        }*/

        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onStop() {
        try {
            /*if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
            TabBarView tabBarView = BaseLoggedActivity.getTabBarView(getContext());
            if (tabBarView != null) {
                tabBarView.clearListener();
            }*/
        } finally {
            super.onStop();
        }
    }

    /*@Override
    public void postEvent(Object event) {
        EventBus.getDefault().post(event);
    }*/

    @UiThread
    public void showLoadingDialog() {
        /*final BaseActivity activity = (BaseActivity) getActivity();
        if (activity != null && !activity.isFinishing() && (this.dialog == null || !this.dialog.isVisible())) {
            this.dialog = LoadingDialog_.builder().build().show(activity);
        }*/
    }

    @UiThread
    public void hideLoadingDialog() {
       /* if (this.dialog != null) {
            this.dialog.dismissAllowingStateLoss();
        }*/
    }

    //protected abstract List<String> getOpKeys();

    /*protected boolean handleErrorAndShouldContinueToDefault(BackendExceptionEvent exceptionEvent) {
        return true;
    }*/

    /*protected void handleException(BackendExceptionEvent exceptionEvent) {
        /*if (exceptionEvent instanceof BackendExceptionEvent.Network) {
            BaseActivityHelper.handleNoConnectionError((BaseActivity) getActivity());
        } else if (exceptionEvent instanceof BackendExceptionEvent.HTTP) {
            BaseActivityHelper.handleHTTTPException((BaseActivity) getActivity(),
                    (BackendExceptionEvent.HTTP) exceptionEvent);
        } else if (exceptionEvent instanceof BackendExceptionEvent.Unexpected) {
            BaseActivityHelper.handleDefaultError((BaseActivity) getActivity(),
                    getString(R.string.error_unexpected));
        } else if (exceptionEvent instanceof BackendExceptionEvent.Token) {
            BaseActivityHelper.handleTokenError((BaseActivity) getActivity(),
                    (BackendExceptionEvent.Token) exceptionEvent);
        } else if (exceptionEvent instanceof BackendExceptionEvent.Conversion) {
            BaseActivityHelper.handleDefaultError((BaseActivity) getActivity(),
                    getString(R.string.error_conversion));
        } else {
            BaseActivityHelper.handleDefaultError((BaseActivity) getActivity(),
                    getString(R.string.error_no_connection_message));
        }
    }*/

    /*protected void handleError(BackendExceptionEvent exceptionEvent) {
        if (exceptionEvent instanceof BackendExceptionEvent.Session ||
                !BackendClient.isValidSession() && getActivity() instanceof BaseLoggedActivity) {
            return;
        }

        if (getOpKeys() != null && getOpKeys().contains(exceptionEvent.getException().getOpKey())
                && handleErrorAndShouldContinueToDefault(exceptionEvent)) {

            hideLoadingDialog();
            handleException(exceptionEvent);
        }
    }*/

    /*protected void showMessage(@StringRes int message) {
        showMessage(getString(message));
    }*/

    /*protected void showMessage(String message) {
        AlertUtils.showMessage(getActivity().getWindow().getDecorView().findViewById(android.R.id
                .content), message);
    }*/

    /*protected void showCoachmark(final View view, final float x, final float y) {
        ViewUtils.showCoachmark(getActivity(), view, x, y);
    }*/

    public abstract String tag();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        /*if (baseActivity() == null || !isAdded()) {
            return;
        }*/

        final FragmentManager supportFragmentManager = getActivity().getSupportFragmentManager();
        if (supportFragmentManager != null) {
            Fragment fragment =
                    FragmentUtils.getFragment(supportFragmentManager, R.id.frame_home_container);

        }

        if (getToolBarTitle() != null) {
         //   baseActivity().setToolbarTitle(getToolBarTitle());
        }

       // disableActionBar();
    }

    /*private void disableActionBar() {
        final ActionBar supportActionBar = baseActivity().getSupportActionBar();
        if (supportActionBar != null && getActivity() != null && getActivity() instanceof HomeActivity_) {
            final Drawable drawableFromAttr = ViewUtils
                    .getDrawableFromAttr(R
                            .attr.ic_seta_esquerda, getActivity());
            if (drawableFromAttr != null) {
                //Desabilita a navegação do icone
                supportActionBar.setHomeAsUpIndicator(drawableFromAttr);
            }
        }
    }*/

    public String getToolBarTitle() {
        return null;
    }

    /*@Override
    public Context getEventContext() {
        return getContext();
    }*/
}
