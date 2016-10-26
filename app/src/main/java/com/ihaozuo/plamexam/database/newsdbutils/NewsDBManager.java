package com.ihaozuo.plamexam.database.newsdbutils;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.ihaozuo.plamexam.bean.NewsBean;
import com.ihaozuo.plamexam.common.Constants;
import com.ihaozuo.plamexam.framework.SysConfig;
import com.ihaozuo.plamexam.util.HZUtils;

import java.util.ArrayList;
import java.util.List;

public class NewsDBManager {

    public static NewsDBPojo convert(NewsBean bean) {
        String url = SysConfig.NEWS_DETAIL_URL + bean.id;
        String title = bean.title;
        String subtitle;
        if (bean.description == null) {
            subtitle = "from 掌上体检";
        } else {
            subtitle = bean.description;
        }
        String date = bean.time + "";
        NewsDBPojo subsDBpojo = new NewsDBPojo(url, title, subtitle, bean.imgFormat, bean.timeFormat, date);
        return subsDBpojo;
    }

    public static List<NewsDBPojo> queryPojo() {
        List<NewsDBPojo> list = new Select().from(NewsDBPojo.class).execute();
        return list;
    }

    public static boolean hasPojo(NewsDBPojo pojo) {
        NewsDBPojo mNewsDBPojo = new Select().from(NewsDBPojo.class).where("date=?", pojo.getDate()).executeSingle();
        return mNewsDBPojo != null;
    }

    public static void toggleFavor(NewsDBPojo anime2) {
        NewsDBPojo anime = HZUtils.cloneTo(anime2);
        if (hasPojo(anime)) {
            delete(anime);
        } else {
            save(anime);
        }
    }

    public static void save(NewsDBPojo pojo) {
        pojo.save();
    }

    public static void delete(NewsDBPojo pojo) {
        new Delete().from(NewsDBPojo.class).where("date=?", pojo.getDate()).execute();
    }

    public static void initNews() {
        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < 4; i++) {
                NewsDBPojo newsDBPojo = new NewsDBPojo(Constants.LINKURL_HOMEBANNER_DEFAULT, "艾欧尼亚" + i, "",
                        Constants.IMAGEURL_HOMEBANNER_DEFAULT, "time" + i, "time" + i);
                save(newsDBPojo);
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
    }

    public static List<NewsDBPojo> repalceNews(List<NewsBean> bean) {
        List<NewsDBPojo> list = new ArrayList<>();
        ActiveAndroid.beginTransaction();
        new Delete().from(NewsDBPojo.class).execute();
        try {
            int size = bean.size();
            for (int i = 0; i < size; i++) {
                list.add(convert(bean.get(i)));
                save(convert(bean.get(i)));
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }
        return list;
    }

}





