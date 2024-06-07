package com.masterofanalysis.DataModels;

import java.util.List;


public class ProductsModel {

    private String status;
    private String message;
    private List<ProductsModel.Products> data;

    public List<Products> getData() {
        return data;
    }

    public class Products{
        private String product_id;
        private String product_title_tr;
        private String product_title_en;
        private String product_body_tr;
        private String product_body_en;
        private String aciklama_tr;
        private String aciklama_en;
        private String kampanya_bitis_tarihi;
        private String productType;
        private String productPrize;
        private String now_time;

        public String getNow_time() {
            return now_time;
        }

        public String getProductPrize() {
            return productPrize;
        }

        public void setProductPrize(String productPrize) {
            this.productPrize = productPrize;
        }

        public String getProduct_id() {
            return product_id;
        }

        public String getProduct_title_tr() {
            return product_title_tr;
        }

        public String getProduct_title_en() {
            return product_title_en;
        }

        public String getProduct_body_tr() {
            return product_body_tr;
        }

        public String getProduct_body_en() {
            return product_body_en;
        }

        public String getAciklama_tr() {
            return aciklama_tr;
        }

        public String getAciklama_en() {
            return aciklama_en;
        }

        public String getKampanya_bitis_tarihi() {
            return kampanya_bitis_tarihi;
        }

        public String getProductType() {
            return productType;
        }
    }




    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
