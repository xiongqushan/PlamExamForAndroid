package com.ihaozuo.plamexam.bean;

import java.util.List;

/**
 * Created by hzguest3 on 2016/10/12.
 */
public class testBean {
    public int ID;
    public String Name;
    public AAABean AAA;

    public static class AAABean {
        public int ID;
        public List<String> Arrs;
    }
}
