package br.com.andrecouto.paypay.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.os.Bundle;
import android.view.View;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Arrays;
import javax.inject.Inject;
import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.application.AppApplication;
import br.com.andrecouto.paypay.view.custom.ProgressBarView;
import br.com.andrecouto.paypay.entity.AccessToken;
import br.com.andrecouto.paypay.entity.User;
import br.com.andrecouto.paypay.manager.AuthenticationManager;
import br.com.andrecouto.paypay.manager.UserManager;
import br.com.andrecouto.paypay.util.AlertUtils;
import br.com.andrecouto.paypay.viewmodel.LoginViewModel;
import br.com.andrecouto.paypay.viewmodel.UserViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends ViewModelActivity implements LoginViewModel.Listener, UserViewModel.Listener {

    private static final String TAG = LoginActivity.class.toString();
    @Inject
    AuthenticationManager authenticationManager;
    @Inject
    LoginViewModel mLoginViewModel;
    @Inject
    UserManager userManager;
    @Inject
    UserViewModel mUserViewModel;
    @BindView(R.id.tilLogin)
    public TextInputLayout txtInputLogin;
    @BindView(R.id.tilPassword)
    public TextInputLayout txtInputPassword;
    @BindView(R.id.progress_loading)
    public ProgressBarView loading;
    @BindView(R.id.facebook_login_button)
    public LoginButton loginButton;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AppApplication) getApplicationContext()).getComponent().inject(LoginActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        //FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken().getCurrentAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                try {
                                    User user = new User();
                                    user.setIdUsuario(object.getLong("id"));
                                    user.setNome(object.getString("first_name")+" "+object.getString("last_name"));
                                    user.setEmail(object.getString("email"));
                                    user.setCelular("11-987675437");//default sample
                                    startHome(user);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,email,first_name,last_name,gender,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                hideLoadingDialog();
                AlertUtils.createAlertDialog(LoginActivity.this, "Cancelado", "Login cancelado.").show();
            }

            @Override
            public void onError(FacebookException error) {
                hideLoadingDialog();
                AlertUtils.createAlertDialog(LoginActivity.this, "Erro", error.getMessage()).show();
            }
        });

    }

    @Override
    protected void createViewModel() {
        mViewModel = mLoginViewModel;
        mLoginViewModel.setListener(this);
        mUserViewModel.setListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLoginViewModel.setListener(this);
        mUserViewModel.setListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLoginViewModel.clearListener();
        mUserViewModel.clearListener();
    }

    @OnClick(R.id.btnLogin)
    public void Login(View v) {
        showLoadingDialog();
        mLoginViewModel.getToken(txtInputLogin.getEditText().getText().toString(), txtInputPassword.getEditText().getText().toString(), "password");
    }

    @OnClick(R.id.txt_nao_cadastrado)
    public void Register(View v) {
        startActivity(new Intent(this,RegisterActivity.class));
    }

    @Override
    public void onError(String header, String message) {
        hideLoadingDialog();
        AlertUtils.createAlertDialog(LoginActivity.this, header, message).show();
    }

    @Override
    public void onAccessToken(AccessToken accessToken) {
        authenticationManager.setAccessToken(accessToken);
        mUserViewModel.getUser(txtInputLogin.getEditText().getText().toString());
    }

    @Override
    public void onUserResponse(User user) {
      startHome(user);
    }

    private void startHome(User user) {
        hideLoadingDialog();
        userManager.setUser(user);
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
