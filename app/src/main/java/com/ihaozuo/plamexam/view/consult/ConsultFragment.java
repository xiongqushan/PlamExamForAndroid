package com.ihaozuo.plamexam.view.consult;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.iflytek.sunflower.FlowerCollector;
import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.bean.ConsultDetailBean;
import com.ihaozuo.plamexam.contract.ConsultContract;
import com.ihaozuo.plamexam.presenter.IBasePresenter;
import com.ihaozuo.plamexam.util.JsonParser;
import com.ihaozuo.plamexam.view.base.AbstractView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ConsultFragment extends AbstractView implements ConsultContract.IConsultView {

    @Bind(R.id.layer_docInfo)
    LinearLayout layerDocInfo;
    @Bind(R.id.img_actionbar_left)
    ImageView imgActionbarLeft;
    @Bind(R.id.txt_actionbar_title)
    TextView txtActionbarTitle;
    @Bind(R.id.img_actionbar_right)
    ImageView imgActionbarRight;
    @Bind(R.id.actionbar)
    RelativeLayout actionbar;
    @Bind(R.id.img_head)
    SimpleDraweeView imgHead;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_role)
    TextView tvRole;
    @Bind(R.id.btn_sound_message)
    ImageView btnSoundMessage;
    @Bind(R.id.bottom_sheet)
    View bottomSheet;
    @Bind(R.id.edittxt_message)
    EditText edittxtMessage;
    @Bind(R.id.pull_refresh_recycler)
    RecyclerView pullRefreshRecycler;
    @Bind(R.id.fab)
    FloatingActionButton fab;

    private View rootView;
    private Context mContext;
    private ConsultContract.IConsultPresenter mIConsultPresenter;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerView mRecyclerView;
    private ConsultDetailAdapter mAdapter;

    public static final String PREFER_NAME = "com.iflytek.setting";
    private static String TAG = ConsultFragment.class.getSimpleName();
    private SpeechRecognizer mIat;                                                              // 语音听写对象
    private RecognizerDialog mIatDialog;                                                       // 语音听写UI
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();         // 用HashMap存储听写结果
    private String mEngineType = SpeechConstant.TYPE_CLOUD;                                   // 引擎类型
    int ret = 0;                                                                                 // 函数调用返回值
    private Toast mToast;
    private SharedPreferences mSharedPreferences;


    public ConsultFragment() {
        // Required empty public constructor
    }

    public static ConsultFragment newInstance() {
        return new ConsultFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.consult_frag, container, false);
            mContext = getContext();
            setCustomerTitle(rootView, "健康咨询服务");
        }
        ButterKnife.bind(this, rootView);
        initSpeechRecognizer();

//        mRecyclerView = pullRefreshRecycler.getRefreshableView();
        mRecyclerView = pullRefreshRecycler;
        ((DefaultItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        linearLayoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(linearLayoutManager);
//        //添加PULL_TO_REFRESH音效
//        SoundPullEventListener<RecyclerView> soundListener = new SoundPullEventListener<RecyclerView>(mContext);
//        soundListener.addSoundEvent(PullToRefreshBase.State.PULL_TO_REFRESH, R.raw.pull_event);
//        soundListener.addSoundEvent(PullToRefreshBase.State.RESET, R.raw.reset_sound);
//        soundListener.addSoundEvent(PullToRefreshBase.State.REFRESHING, R.raw.refreshing_sound);
//        pullRefreshRecycler.setOnPullEventListener(soundListener);

//        设置PULL_TO_REFRESH LOADING
//        pullRefreshRecycler.setHeaderLayout(new LoadingLayout(mContext));

        mAdapter = new ConsultDetailAdapter();
        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mIConsultPresenter.getConsultDetail();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//         退出时释放连接
        mIat.cancel();
        mIat.destroy();
    }


    @Override
    protected IBasePresenter getPresenter() {
        return mIConsultPresenter;
    }

    @Override
    protected View getRootView() {
        return rootView;
    }

    @Override
    public void setPresenter(ConsultContract.IConsultPresenter presenter) {
        mIConsultPresenter = presenter;
    }

    @OnClick({R.id.btn_sound_message, R.id.btn_send, R.id.fab})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sound_message:
//                String editContent = edittxtMessage.getText().toString();
//                if (!StringUtil.isEmpty(editContent)) {
//                    mIConsultPresenter.sendMessage(1, editContent);
//                }
                getVoiceContent();
                break;

            case R.id.btn_send:
                if (View.VISIBLE == bottomSheet.getVisibility()) {
                    bottomSheet.setVisibility(View.GONE);
                } else {
                    bottomSheet.setVisibility(View.VISIBLE);
                }
                break;

            case R.id.fab:
                startActivity(new Intent(mContext, ConsultGradeActivity.class));
        }
    }

    public void getVoiceContent() {
        // 移动数据分析，收集开始听写事件
        FlowerCollector.onEvent(mContext, "iat_recognize");
        edittxtMessage.setText(null);// 清空显示内容
        mIatResults.clear();
        // 设置参数
        setParam();
        boolean isShowDialog = mSharedPreferences.getBoolean(
                getString(R.string.pref_key_iat_show), true);
        if (isShowDialog) {
            // 显示听写对话框
            mIatDialog.setListener(mRecognizerDialogListener);
            mIatDialog.show();
            showTip(getString(R.string.text_begin));
        } else {
            // 不显示听写对话框
            ret = mIat.startListening(mRecognizerListener);
            if (ret != ErrorCode.SUCCESS) {
                showTip("听写失败,错误码：" + ret);
            } else {
                showTip(getString(R.string.text_begin));
            }
        }
    }

    public void initSpeechRecognizer() {
        mIat = SpeechRecognizer.createRecognizer(mContext, mInitListener);
        mIatDialog = new RecognizerDialog(mContext, mInitListener);
        mSharedPreferences = mContext.getSharedPreferences(this.PREFER_NAME,
                Activity.MODE_PRIVATE);
        mToast = Toast.makeText(mContext, "", Toast.LENGTH_SHORT);
    }

    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                showTip("初始化失败");
            }
        }
    };

    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            showTip(error.getPlainDescription(true));
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            Log.d(TAG, results.getResultString());
            printResult(results);

            if (isLast) {

            }
        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据：" + data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());
        String sn = null;       // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        edittxtMessage.setText(resultBuffer.toString());
        edittxtMessage.setSelection(edittxtMessage.length());
    }

    private RecognizerDialogListener mRecognizerDialogListener = new RecognizerDialogListener() {
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);
        }

        /**
         * 识别回调错误.
         */
        public void onError(SpeechError error) {
            showTip(error.getPlainDescription(true));
        }

    };

    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    public void setParam() {
        // 清空参数
        mIat.setParameter(SpeechConstant.PARAMS, null);
        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        String lag = mSharedPreferences.getString("iat_language_preference",
                "mandarin");
        if (lag.equals("en_us")) {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "en_us");
        } else {
            // 设置语言
            mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
            // 设置语言区域
            mIat.setParameter(SpeechConstant.ACCENT, lag);
        }

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS, mSharedPreferences.getString("iat_vadbos_preference", "5000"));

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, mSharedPreferences.getString("iat_vadeos_preference", "1000"));

        // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
        mIat.setParameter(SpeechConstant.ASR_PTT, mSharedPreferences.getString("iat_punc_preference", "1"));

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mIat.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mIat.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/iat.wav");
    }

    @Override
    public void refreshConsultList(List<ConsultDetailBean> consultDetailList) {
        mAdapter.refreshList(mContext, consultDetailList);
    }

}
