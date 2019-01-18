package com.example.com.myapplication.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.myapplication.R;

public class MyView extends LinearLayout {

    private TextView add;
    private TextView minus;
    private TextView num;
    private int numb = 1;

    public MyView(Context context) {
        this(context, null);
    }

    public MyView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.add_minus_layout, this);
        add = inflate.findViewById(R.id.my_add);
        minus = inflate.findViewById(R.id.my_minus);
        num = inflate.findViewById(R.id.my_num);

        add.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                numb++;
                num.setText(numb + "");
                addCallback.getnum(numb);
            }
        });

        minus.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numb == 0) {
                    Toast.makeText(getContext(), "不能再减了", Toast.LENGTH_SHORT).show();
                    return;
                }
                numb--;
                num.setText(numb + "");
                addCallback.getnum(numb);
            }
        });
    }

    private addCallback addCallback;

    public void setAddCallback(MyView.addCallback addCallback) {
        this.addCallback = addCallback;
    }

    public interface addCallback {
        void getnum(int i);
    }
}

