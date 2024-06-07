package com.masterofanalysis.api;

import com.android.billingclient.api.ProductDetails;

import java.util.List;
import java.util.Map;

public interface class_toFragmentInterface {
    void setPrizesubs(Map<String, String> productsubs,List<ProductDetails> packed);
    void setPrizeinapp(Map<String, String> productinapp,List<ProductDetails> packed);
    void setpurchase(String Productid, String PurchaseToken, String ProductType);
    //void setPrizeinapp(Map<String, String> productinapp,List<ProductDetails> packed);
}
