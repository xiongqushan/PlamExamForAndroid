package com.ihaozuo.plamexam.util;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by xiongwei on 16/5/17.
 */
public class UIHelper {

    public static void CreateShape(View view, float radius, String fillColor, int alpha) {
        CreateShape(view, radius, 0, "#ffffff", fillColor, alpha);
    }

    //alpha 0~255
    public static void CreateShape(View view, float radius, int strokeWidth,
                                   String strokeColor, String fillColor, int alpha) {

        GradientDrawable gd = new GradientDrawable();
        gd.setCornerRadius(radius);
        gd.setColor(Color.parseColor(fillColor));
        gd.setStroke(strokeWidth, Color.parseColor(strokeColor));
        gd.setAlpha(alpha);
        view.setBackgroundDrawable(gd);

    }

    // TODO  通用 ViewHolder
    public static <T extends View> T getAdapterView(View convertView, int id) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();
        if (viewHolder == null) {
            viewHolder = new SparseArray<View>();
            convertView.setTag(viewHolder);
        }
        View childView = viewHolder.get(id);
        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }
        return (T) childView;
    }

}
