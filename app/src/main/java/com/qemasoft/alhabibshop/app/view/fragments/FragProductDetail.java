package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
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
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.ProductImagePreviewAdapter;
import com.qemasoft.alhabibshop.app.controller.ProductOptionsAdapter;
import com.qemasoft.alhabibshop.app.model.Options;
import com.qemasoft.alhabibshop.app.model.Product;
import com.qemasoft.alhabibshop.app.model.ProductOptionValueItem;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.qemasoft.alhabibshop.app.AppConstants.FORCED_CANCEL;
import static com.qemasoft.alhabibshop.app.AppConstants.ITEM_COUNTER;
import static com.qemasoft.alhabibshop.app.AppConstants.PRODUCT_DETAIL_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;
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
    private ImageView previewIV;

    private Product product;
    private TextView productTitleTV, productModelTV, manufacturerTV, productDescriptionTV,
            productPriceTV, productSpecialPriceTV, discountTV, percentDiscTV,
            stockStatusTV, dateAddedTV, productDescTV;

    private Button addToCartBtn;

    private RecyclerView mRecyclerViewOptions;
    private LinearLayout brandLayout, specialPriceLayout, discountLayout, availableOptionsLayout;

    public FragProductDetail() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_product_detail, container, false);
        initViews(view);
        initUtils();


        Bundle bundle = getArguments();
        if (bundle != null) {
            String id = getArguments().getString("id");
            requestData(id);
        } else {
            utils.showErrorDialog("No Data to Show");
        }

        addToCartBtn.setOnClickListener(this);


        return view;
    }

    private void initViews(View view) {

        pb = view.findViewById(R.id.progress_bar);
        previewIV = view.findViewById(R.id.image_view);

//        brandLayout = view.findViewById(R.id.brand_layout);
        specialPriceLayout = view.findViewById(R.id.special_price_layout);
        discountLayout = view.findViewById(R.id.disc_layout);

        productTitleTV = view.findViewById(R.id.product_title_val_tv);
        productModelTV = view.findViewById(R.id.product_model_val_tv);
//        manufacturerTV = view.findViewById(R.id.maker_company_tv);
        percentDiscTV = view.findViewById(R.id.product_disc_tv);
        productDescriptionTV = view.findViewById(R.id.product_desc_val_tv);
        productPriceTV = view.findViewById(R.id.product_price_val_tv);
        productSpecialPriceTV = view.findViewById(R.id.product_special_price_val_tv);
        discountTV = view.findViewById(R.id.product_disc_val_tv);
        percentDiscTV = view.findViewById(R.id.disc_percent_val_tv);

        stockStatusTV = view.findViewById(R.id.stock_status_val_tv);

        dateAddedTV = view.findViewById(R.id.added_date_val_tv);
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
                    );
                    JSONArray slideShow = proObj.optJSONArray("slideshow");
                    List<String> productImageSlides = new ArrayList<>();
                    for (int j = 0; j < slideShow.length(); j++) {
                        JSONObject img = slideShow.getJSONObject(j);

                        productImageSlides.add(img.optString("image"));
                    }
                    if (!productImageSlides.isEmpty() && productImageSlides.size() > 0) {

                        RecyclerView.LayoutManager mLayoutManager =
                                new LinearLayoutManager(getActivity()
                                        , LinearLayoutManager.HORIZONTAL, false);
                        mRecyclerView.setLayoutManager(mLayoutManager);
                        mRecyclerView.setAdapter(
                                new ProductImagePreviewAdapter(previewIV, pb, productImageSlides));
                    }
                    /// end

                    productTitleTV.setText(product.getName());
//                    if (!product.getManufacturer().isEmpty()) {
//                        brandLayout.setVisibility(View.VISIBLE);
//                        manufacturerTV.setText(product.getManufacturer());
//                    }
//                    productModelTV.setText(product.getModel());
                    if (!product.getProductDescription().isEmpty())
                        percentDiscTV.setVisibility(View.VISIBLE);
                    productDescriptionTV.setText(product.getProductDescription());
                    productPriceTV.setText(product.getPrice().concat("").concat(symbol));
                    productSpecialPriceTV.setText(product.getSpacialPrice().concat("").concat(symbol));
                    if (!product.getSpacialPrice().isEmpty()) {
                        specialPriceLayout.setVisibility(View.VISIBLE);
                        discountLayout.setVisibility(View.VISIBLE);
                        productPriceTV.setPaintFlags(productPriceTV.getPaintFlags()
                                | Paint.STRIKE_THRU_TEXT_FLAG);
                        float disc = Float.parseFloat(product.getPrice())
                                - Float.parseFloat(product.getSpacialPrice());
                        discountTV.setText(String.valueOf(disc).concat(symbol));
                        float discPercent = (1 - Float.parseFloat(product.getSpacialPrice())
                                / Float.parseFloat(product.getPrice())) * 100;
                        int val = Math.round(discPercent);
                        percentDiscTV.setText(String.valueOf(val).concat("%")
                                .concat(findStringByName("disc")));
                    }
                    if (!product.getDiscPercent().isEmpty()) {
                        percentDiscTV.setText(product.getDiscPercent().concat("").concat(symbol));
                    }

                    stockStatusTV.setText(product.getStockStatus());
//                    productQtyTV.setText(product.getQuantity());
                    dateAddedTV.setText(product.getDateAdded());

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
        } else if (resultCode == FORCED_CANCEL) {
            utils.showToast("Request Cancelled by User");
        } else if (resultCode == Activity.RESULT_CANCELED) {
            utils.showErrorDialog("Error Getting Data From Server!");
        }


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.add_to_cart_btn:
                TextView itemCountTV = getActivity().findViewById(R.id.actionbar_notification_tv);
                int val = Preferences.getSharedPreferenceInt(appContext, ITEM_COUNTER, 0);
                val++;
                itemCountTV.setText(String.valueOf(val));
                Preferences.setSharedPreferenceInt(appContext, ITEM_COUNTER,
                        Integer.parseInt(itemCountTV.getText().toString()));
                Bundle bundle = new Bundle();
                bundle.putString("id", product.getProductId());
                utils.printLog("ProductId", "ID=" + product.getProductId());
                bundle.putString("midFix", "addCart");
                utils.switchFragment(new FragCartDetail(), bundle);
                break;
        }
    }
}
