package br.com.andrecouto.paypay.activity;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.Toast;

import javax.inject.Inject;
import br.com.andrecouto.paypay.R;
import br.com.andrecouto.paypay.application.AppApplication;
import br.com.andrecouto.paypay.entity.User;
import br.com.andrecouto.paypay.util.AlertUtils;
import br.com.andrecouto.paypay.viewmodel.UserViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends ViewModelActivity implements UserViewModel.Listener  {

    @BindView(R.id.signup_input_layout_name)
    public TextInputLayout edtName;
    @BindView(R.id.signup_input_layout_email)
    public TextInputLayout edtEmail;
    @BindView(R.id.signup_input_layout_password)
    public TextInputLayout edtPassword;
    @BindView(R.id.signup_input_layout_cpf)
    public TextInputLayout edtCpf;
    @BindView(R.id.signup_input_layout_phone)
    public TextInputLayout edtPhone;

    @Inject
    UserViewModel mUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        ((AppApplication) getApplicationContext()).getComponent().inject(RegisterActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_register)
    public void onClickRegister(View v) {
        showLoadingDialog();
        User user = new User();
        user.setEmail(edtEmail.getEditText().getText().toString());
        user.setSenha(edtPassword.getEditText().getText().toString());
        user.setCpf(edtCpf.getEditText().getText().toString());
        user.setCelular(edtPhone.getEditText().getText().toString());
        user.setNome(edtName.getEditText().getText().toString());
        mUserViewModel.registerUser(user);
    }

    @Override
    protected void createViewModel() {
        mViewModel = mUserViewModel;
        mUserViewModel.setListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mUserViewModel.setListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mUserViewModel.clearListener();
    }

    @Override
    public void onError(String header, String message) {
        Toast.makeText(this,"Erro no cadastro.",Toast.LENGTH_SHORT).show();
        hideLoadingDialog();
        finish();
    }

    @Override
    public void onUserResponse(User user) {
        Toast.makeText(this,user.getNome()+" cadastrado com sucesso!",Toast.LENGTH_SHORT).show();
        hideLoadingDialog();
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
