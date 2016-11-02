package com.hme.turman.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hme.turman.R;

/**
 * Created by diaoqf on 2016/11/2.
 */

public class SimpleItemLayout extends LinearLayout {
    TextView title;
    TextView content;

    public SimpleItemLayout(Context context) {
        super(context);
        init(context, null);
    }

    public SimpleItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.wgt_simple_item_layout, this, true);

        title = (TextView) view.findViewById(R.id.title);
        content = (TextView) view.findViewById(R.id.content);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SimpleItemLayout);
        title.setText(a.getText(R.styleable.SimpleItemLayout_title));
        content.setText(a.getText(R.styleable.SimpleItemLayout_content));

        a.recycle();
    }

    public void setTitle(String str) {
        title.setText(str);
    }

    public void setTitle(int strId) {
        title.setText(getResources().getString(strId));
    }

    public void setContent(String str) {
        content.setText(str);
    }

    public void setContent(int strId) {
        content.setText(getResources().getString(strId));
    }
}
