package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.containers.SandwichNameData;

import java.util.List;


public class CateringBuilderAdapter extends RecyclerView.Adapter<CateringBuilderAdapter.CateringBuilderViewHolder> {

    private Context mContext;
    private List<SandwichNameData> sandwichNameData;

    public CateringBuilderAdapter(Context mContext, List<SandwichNameData> sandwichNameData) {
        this.mContext = mContext;
        this.sandwichNameData = sandwichNameData;
    }

    @Override
    public CateringBuilderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutR = R.layout.catering_builder_sandwich_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutR, parent, false);
        return new CateringBuilderViewHolder(view);
    }

    private int countSelected;
    private int maxSelectedSubs = 4;

    private CheckBox lastSelectedCheckBox;
    private int lastPosition;

    @Override
    public void onBindViewHolder(CateringBuilderViewHolder holder, final int position) {
        holder.tv_sandwichDescription.setText(sandwichNameData.get(position).getsDescription());
        holder.tv_sandwichName.setText(sandwichNameData.get(position).getsName());

        //check box limiter
        CheckBox selectedSandwiches = holder.cb_selectedSandwiches;

        selectedSandwiches.setTag(position);

        if (position == 0 && selectedSandwiches.isChecked()) {
            lastSelectedCheckBox = selectedSandwiches;
            lastPosition = 0;
        }

        selectedSandwiches.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    countSelected++;
                } else {
                    countSelected--;
                }
            }
        });

        selectedSandwiches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (countSelected >= maxSelectedSubs) {
                    CheckBox checkBox = (CheckBox) v;
                    int cbTag = (Integer) checkBox.getTag();
                    if (checkBox.isChecked()) {
                        //countSelected++;
                        if (lastSelectedCheckBox != null) {
                            lastSelectedCheckBox.setChecked(false);
                            lastSelectedCheckBox.setSelected(false);
                        }

                        lastSelectedCheckBox = checkBox;
                        lastPosition = cbTag;
                    } else {
                        lastSelectedCheckBox = null;
                    }
                }

                Toast.makeText(mContext, "Selected Items: " + countSelected, Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return sandwichNameData.size();
    }

    class CateringBuilderViewHolder extends RecyclerView.ViewHolder {

        TextView tv_sandwichName;
        TextView tv_sandwichDescription;
        CheckBox cb_selectedSandwiches;

        CateringBuilderViewHolder(View v) {
            super(v);

            tv_sandwichName = v.findViewById(R.id.cb_sandwich_item_name);
            tv_sandwichDescription = v.findViewById(R.id.cb_sandwich_item_description);
            cb_selectedSandwiches = v.findViewById(R.id.cb_check_box_sandwich);
        }

    }

}
