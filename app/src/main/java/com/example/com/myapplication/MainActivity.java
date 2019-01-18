package com.example.com.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.com.myapplication.adapter.MainAdapter;
import com.example.com.myapplication.bean.CareBean;
import com.example.com.myapplication.cardcallback.CartCallback;
import com.example.com.myapplication.contract.CartContract;
import com.example.com.myapplication.presenter.CartPresenter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CartContract.CartView, CartCallback {


    private List<CareBean.Cart> list = new ArrayList<>();
    private XRecyclerView xrevylerview;
    private CheckBox checkAll;
    private TextView sumprice;
    private TextView pay;
    private List<CareBean.Cart> list2;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        list2 = new ArrayList<>();
        CartPresenter cartPresenter = new CartPresenter(this);
        cartPresenter.CartPresenter(new HashMap<String, String>());

    }

    @Override
    public void success(CareBean careBean) {

        Toast.makeText(this, careBean.msg + "", Toast.LENGTH_SHORT).show();
        list.addAll(careBean.data);
        list2 = list;
        mainAdapter = new MainAdapter(list2, this);
        mainAdapter.setCartCallback(this);
        Toast.makeText(this, list2.get(0).sellerName, Toast.LENGTH_SHORT).show();
        xrevylerview.setAdapter(mainAdapter);

    }

    @Override
    public void failure(String msg) {

    }


    private void initView() {
        xrevylerview = (XRecyclerView) findViewById(R.id.xrevylerview);
        checkAll = (CheckBox) findViewById(R.id.checkAll);
        sumprice = (TextView) findViewById(R.id.sumprice);
        pay = (TextView) findViewById(R.id.pay);
        //给xrecyclerview设置显示的布局样式
        xrevylerview.setLayoutManager(new LinearLayoutManager(this));
        //全选的点击
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {//全选按钮选中

                    for (CareBean.Cart c : list2) {
                        c.isChecked = true;
                        for (CareBean.Cart.product cc : c.list) {
                            cc.isProductChecked = true;
                        }
                    }
                } else {//未选中
                    for (CareBean.Cart c : list2) {
                        c.isChecked = false;
                        for (CareBean.Cart.product cc : c.list) {
                            cc.isProductChecked = false;
                        }
                    }
                }
                getTotalPrices();
                mainAdapter.notifyDataSetChanged();

            }
        });
    }

    /**
     * 获取总价
     */
    public void getTotalPrices() {
        double totalprices = 0;
        for (CareBean.Cart c : list2) {

            for (CareBean.Cart.product cc : c.list) {
                if (cc.isProductChecked) {
                    totalprices += cc.price*cc.productnum;
                }
            }
        }
        sumprice.setText(totalprices + "");
    }


    @Override
    public void notifyCart() {
        getTotalPrices();
    }
}
