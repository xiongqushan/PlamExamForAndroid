package com.ihaozuo.plamexam.common.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.ScreenUtils;

/**
 * Created by hzguest3 on 2016/10/20.
 */
public class ConfirmDialog extends Dialog{

    private View view;
    private TextView tv_content;
    private TextView tv_confirm;
    private Context context;

    public ConfirmDialog(Context context, View.OnClickListener listener) {
        super(context,false,null);
        this.context = context;
        view = LayoutInflater.from(context).inflate(R.layout.dialog_empty, null);
        tv_confirm = (TextView) view.findViewById(R.id.tv_confirm);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        tv_confirm.setOnClickListener(listener);
        initWindow();
    }

    private void initWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.setContentView(view);
        window.setBackgroundDrawableResource(R.drawable.dialog_bg);//背景属性设置到view上无效。
        WindowManager.LayoutParams p = window.getAttributes();  //获取对话框当前的参数值
        p.height = (int) (ScreenUtils.getScreenHeight(context) * 0.3);   //高度设置为屏幕的0.3
        p.width = (int) (ScreenUtils.getScreenWidth(context) * 0.8);    //宽度设置为屏幕的0.6
//        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        p.width = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(p);     //设置生效
    }

    public ConfirmDialog setContentText(String string) {
        tv_content.setText(string);
        return this;
    }

}
