package com.questappx.tshirtmaker.Billing;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.billingclient.api.AcknowledgePurchaseParams;
import com.android.billingclient.api.AcknowledgePurchaseResponseListener;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.ProductDetailsResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.collect.ImmutableList;
import com.questappx.tshirtmaker.R;

import java.io.IOException;
import java.util.List;

public class InApp {
    BillingClient billingClient;

    public static boolean isPaid = false;
    public static final String BillingPreferences = "billing_prefs";

    private static final String REMOVE_AD_PRODUCT_ID = "adsremove";
    private static final String BASE64KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA6mrs7Mp5G5lHCJP7deOae68VdDFtwgsbp6s6sTzMr7n1GlS9CXhnnz0Rw2H5AaquGbEcp6xbrHdPs6/L6qNefXaZUT/+vaZhI8d7tUbTqFIJzekbe20DLBjmfGmLY/WBZYomnNzKPoiXC/vKHejDaqzyrxEfvfKsosUasB2lav5kR27ro4cWpLkylc5TJIdeD9/RMv0pqD+m3GCSrm1nW0VCO7kdtfQNg9HXmCjjoBjrv8DPsUSAr2ZUGBMfdrSLV2CJfIczv2llcB6zbd/1VKugn1k+XdYGEXjxOp+WECKbu/sInBpjvJgD7lK++Oq6ZaChutLznguAYTlTDHMxSQIDAQAB";

    Context context;
    public InApp(Context context) {
        this.context = context;
        init();
    }
    private void init()
    {
        isPaid = getPurchaseStatus();
        billingWorking();
//        purchaseConsumeProduct();
    }

    private void billingWorking() {
        billingClient = BillingClient.newBuilder(((Activity)context))
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();
    }


