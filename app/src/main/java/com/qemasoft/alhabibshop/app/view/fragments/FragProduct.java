package com.qemasoft.alhabibshop.app.view.fragments;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.AppConstants;
import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.controller.ItemAdapter;
import com.qemasoft.alhabibshop.app.controller.SubCatAdapter;
import com.qemasoft.alhabibshop.app.model.MyCategory;
import com.qemasoft.alhabibshop.app.model.MyItem;
import com.qemasoft.alhabibshop.app.view.activities.FetchData;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.qemasoft.alhabibshop.app.AppConstants.LEFT;
import static com.qemasoft.alhabibshop.app.AppConstants.PRODUCT_REQUEST_CODE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragProduct extends MyBaseFragment {

    private ImageView backBannerIV;
    private ProgressBar progressBar;
    private SubCatAdapter subCatAdapter;
    private RecyclerView subCatRecycleView;
    private ItemAdapter itemAdapter;
    private List<MyItem> myItemList;
    private TextView productTitleTV, filterTV, sortTV;

    public FragProduct() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_products, container, false);
        initViews(view);
        initUtils();
        myItemList = new ArrayList<>();

        Bundle bundle = getArguments();
        if (bundle != null) {
            if (bundle.containsKey("id"))
                requestData(bundle.getString("id"));
        } else {
            utils.showErrorDialog("No Data to Show");
        }

        utils.setCompoundDrawable(filterTV, LEFT, R.drawable.ic_filter_black);
        utils.setCompoundDrawable(sortTV, LEFT, R.drawable.ic_sort_black);

        return view;
    }

    private void requestData(String id) {

        String from;
        if (getArguments() != null)
            from = getArguments().getString("from", "");
        else from = "";
        Bundle bundle = new Bundle();
        Intent intent = new Intent(getContext(), FetchData.class);
        Map<String, String> map = new HashMap<>();
        utils.printLog("From = " + from + "\nId = " + id);
        if (from.contains("fromSearch")) {
            AppConstants.setMidFixApi("searchProduct");
            map.put("search", id);
            bundle.putBoolean("hasParameters", true);
            bundle.putSerializable("parameters", (Serializable) map);
            utils.printLog("Within Search = " + from);
        } else if (from.contains("mainActivity")) {
            AppConstants.setMidFixApi("getSpecialProducts");
            utils.printLog("Within Special Products = " + from);
        } else {
            utils.printLog("Within Else = " + from);
            AppConstants.setMidFixApi("products");
            map.put("category_id", id);
            bundle.putBoolean("hasParameters", true);
            bundle.putSerializable("parameters", (Serializable) map);
        }
        intent.putExtras(bundle);
        startActivityForResult(intent, PRODUCT_REQUEST_CODE);
        utils.printLog("Execution Completed = " + from);
    }

    private void initViews(View view) {

        backBannerIV = view.findViewById(R.id.image_view);
        progressBar = view.findViewById(R.id.progress_bar);
        productTitleTV = view.findViewById(R.id.category_title_tv);
        subCatRecycleView = view.findViewById(R.id.sub_cat_recycle_view);

        mRecyclerView = view.findViewById(R.id.product_img_recycler_view);
        filterTV = view.findViewById(R.id.filter_tv);
        sortTV = view.findViewById(R.id.sort_tv);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PRODUCT_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    utils.printLog("Inside Res Frag Products = ");
                    final JSONObject response = new JSONObject(data.getStringExtra("result"));

                    String backBanner = response.optString("banner_category");
                    if (backBanner != null && !backBanner.isEmpty())
                        Picasso.with(context)
                                .load(backBanner)
                                .noFade()
                                .into(backBannerIV, new Callback() {
                                    @Override
                                    public void onSuccess() {
                                        progressBar.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError() {
                                        progressBar.setVisibility(View.GONE);
                                        backBannerIV.setImageResource(R.drawable.ic_close_black);
                                    }
                                });
                    else {
                        backBannerIV.setVisibility(View.GONE);
                        progressBar.setVisibility(View.GONE);
                    }

                    String categoryName = response.optString("category_name");
                    productTitleTV.setText(categoryName);

                    JSONArray categories = response.optJSONArray("categories");

                    List<MyCategory> categoryList = new ArrayList<>();
                    if (categories != null) {

                        for (int i = 0; i < categories.length(); i++) {
                            JSONObject catObj = categories.optJSONObject(i);
                            MyCategory category = new MyCategory(
                                    catObj.optString("category_id"),
                                    catObj.optString("name"));
                            categoryList.add(category);
                        }

                        RecyclerView.LayoutManager mLayoutManagerCat =
                                new LinearLayoutManager(context,
                                        LinearLayoutManager.VERTICAL, false);
                        subCatRecycleView.setLayoutManager(mLayoutManagerCat);
                        subCatRecycleView.setItemAnimator(new DefaultItemAnimator());

                        subCatAdapter = new SubCatAdapter(categoryList);
                        subCatRecycleView.setAdapter(subCatAdapter);
                    } else productTitleTV.setVisibility(View.GONE);

                    JSONArray products = response.optJSONArray("products");

                    utils.printLog("Products", products.toString());
                    for (int i = 0; i < products.length(); i++) {
                        JSONObject productObj = products.optJSONObject(i);
                        MyItem item = new MyItem(productObj.optString("product_id")
                                , productObj.optString("name"), productObj.optString("special")
                                , productObj.optString("price"), productObj.optString("image"));
                        myItemList.add(item);
                    }
                    itemAdapter = new ItemAdapter(myItemList);
                    RecyclerView.LayoutManager mLayoutManager =
                            new GridLayoutManager(context, 2, LinearLayoutManager.VERTICAL, false);
                    mRecyclerView.setLayoutManager(mLayoutManager);
                    mRecyclerView.setItemAnimator(new DefaultItemAnimator());

                    mRecyclerView.setAdapter(itemAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == AppConstants.FORCED_CANCEL) {
                try {
                    JSONObject response = new JSONObject(data.getStringExtra("result"));
                    String error = response.optString("error");
                    if (!error.isEmpty()) {
                        utils.showErrorDialog(error);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                utils.showErrorDialog("Error Fetching Data! ...");
            }
        }
    }


}
