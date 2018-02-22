package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.ProductImagePreviewAdapter;
import com.qemasoft.alhabibshop.app.controller.ProductOptionsAdapter;
import com.qemasoft.alhabibshop.app.model.Options;
import com.qemasoft.alhabibshop.app.model.Product;
import com.qemasoft.alhabibshop.app.model.ProductOptionValueItem;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.qemasoft.alhabibshop.app.AppConstants.FORCE_CANCELED;
import static com.qemasoft.alhabibshop.app.AppConstants.PRODUCT_DETAIL_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.findStringByName;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragProductDetail extends MyBaseFragment implements View.OnClickListener {
    
    private List<Options> optionsList;
    ProductOptionsAdapter.ProductOptionsAdapterInterface adapterInterface
            = new ProductOptionsAdapter.ProductOptionsAdapterInterface() {
        @Override
        public void OnItemClicked(int adapterPosition, String val) {
            utils.printLog("Interface Bridge Working! Id = " + adapterPosition
                    + "\nItem Value = " + val);
            AppConstants.optionsList.add(new ProductOptionValueItem(optionsList.get(adapterPosition)
                    .getProductOptionId(), val));
            for (int i = 0; i < AppConstants.optionsList.size(); i++) {
                utils.printLog("Option = " + AppConstants.optionsList.get(i)
                        .getOptionValueId() + " value = " + AppConstants.optionsList.get(i).getName());
                utils.printLog(" List Size = " + AppConstants.optionsList.size());
            }
            // Do whatever you wants to do with this data that is coming from your adapter
        }
    };
    
    private ProgressBar pb;
    private ImageView previewIV, shareIV;
    
    private Product product;
    private TextView productTitleTV, productPriceTV, productSpecialPriceTV,
            productDiscountTV, stockStatusTV, productDescriptionTV;
    
    private Button addToCartBtn;
    
    private RecyclerView mRecyclerViewOptions;
    private LinearLayout specialPriceLayout, discountLayout,
            stockLayout, availableOptionsLayout;
    
    public FragProductDetail() {
        // Required empty public constructor
    }
    
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_product_detail, container, false);
        initViews(view);
        initUtils();
        
        
        Bundle bundle = getArguments();
        if (bundle != null) {
            String id = getArguments().getString("id");
            requestData(id);
        }
        
        addToCartBtn.setOnClickListener(this);
        shareIV.setOnClickListener(this);
        
        
        return view;
    }
    
    private void initViews(View view) {
        
        productTitleTV = view.findViewById(R.id.product_title_tv);
        pb = view.findViewById(R.id.progress_bar);
        previewIV = view.findViewById(R.id.image_view);
        shareIV = view.findViewById(R.id.share_iv);
        
        specialPriceLayout = view.findViewById(R.id.special_price_layout);
        discountLayout = view.findViewById(R.id.disc_layout);
        stockLayout = view.findViewById(R.id.stock_layout);
        
        productDescriptionTV = view.findViewById(R.id.product_desc_tv);
        productPriceTV = view.findViewById(R.id.product_price_tv);
        productSpecialPriceTV = view.findViewById(R.id.product_special_price_tv);
        productDiscountTV = view.findViewById(R.id.product_disc_tv);
        stockStatusTV = view.findViewById(R.id.stock_status_tv);
        
        availableOptionsLayout = view.findViewById(R.id.options_available_layout);
        addToCartBtn = view.findViewById(R.id.add_to_cart_btn);
        
        mRecyclerView = view.findViewById(R.id.product_img_recycler_view);
        mRecyclerViewOptions = view.findViewById(R.id.product_options_recycler_view);
        
    }
    
    private void requestData(String id) {
        
        AppConstants.setMidFixApi("getProduct/product_id/" + id);
        
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), FetchData.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, PRODUCT_DETAIL_REQUEST_CODE);
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == PRODUCT_DETAIL_REQUEST_CODE) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    utils.printLog("ResponseInFragProDetail", response.toString());
                    JSONObject proObj = response.optJSONObject("product");
                    
                    product = new Product(proObj.optString("id")
                            , proObj.optString("name")
                            , proObj.optString("model")
                            , proObj.optString("price")
                            , proObj.optString("special")
                            , proObj.optString("quantity")
                            , proObj.optString("description")
                            , proObj.optString("stock_status")
                            , proObj.optString("manufacturer")
                            , proObj.optString("disc")
                            , proObj.optString("disc_percent")
                            , proObj.optString("date_added")
                            , proObj.optString("rating")
                            , proObj.optString("review_total")
                            , proObj.optString("href")
                    );
                    
                    JSONArray images = proObj.optJSONArray("images");
                    List<String> productImages = new ArrayList<>();
                    if (images != null)
                        for (int j = 0; j < images.length(); j++) {
                            JSONObject img = images.getJSONObject(j);
                            productImages.add(img.optString("image"));
                        }
                    if (!productImages.isEmpty() && productImages.size() > 1) {
                        
                        RecyclerView.LayoutManager mLayoutManager =
                                new LinearLayoutManager(getActivity()
                                        , LinearLayoutManager.HORIZONTAL, false);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(
                                new ProductImagePreviewAdapter(previewIV, pb, productImages));
                    } else {
                        mRecyclerView.setVisibility(View.GONE);
                        String imgPath = proObj.optString("image");
                        if (!imgPath.isEmpty())
                            Picasso.with(context)
                                    .load(imgPath)
                                    .noFade()
                                    .into(previewIV, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            pb.setVisibility(View.GONE);
                                        }
                                        
                                        @Override
                                        public void onError() {
                                            pb.setVisibility(View.GONE);
                                            previewIV.setImageResource(R.drawable.ic_close_black);
                                        }
                                    });
                    }
                    /// end
                    productTitleTV.setText(product.getName());
                    
                    productDescriptionTV.setText(product.getProductDescription());
                    productPriceTV.setText(symbol.concat("").concat(product.getPrice()));
                    productSpecialPriceTV.setText(symbol.concat("").concat(product.getSpacialPrice()));
                    
                    if (!product.getSpacialPrice().isEmpty()) {
                        specialPriceLayout.setVisibility(View.VISIBLE);
                        productPriceTV.setPaintFlags(productPriceTV.getPaintFlags()
                                | Paint.STRIKE_THRU_TEXT_FLAG);
                    }
                    if (!product.getDiscount().isEmpty()) {
                        discountLayout.setVisibility(View.VISIBLE);
                        productDiscountTV.setText(product.getDiscount());
                    }
                    if (!product.getStockStatus().isEmpty()) {
                        stockLayout.setVisibility(View.VISIBLE);
                        stockStatusTV.setText(product.getStockStatus());
                    }
                    
                    JSONArray optionsArray = proObj.optJSONArray("options");
                    optionsList = new ArrayList<>();
                    for (int j = 0; j < optionsArray.length(); j++) {
                        JSONObject optionsObj = optionsArray.getJSONObject(j);
                        JSONArray subOptionsArray = optionsObj.optJSONArray("product_option_value");
                        List<ProductOptionValueItem> subOptionsList = new ArrayList<>();
                        for (int k = 0; k < subOptionsArray.length(); k++) {
                            JSONObject subOptionsObj = subOptionsArray.getJSONObject(k);
                            subOptionsList.add(new ProductOptionValueItem(
                                    subOptionsObj.optString("option_value_id")
                                    , subOptionsObj.optString("name")));
                            String val = subOptionsList.get(k).getName();
                            utils.printLog("Color Value = ", val);
                        }
                        optionsList.add(new Options(optionsObj.optString("product_option_id")
                                , subOptionsList
                                , optionsObj.optString("name")
                                , optionsObj.optString("option_id")));
                    }
                    if (!optionsList.isEmpty() || optionsList.size() > 0) {
                        availableOptionsLayout.setVisibility(View.VISIBLE);
                        RecyclerView.LayoutManager mLayoutManagerOptions =
                                new LinearLayoutManager(getActivity()
                                        , LinearLayoutManager.VERTICAL, false);
                        mRecyclerViewOptions.setLayoutManager(mLayoutManagerOptions);
                        mRecyclerViewOptions.setAdapter(
                                new ProductOptionsAdapter(optionsList, adapterInterface));
                    }
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                
            }
        } else if (resultCode == FORCE_CANCELED) {
            utils.showToast(findStringByName("no_data"));
        } else if (resultCode == Activity.RESULT_CANCELED) {
            utils.showAlert(R.string.an_error, R.string.error_fetching_data,
                    false,
                    R.string.ok, null,
                    R.string.cancel_text, null);
        }
        
        
    }
    
    @Override
    public void onClick(View v) {
        
        switch (v.getId()) {
            
            case R.id.add_to_cart_btn:
                Bundle bundle = new Bundle();
                bundle.putString("id", product.getProductId());
                utils.printLog("ProductId", "ID=" + product.getProductId());
                bundle.putString("midFix", "addCart");
                utils.switchFragment(new FragCartDetail(), bundle);
                break;
            
            case R.id.share_iv:
                utils.shareContent(product.getName(), product.getUrl());
                break;
        }
    }
}
