package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.containers.SandwichNameData;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by soundoffear on 13/12/2017.
 */

public class SandwichNamesAndDescAdapter extends RecyclerView.Adapter<SandwichNamesAndDescAdapter.SND_ViewHolder> {

    private Context aContext;
    private List<SandwichNameData> aNamesData;

    public SandwichNamesAndDescAdapter(Context context, List<SandwichNameData> nameData) {
        this.aContext = context;
        this.aNamesData = nameData;
    }

    @Override
    public SND_ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutSandwich = R.layout.sandwich_builder_main_sandwich_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View sandwichView = layoutInflater.inflate(layoutSandwich, parent, false);

        return new SND_ViewHolder(sandwichView);
    }

    @Override
    public void onBindViewHolder(SND_ViewHolder holder, int position) {

        String name = aNamesData.get(position).getsName();
        String description = aNamesData.get(position).getsDescription();
        String price = aNamesData.get(position).getsPrice();

        holder.sTitle.setText(name);
        holder.sDescription.setText(description);
        holder.sPrice.setText(price + " PLN");

    }

    @Override
    public int getItemCount() {
        return aNamesData.size();
    }

    class SND_ViewHolder extends RecyclerView.ViewHolder {

        CheckBox sTitle;
        TextView sDescription;
        TextView sPrice;

        public SND_ViewHolder(View itemView) {
            super(itemView);

            sTitle = itemView.findViewById(R.id.sub_build_list_selected_sandwich_check_box);
            sDescription = itemView.findViewById(R.id.sub_build_list_description_text_view);
            sPrice = itemView.findViewById(R.id.sub_build_list_sandwich_price);
        }
    }


}
