package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TabHost;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.Preferences;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.ProductOptionsAdapter;
import com.qemasoft.alhabibshop.app.controller.ProductReviewsAdapter;
import com.qemasoft.alhabibshop.app.model.Options;
import com.qemasoft.alhabibshop.app.model.Product;
import com.qemasoft.alhabibshop.app.model.ProductOptionValueItem;
import com.qemasoft.alhabibshop.app.model.Reviews;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

import static com.qemasoft.alhabibshop.app.AppConstants.ITEM_COUNTER;
import static com.qemasoft.alhabibshop.app.AppConstants.PRODUCT_DETAIL_REQUEST_CODE;
import static com.qemasoft.alhabibshop.app.AppConstants.appContext;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragProductDetail extends MyBaseFragment implements View.OnClickListener {

    private ViewPager mPager;
    //    private int currentPage = 0;
//    private ArrayList<Slideshow> slideshowArrayList;
    private CircleIndicator indicator;
    private List<Reviews> reviewsList;
    private Product product;

    private TextView productTitleTV, productModelTV, manufacturerTV, productDescriptionTV,
            productPriceTV, discountTV, percentDiscTV, productQtyTV, stockStatusTV,
            dateAddedTV, optionsTV, writeReviewTV, postReviewTV;
    private Button addToCartBtn, submitReviewBtn;
    private RatingBar ratingBarOverall, ratingBarPost;
    private RecyclerView mRecyclerViewRating, mRecyclerViewOptions;

    private EditText yourNameET, reviewCommentET;
    private TabHost tabHost;
    private ScrollView scrollView;

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


        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Description");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Description");
        tabHost.addTab(spec);

        //Tab 2
        spec = tabHost.newTabSpec("Reviews");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Reviews");
        tabHost.addTab(spec);

        Bundle bundle = getArguments();
        if (bundle != null) {
            String id = getArguments().getString("id");
            requestData(id);
        } else {
            utils.showErrorDialog("No Data to Show");
        }

        writeReviewTV.setOnClickListener(this);
        addToCartBtn.setOnClickListener(this);
        submitReviewBtn.setOnClickListener(this);


        return view;
    }


    private void initViews(View view) {

        scrollView = view.findViewById(R.id.sv);

        mPager = view.findViewById(R.id.pager);
        indicator = view.findViewById(R.id.indicator);

        tabHost = view.findViewById(R.id.tabHost);
        productTitleTV = view.findViewById(R.id.product_title_val_tv);
        productModelTV = view.findViewById(R.id.product_model_val_tv);
        manufacturerTV = view.findViewById(R.id.maker_company_tv);
        productDescriptionTV = view.findViewById(R.id.product_desc_val_tv);
        productPriceTV = view.findViewById(R.id.product_price_val_tv);
        discountTV = view.findViewById(R.id.product_disc_val_tv);
        percentDiscTV = view.findViewById(R.id.disc_percent_val_tv);
        ratingBarOverall = view.findViewById(R.id.ratingBar);
        stockStatusTV = view.findViewById(R.id.stock_status_val_tv);
        productQtyTV = view.findViewById(R.id.product_qty_tv);
        dateAddedTV = view.findViewById(R.id.added_date_val_tv);
        writeReviewTV = view.findViewById(R.id.write_review_tv);

        addToCartBtn = view.findViewById(R.id.add_to_cart_btn);

        mRecyclerViewRating = view.findViewById(R.id.author_recycler_view);
        mRecyclerViewOptions = view.findViewById(R.id.product_options_recycler_view);
        postReviewTV = view.findViewById(R.id.review_comment_tv);
        yourNameET = view.findViewById(R.id.name_et);
        reviewCommentET = view.findViewById(R.id.review_comment_et);
        ratingBarPost = view.findViewById(R.id.post_rating_bar);

        submitReviewBtn = view.findViewById(R.id.submit_btn);
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
        if (requestCode == PRODUCT_DETAIL_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));
                    Log.e("ResponseInFragProDetail",response.toString());
                    JSONObject proObj = response.optJSONObject("product");

                    product = new Product(proObj.optString("id")
                            , proObj.optString("name"), proObj.optString("model")
                            , proObj.optString("price"), proObj.optString("quantity")
                            , proObj.optString("description"), proObj.optString("stock_status")
                            , proObj.optString("manufacturer"), proObj.optString("disc")
                            , proObj.optString("disc_percent"), proObj.optString("date_added")
                            , proObj.optString("rating")
                            , proObj.optString("review_total")
                    );
                    JSONArray slideShow = proObj.optJSONArray("slideshow");
                    AppConstants.setSlideshowExtra(slideShow.toString());
                    utils.setupSlider(mPager, indicator, false);
                    productTitleTV.setText(product.getName());
                    productModelTV.setText(product.getModel());
                    manufacturerTV.setText(product.getManufacturer());
                    productDescriptionTV.setText(product.getProductDescription());
                    productPriceTV.setText(product.getPrice());
                    if (!product.getDiscPercent().isEmpty()) {
                        percentDiscTV.setText(product.getDiscPercent());
                    }
                    ratingBarOverall.setRating(Float.parseFloat(product.getRating()));

                    stockStatusTV.setText(product.getStockStatus());
                    productQtyTV.setText(product.getQuantity());
                    dateAddedTV.setText(product.getDateAdded());

                    JSONArray reviews = proObj.optJSONArray("reviews");
                    reviewsList = new ArrayList<>();
                    for (int i = 0; i < reviews.length(); i++) {
                        JSONObject revObject = reviews.optJSONObject(i);
                        reviewsList.add(new Reviews(revObject.optString("id")
                                , revObject.optString("author")
                                , revObject.optString("date_added")
                                , revObject.optString("text")
                                , revObject.optString("rating")));
                    }
                    RecyclerView.LayoutManager mLayoutManagerReviews =
                            new LinearLayoutManager(getActivity()
                                    , LinearLayoutManager.VERTICAL, false);
                    mRecyclerViewRating.setLayoutManager(mLayoutManagerReviews);
                    mRecyclerViewRating.setAdapter(new ProductReviewsAdapter(reviewsList));
                    if (!product.getReviewCount().isEmpty()) {
                        writeReviewTV.setText(product.getReviewCount() + " Reviews/ Write Review");
                    }else {
                        writeReviewTV.setText("0 Reviews/ Write Review");
                    }

                    JSONArray optionsArray = proObj.optJSONArray("options");
                    List<Options> optionsList = new ArrayList<>();
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
                            Log.e("Color Value = ", val);
                        }
                        optionsList.add(new Options(optionsObj.optString("product_option_id")
                                , subOptionsList
                                , optionsObj.optString("name")
                                , optionsObj.optString("option_id")));
                    }
                    RecyclerView.LayoutManager mLayoutManagerOptions =
                            new LinearLayoutManager(getActivity()
                                    , LinearLayoutManager.VERTICAL, false);
                    mRecyclerViewOptions.setLayoutManager(mLayoutManagerOptions);
                    mRecyclerViewOptions.setAdapter(new ProductOptionsAdapter(optionsList));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                utils.showAlertDialog("Invalid Request!", "Either the request is invalid or no relevant record found");
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.write_review_tv:
                tabHost.setCurrentTab(1);
                break;
            case R.id.add_to_cart_btn:
                TextView itemCountTV = getActivity().findViewById(R.id.actionbar_notification_tv);
                int val = Preferences.getSharedPreferenceInt(appContext, ITEM_COUNTER, 0);
                val++;
                itemCountTV.setText(String.valueOf(val));
                Preferences.setSharedPreferenceInt(appContext, ITEM_COUNTER,
                        Integer.parseInt(itemCountTV.getText().toString()));
                Bundle bundle = new Bundle();
                bundle.putString("id", product.getProductId());
                switchFragment(new FragCartDetail(), bundle);

                break;
            case R.id.submit_btn:
                String nameVal = yourNameET.getText().toString();
                String commentVal = reviewCommentET.getText().toString();
                float rating = ratingBarPost.getRating();
                if (nameVal.isEmpty()) {
                    yourNameET.setError("Required!");
                    return;
                }
                if (commentVal.isEmpty()) {
                    reviewCommentET.setError("Required!");
                    return;
                }
                reviewsList.add(new Reviews(nameVal, "", commentVal,
                        String.valueOf(rating)));
                mRecyclerViewRating.getAdapter().notifyItemInserted(reviewsList.size() - 1);
                mRecyclerViewRating.getAdapter().notifyDataSetChanged();
                mRecyclerViewRating.scrollToPosition(reviewsList.size() - 1);
                scrollView.smoothScrollTo(0, mRecyclerViewRating.getBottom());
                yourNameET.setText("");
                reviewCommentET.setText("");
//                utils.showAlertDialog("Confirmation Dialog","Review submitted successfully");
                break;
            case R.id.whatsapp_icon:
                utils.sendAppMsg(v);
        }
    }
}
