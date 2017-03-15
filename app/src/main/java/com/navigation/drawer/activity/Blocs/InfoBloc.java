package com.navigation.drawer.activity.Blocs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.navigation.drawer.activity.R;

import java.util.Vector;

/**
 * Created by Pro on 12/03/2017.
 */

public class InfoBloc extends LinearLayout {

    Context context = null ;
    final float scale;

    public InfoBloc(Context context,String key,Vector<String> value) {
        super(context);
        this.context = context ;
        scale = context.getResources().getDisplayMetrics().density;
        setBackground(getResources().getDrawable(R.drawable.edit_text));
        int height = (int)((40 * scale + 0.5f)*value.size()+1);
        setOrientation(VERTICAL);
        LinearLayout.LayoutParams ilParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ilParams.topMargin = 20 ;
        setLayoutParams(ilParams);
        this.setMinimumHeight(height);
        addTextView(key,10,true);
        for(int i=0;i<value.size();i++){
            Log.d("haaaInfo ---->",value.get(i));
            addTextView(value.get(i),25,false);
        }
    }
    public void addTextView(String content,int marginleft,boolean isTitle){
        TextView currentTV = new TextView(context);
        int height = (int)(40 * scale + 0.5f);
        if(isTitle)
            currentTV.setTypeface(null, Typeface.BOLD);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        this.addView(currentTV);
        currentTV.setMinHeight(height);
        currentTV.setTextColor(Color.BLACK);
        layoutParams.gravity = Gravity.CENTER_VERTICAL ;
        int pixel =  (int)(marginleft * scale + 0.5f);
        layoutParams.setMargins(pixel,0,0,0);
        currentTV.setLayoutParams(layoutParams);
        currentTV.setText(content);

    }
}
