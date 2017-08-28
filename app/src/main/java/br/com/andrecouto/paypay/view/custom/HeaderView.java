package br.com.andrecouto.paypay.view.custom;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.activity.BaseLoggedActivity;
import br.com.andrecouto.paypay.application.AppApplication;
import br.com.andrecouto.paypay.controller.HeaderController;

import br.com.andrecouto.paypay.manager.UserManager;
import br.com.andrecouto.paypay.util.AccessibilityUtils;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HeaderView extends RelativeLayout implements HeaderInterface {

    @Inject
    UserManager userManager;
    Context context;
    TextIconView iconFilter;
    ImageView iconOpenClose;
    ImageView imageAvatar;
    ImageView imageAvatarMenuProfile;
    TextView textInitialsNameAvatar;
    TextView textName;
    TextView textEmail;
    TextView textShot;
    TextView textInitialsNameAvatarMenuProfile;
    TextView textNameMenuProfile;
    TextView textAmMenuProfile;
    TextView textShotMenuProfile;
    TextView textLastAccessMenuProfile;
    TextView textFirstMenuOption;
    TextView textFourthMenuOption;
    ImageView buttonBack;
    RelativeLayout viewMenuProfile;
    RelativeLayout viewMenuProfileAnimate;
    RelativeLayout relativeMenuProfileDetails;
    RelativeLayout relativeHeader;
    private HeaderTouchListener headerTouchListener;
    private HeaderMenuProfileListener headerMenuProfileListener;
    HeaderController headerHelper = new HeaderController();

    public HeaderView(Context context) {
        super(context);
        this.context = context;
        ((AppApplication) context.getApplicationContext()).getComponent().inject(HeaderView.this);
        init();
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        ((AppApplication) context.getApplicationContext()).getComponent().inject(HeaderView.this);
        init();
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        ((AppApplication) context.getApplicationContext()).getComponent().inject(HeaderView.this);
        init();
    }

    public void init() {
        View v = inflate(getContext(), R.layout.view_header, this);
        ButterKnife.bind(this,v);
        //iconFilter = (TextIconView) findViewById(R.id.text_view_filter);
        iconOpenClose = (ImageView) findViewById(R.id.img_view_open_close);
        imageAvatar = (ImageView) findViewById(R.id.image_view_avatar_picture);
        imageAvatarMenuProfile = (ImageView) findViewById(R.id.image_view_menu_profile_avatar);
        textInitialsNameAvatar = (TextView)  findViewById(R.id.text_view_avatar_initials_name);
        textName = (TextView) findViewById(R.id.text_view_name);
        textEmail = (TextView) findViewById(R.id.text_view_email);
        textShot = (TextView) findViewById(R.id.text_view_shot);
        textInitialsNameAvatarMenuProfile = (TextView) findViewById(R.id.text_view_menu_profile_initials_name);
        textNameMenuProfile = (TextView) findViewById(R.id.text_view_menu_profile_name);
        textAmMenuProfile = (TextView) findViewById(R.id.text_view_menu_profile_am);
        textShotMenuProfile = (TextView) findViewById(R.id.text_view_menu_profile_shot);
        textLastAccessMenuProfile = (TextView) findViewById(R.id.text_view_menu_profile_last_access);
        textFirstMenuOption = (TextView) findViewById(R.id.text_view_first_menu_option);
        textFourthMenuOption = (TextView) findViewById(R.id.text_view_fourth_menu_option);
        buttonBack = (ImageView) findViewById(R.id.img_view_menu_profile_back);
        viewMenuProfile = (RelativeLayout) findViewById(R.id.relative_menu_profile);
        viewMenuProfileAnimate = (RelativeLayout) findViewById(R.id.relative_menu_profile_animate);
        relativeMenuProfileDetails = (RelativeLayout) findViewById(R.id.relative_menu_profile_details);
        relativeHeader = (RelativeLayout) findViewById(R.id.relative_header);
        showNotificationIcon(true);
        headerHelper.init(this,userManager.getUser(), context);
    }

    /*@OnClick(R.id.text_view_search)
    protected void clickSearch() {
        if (listener != null) {
            listener.onSearchClick();
        } else {
            callDefaultAppSearchView();
        }
    }*/

    @OnClick(R.id.relative_first_menu_option)
    public void firstMenuOptionClicked() {
        //controller.firstMenuOptionClicked();
    }

    @OnClick(R.id.relative_second_menu_option)
    public void secondMenuOptionClicked() {
        //controller.secondMenuOptionClicked();
    }

    @OnClick(R.id.relative_third_menu_option)
    public void thirdMenuOptionClicked() {
        ///controller.thirdMenuOptionClicked();
    }

    @OnClick(R.id.relative_fourth_menu_option)
    public void fourthMenuOptionClicked() {
        //controller.fourthMenuOptionClicked();
    }

    /**
     * Quinta opção do menu clicada.
     */
    @OnClick(R.id.relative_fifth_menu_option)
    public void fifthMenuOptionClicked() {
        ((BaseLoggedActivity ) context).finish();
        LoginManager.getInstance().logOut();
    }

    @OnClick(R.id.relative_header)
    public void headerClicked() {
        headerHelper.animateMenuProfileIn();
        dispatchOnTouchHeaderListener();
        if (headerMenuProfileListener != null) {
            headerMenuProfileListener.onToggle(true);
        }

        accessibilityForNoContextViews(false);
    }

    /**
     * Clique em voltar.
     */
    @OnClick(R.id.img_view_menu_profile_back)
    public void backClicked() {
        backClickMenuProfile();
        dispatchOnTouchHeaderListener();
        setHeaderAccessibilityFocus();
    }

    private void dispatchOnTouchHeaderListener() {
        if (headerTouchListener != null) {
            headerTouchListener.onTouch();
        }
    }

    /**
     * Exibe o filtro.
     */
    public void showIconFilter() {

        iconFilter.setVisibility(View.VISIBLE);
    }

    /**
     * Esconde o filtro.
     */
    public void hideIconFilter() {
        iconFilter.setVisibility(View.GONE);
    }

    /**
     * Retorna altura do header
     */
    public int getHeightHeader() {
        return relativeHeader.getHeight();
    }



    /**
     * Ao receber resultado através da activity (Usado para o ciclo de tirar foto).
     *
     * @param requestCode codigo da requisição
     * @param resultCode  codigo do resultado
     * @param data        informação
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

    /**
     * Sets header touch listener.
     *
     * @param headerTouchListener the header touch listener
     */
    public void setHeaderTouchListener(HeaderTouchListener headerTouchListener) {
        this.headerTouchListener = headerTouchListener;
    }

    /**
     * Sets header menu profile listener.
     *
     * @param headerMenuProfileListener the header menu profile listener
     */
    public void setHeaderMenuProfileListener(HeaderMenuProfileListener headerMenuProfileListener) {
        this.headerMenuProfileListener = headerMenuProfileListener;
    }

    /**
     * Chama a tela padrão de busca da app
     */
    public void callDefaultAppSearchView() {

        //controller.onSearchIconClicked();
    }

    @Override
    public void setAvatarText(String firstName, String name, String shot, String am, String lastAccess) {
        textName.setText(firstName);
        textNameMenuProfile.setText(name);
        textEmail.setText(shot);
        textAmMenuProfile.setText(shot);
        textShot.setText(am);
        textShotMenuProfile.setText(am);
        textLastAccessMenuProfile.setText(lastAccess);
    }

    @Override
    public void setAvatarClickText(String title) {
        textFirstMenuOption.setText(title);
    }

    @Override
    public void setEvaluateClickText(String title) {
        textFourthMenuOption.setText(title);
    }

    @Override
    public void setHeaderAccessibility(String name, String am, String shot) {
        textNameMenuProfile.setContentDescription(null);
    }

    @Override
    public void setArrowInMenuProfileAccessibilityFocus() {
        if (AccessibilityUtils.isAccessibilityActive(getContext())) {
            AccessibilityUtils.forceAccessibilityFocusAfterPostDelay(buttonBack);
        }
    }

    @Override
    public void setHeaderAccessibilityFocus() {
        if (AccessibilityUtils.isAccessibilityActive(getContext())) {
            AccessibilityUtils.forceAccessibilityFocusAfterPostDelay(relativeHeader);
        }
    }

    @Override
    public void setHeaderAccessibilityFocus(int time) {
        if (AccessibilityUtils.isAccessibilityActive(getContext())) {
            AccessibilityUtils.forceAccessibilityFocusAfterPostDelay(relativeHeader, time);
        }
    }

    @Override
    public List<View> getViewsToDisableInAccessibility() {
        ArrayList<View> list = new ArrayList<>();
        list.add(viewMenuProfile);
        return list;
    }

    @Override
    public ImageView getAvatarImageView() {
        return imageAvatar;
    }

    @Override
    public ImageView getAvatarMenuProfileImageView() {
        return imageAvatarMenuProfile;
    }

    @Override
    public TextView getAvatarFirstLettersTextView() {
        return textInitialsNameAvatar;
    }

    @Override
    public TextView getAvatarFirstLettersMenuProfileTextView() {
        return textInitialsNameAvatarMenuProfile;
    }


    @Override
    public void setAnimateMenuProfile(Animation animation) {
        viewMenuProfileAnimate.clearAnimation();
        viewMenuProfileAnimate.startAnimation(animation);
    }

    @Override
    public void setAnimateMenuSecondLevel(Animation animation) {
         //viewMenuSecondLevelProfile.clearAnimation();
        //viewMenuSecondLevelProfile.startAnimation(animation);
    }

    public void start() {

    }

    @Override
    public void setMenuSecondLevelViewVisibility(int visibility) {
        //viewMenuSecondLevelProfile.setVisibility(visibility);
    }

    @Override
    public void setMenuSecondLevelVisibility(int visibility) {
        //menuSecondLevelView.setVisibility(visibility);
    }

    @Override
    public void setMenuProfileVisibility(int visibility) {
        viewMenuProfile.setVisibility(visibility);
    }

    @Override
    public void setMenuProfileAnimateVisibility(int visibility) {
        viewMenuProfileAnimate.setVisibility(visibility);
    }

    /**
     * Verifica se o menu está aberto.
     *
     * @return true /false
     */
    public boolean isMenuProfileOpen() {
        return viewMenuProfile.getVisibility() == VISIBLE;
    }

    /**
     * Verifica se o menu segundo nivel está aberto.
     *
     * @return true /false
     */
    public boolean isMenuSecondLevelOpen() {
        //return viewMenuSecondLevelProfile.getVisibility() == VISIBLE;
        return false;
    }

    /**
     * Clique em voltar no menu de perfil.
     */
    public void backClickMenuProfile() {
        headerHelper.animateMenuProfileOut();
        if (headerMenuProfileListener != null) {
            headerMenuProfileListener.onToggle(false);
        }

        accessibilityForNoContextViews(true);
    }

    private void accessibilityForNoContextViews(boolean enable) {
        if (AccessibilityUtils.isAccessibilityActive(getContext())) {
            int type = enable ? ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_AUTO : ViewCompat.IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS;

            // header
            ViewCompat.setImportantForAccessibility(relativeHeader, type);
        }
    }

    public boolean isMenuProfileVisible() {
        return viewMenuProfile.getVisibility() == VISIBLE;
    }

    /**
     * Trata o clique no badge (sino) da central de notificações
     *
     * @param fragmentManager O FragmentManager
     */
    public void handleNotificationIconClick(FragmentManager fragmentManager) {
        //iconNotification.setOnClickListener(NotificationCenterHelper.handleBadgeClick(fragmentManager));
    }

    /**
     * Mostra ou esconde a badge (sino) da central de notificações
     *
     * @param visible mostrar ou esconder
     */
    public void showNotificationIcon(boolean visible) {
        /*if (!NotificationCentralInternal.getLibraryDisabled() && visible) {
            iconNotification.setVisibility(VISIBLE);
        } else {
            iconNotification.setVisibility(GONE);
        }*/
    }

    /**
     * Interface do listener.
     */
    public interface Listener {
        /**
         * Ouvinte do filtro clicado.
         */
        void onFilterClick();

        /**
         * Ouvinte da busca clicado.
         */
        void onSearchClick();
    }

    /**
     * Interface do header touch.
     */
    public interface HeaderTouchListener {
        /**
         * ao clicar.
         */
        void onTouch();
    }

    /**
     * Interface do menu perfil.
     */
    public interface HeaderMenuProfileListener {
        /**
         * ao trocar estado.
         *
         * @param isOpen está aberto (verdadeiro/falso)
         */
        void onToggle(boolean isOpen);
    }





}
