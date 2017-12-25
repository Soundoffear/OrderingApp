package com.ejoapps.m2d2_sub.orderingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.R;
import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.CateringItemOnClickListener;

import java.util.List;

public class CateringListToBuildAdapter extends RecyclerView.Adapter<CateringListToBuildAdapter.CateringListToBuildViewHolder> {

    private Context mContext;
    private List<CateringNameAndTypeData> cateringNameAndTypeDataList;
    private static CateringItemOnClickListener cateringItemOnClickListener1;

    public CateringListToBuildAdapter(Context mContext, List<CateringNameAndTypeData> cateringNameAndTypeDataList,
                                      CateringItemOnClickListener cateringItemOnClickListener) {
        this.mContext = mContext;
        this.cateringNameAndTypeDataList = cateringNameAndTypeDataList;
        cateringItemOnClickListener1 = cateringItemOnClickListener;
    }

    @Override
    public CateringListToBuildViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutR = R.layout.catering_build_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutR, parent, false);

        return new CateringListToBuildViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CateringListToBuildViewHolder holder, int position) {
        holder.tv_cateringName.setText(cateringNameAndTypeDataList.get(position).getCateringName());
        holder.tv_cateringPrice.setText(cateringNameAndTypeDataList.get(position).getsCateringPrice());
        holder.tv_sub1.setText(cateringNameAndTypeDataList.get(position).getSub1());
        holder.tv_sub2.setText(cateringNameAndTypeDataList.get(position).getSub2());
        holder.tv_sub3.setText(cateringNameAndTypeDataList.get(position).getSub3());
        holder.tv_sub4.setText(cateringNameAndTypeDataList.get(position).getSub4());
    }

    @Override
    public int getItemCount() {
        return cateringNameAndTypeDataList.size();
    }

    class CateringListToBuildViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_cateringName;
        TextView tv_cateringPrice;
        TextView tv_sub1;
        TextView tv_sub2;
        TextView tv_sub3;
        TextView tv_sub4;

        public CateringListToBuildViewHolder(View itemView) {
            super(itemView);
            tv_cateringName = itemView.findViewById(R.id.catering_list_item_label);
            tv_cateringPrice = itemView.findViewById(R.id.catering_list_item_price);
            tv_sub1 = itemView.findViewById(R.id.catering_list_sub1);
            tv_sub2 = itemView.findViewById(R.id.catering_list_sub2);
            tv_sub3 = itemView.findViewById(R.id.catering_list_sub3);
            tv_sub4 = itemView.findViewById(R.id.catering_list_sub4);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            cateringItemOnClickListener1.onCateringItemClick(v, this.getLayoutPosition());
        }
    }

}
