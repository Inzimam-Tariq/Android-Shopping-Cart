package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.MyCategory;
import com.qemasoft.alhabibshop.app.model.MyItem;
import com.qemasoft.alhabibshop.app.model.Slideshow;
import com.qemasoft.alhabibshop.app.view.fragments.FragProduct;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.qemasoft.alhabibshop.app.AppConstants.getHomeExtra;


/**
 * Created by Inzimam Tariq on 17-Oct-17.
 */

public class MainFragmentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    
    private final static int CATEGORY_VIEW = 0;
    private final static int PROMOTION_VIEW = 1;
    private final static int ITEM_VIEW = 2;
    
    private List<String> keysStrList;
    
    private List<Object> myAllItemsList = new ArrayList<>();
    
    
    private Context context;
    private Utils utils;
    
    
    public MainFragmentAdapter(List<String> keysList) {
        this.keysStrList = keysList;
        prepareData();
        Log.e("AllItemTypeSize = ", myAllItemsList.size() + "");
    }
    
    @Override
    public int getItemViewType(int position) {
        
        Object o = myAllItemsList.get(position);
        if (o instanceof List) {
            for (Object obj : (List) o) {
                if (obj instanceof MyCategory) {
//                    utils.printLog("InsideInstenceof", "Success");
                    return CATEGORY_VIEW;
                }
            }
            return ITEM_VIEW;
        } else if (o instanceof Slideshow) {
            return PROMOTION_VIEW;
        } else {
            return ITEM_VIEW;
        }
    }
    
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        
        this.context = parent.getContext();
        utils = new Utils(context);
        RecyclerView.ViewHolder viewHolder;
        
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        utils.printLog("itemType = ", "ViewTypeOnCreate " + viewType);
        switch (viewType) {
            case CATEGORY_VIEW:
                View v1 = inflater.inflate(R.layout.layout_main_frag_categories, parent, false);
                viewHolder = new ViewHolder1(v1);
                break;
            case PROMOTION_VIEW:
                View v2 = inflater.inflate(R.layout.discout_layout, parent, false);
                viewHolder = new ViewHolder2(v2);
                break;
            default:
                View v3 = inflater.inflate(R.layout.layout_main_frag_categories, parent, false);
                viewHolder = new ViewHolder1(v3);
                break;
        }
        
        return viewHolder;
    }
    
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int itemType = holder.getItemViewType();
        utils.printLog("itemType = ", itemType + "");
        Object o = myAllItemsList.get(position);
        RecyclerView.LayoutManager mLayoutManagerCat = null;
        int layoutColumnsCat = 2;
        int layoutColumnsItem = 2;
        if (o instanceof List) {
            List<Object> list = (List<Object>) myAllItemsList.get(position);
            
            
            if (list.size() < 4) {
                layoutColumnsCat = 1;
                layoutColumnsItem = 1;
            }
            int screenWidth = Utils.getScreenWidth(context);
            if (screenWidth > 480 && list.size() < 5) {
                layoutColumnsCat = 1;
            }
            if (screenWidth > 1000 && list.size() < 7) {
                layoutColumnsCat = 1;
            }
            
            
        }
        switch (itemType) {
            case CATEGORY_VIEW:
                mLayoutManagerCat =
                        new GridLayoutManager(context, layoutColumnsCat,
                                LinearLayoutManager.HORIZONTAL, false);
                ViewHolder1 vh1 = (ViewHolder1) holder;
                vh1.getmRecyclerView().setLayoutManager(mLayoutManagerCat);
                vh1.getmRecyclerView().setAdapter(new CategoryAdapter(
                        (List<MyCategory>) myAllItemsList.get(position)));
                break;
            case PROMOTION_VIEW:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2, position);
                break;
            default:
                mLayoutManagerCat =
                        new GridLayoutManager(context, layoutColumnsItem,
                                LinearLayoutManager.HORIZONTAL, false);
                ViewHolder1 vh3 = (ViewHolder1) holder;
                vh3.getmRecyclerView().setLayoutManager(mLayoutManagerCat);
                vh3.getmRecyclerView().setAdapter(new ItemAdapter(
                        (List<MyItem>) myAllItemsList.get(position)));
                break;
        }
    }
    
    @Override
    public int getItemCount() {
        return keysStrList.size();
    }
    
    
    private void configureViewHolder2(final ViewHolder2 vh2, int position) {
        Slideshow promotion = (Slideshow) myAllItemsList.get(position);
        if (promotion != null) {
//            vh2.getTitle().setText(promotion.getId());
            String imgPath = promotion.getImage();
            utils.printLog("bannerPath", "" + imgPath);
            if (imgPath != null && !imgPath.isEmpty()) {
                utils.printLog("bannerPathInsideTrue", "" + imgPath);
//                Picasso.with(context).load(imgPath).into(vh2.getImageView());
                Picasso.with(context)
                        .load(imgPath)
                        .into(vh2.getImageView(), new Callback() {
                            @Override
                            public void onSuccess() {
                                vh2.getProgressBar().setVisibility(View.GONE);
                            }
                            
                            @Override
                            public void onError() {
                                vh2.getProgressBar().setVisibility(View.GONE);
                                vh2.getImageView().setImageResource(R.drawable.ic_close_black);
                            }
                        });
            }
        }
        vh2.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("from", "mainActivity");
                utils.switchFragment(new FragProduct(), bundle);
            }
        });
    }
    
    private void prepareData() {
        
        List<MyCategory> myCategoryList = new ArrayList<>();
        List<MyItem> myItemList;
        
        String responseStr = getHomeExtra();
        Log.e("MAinFragAdapter", "GetHomeObject : " + responseStr);
        try {
            JSONObject responseObject = new JSONObject(responseStr);
            Log.e("JSON_Response", "" + responseObject);
            boolean success = responseObject.optBoolean("success");
            if (success) {
                JSONObject homeObject = responseObject.optJSONObject("home");
                JSONObject modules = homeObject.optJSONObject("modules");
                
                for (int a = 0; a < keysStrList.size(); a++) {
                    Log.e("KeyStr = ", keysStrList.get(a));
                    if (keysStrList.get(a).equals("categories")) {
                        JSONArray featuredCategories = modules.optJSONArray(keysStrList.get(a));
                        for (int i = 0; i < featuredCategories.length(); i++) {
                            JSONObject categoryObject = featuredCategories.getJSONObject(i);
                            MyCategory myCategory = new MyCategory(categoryObject.optString("category_id"),
                                    categoryObject.optString("name"), categoryObject.optString("image"));
                            myCategoryList.add(myCategory);
                        }
                        myAllItemsList.add(myCategoryList);
                    } else if (keysStrList.get(a).equals("banner")) {
//                        JSONArray promotionArray = modules.optJSONArray("promotion");
                        JSONObject promotionObject = modules.optJSONObject("banner");
//                        utils.printLog("URL", ""+modules.optString("banner"));
                        Slideshow banner = new Slideshow(
                                promotionObject.optString("banner"),
                                promotionObject.optString("id"),
                                promotionObject.optString("banertype"));
                        myAllItemsList.add(banner);
                        
                    } else {
                        myItemList = new ArrayList<>();
                        JSONArray featuredProduct = modules.optJSONArray(keysStrList.get(a));
                        for (int i = 0; i < featuredProduct.length(); i++) {
                            JSONObject productObj = featuredProduct.getJSONObject(i);
                            MyItem myItem = new MyItem(productObj.optString("product_id"),
                                    productObj.optString("name"), productObj.optString("dis"),
                                    productObj.optString("price"), productObj.optString("image"));
                            myItemList.add(myItem);
                        }
                        myAllItemsList.add(myItemList);
                    }
                }
            } else {
                Log.e("SuccessFalse", "Within getCategories");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("JSONEx_CatAdapterTest", responseStr);
        }
    }
    
}
