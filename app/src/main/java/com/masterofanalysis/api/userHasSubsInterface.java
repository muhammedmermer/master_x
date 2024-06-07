package com.masterofanalysis.api;

import com.android.billingclient.api.Purchase;

import java.util.List;

public interface userHasSubsInterface {
    void onPurchasesFetched(List<Purchase> purchases);
}
