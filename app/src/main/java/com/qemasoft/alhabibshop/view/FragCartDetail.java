package com.qemasoft.alhabibshop.view;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qemasoft.alhabibshop.Utils;
import com.qemasoft.alhabibshop.controller.CartDetailAdapter;
import com.qemasoft.alhabibshop.model.MyCartDetail;

import java.util.ArrayList;
import java.util.List;

import hostflippa.com.opencart_android.R;

/**
 * Created by Inzimam on 24-Oct-17.
 */

public class FragCartDetail extends Fragment {

    Context context;
    CheckBox useCoupon;
    private RecyclerView mRecyclerView;
    private TextView orderId, orderDate, qty, productModel, ProductTitle,
            unitPrice, grandTotal;
    private CartDetailAdapter cartDetailAdapter;
    private List<MyCartDetail> myOrderList = new ArrayList<>();
    private Utils utils;

    public FragCartDetail() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frag_cart, container, false);
        initViews(view);
        this.context = getContext();
        this.utils = new Utils(context);

        loadDummyData();
        setupAdaptersAndShowData();
        LoadData();

        useCoupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    createAndShowCustomAlertDialog();
//                    showDialog();
                } else {
                    // do nothing
                }
            }
        });

        return view;
    }

    private void createAndShowCustomAlertDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialogTheme);

        // If you want to use custom layout for dialog
//        LayoutInflater inflater = LayoutInflater.from(getActivity());
//        LayoutInflater inflater = getActivity().getLayoutInflater();

        builder.setTitle("Apply Coupon");
        builder.setCancelable(true);
        final EditText input = new EditText(getContext());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        lp.setMargins(10,0,10,0);
        input.setLayoutParams(lp);
        input.setHint("enter your coupon here");
        builder.setView(input);
//        builder.setIcon(R.drawable.galleryalart);
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                utils.showToast(""+input.getText());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
//        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
//                .setBackgroundColor(getResources().getColor(colorPrimaryDark));
    }

    private void showDialog(){
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).create();
        final EditText input = new EditText(getContext());
        input.setHint("hint");
        alertDialog.setTitle("title");
        alertDialog.setMessage("message");
        alertDialog.setView(input);
        alertDialog.setButton(0, "Apply", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                utils.showToast(input.getText()+"");
            }
        });
    }

    private void LoadData() {


    }

    private void initViews(View view) {
        mRecyclerView = view.findViewById(R.id.cart_detail_recycler_view);
//        orderId = view.findViewById(R.id.order_id_value);
        grandTotal = view.findViewById(R.id.grand_total);
        useCoupon = view.findViewById(R.id.use_coupon_cb);
    }

    // lkj

    private void setupAdaptersAndShowData() {

        // for Orders
        Log.e("ItemDataListPopulated", "Item Data list populated");
        cartDetailAdapter = new CartDetailAdapter(myOrderList);
        RecyclerView.LayoutManager mLayoutManager =
                new LinearLayoutManager(context
                        , LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        Log.e("SettingAdapterForItems", "Setting Adapter For Items");
        mRecyclerView.setAdapter(cartDetailAdapter);
        Log.e("AdapterSet", "Adapter Set Success");
    }

    private void loadDummyData() {

        int orderIdInt = 1;
        String[] fullPrice = {"3000", "2490", "4965", "3000", "2490", "4965"};
        String[] qty = {"2", "1", "3", "1", "1", "4"};
        String[] productName = {"IPhone", "Laptop", "LCD", "Speaker", "Headphone"};

        for (int i = 0; i < 2; i++) {

            MyCartDetail data = new MyCartDetail("Model A", productName[i], qty[i],
                    fullPrice[i]);
            Log.e("LogsInForLoop",
                    "\nProductName = " + data.getProductName() +
                            "\nOrderQuantity = " + data.getOrderQty() +
                            "\nPriceTotal = " + data.getProductPrice());
            myOrderList.add(data);
        }
//        int sTotal = Utils.subTotalDummy;
//        subTotal.setText("" + sTotal);
//        Log.e("SubTotalInMain = ", "" + sTotal);
//        couponDisc.setText("26");
//        int gTotalInt = Integer.parseInt(subTotal.getText().toString()) -
//                Integer.parseInt(couponDisc.getText().toString());
//        grandTotal.setText("" + gTotalInt);

    }

    @Override
    public void onPause() {
        super.onPause();
        Utils.subTotalDummy = 0;
    }
}
