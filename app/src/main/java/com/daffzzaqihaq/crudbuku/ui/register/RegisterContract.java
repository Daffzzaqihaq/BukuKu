package com.daffzzaqihaq.crudbuku.ui.register;

import com.daffzzaqihaq.crudbuku.model.login.LoginData;

public interface RegisterContract {
    interface View{
        void showProgress();
        void hideProgress();
        void showError(String message);
        void showRegisterSuccess(String message);
    }

    interface Presenter{
        void doRegisterUser(LoginData loginData);


    }
}
