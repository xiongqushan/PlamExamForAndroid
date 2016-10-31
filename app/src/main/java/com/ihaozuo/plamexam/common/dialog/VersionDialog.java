package com.ihaozuo.plamexam.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;


/**
 * Created by zy on 16/10/25.
 */
public class VersionDialog extends Dialog {
    private View view;
    private TextView tv_title;
    private TextView tv_subtitle;
    private Context context;
    private OnDialogListener listener;
    private TextView tv_confirm;
    private TextView tv_cancel;
    private View divider;

    public VersionDialog(Context context, OnDialogListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
        view = LayoutInflater.from(context).inflate(R.layout.version_dialog, null);
        initView();
        initWindow();
    }

    private void initWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setContentView(view);
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);//背景属性设置到view上无效。
        WindowManager.LayoutParams p = window.getAttributes();  //获取对话框当前的参数值
//        p.height = (int) (ScreenUtils.getScreenHeight(context) * 0.3);   //高度设置为屏幕的0.3
//        p.width = (int) (ScreenUtils.getScreenWidth(context) * 0.8);    //宽度设置为屏幕的0.6
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        p.width = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(p);     //设置生效
    }

    private void initView() {
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_subtitle = (TextView) view.findViewById(R.id.tv_subtitle);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_cancel = (TextView) view.findViewById(R.id.tv_cancel);
        divider = view.findViewById(R.id.divider_dialog);

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnDialogCancelListener();
                dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.OnDialogConfirmListener();
                dismiss();
            }
        });
    }

    public VersionDialog setConfirmText(String str) {
        if (!TextUtils.isEmpty(str)) {
            tv_confirm.setText(str);
        }
        return this;
    }

    public VersionDialog setCancelText(String str) {
        if (!TextUtils.isEmpty(str)) {
            tv_cancel.setText(str);
        }
        tv_cancel.setVisibility(View.VISIBLE);
        divider.setVisibility(View.VISIBLE);
        return this;
    }

    public VersionDialog setTitle(String string) {
        tv_title.setText(string);
        return this;
    }

    public VersionDialog setSubtitle(String string) {
        if (!string.equals("")) {
            tv_subtitle.setText(string);
            tv_subtitle.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public VersionDialog setCanCancel(boolean flag) {
        setCancelable(flag);
        return this;
    }

    public interface OnDialogListener {

        void OnDialogConfirmListener();

        void OnDialogCancelListener();

    }
}