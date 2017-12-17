package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.SandwichBuilderActivity;
import com.ejoapps.m2d2_sub.orderingapp.containers.SandwichNameData;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;
import com.ejoapps.m2d2_sub.orderingapp.storage.TotalSandwichValue;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by soundoffear on 13/12/2017.
 */

public class SandwichNamesAndDescAdapter extends RecyclerView.Adapter<SandwichNamesAndDescAdapter.SND_ViewHolder> {

    private Context aContext;
    private List<SandwichNameData> aNamesData;
    private static CheckBox lastCheckedBox = null;
    private String carrierChosen;
    private static int lastCheckedPosition;

    public SandwichNamesAndDescAdapter(Context context, List<SandwichNameData> nameData, String carrierType) {
        this.aContext = context;
        this.aNamesData = nameData;
        this.carrierChosen = carrierType;
    }

    @Override
    public SND_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutSandwich = R.layout.sandwich_builder_main_sandwich_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View sandwichView = layoutInflater.inflate(layoutSandwich, parent, false);

        return new SND_ViewHolder(sandwichView);
    }

    private double priceDouble;

    @Override
    public void onBindViewHolder(final SND_ViewHolder holder, int position) {

        String name = aNamesData.get(position).getsName();
        String description = aNamesData.get(position).getsDescription();
        final String price = aNamesData.get(position).getsPrice();

        if(carrierChosen.equals(SandwichListStorage.CARRIER_SUB15)) {
            priceDouble = Double.parseDouble(price);
        } else if (carrierChosen.equals(SandwichListStorage.CARRIER_SUB30)) {
            priceDouble = Double.parseDouble(price) + 8.00;
        } else if (carrierChosen.equals(SandwichListStorage.CARRIER_WRAP)) {
            priceDouble = Double.parseDouble(price);
        } else if (carrierChosen.equals(SandwichListStorage.CARRIER_SALAD)) {
            priceDouble = Double.parseDouble(price) + 2.00;
        }

        DecimalFormat decimalPrice = new DecimalFormat("0.00");
        String decimalFormattedPrice = decimalPrice.format(priceDouble) + " PLN";

        holder.sTitle.setText(name);
        holder.sDescription.setText(description);
        holder.sPrice.setText(decimalFormattedPrice);

        //Set Tag to CheckBox
        holder.sTitle.setTag(Integer.valueOf(position));

        if(position == 0 && holder.sTitle.isChecked()) {
            lastCheckedBox = holder.sTitle;
            lastCheckedPosition = 0;
        }

        holder.sTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                int checkedTag = ((Integer) checkBox.getTag()).intValue();

                if(checkBox.isChecked()) {
                    if(lastCheckedBox != null) {
                        lastCheckedBox.setChecked(false);
                        lastCheckedBox.setSelected(false);
                    }

                    lastCheckedBox = checkBox;
                    lastCheckedPosition = checkedTag;

                    if(carrierChosen.equals(SandwichListStorage.CARRIER_SUB15)) {
                        priceDouble = Double.parseDouble(price);
                    } else if (carrierChosen.equals(SandwichListStorage.CARRIER_SUB30)) {
                        priceDouble = Double.parseDouble(price) + 8.00;
                    } else if (carrierChosen.equals(SandwichListStorage.CARRIER_WRAP)) {
                        priceDouble = Double.parseDouble(price);
                    } else if (carrierChosen.equals(SandwichListStorage.CARRIER_SALAD)) {
                        priceDouble = Double.parseDouble(price) + 2.00;
                    }

                    TotalSandwichValue.sandwichValue = priceDouble;
                    SandwichBuilderActivity.setFinalPrice(TotalSandwichValue.finalValue());
                }
                else
                {
                    lastCheckedBox = null;
                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return aNamesData.size();
    }

    public class SND_ViewHolder extends RecyclerView.ViewHolder {

        public CheckBox sTitle;
        public TextView sDescription;
        public TextView sPrice;

        public SND_ViewHolder(View itemView) {
            super(itemView);

            sTitle = itemView.findViewById(R.id.sub_build_list_selected_sandwich_check_box);
            sDescription = itemView.findViewById(R.id.sub_build_list_description_text_view);
            sPrice = itemView.findViewById(R.id.sub_build_list_sandwich_price);
        }
    }
}