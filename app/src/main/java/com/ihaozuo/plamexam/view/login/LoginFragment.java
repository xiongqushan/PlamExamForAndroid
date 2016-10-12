package com.ihaozuo.plamexam.view.login;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.contract.LoginContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.view.base.AbstractView;
import com.ihaozuo.plamexam.view.main.MainActivity;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends AbstractView implements LoginContract.ILoginView {

    @Bind(R.id.email)
    AutoCompleteTextView email;
    private LoginContract.ILoginPresenter mLoginPresenter;
    private View rootView;
    private Context mContext;
    private ArrayAdapter<String> arrayAdapter;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    protected IBasePresenter getPresenter() {
        return null;
    }

    @Override
    protected View getRootView() {
        return null;
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mContext = getContext();
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.login_act, container, false);
        }

        ButterKnife.bind(this, rootView);
        String[] arr = {"aa", "aab", "aa", "aab", "aa", "aab", "aa", "aab", "aa", "aab", "aa", "aab", "aa", "aab", "aac"};
        arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arr);
        email.setAdapter(arrayAdapter);

        return rootView;
    }

    @Override
    public void setPresenter(LoginContract.ILoginPresenter presenter) {
        mLoginPresenter = presenter;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick(R.id.email_sign_in_button)
    public void login() {
        startActivity(new Intent(mContext, MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void goToHomePage() {
        startActivity(new Intent(mContext, MainActivity.class));
        getActivity().finish();
    }
}
