package com.masterofanalysis.AppPurchase;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.QueryPurchasesParams;
import com.masterofanalysis.Function;
import com.masterofanalysis.api.class_toFragmentInterface;
import com.masterofanalysis.api.userHasSubsInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PurchaseClass implements PurchasesUpdatedListener {
    private static BillingClient billingClient;
    private final Activity activity;
    private final Context context;
    private Map<String, String> product_is;
    private class_toFragmentInterface mCallback;
    public void setListener(class_toFragmentInterface callBack){
        Log.e("LOGE","setListener");
        this.mCallback=callBack;
    }
    public PurchaseClass(Activity activity, Context context) {
        billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(this).build();
        this.activity = activity;
        this.context = context;
        Log.e("LOGE","PurchaseModel");
    }
    public void startlaunchBillingFlow(ProductDetails a){
        String value = product_is.get(a.getProductId());
        ArrayList<BillingFlowParams.ProductDetailsParams> productList = new ArrayList<>();
        if (value.equalsIgnoreCase("INAPP")){
            productList.add(
                    BillingFlowParams.ProductDetailsParams.newBuilder()
                            .setProductDetails(a)
                            .build());
        }else{
            for (ProductDetails.SubscriptionOfferDetails q : a.getSubscriptionOfferDetails()){
                productList.add(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(a)
                                .setOfferToken(q.getOfferToken())
                                .build());
            }
        }
        try {
            BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(productList)
                    .build();
            billingClient.launchBillingFlow(this.activity, billingFlowParams);
        }catch (Exception e){
            Log.e("HATA","startlaunchBillingFlow  : " + e);
        }
    }
    public void startPurchase(List<String> inapp, List<String> subs,Map<String, String> product_is) {
        this.product_is = product_is;
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    if (billingClient.isReady()) {
                        Log.e("LOGE", "satınalımhazır");
                        setUpBillingsubs(subs);
                        setUpBillinginapp(inapp);
                    }
                } else {
                    Log.e("LOGE", "onBillingSetupFinished : " + billingResult.toString());
                    //Toast.makeText(getContext(), ""+billingResult.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                Log.e("LOGE", "onBillingServiceDisconnected");
            }
        });
    }
    private void setUpBillinginapp(List<String> skuList) {
        Log.e("LOGE","setUpBillinginapp");
        Map<String, String> productinapp = new HashMap<>();
        ArrayList<QueryProductDetailsParams.Product> productList = new ArrayList<>();
        for(String sku : skuList) {
            Log.e("LOGEskuinapp",sku);
            productList.add(
                    QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(sku)
                            .setProductType(BillingClient.ProductType.INAPP)
                            .build()
            );
        }
        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build();
        billingClient.queryProductDetailsAsync(params, (billingResult, productDetailsList) -> {
            try {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    if (productDetailsList.size() > 0){
                        for (ProductDetails productDetails : productDetailsList) {
                            String productPrice = productDetails.getOneTimePurchaseOfferDetails().getFormattedPrice();
                            String productID = productDetails.getProductId();
                            productinapp.put(productID, productPrice);
                        }
                        mCallback.setPrizeinapp(productinapp,productDetailsList);
                    }
                }
            }catch (Exception e){
                Log.e("LOGE",e.toString());
                Log.e("Exception",e.toString());
            }
        });
    }
    private void setUpBillingsubs(List<String> skuList) {
        Log.e("LOGE","setUpBillingsubs");
        Map<String, String> productsubs = new HashMap<>();
        ArrayList<QueryProductDetailsParams.Product> productList = new ArrayList<>();
        for(String sku : skuList) {
            Log.e("LOGEskusubs",sku);
            productList.add(
                    QueryProductDetailsParams.Product.newBuilder()
                            .setProductId(sku)
                            .setProductType(BillingClient.ProductType.SUBS)
                            .build()
            );
        }
        QueryProductDetailsParams params = QueryProductDetailsParams.newBuilder()
                .setProductList(productList)
                .build();
        billingClient.queryProductDetailsAsync(params, (billingResult, productDetailsList) -> {
            try {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    Log.e("LOGEgetResponsesubs",productDetailsList.size() +"");
                    if (productDetailsList.size() > 0){
                        for (ProductDetails productDetails : productDetailsList) {
                            String productPrice = productDetails.getSubscriptionOfferDetails().get(0).getPricingPhases().getPricingPhaseList().get(0).getFormattedPrice();
                            String productID = productDetails.getProductId();
                            productsubs.put(productID, productPrice);
                        }
                        mCallback.setPrizesubs(productsubs,productDetailsList);
                    }
                }
            }catch (Exception e){
                Log.e("LOGE",e.toString());
            }
        });
    }
    public void isUserHasSubscription(userHasSubsInterface listener) {
        List<Purchase> qlist = new ArrayList<>();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {
                Log.e("LOGE","onBillingServiceDisconnected");
                listener.onPurchasesFetched(new ArrayList<>());
            }
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                billingClient.queryPurchasesAsync(QueryPurchasesParams.newBuilder().setProductType(BillingClient.ProductType.SUBS).build(), (billingResult12, list) -> {
                    Log.e("LOGE","onBillingSetupFinished");
                    if (billingResult12.getResponseCode() == BillingClient.BillingResponseCode.OK && !list.isEmpty()) {
                        Log.e("LOGE","ifin içinde");
                        for (int i = 0; i < list.size(); i++) {
                            Log.e("LOGE","for içi");
                            Purchase pc = list.get(i);
                            Log.e("billingpc", list.get(i).getProducts()+"");
                            qlist.add(pc);
                        }
                        listener.onPurchasesFetched(qlist);
                    }else{
                        Log.e("LOGE","else");
                        listener.onPurchasesFetched(new ArrayList<>());
                    }
                });
            }
        });
        Log.e("LOGEUSERHAS",qlist.size()+"");
    }
    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        Function.showLoading(activity,context);
        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list !=null ){
            for(Purchase purchase:list){
                handlePurchase(purchase);
            }
        }else{
            Function.dismissLoading(activity);
        }
    }
    private void handlePurchase(final Purchase purchase) {
        if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED && purchase.getProducts().size() >0) {
            List<String> purchasedProducts = purchase.getProducts();
            for (String product : purchasedProducts) {
                if (product_is.containsKey(product)) {
                    String value = product_is.get(product);
                    if (value.equalsIgnoreCase("INAPP")){
                        ConsumeParams consumeParams =
                                ConsumeParams.newBuilder()
                                        .setPurchaseToken(purchase.getPurchaseToken())
                                        .build();
                        billingClient.consumeAsync(consumeParams, (billingResult, purchaseToken) -> {
                            mCallback.setpurchase(product,purchaseToken,value);
                        });
                    }else{
                        if(!purchase.isAcknowledged()){
                            AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                                    .setPurchaseToken(purchase.getPurchaseToken())
                                    .build();
                            billingClient.acknowledgePurchase(acknowledgePurchaseParams, billingResult -> {
                                mCallback.setpurchase(product,purchase.getPurchaseToken(),value);
                            });
                        }
                    }
                }else{
                    Function.dismissLoading(activity);
                }
            }
        }
    }
    public void destroyBillingClient() {
        if (billingClient != null) {
            billingClient.endConnection();
            billingClient = null;
        }
        Log.e("destroyBillingClient","son durum");
    }
}
