package br.com.andrecouto.paypay.controller;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.application.AppApplication;
import br.com.andrecouto.paypay.fragment.AboutDialog;
import br.com.andrecouto.paypay.fragment.dashboard.ChatDashBoardFragment;
import br.com.andrecouto.paypay.fragment.dashboard.ContactsDashBoardFragment;
import br.com.andrecouto.paypay.fragment.dashboard.DiscoveryDashBoardFragment;
import br.com.andrecouto.paypay.fragment.dashboard.MeDashBoardFragment;
import br.com.andrecouto.paypay.helper.ManagerHelper;
import br.com.andrecouto.paypay.sessionmanager.SessionManager;
import br.com.andrecouto.paypay.util.FragmentUtils;
import br.com.andrecouto.paypay.util.TabBarUtils;
import br.com.andrecouto.paypay.view.custom.CircleTransformation;
import br.com.andrecouto.paypay.view.custom.HeaderInterface;
import br.com.andrecouto.paypay.entity.User;
import br.com.andrecouto.paypay.util.StringUtils;

public class HeaderController {

    private static final int ANIMATE_DURATION = 500;
    private User user;
    private HeaderInterface headerInterface;
    private Context context;
    private SessionManager sessionManager;

    public void init(HeaderInterface i, User user, Context context) {
        headerInterface = i;
        this.user = user;
        this.context = context;
        sessionManager = new SessionManager(context);

        setupAvatar();
        setVisibility();
        headerInterface.setHeaderAccessibilityFocus();

    }

    private void setupAvatar() {
        String firstName = StringUtils.getFirstWordAndCapitalize(user.getNome()).split(" ")[0];
        String email = user.getEmail();

        headerInterface.setAvatarText(context.getString(R.string.header_avatar_label, firstName), StringUtils.getFirstAndLastName(user.getNome()),
                context.getString(R.string.header_email, email),
                "", "");
        headerInterface.setHeaderAccessibility(StringUtils.getFirstAndLastName(user.getNome()), user.getCelular(), user.getCelular());

        headerInterface.getAvatarFirstLettersTextView().setText(ManagerHelper.nameInitialLettters(user.getNome()));
        headerInterface.getAvatarFirstLettersMenuProfileTextView().setText(ManagerHelper.nameInitialLettters(user.getNome()));

        setImage();
    }

    private void setImage() {

        headerInterface.getAvatarImageView().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_manager));
        headerInterface.getAvatarMenuProfileImageView().setImageDrawable(ContextCompat.getDrawable(context, R.drawable.circle_avatar_ffffff));
        headerInterface.getAvatarFirstLettersTextView().setVisibility(View.VISIBLE);
        headerInterface.getAvatarFirstLettersMenuProfileTextView().setVisibility(View.VISIBLE);

        loadImage(headerInterface.getAvatarImageView(), headerInterface.getAvatarFirstLettersTextView(), false);
        loadImage(headerInterface.getAvatarMenuProfileImageView(), headerInterface.getAvatarFirstLettersMenuProfileTextView(), false);
    }

    private void loadImage(final ImageView imageView, final TextView avatarFirstLetter, final boolean setAvatarClickText) {
        String imageUrl = "https://graph.facebook.com/" + user.getIdUsuario() + "/picture?type=large";
        Picasso.with(context)
                .load(imageUrl)
                .memoryPolicy(MemoryPolicy.NO_CACHE)
                .transform(new CircleTransformation(context,
                        R.color.gray_D2D2D2,
                        0))
                .placeholder(R.drawable.circle_avatar_ffffff)
                .centerCrop().fit()
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        avatarFirstLetter.setVisibility(View.INVISIBLE);
                        headerInterface.getAvatarFirstLettersMenuProfileTextView().setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onError() {
                        if (setAvatarClickText) {
                            //headerInterface.setAvatarClickText(context.getString(R.string.menu_profile_option_text_first));
                        }
                        //imageView.setVisibility(View.GONE);
                    }
                });
    }

    private void setVisibility() {
        if(((AppApplication) context.getApplicationContext()).isMenuProfileOpen()) {
            headerInterface.setMenuProfileVisibility(View.VISIBLE);
            headerInterface.setMenuProfileAnimateVisibility(View.VISIBLE);
            ((AppApplication) context.getApplicationContext()).setMenuProfileOpen(false);
        }
    }

    public void animateMenuProfileIn() {
        headerInterface.setMenuProfileVisibility(View.VISIBLE);
        headerInterface.setMenuProfileAnimateVisibility(View.VISIBLE);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_in_top_with_fade);
        animation.setDuration(ANIMATE_DURATION);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // nada a fazer
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                headerInterface.setArrowInMenuProfileAccessibilityFocus();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                //nada a fazer
            }
        });

        headerInterface.setAnimateMenuProfile(animation);
        TabBarUtils.hideTabBar(context);
    }

    public void animateMenuProfileOut() {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.slide_out_top_with_fade);
        animation.setDuration(ANIMATE_DURATION);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                headerInterface.setMenuProfileAnimateVisibility(View.VISIBLE);
                headerInterface.setMenuProfileVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                headerInterface.setMenuProfileAnimateVisibility(View.GONE);
                headerInterface.setMenuProfileVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // não é necessário nesse fluxo
            }
        });
        headerInterface.setAnimateMenuProfile(animation);
        TabBarUtils.showTabBar(context);
    }

    public void logout() {
        sessionManager.logoutUser();
    }

    public void goChat() {
        FragmentUtils.replaceFragment( ((FragmentActivity) context).getSupportFragmentManager(), new ChatDashBoardFragment(), R.id.frame_home_container, true);
        TabBarUtils.showTabBar(context);
    }

    public void goDiscovery() {
        FragmentUtils.replaceFragment( ((FragmentActivity) context).getSupportFragmentManager(), new DiscoveryDashBoardFragment(), R.id.frame_home_container, true);
        TabBarUtils.showTabBar(context);
    }

    public void goMe() {
        FragmentUtils.replaceFragment( ((FragmentActivity) context).getSupportFragmentManager(), new MeDashBoardFragment(), R.id.frame_home_container, true);
        TabBarUtils.showTabBar(context);
    }

    public void goAbout() {
        AboutDialog.showAbout(((FragmentActivity) context).getSupportFragmentManager());
    }

    public void goContacts() {
        FragmentUtils.replaceFragment( ((FragmentActivity) context).getSupportFragmentManager(), new ContactsDashBoardFragment(), R.id.frame_home_container, true);
    }

}
