package com.ihaozuo.plamexam.util;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.util.SparseArray;
import android.view.View;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

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

    public static void setFrescoURL(SimpleDraweeView simpleDraweeView, String URL, String DefaultURL){
        String URIString;
        if (URL == null){
            URIString = DefaultURL;
//            URIString = "res://haozuo.com.healthdoctor.view.custom/"+R.drawable.default_photourl;
        }
        else {
            URIString = URL;
        }

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(URIString))
                .setAutoRotateEnabled(true)
                .build();

        PipelineDraweeController controller = (PipelineDraweeController) Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .build();
        simpleDraweeView.setController(controller);
    }

    public static void setFrescoURL(SimpleDraweeView simpleDraweeView, String URL){
        setFrescoURL(simpleDraweeView,URL,null);
    }
}
