package com.daffzzaqihaq.crudbuku.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.daffzzaqihaq.crudbuku.R;
import com.daffzzaqihaq.crudbuku.model.login.LoginData;
import com.daffzzaqihaq.crudbuku.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements RegisterContract.View {

    @BindView(R.id.edtnama)
    EditText edtnama;
    @BindView(R.id.edtalamat)
    EditText edtalamat;
    @BindView(R.id.edtnotelp)
    EditText edtnotelp;
    @BindView(R.id.radioLaki)
    RadioButton radioLaki;
    @BindView(R.id.radioPerempuan)
    RadioButton radioPerempuan;
    @BindView(R.id.edtusername)
    EditText edtusername;
    @BindView(R.id.edtpassword)
    TextInputEditText edtpassword;
    @BindView(R.id.edtpasswordconfirm)
    TextInputEditText edtpasswordconfirm;
    @BindView(R.id.btnregister)
    Button btnregister;

    private ProgressDialog progressDialog;
    private RegisterPresenter mRegisterPresenter = new RegisterPresenter(this);
    private String jenkel;
    private String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        // set jenkel default
        setRadio();
    }

    private void setRadio() {
        if (radioLaki.isChecked()){
            jenkel = "Laki-laki";
        }else {
            jenkel =  "Perempuan";
        }

    }

    @Override
    public void showProgress() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Just a sec...");
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
        progressDialog.dismiss();
    }

    @Override
    public void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showRegisterSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @OnClick({R.id.radioLaki, R.id.radioPerempuan, R.id.btnregister})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radioLaki:
                jenkel = "Laki-laki";
                break;
            case R.id.radioPerempuan:
                jenkel = "Perempuan";
                break;
            case R.id.btnregister:
                // Membuat object model class LoginData
                LoginData mLoginData = new LoginData();

                // Memasukan data ke dalam model LoginData
                mLoginData.setUsername(edtusername.getText().toString());
                mLoginData.setPassword(edtpassword.getText().toString());
                mLoginData.setAlamat(edtalamat.getText().toString());
                mLoginData.setNo_telp(edtnotelp.getText().toString());
                mLoginData.setJenkel(jenkel);
                mLoginData.setNama_user(edtnama.getText().toString());
                mLoginData.setUsername(edtusername.getText().toString());

                // Mengirimkan data ke Presenter
                mRegisterPresenter.doRegisterUser(mLoginData);
                break;
        }
    }
}
