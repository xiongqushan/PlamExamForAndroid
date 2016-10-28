package com.ihaozuo.plamexam.common.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.HZUtils;
import com.umeng.socialize.Config;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

/**
 * Created by hzguest3 on 2016/10/21.
 */
public class ShareDialog extends Dialog {

    ImageView btnWeixin;
    ImageView btnQq;
    ImageView btnWeibo;
    ImageView btnPengyouquan;
    ImageView btnClose;
    private Context mContext;
    private String title;
    private String subTitle;
    private String targetUrl;
    UMImage image ;

    public ShareDialog(Context context, int themeResId,String title, String targetUrl,String subtitle) {
        super(context, themeResId);
        this.mContext = context;
        this.title = title;
        this.subTitle = subtitle;
        this.targetUrl = targetUrl.replace(targetUrl.charAt(targetUrl.length()-1)+"","1");
        Config.dialog = new LoadingDialog(mContext);
        Config.IsToastTip = true;
        initWindow();
    }

    private void initWindow() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.share_dialog_layout);
        // 设置背景模糊参数
        WindowManager.LayoutParams winlp = getWindow()
                .getAttributes();
        winlp.alpha = 1.0f; // 0.0-1.0
        winlp.dimAmount=0.6f;
        getWindow().setAttributes(winlp);

        Window win = getWindow();
        win.getDecorView().setPadding(0, 0, 0, 0);
        win.setGravity(Gravity.BOTTOM);
        win.setWindowAnimations(R.style.draw_dialog);
        WindowManager.LayoutParams lp = win.getAttributes();
        Display d = ((Activity) mContext).getWindowManager().getDefaultDisplay();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = (int) (d.getHeight() * 0.9);
        win.setAttributes(lp);


        btnWeixin = (ImageView) this.findViewById(R.id.btn_weixin);
        btnQq = (ImageView) this.findViewById(R.id.btn_qq);
        btnWeibo = (ImageView) this.findViewById(R.id.btn_weibo);
        btnPengyouquan = (ImageView) this.findViewById(R.id.btn_pengyouquan);
        btnClose = (ImageView) this.findViewById(R.id.btn_close);

        btnWeixin.setOnClickListener(onClickListener);
        btnQq.setOnClickListener(onClickListener);
        btnWeibo.setOnClickListener(onClickListener);
        btnPengyouquan.setOnClickListener(onClickListener);
        btnClose.setOnClickListener(onClickListener);

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (HZUtils.isFastDoubleClick()){
                return;
            }
            ShareAction shareAction = new ShareAction((Activity) mContext)
                    .withTitle(title)
                    .withText(subTitle)
                    .withTargetUrl(targetUrl)
                    .withMedia(new UMImage(mContext, R.drawable.logo));

            switch (v.getId()) {
                case R.id.btn_weixin:

                    shareAction.setPlatform(SHARE_MEDIA.WEIXIN).setCallback(umShareListener).share();
                    break;
                case R.id.btn_qq:
                    shareAction.setPlatform(SHARE_MEDIA.QQ).setCallback(umShareListener).share();
                    break;
                case R.id.btn_weibo:
                    new ShareAction((Activity) mContext)
                            .setPlatform(SHARE_MEDIA.SINA)
                            .withTitle(title)
                            .withText(subTitle + targetUrl)
                            .share();
//                            .withMedia(new UMImage(mContext, R.drawable.logo))
                    break;
                case R.id.btn_pengyouquan:
                    shareAction.setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE).setCallback(umShareListener).share();
                    break;
                case R.id.btn_close:
                    dismiss();
                    break;
            }
        }
    };

    private UMShareListener umShareListener = new UMShareListener() {

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat","platform"+platform);
            if(platform.name().equals("WEIXIN_FAVORITE")){
                Toast.makeText(mContext,checkPlatform(platform) + " 收藏成功啦",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(mContext, checkPlatform(platform) + " 分享成功啦", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(mContext,checkPlatform(platform) + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if(t!=null){
                Log.d("throw","throw:"+t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(mContext,checkPlatform(platform) + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    public String checkPlatform(SHARE_MEDIA platform){
        switch (platform){
            case WEIXIN_CIRCLE:
                return "朋友圈";
            case WEIXIN:
                return "微信";
            case QQ:
                return "QQ";
            case SINA:
                return "新浪";
            default:
                return "";
        }
    }



}
