package com.ejoapps.m2d2_sub.orderingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.adapters.CateringOrderSummaryAdapter;
import com.ejoapps.m2d2_sub.orderingapp.adapters.OrderSummaryAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.CateringNameAndTypeData;
import com.ejoapps.m2d2_sub.orderingapp.containers.FinalSandwichData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.CateringBuilderDatabase;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichFinalDatabase;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;

import java.text.DecimalFormat;
import java.util.List;

public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView orderSummaryRecyclerView;

    TextView totalSumMoney;

    Button cancelOrder;
    Button placeAnOrder;

    List<FinalSandwichData> finalSandwichDataList;
    private double finalPriceAllSandwiches;

    public static final String TOTAL_ORDER_FINAL = "total-order-final";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        orderSummaryRecyclerView = findViewById(R.id.finalOrderRecyclerView);

        cancelOrder = findViewById(R.id.order_btn_cancel_order);
        placeAnOrder = findViewById(R.id.order_btn_placeAnOrder);

        placeAnOrder.setOnClickListener(this);
        cancelOrder.setOnClickListener(this);

        totalSumMoney = findViewById(R.id.orderTotalSumTotalMoney);

        if(!SandwichListStorage.isComingFromCatering) {
            sandwichV();
        } else {
            cateringV();
        }

    }

    void cateringV() {
        double tempPrice = 0;
        CateringBuilderDatabase cateringBuilderDatabase = new CateringBuilderDatabase(this);
        List<CateringNameAndTypeData> cateringNameAndTypeDataList = cateringBuilderDatabase.getAllCateringData();
        for(int i = 0; i < cateringNameAndTypeDataList.size(); i++) {
            String priceStringWithCurrency = cateringNameAndTypeDataList.get(i).getsCateringPrice();
            String[] splitPriceAndCurrency = priceStringWithCurrency.split(" ");
            String priceOnly = splitPriceAndCurrency[0];
            tempPrice = Double.parseDouble(priceOnly);
            tempPrice ++;
        }
        CateringOrderSummaryAdapter cateringOrderSummaryAdapter = new CateringOrderSummaryAdapter(this, cateringNameAndTypeDataList);
        //TODO create recyclerView layout to be inflated for catering items
        //TODO make sure everything is being counted and calculated as required
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        orderSummaryRecyclerView.setHasFixedSize(true);
        orderSummaryRecyclerView.setLayoutManager(linearLayoutManager);
        orderSummaryRecyclerView.setAdapter(cateringOrderSummaryAdapter);
        finalPriceAllSandwiches = tempPrice;
        String sPriceFinal = new DecimalFormat("0.00").format(finalPriceAllSandwiches) + " PLN";
        totalSumMoney.setText(sPriceFinal);
    }

    void sandwichV() {
        SandwichFinalDatabase sandwichFinalDatabase = new SandwichFinalDatabase(this);
        finalSandwichDataList = sandwichFinalDatabase.getAllFinalSubData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        orderSummaryRecyclerView.setHasFixedSize(true);
        orderSummaryRecyclerView.setLayoutManager(linearLayoutManager);
        OrderSummaryAdapter orderSummaryAdapter = new OrderSummaryAdapter(this, finalSandwichDataList);
        orderSummaryRecyclerView.setAdapter(orderSummaryAdapter);

        Log.d("Test Sum Price", String.valueOf(SandwichListStorage.allSandwichesTogether));

        Intent intentFromSandwichList = getIntent();
        finalPriceAllSandwiches = intentFromSandwichList.getDoubleExtra(SandwichListActivity.FINAL_PRICE_CALCULATED, 0);
        DecimalFormat format = new DecimalFormat("0.00");
        String finalPriceWithCurrency = format.format(finalPriceAllSandwiches) + " PLN";

        totalSumMoney.setText(finalPriceWithCurrency);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.order_btn_placeAnOrder:
                Intent intentToFinalOrder = new Intent(OrderSummaryActivity.this, OrderAddressAndFinalConfirmationActivity.class);
                Bundle dataToCarryOver = new Bundle();
                dataToCarryOver.putDouble(TOTAL_ORDER_FINAL, finalPriceAllSandwiches);
                intentToFinalOrder.putExtras(dataToCarryOver);
                startActivity(intentToFinalOrder);
            case R.id.order_btn_cancel_order:

        }
    }
}
