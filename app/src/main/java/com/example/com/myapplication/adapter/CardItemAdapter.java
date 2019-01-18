package com.example.com.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.com.myapplication.R;
import com.example.com.myapplication.bean.CareBean;
import com.example.com.myapplication.cardcallback.CartCallbackTwo;
import com.example.com.myapplication.widget.MyView;

import java.util.List;

public class CardItemAdapter extends RecyclerView.Adapter<CardItemAdapter.ViewHolder> {
    private List<CareBean.Cart.product> list;
    private Context context;
    private CartCallbackTwo cartCallbackTwo;

    public void setCartCallbackTwo(CartCallbackTwo cartCallbackTwo) {
        this.cartCallbackTwo = cartCallbackTwo;
    }

    public CardItemAdapter(List<CareBean.Cart.product> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_item_layout, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(inflate);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        viewHolder.shopsprice.setText(list.get(i).price + "");
        viewHolder.shopstitle.setText(list.get(i).title);
        String images = list.get(i).images;
        String[] split = images.split("!");
        Glide.with(context).load(split[0]).into(viewHolder.shopsimage);
        viewHolder.checkBox.setChecked(list.get(i).isProductChecked);
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list.get(i).isProductChecked = viewHolder.checkBox.isChecked();
                cartCallbackTwo.notifyCart();
                for (CareBean.Cart.product product : list) {
                    if (!product.isProductChecked) {
                        cartCallbackTwo.setShopHomeisChe(false, product.pos);
                        return;
                    }
                    cartCallbackTwo.setShopHomeisChe(true, product.pos);
                }
            }
        });
        viewHolder.myview.setAddCallback(new MyView.addCallback() {
            @Override
            public void getnum(int ii) {
                list.get(i).productnum = ii;
                cartCallbackTwo.notifyCart();
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView shopsprice;
        private TextView shopstitle;
        private ImageView shopsimage;
        private CheckBox checkBox;
        private MyView myview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopsprice = itemView.findViewById(R.id.shopsprice);
            shopstitle = itemView.findViewById(R.id.shopstitle);
            shopsimage = itemView.findViewById(R.id.shopsimage);
            checkBox = itemView.findViewById(R.id.itemCheckbox);
            myview = itemView.findViewById(R.id.add_minus);
        }
    }
}
