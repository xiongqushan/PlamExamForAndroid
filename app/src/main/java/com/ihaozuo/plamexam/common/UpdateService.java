package com.ihaozuo.plamexam.common;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.ihaozuo.plamexam.R;
import com.ihaozuo.plamexam.util.HZUtils;
import com.ihaozuo.plamexam.util.StringUtil;
import com.ihaozuo.plamexam.util.ToastUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UpdateService extends Service {

    private String appName = "";
    private static final String DIR_CACHE = "PECache";
    public static final String INTENTKEY_UPDATE_URL = "INTENTKEY_UPDATE_URL";
    private int fileLength;
    private static final int ID_NOTIFF = 0;
    private RemoteViews remoteViews;
    private NotificationManager manager;
    private Notification notification;
    private String downloadURL;

    public UpdateService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            downloadURL = intent.getStringExtra(INTENTKEY_UPDATE_URL);
            download(downloadURL);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void download(String url) {
        new DownloadAsyncTast().execute(url);
        ToastUtils.showToast("正在后台为你下载");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class myBinder extends Binder {
    }

    public void sendNotify() {
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        remoteViews = new RemoteViews(getPackageName(), R.layout.notify_layout);
        notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_notifycation)
                // .setTicker("tickerText")
                .setWhen(System.currentTimeMillis())
                // .setContentTitle("ContentTitle")
                // .setContentText("ContentText")
                .setContent(remoteViews)
                // .setContentIntent(pendingIntent)
                .build();
        manager.notify(ID_NOTIFF, notification);
    }

    public void finishNotify() {
        updateNotify(fileLength);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // intent.setDataAndType(Uri.parse("file://"+fileName),
        // "application/vnd.android.package-archive");
        // 代码安装
        String fileName = appName;
        intent.setDataAndType(Uri.fromFile(new File(fileName)),
                "application/vnd.android.package-archive");
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, 0);
        notification = new NotificationCompat.Builder(this)
                // .setSmallIcon(R.drawable.icon_notifycation)
                // .setTicker("tickerText")
                .setWhen(System.currentTimeMillis())
                // .setContentTitle("ContentTitle")
                // .setContentText("ContentText")
                .setContent(remoteViews).setContentIntent(pendingIntent)
                .build();
        manager.notify(ID_NOTIFF, notification);

    }

    public void updateNotify(final Integer values) {
        int progress = values * 100 / fileLength;
        remoteViews.setTextViewText(R.id.textView_notify, progress + "%");
        remoteViews.setProgressBar(R.id.progressBar_notify, fileLength, values,
                false);
        manager.notify(ID_NOTIFF, notification);
    }

    class DownloadAsyncTast extends AsyncTask<String, Integer, String> {
        private static final int timeoutMillis = 10000;

        @Override
        protected String doInBackground(String... params) {
            if (isCancelled()) {
                return null;
            }
            String str = params[0];
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                // URL url=new URL(anime.getVideoUrl());
                URL url = new URL(str);
                HttpURLConnection conn = (HttpURLConnection) url
                        .openConnection();
                conn.setConnectTimeout(timeoutMillis);
                conn.setReadTimeout(timeoutMillis);
                fileLength = conn.getContentLength();
                if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                    return null;
                }
                is = conn.getInputStream();
                int len = 0;
                String fileName = StringUtil.encodeByMD5(downloadURL);
                File cacheFileDir = HZUtils.createFileDir(
                        getApplicationContext(), DIR_CACHE);
                int loadlenth = 0;
                appName = cacheFileDir + File.separator + fileName + ".apk";
                fos = new FileOutputStream(appName);
                byte[] buffer = new byte[1024];
                int times = 0;
                while (-1 != (len = is.read(buffer))) {
                    fos.write(buffer, 0, len);
                    loadlenth += len;
                    if ((loadlenth * 100 / fileLength) > 7 * times) {
                        times++;
                        publishProgress(loadlenth);
                    }
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if (fos != null) {
                        fos.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return "掌上体检";
        }

        @Override
        protected void onPreExecute() {
            sendNotify();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            updateNotify(values[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            finishNotify();
            ToastUtils.showToast(result + "最新版下载完成");
            // 代码安装
            String fileName = appName;
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(fileName)),
                    "application/vnd.android.package-archive");
            startActivity(intent);
        }

    }

}
