package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.adapters.CateringListToBuildAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.CateringBuilderDatabase;
import com.ejoapps.m2d2_sub.orderingapp.interfaces.CateringItemOnClickListener;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;

import java.text.DecimalFormat;
import java.util.List;

public class CateringListToBuildActivity extends AppCompatActivity implements CateringItemOnClickListener, View.OnClickListener {

    RecyclerView cateringBuilderRecView;
    List<CateringNameAndTypeData> cateringNames;
    CateringBuilderDatabase cateringBuilderDatabase;
    TextView tv_totalPrice;

    Button btn_placeOrder;
    Button btn_cancelOrder;

    public static boolean isCatering = true;

    //TODO build all mechanics for adding drinks to order

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catering_list_to_build);
        tv_totalPrice = findViewById(R.id.cb_total_price_output);
        cateringBuilderDatabase = new CateringBuilderDatabase(this);
        if(CateringOrderActivity.isCateringStarting) {
            cateringBuilderDatabase.deleteAllCateringRecords();
            CateringOrderActivity.isCateringStarting = false;
        }

        btn_placeOrder = findViewById(R.id.cb_btn_confirm);
        btn_cancelOrder = findViewById(R.id.cb_btn_cancel);
        btn_placeOrder.setOnClickListener(this);
        btn_cancelOrder.setOnClickListener(this);

        if(SandwichListStorage.isSandwichBuilt) {
            Intent intent = getIntent();
            Bundle receivedData = intent.getExtras();
            assert receivedData != null;
            List<CateringNameAndTypeData> cateringNameAndTypeDataList = receivedData.getParcelableArrayList(CateringOrderActivity.CATERING_DATA_TRANSFER);
            assert cateringNameAndTypeDataList != null;
            for(int i = 0; i < cateringNameAndTypeDataList.size(); i++) {
                for(int j =0; j < cateringNameAndTypeDataList.get(i).getCateringValue(); j++) {
                    String cateringItem = cateringNameAndTypeDataList.get(i).getCateringName();
                    String cateringPrice = String.valueOf(cateringNameAndTypeDataList.get(i).getCateringPrice());
                    Log.d("CATERING PRICE ", cateringPrice);
                    CateringNameAndTypeData cateringNameAndTypeData = new CateringNameAndTypeData(cateringItem,
                            getResources().getString(R.string.sandwich),
                            getResources().getString(R.string.sandwich),
                            getResources().getString(R.string.sandwich),
                            getResources().getString(R.string.sandwich),
                            cateringPrice);
                    cateringBuilderDatabase.insertItemsIntoCateringDB(cateringNameAndTypeData);
                    cateringNames = cateringBuilderDatabase.getAllCateringData();
                }
            }
            Log.d("TEST", "PRICE TEST +++++++++ " + cateringNames.get(0).getCateringPrice());
            SandwichListStorage.isSandwichBuilt = false;
        } else {
            cateringNames = cateringBuilderDatabase.getAllCateringData();
        }

        cateringBuilderRecView = findViewById(R.id.cb_recView);

        cateringBuilderRecView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        cateringBuilderRecView.setLayoutManager(linearLayoutManager);
        CateringListToBuildAdapter cateringListToBuildAdapter = new CateringListToBuildAdapter(this, cateringNames, this);
        cateringBuilderRecView.setAdapter(cateringListToBuildAdapter);

        finalCateringPrice();
    }

    private double finalPrice = 0;

    void finalCateringPrice() {
        for(int i = 0; i < cateringNames.size(); i++) {
            double dCateringSingleItemPrice = Double.parseDouble(cateringNames.get(i).getsCateringPrice());
            finalPrice += dCateringSingleItemPrice;
        }
        Log.d("Test final price ", String.valueOf(finalPrice));
        String totalPriceFinal = new DecimalFormat("0.00").format(finalPrice) + " PLN";
        tv_totalPrice.setText(totalPriceFinal);
    }

    public static final String POSITION_OF_ITEM_CATERING = "position-item-catering";
    @Override
    public void onCateringItemClick(View v, int position) {

        Intent intentStartBuilder = new Intent(CateringListToBuildActivity.this, CateringBuilderActivity.class);
        Bundle dataToBuilder = new Bundle();
        dataToBuilder.putInt(POSITION_OF_ITEM_CATERING, position);
        intentStartBuilder.putExtras(dataToBuilder);
        startActivity(intentStartBuilder);

    }

    public static final String FINAL_ORDER_PRICE = "price-finalize-order";

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.cb_btn_confirm:
                SandwichListStorage.isComingFromCatering = true;
                Intent intentToSummaryOrder = new Intent(CateringListToBuildActivity.this, OrderSummaryActivity.class);
                Bundle dataToSummaryOrder = new Bundle();
                dataToSummaryOrder.putDouble(FINAL_ORDER_PRICE, finalPrice);
                intentToSummaryOrder.putExtras(dataToSummaryOrder);
                startActivity(intentToSummaryOrder);

                break;
            case R.id.cb_btn_cancel:
                break;
        }
    }
}
