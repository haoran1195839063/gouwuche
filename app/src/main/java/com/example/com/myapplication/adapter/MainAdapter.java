package com.example.com.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.com.myapplication.R;
import com.example.com.myapplication.bean.CareBean;
import com.example.com.myapplication.cardcallback.CartCallback;
import com.example.com.myapplication.cardcallback.CartCallbackTwo;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> implements CartCallbackTwo {
    private List<CareBean.Cart> list;
    private Context context;
    private CartCallback cartCallback;

    public void setCartCallback(CartCallback cartCallback) {
        this.cartCallback = cartCallback;
    }

    public MainAdapter(List<CareBean.Cart> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {

        for (CareBean.Cart cart : list) {
            for (CareBean.Cart.product product : cart.list) {
                product.pos = i;
            }
        }

        viewHolder.shopsname.setText(list.get(i).sellerName);
        viewHolder.recy.setLayoutManager(new LinearLayoutManager(context));
        CardItemAdapter cardItemAdapter = new CardItemAdapter(list.get(i).list, context);
        cardItemAdapter.setCartCallbackTwo(this);
        viewHolder.recy.setAdapter(cardItemAdapter);

        Log.d("qqqqqqqqqq", list.get(0).sellerName);
        viewHolder.checkBox.setChecked(list.get(i).isChecked);

        //商店内全选
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(i).isChecked = viewHolder.checkBox.isChecked();
                for (CareBean.Cart.product product : list.get(i).list) {
                    product.isProductChecked = list.get(i).isChecked;
                }
                notifyDataSetChanged();
                if (cartCallback != null) {
                    cartCallback.notifyCart();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    @Override
    public void setShopHomeisChe(boolean ische, int pos) {
        list.get(pos - 1).isChecked = ische;
        notifyDataSetChanged();
    }

    //调用刷新价格的代码
    @Override
    public void notifyCart() {
        if (cartCallback != null) {
            cartCallback.notifyCart();
        }
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView shopsname;
        private RecyclerView recy;
        private CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopsname = itemView.findViewById(R.id.shopsname);
            recy = itemView.findViewById(R.id.cartRecy);
            checkBox = itemView.findViewById(R.id.mycheckbox);

        }
    }
}
