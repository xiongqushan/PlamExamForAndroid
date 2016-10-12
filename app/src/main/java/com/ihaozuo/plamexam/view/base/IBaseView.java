package com.ihaozuo.plamexam.view.base;


import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.CustomDialog;

/**
 * Created by xiongwei1 on 2016/7/4.
 */
public interface IBaseView<T extends IBasePresenter> {
    void showDialog();

    void showDialog(String msg);

    void hideDialog();

    void hideDialog(String msg);

    void showConfirmDialog(String string, CustomDialog.OnDialogListener onConfirmDialogListener);

    void showConfirmDialog(String content, String confirmText, String cancelText, CustomDialog.OnDialogListener onConfirmDialogListener);

    void showConfirmDialog(String content, String confirmText, CustomDialog.OnDialogListener onConfirmDialogListener);

    void setPresenter(T presenter);
}
