package br.com.andrecouto.paypay.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import br.com.andrecouto.paypay.R;

public class RegisterActivity extends ViewModelActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    @Override
    protected void createViewModel() {

    }
}