    private PurchasesUpdatedListener purchasesUpdatedListener = new PurchasesUpdatedListener() {
        @Override
        public void onPurchasesUpdated(BillingResult billingResult, List<Purchase> purchases) {
//            Toast.makeText(context, "Purchase updated", Toast.LENGTH_SHORT).show();
            if (billingResult.getResponseCode() ==  BillingClient.BillingResponseCode.OK && purchases != null) {
                for(Purchase purchase : purchases)
                {
                    handlePurchase(purchase);
                }
            }
            else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.BILLING_UNAVAILABLE)
            {
                Toast.makeText(((Activity)context), "BILLING_UNAVAILABLE", Toast.LENGTH_SHORT).show();
            }
            else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED)
            {
                Toast.makeText(((Activity)context), "ITEM_ALREADY_OWNED", Toast.LENGTH_SHORT).show();
            }
            else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.DEVELOPER_ERROR)
            {
                Toast.makeText(((Activity)context), "DEVELOPER_ERROR", Toast.LENGTH_SHORT).show();
            }
            else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.SERVICE_DISCONNECTED)
            {
                Toast.makeText(((Activity)context), "SERVICE_DISCONNECTED", Toast.LENGTH_SHORT).show();
            }
            else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.FEATURE_NOT_SUPPORTED)
            {
                Toast.makeText(((Activity)context), "FEATURE_NOT_SUPPORTED", Toast.LENGTH_SHORT).show();
            }
            else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.NETWORK_ERROR)
            {
                Toast.makeText(((Activity)context), "NETWORK_ERROR", Toast.LENGTH_SHORT).show();
            }
            else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED)
            {
                Toast.makeText(((Activity)context), "USER_CANCELED", Toast.LENGTH_SHORT).show();
            }
            else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_NOT_OWNED)
            {
                Toast.makeText(((Activity)context), "ITEM_NOT_OWNED", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(((Activity)context), ""+billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(((Activity)context), ""+billingResult.getDebugMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void handlePurchase(Purchase purchase) {
        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = new ConsumeResponseListener() {
            @Override
            public void onConsumeResponse(BillingResult billingResult, String purchaseToken) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    // Handle the success of the consume operation.
                }
            }
        };

        billingClient.consumeAsync(consumeParams, listener);

        //Verify
        if(purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED)
        {
            if(!verifyValidSignature(purchase.getOriginalJson(), purchase.getSignature()));
            {
                Toast.makeText(((Activity)context), "Error : Invalid Purchase", Toast.LENGTH_SHORT).show();
//                return;
            }
            if(!purchase.isAcknowledged())
            {
                AcknowledgePurchaseParams acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder().
                        setPurchaseToken(purchase.getPurchaseToken()).build();
                billingClient.acknowledgePurchase(acknowledgePurchaseParams, acknowledgePurchaseResponseListener);
                isPaid = true;
                savePurchaseStatus(isPaid);
                Toast.makeText(((Activity)context), "Purchased", Toast.LENGTH_SHORT).show();
            }
            else {
                isPaid = true;
                savePurchaseStatus(isPaid);
                Toast.makeText(((Activity)context), "Already Purchased", Toast.LENGTH_SHORT).show();
            }
        }
        else if(purchase.getPurchaseState() == Purchase.PurchaseState.UNSPECIFIED_STATE)
        {
            Toast.makeText(((Activity)context), "UNSPECIFIED_STATE", Toast.LENGTH_SHORT).show();
        }
        else if(purchase.getPurchaseState() == Purchase.PurchaseState.PENDING)
        {
            Toast.makeText(((Activity)context), "PENDING", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verifyValidSignature(String originalJson, String signature) {
        try {
            String base64Key = BASE64KEY;
            return Verify.verifyPurchase(base64Key, originalJson, signature);
        }catch (IOException e)
        {
            return false;
        }
    }

    AcknowledgePurchaseResponseListener acknowledgePurchaseResponseListener  = new AcknowledgePurchaseResponseListener() {
        @Override
        public void onAcknowledgePurchaseResponse(@NonNull BillingResult billingResult) {
            Toast.makeText(((Activity)context), "Acknowledged", Toast.LENGTH_SHORT).show();
        }
    };

    public void startPurchase() {
        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingServiceDisconnected() {

            }

            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK)
                {
                    showAvailableProducts();
                }
            }
        });
    }

    private void savePurchaseStatus(boolean isPaid) {
        SharedPreferences preferences = context.getSharedPreferences(BillingPreferences, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isPaid", isPaid);
        editor.apply();
    }

    private void showAvailableProducts() {
        QueryProductDetailsParams queryProductDetailsParams =
                QueryProductDetailsParams.newBuilder()
                        .setProductList(
                                ImmutableList.of(
                                        QueryProductDetailsParams.Product.newBuilder()
                                                .setProductId(REMOVE_AD_PRODUCT_ID)
                                                .setProductType(BillingClient.ProductType.INAPP)
                                                .build()))
                        .build();

        billingClient.queryProductDetailsAsync(
                queryProductDetailsParams,
                new ProductDetailsResponseListener() {
                    public void onProductDetailsResponse(BillingResult billingResult,
                                                         List<ProductDetails> productDetailsList) {
                        for(ProductDetails productDetails : productDetailsList)
                        {
                            launchPurchaseFlow(productDetails);
                        }
                    }
                }
        );
    }

    private void launchPurchaseFlow(ProductDetails productDetails) {
        ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
                ImmutableList.of(
                        BillingFlowParams.ProductDetailsParams.newBuilder()
                                // retrieve a value for "productDetails" by calling queryProductDetailsAsync()
                                .setProductDetails(productDetails)
                                .build()
                );
        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                .setProductDetailsParamsList(productDetailsParamsList)
                .build();

        BillingResult billingResult = billingClient.launchBillingFlow(((Activity) context), billingFlowParams);
    }


    public boolean getPurchaseStatus() {
        SharedPreferences preferences = context.getSharedPreferences(BillingPreferences, Context.MODE_PRIVATE);
        return preferences.getBoolean("isPaid", false);
    }

    public void showDialog(Context context1)
    {
        Dialog dialog = new Dialog(context1);
        dialog.setContentView(R.layout.adsfreeversion_dialog);
        dialog.show();
        ImageView buttonCancel = dialog.findViewById(R.id.dialogBtnCancel);
        ImageView buttonOk = dialog.findViewById(R.id.dialogBtnOk);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);



        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startPurchase();
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

    }

}
