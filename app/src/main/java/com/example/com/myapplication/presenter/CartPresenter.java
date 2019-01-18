package com.example.com.myapplication.presenter;

import com.example.com.myapplication.bean.CareBean;
import com.example.com.myapplication.contract.CartContract;
import com.example.com.myapplication.model.CartModel;
import com.example.com.myapplication.model.ICartmodelCallback;
import com.google.gson.Gson;

import java.util.HashMap;

public class CartPresenter extends CartContract.CartPresenter {
    private CartModel cartModel;
    private CartContract.CartView view;

    public CartPresenter(CartContract.CartView view) {
        this.view = view;
        this.cartModel = new CartModel();
    }

    @Override
    public void CartPresenter(HashMap<String, String> params) {
        cartModel.getCart(params, new ICartmodelCallback() {
            @Override
            public void success(String result) {
                CareBean careBean = new Gson().fromJson(result, CareBean.class);
                view.success(careBean);
            }

            @Override
            public void failure(String msg) {
                view.failure("错误");
            }
        });
    }

}
