package com.hme.turman.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hme.turman.R;

/**
 * Created by diaoqf on 2016/10/31.
 */

public class FormItemLayout extends LinearLayout {
    private ImageView left_image;
    private TextView title;
    private LinearLayout click_layout;
    private TextView hint;

    private OnItemClickListener onItemClickListener;


    public FormItemLayout(Context context) {
        super(context);

        init(context,null);
    }

    public FormItemLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        View view = LayoutInflater.from(context).inflate(R.layout.wgt_form_item_layout, this, true);
        left_image = (ImageView) findViewById(R.id.left_image);
        title = (TextView) findViewById(R.id.title);
        click_layout = (LinearLayout) findViewById(R.id.click_layout);
        hint = (TextView) findViewById(R.id.hint);

        click_layout.setOnClickListener((v)->{
            if (onItemClickListener != null) {
                onItemClickListener.onClick(view);
            }
        });

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FormItemLayout);

        left_image.setImageDrawable(getResources().getDrawable(a.getResourceId(R.styleable.FormItemLayout_left_img, R.drawable.ic_main_tab_one_nor)));
        title.setText(a.getText(R.styleable.FormItemLayout_title));
        hint.setText(a.getText(R.styleable.FormItemLayout_hint));
        a.recycle();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void onClick(View v);
    }

}
