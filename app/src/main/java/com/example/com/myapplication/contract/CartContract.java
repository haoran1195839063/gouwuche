package com.example.com.myapplication.contract;

import com.example.com.myapplication.bean.CareBean;
import com.example.com.myapplication.model.ICartmodelCallback;

import java.util.HashMap;
import java.util.List;

public interface CartContract {
    public abstract class CartPresenter {
        public abstract void CartPresenter(HashMap<String, String> params);
    }

    interface CartModel {
        void getCart(HashMap<String, String> params, ICartmodelCallback callback);
    }

    interface CartView {
        void success(CareBean careBean);

        void failure(String msg);
    }
}
