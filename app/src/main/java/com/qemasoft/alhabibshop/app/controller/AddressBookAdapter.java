package com.qemasoft.alhabibshop.app.controller;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qemasoft.alhabibshop.app.R;
import com.qemasoft.alhabibshop.app.Utils;
import com.qemasoft.alhabibshop.app.model.Address;
import com.qemasoft.alhabibshop.app.view.activities.MainActivity;
import com.qemasoft.alhabibshop.app.view.fragments.FragEditAddress;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Inzimam on 17-Oct-17.
 */

public class AddressBookAdapter extends RecyclerView.Adapter<AddressBookAdapter.MyViewHolder> {

    private List<Address> dataList;
    private Context context;
    private Utils utils;
    private int itemPosition;

    public AddressBookAdapter(List<Address> dataList) {
        this.dataList = dataList;
        Log.e("Constructor", "Working");
        Log.e("Constructor", "DataList Size = " + dataList.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_address, parent, false);
        Log.e("LayoutInflated", "Working");
        this.context = parent.getContext();
        this.utils = new Utils(context);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Log.e("OnBIndMethod", "OnBind Working");
//        final int pos = holder.getAdapterPosition();
        final Address data = dataList.get(position);

        holder.nameTV.setText(data.getFirstName() + " " + data.getLastName());

        Log.e("Company", data.getCompany() + "C");
        Log.e("Address", data.getAddress());
        Log.e("City", data.getCity());
        Log.e("PostCode", data.getPostalCode());
        Log.e("Country", data.getCountry());
        Log.e("State", data.getState());
        if (data.getCompany().isEmpty()) {
            holder.companyTV.setVisibility(View.GONE);
        } else {
            holder.companyTV.setText(data.getCompany());
        }
        holder.addressTV.setText(data.getAddress());
        holder.cityTV.setText(data.getCity());

        if (data.getPostalCode().isEmpty()) {
            holder.postCodeTV.setVisibility(View.GONE);
        } else {
            holder.postCodeTV.setText(data.getPostalCode());
        }
        holder.countryTV.setText(data.getCountry());
        holder.stateTV.setText(data.getState());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e("Position", "Long Click Position = " + position);
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Select Option");
                builder.setCancelable(false);

                int checkedItem = itemPosition = 0; // 1st element
                final List<String> list = new ArrayList();
                list.add("Edit");
                list.add("Delete");
                builder.setSingleChoiceItems(list.toArray(new String[list.size()]),
                        checkedItem, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (which > 0) {
                                    itemPosition = which;
                                }
                            }
                        });
                builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.e("Which", "List position =" + list.get(itemPosition));
                        if (itemPosition == 0) {
                            Fragment fragment = new FragEditAddress();
                            FragmentTransaction transaction = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
                            transaction.addToBackStack(null);
                            transaction.replace(R.id.flFragments, fragment).commit();
                        } else if (itemPosition == 1) {
                            Log.e("Position", "Position = " + position);
                            dataList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, dataList.size());
                        }
                    }
                });
                builder.setNegativeButton("Cancel", null);
                AlertDialog dialog = builder.create();
                dialog.show();
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView nameTV, companyTV, addressTV, cityTV, postCodeTV,
                countryTV, stateTV;


        public MyViewHolder(View itemView) {
            super(itemView);
            nameTV = itemView.findViewById(R.id.name_tv);
            companyTV = itemView.findViewById(R.id.company_tv);
            addressTV = itemView.findViewById(R.id.address_tv);
            cityTV = itemView.findViewById(R.id.city_tv);
            postCodeTV = itemView.findViewById(R.id.post_code_tv);
            countryTV = itemView.findViewById(R.id.country_tv);
            stateTV = itemView.findViewById(R.id.state_tv);

            Log.e("FindViewById", "Working");
        }
    }

}
