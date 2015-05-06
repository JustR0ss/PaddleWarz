package com.paddlewarz.game.android;



import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.ClipData.Item;
import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Returns> {

	List<Returns> values;
	
	
    public CustomAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
        
    }
    public CustomAdapter(Context context, int textViewResourceId,List<Returns> values) {
        super(context, textViewResourceId,values);
        this.values=values;
    }

    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
	System.out.println(position);
        View v = convertView;
        
        if (v == null) {

            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.layout_item, null);

        }
            
        	TextView tv1 = (TextView)v.findViewById(R.id.txtv1);
        	tv1.setText(position+1+"");
        	TextView tv2 = (TextView)v.findViewById(R.id.txtv2);
        	tv2.setText(values.get(position).getName());
        	TextView tv3 = (TextView)v.findViewById(R.id.txtv3);
        	tv3.setText(values.get(position).getDate());
        	TextView tv4 = (TextView)v.findViewById(R.id.txtv4);
        	tv4.setText(values.get(position).getScore()+"");
        	
        
        return v;

    }
}

