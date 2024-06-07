package com.masterofanalysis.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.android.billingclient.api.ProductDetails;
import com.masterofanalysis.Adapter.ProductsAdapter;
import com.masterofanalysis.AppPurchase.PurchaseClass;
import com.masterofanalysis.Constants;
import com.masterofanalysis.DataModels.GetResponseModel;
import com.masterofanalysis.DataModels.ProductsModel;
import com.masterofanalysis.Function;
import com.masterofanalysis.R;
import com.masterofanalysis.api.ApiClient;
import com.masterofanalysis.api.ApiService;
import com.masterofanalysis.api.class_toFragmentInterface;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class fragment_odeme extends Fragment implements class_toFragmentInterface {
    View view;
    private FirebaseAuth auth;
    private RecyclerView rvm;
    private ProductsAdapter productsAdapter;
    private List<ProductsModel.Products> productsList;
    ArrayList<String> inapp;
    ArrayList<String> subs;
    PurchaseClass purchaseClass;
    List<ProductDetails> inapp_packed = new ArrayList<>();
    List<ProductDetails> subs_packed = new ArrayList<>();
    Map<String, String> produc_map;
    private Context mContext;
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }
    public void checkIfFragmentAttached(fragment_kredili.ContextOperation operation) {
        if (isAdded() && getContext() != null) {
            operation.performOperation(requireContext());
        }
    }
    public interface ContextOperation {
        void performOperation(Context context);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment__odeme , container , false);
        if (mContext != null) {
            rvm = view.findViewById(R.id.rvm);
            int spanCount = 2;
            GridLayoutManager layoutManager = new GridLayoutManager(mContext, spanCount);
            rvm.setLayoutManager(layoutManager);
            rvm.setHasFixedSize(true);
            auth = FirebaseAuth.getInstance();
            productsList = new ArrayList<>();
            inapp = new ArrayList<>();
            subs = new ArrayList<>();
            purchaseClass = new PurchaseClass(requireActivity(), mContext);
            purchaseClass.setListener(fragment_odeme.this);
            Function.showLoading(getActivity(), mContext);

            productsAdapter = new ProductsAdapter(mContext, productsList);
            rvm.setAdapter(productsAdapter);

            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ProductsModel> call = apiService.GetProducts(
                    "get_products",
                    auth.getUid(),
                    Function.getTimezone(),
                    Constants.API_KEY);
            call.enqueue(new Callback<ProductsModel>() {
                @Override
                public void onResponse(Call<ProductsModel> call, Response<ProductsModel> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("success")) {
                                ProductsModel model = response.body();
                                for (ProductsModel.Products m : model.getData()) {
                                    if (m.getProductType().equalsIgnoreCase("INAPP")) {
                                        inapp.add(m.getProduct_id());
                                    } else if (m.getProductType().equalsIgnoreCase("SUBS")) {
                                        subs.add(m.getProduct_id());
                                    }
                                }
                                productsList = model.getData();
                                produc_map = new HashMap<>();
                                for (int i = 0; i < productsList.size(); i++) {
                                    produc_map.put(productsList.get(i).getProduct_id(), productsList.get(i).getProductType());
                                }
                                setadapter();
                                purchaseClass.startPurchase(inapp, subs, produc_map);
                                Function.dismissLoading(getActivity());
                            } else {
                                Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                                Function.dismissLoading(getActivity());
                            }
                        } else {
                            Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                            Function.dismissLoading(getActivity());
                        }
                    } catch (Exception e) {
                        Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                    }
                }

                @Override
                public void onFailure(Call<ProductsModel> call, Throwable t) {
                    Function.showToast(mContext, mContext.getString(R.string.kayit_hatasi), 1);
                }
            });
        }
        return view;
    }
    private void setadapter(){
        productsAdapter = new ProductsAdapter(mContext, productsList);
        rvm.setAdapter(productsAdapter);
        productsAdapter.setOnItemClickListener(position -> {
            //Log.e("PRODUCTMAP",produc_map.get(productsList.get(position).getProduct_id())+".");
            if (productsList.get(position).getProductPrize()!=null){
                Log.e("QQQQQQQ1",productsList.get(position).getProductType());
                if (productsList.get(position).getProductType().equalsIgnoreCase("INAPP")){
                    Log.e("QQQQQQQ2",productsList.get(position).getProductType());
                    for (int i = 0; i < inapp_packed.size(); i++) {
                        Log.e("QQQQQQQ3",productsList.get(position).getProductType());
                        if (inapp_packed.get(i).getProductId().equalsIgnoreCase(productsList.get(position).getProduct_id())){
                            Log.e("QQQQQQQ4",productsList.get(position).getProductType());
                            purchaseClass.startlaunchBillingFlow(inapp_packed.get(i));
                        }
                    }
                } else if (productsList.get(position).getProductType().equalsIgnoreCase("SUBS")){
                    Log.e("WWWWWWW1",productsList.get(position).getProductType());
                    for (int i = 0; i < subs_packed.size(); i++) {
                        Log.e("WWWWWWW2",productsList.get(position).getProductType());
                        if (subs_packed.get(i).getProductId().equalsIgnoreCase(productsList.get(position).getProduct_id())){
                            Log.e("WWWWWWW3",productsList.get(position).getProductType());
                            purchaseClass.startlaunchBillingFlow(subs_packed.get(i));
                        }
                    }
                }
            }else{
                Function.showToast(mContext, mContext.getString(R.string.satin_alim_yok), 1);
            }
            //Toast.makeText(mContext, "tıklanan : "+position+" : "+productsList.get(position).getProduct_id(), Toast.LENGTH_SHORT).show();
        });
    }
    private void rui(){
        requireActivity().runOnUiThread(() -> productsAdapter.notifyDataSetChanged());
    }
    public void setPrizeinapp(Map<String, String> productinapp,List<ProductDetails> packed){
        for (int i = 0; i<productsList.size(); i++){
            String productId = productsList.get(i).getProduct_id();
            if (productinapp.containsKey(productId)){
                String productPrice = productinapp.get(productId);
                productsList.get(i).setProductPrize(productPrice);
            }
        }
        inapp_packed = packed;
        rui();
    }
    public void setPrizesubs(Map<String, String> productsubs,List<ProductDetails> packed){
        Log.e("gfgre","EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        for (int i = 0 ; i< productsList.size();i++){
            String productId = productsList.get(i).getProduct_id();
            if (productsubs.containsKey(productId)){
                String productPrice = productsubs.get(productId);
                productsList.get(i).setProductPrize(productPrice);
            }
        }
        subs_packed = packed;
        rui();
    }
    @Override
    public void onDestroy() {
        Log.e("DESTROY","DESTROY");
        super.onDestroy();
        purchaseClass.destroyBillingClient();
    }
    public void setpurchase(String Productid, String PurchaseToken, String ProductType){
        Log.e("SETPURCHASE-1",Productid);
        String productPrize = "";
        for (int i = 0; i<productsList.size();i++){
            if (productsList.get(i).getProduct_id().equalsIgnoreCase(Productid)){
                productPrize = productsList.get(i).getProductPrize();
                break;
            }
        }
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<GetResponseModel> callPost = apiService.Set_Purchase(
                "set_purchase",
                Constants.API_KEY,
                auth.getUid(),
                Productid,
                PurchaseToken,
                ProductType,
                productPrize
        );
        Log.e("SETPURCHASE0",Productid+"ergerg");
        callPost.enqueue(new Callback<GetResponseModel>() {
            @Override
            public void onResponse(Call<GetResponseModel> call, Response<GetResponseModel> response) {
                Log.e("SETPURCHASE1",response.toString()+"");
                try{
                    Log.e("SETPURCHASE2",response.toString()+"");
                    if (response.isSuccessful()) {
                        if (response.body().getResult().equalsIgnoreCase("success")) {
                            checkIfFragmentAttached(new fragment_kredili.ContextOperation() {
                                @Override
                                public void performOperation(Context context) {
                                    final Dialog dialog = new Dialog(context, R.style.Theme_BottomNavigation);
                                    dialog.setContentView(R.layout.custom_dialog);
                                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    Button btn = dialog.findViewById(R.id.itsokey);
                                    TextView referencebaslik = dialog.findViewById(R.id.referencebaslik);
                                    TextView referenceaciklama = dialog.findViewById(R.id.referenceaciklama);
                                    LottieAnimationView anim = dialog.findViewById(R.id.profildetay);
                                    anim.setAnimation("successpurchase.json");
                                    referencebaslik.setText(context.getString(R.string.successpurchase));
                                    referenceaciklama.setText(context.getString(R.string.successpurchasebody));
                                    btn.setOnClickListener(view2 -> {
                                        dialog.dismiss();
                                    });
                                    dialog.show();
                                }
                            });
                        }else{
                            checkIfFragmentAttached(new fragment_kredili.ContextOperation() {
                                @Override
                                public void performOperation(Context context) {
                                    final Dialog dialog = new Dialog(context, R.style.Theme_BottomNavigation);
                                    dialog.setContentView(R.layout.custom_dialog);
                                    Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                    Button btn = dialog.findViewById(R.id.itsokey);
                                    TextView referencebaslik = dialog.findViewById(R.id.referencebaslik);
                                    TextView referenceaciklama = dialog.findViewById(R.id.referenceaciklama);
                                    LottieAnimationView anim = dialog.findViewById(R.id.profildetay);
                                    anim.setAnimation("noseckin.json");
                                    referencebaslik.setText(context.getString(R.string.errorpurchase));
                                    referenceaciklama.setText(context.getString(R.string.errorpurchasebody));
                                    btn.setOnClickListener(view2 -> {
                                        dialog.dismiss();
                                    });
                                    dialog.show();
                                }
                            });
                        }
                    } else {
                        Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                    }
                    Function.dismissLoading(getActivity());
                }catch (Exception e){
                    Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                    Function.dismissLoading(getActivity());
                }
            }
            @Override
            public void onFailure(Call<GetResponseModel> call, Throwable t) {
                Log.e("SETPURCHASEFAİLURE",call+"");
                Function.showToast(mContext,mContext.getString(R.string.kayit_hatasi),1);
                Function.dismissLoading(getActivity());
            }
        });
    }

}