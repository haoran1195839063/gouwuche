package com.example.com.myapplication.bean;

import java.util.List;

public class CareBean {
    public String msg;
    public String code;
    public List<Cart> data;

    public class Cart {
        public String sellerName;
        public String sellerId;
        public boolean isChecked;
        public List<product> list;

        public class product {
            public boolean isProductChecked;
            public String title;
            public double price;
            public String images;
            public String pid;
            public int pos;
            public int productnum;

        }
    }

}
