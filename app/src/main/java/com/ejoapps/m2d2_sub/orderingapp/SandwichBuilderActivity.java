package com.ejoapps.m2d2_sub.orderingapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ejoapps.m2d2_sub.orderingapp.adapters.PaidAddOnsAdapter;
import com.ejoapps.m2d2_sub.orderingapp.adapters.SandwichNamesAndDescAdapter;
import com.ejoapps.m2d2_sub.orderingapp.containers.BreadTypesData;
import com.ejoapps.m2d2_sub.orderingapp.containers.PaidAddOnsData;
import com.ejoapps.m2d2_sub.orderingapp.containers.SandwichNameData;
import com.ejoapps.m2d2_sub.orderingapp.containers.SauceData;
import com.ejoapps.m2d2_sub.orderingapp.containers.VegetablesData;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.ContractSandwichBuilder;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichBuilderDatabase;
import com.ejoapps.m2d2_sub.orderingapp.database_preload.SandwichFinalDatabase;
import com.ejoapps.m2d2_sub.orderingapp.storage.SandwichListStorage;

import java.util.List;

public class SandwichBuilderActivity extends AppCompatActivity implements View.OnClickListener {

    private List<BreadTypesData> breadTypesList;
    private List<SandwichNameData> sandwichNameList;
    private List<PaidAddOnsData> paidAddOnList;
    private List<VegetablesData> vegeList;
    private List<SauceData> sauceList;

    RecyclerView sandwichNDP;
    RecyclerView paidAddsRecView;

    LinearLayout ll_vegetablesHolder;
    LinearLayout ll_saucesHolder;

    RadioGroup rg_bread;
    RadioButton[] radioButton;

    CheckBox[] vegeCBs;
    CheckBox[] saucesCB;

    public static TextView finalPrice;

    Button orderBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sandwich_builder);

        finalPrice = findViewById(R.id.sub_build_tv_total_final_price_for_sub_value);
        rg_bread = findViewById(R.id.sub_build_radio_button);
        orderBtn = findViewById(R.id.sub_build_btn_confirm_sub);

        orderBtn.setOnClickListener(this);

        Log.d("Test temp Keys 3", SandwichListStorage.allCarriersTogether.get(SandwichListStorage.positionFromToReplace));
        SandwichBuilderDatabase sandwichBuilderDatabase = new SandwichBuilderDatabase(this);
        sandwichNameList = sandwichBuilderDatabase.getSandwichNameAllData(ContractSandwichBuilder.SandwichNames.TABLE_NAME_SANDWICH_NAMES);

        sandwichNDP = findViewById(R.id.sub_build_sub_names_recView);
        sandwichNDP.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SandwichBuilderActivity.this);
        sandwichNDP.setLayoutManager(linearLayoutManager);
        SandwichNamesAndDescAdapter sandwichNamesAndDescAdapter = new SandwichNamesAndDescAdapter(SandwichBuilderActivity.this, sandwichNameList);
        sandwichNDP.setAdapter(sandwichNamesAndDescAdapter);

        breadTypesList = sandwichBuilderDatabase.getBreadTypeAllData();
        // Create RadioButton and populate RadioGroup with it
        radioButton = new RadioButton[breadTypesList.size()];
        for (int i = 0; i < breadTypesList.size(); i++) {
            radioButton[i] = new RadioButton(this);
            radioButton[i].setText(breadTypesList.get(i).getBreadType());
            radioButton[i].setTextSize(16f);
            radioButton[i].setTextColor(Color.BLACK);
            rg_bread.addView(radioButton[i]);
        }

        // Create Paid Add Ons and populate recyclerView
        paidAddOnList = sandwichBuilderDatabase.getPaidAddOnsAllData();
        paidAddsRecView = findViewById(R.id.sub_build_paid_add_ons_recView);
        LinearLayoutManager llManager = new LinearLayoutManager(this);
        paidAddsRecView.setHasFixedSize(true);
        paidAddsRecView.setLayoutManager(llManager);
        PaidAddOnsAdapter paidAddOnsAdapter = new PaidAddOnsAdapter(this, paidAddOnList);
        paidAddsRecView.setAdapter(paidAddOnsAdapter);


        // Create Vegetable and populate LinearLayout with CheckBoxes
        vegeList = sandwichBuilderDatabase.getVegeDataAll();
        ll_vegetablesHolder = findViewById(R.id.sub_build_vegetables_checkBox_holder);
        vegeCBs = new CheckBox[vegeList.size()];
        for (int i = 0; i < vegeList.size(); i++) {
            vegeCBs[i] = new CheckBox(this);
            vegeCBs[i].setText(vegeList.get(i).getVegetables());
            vegeCBs[i].setTextSize(16f);
            vegeCBs[i].setTextColor(Color.BLACK);
            ll_vegetablesHolder.addView(vegeCBs[i]);
        }

        // Create Sauces and populate LinearLayout with CheckBoxes
        sauceList = sandwichBuilderDatabase.getSauceDataAll();
        ll_saucesHolder = findViewById(R.id.sub_build_sauces_checkBox_holder);
        saucesCB = new CheckBox[sauceList.size()];
        for (int i = 0; i < sauceList.size(); i++) {
            saucesCB[i] = new CheckBox(this);
            saucesCB[i].setText(sauceList.get(i).getSauces());
            saucesCB[i].setTextSize(16f);
            saucesCB[i].setTextColor(Color.BLACK);
            ll_saucesHolder.addView(saucesCB[i]);
        }

    }


    public static final void setFinalPrice(String value) {
        finalPrice.setText(value);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.sub_build_btn_confirm_sub:
                SandwichFinalDatabase finalDatabase = new SandwichFinalDatabase(this);

                String sandwichName = null;
                for (int i = 0; i < sandwichNDP.getAdapter().getItemCount(); i++) {
                    CheckBox checkBox = sandwichNDP.getChildAt(i).findViewById(R.id.sub_build_list_selected_sandwich_check_box);
                    if (checkBox.isChecked()) {
                        sandwichName = checkBox.getText().toString();
                    }
                }

                String breadType = null;
                for (int i = 0; i < radioButton.length; i++) {
                    if (radioButton[i].isChecked()) {
                        breadType = radioButton[i].getText().toString();
                    }
                }

                String paidAdds = null;
                for (int i = 0; i < paidAddsRecView.getChildCount(); i++) {
                    TextView tvQuantity = paidAddsRecView.getChildAt(i).findViewById(R.id.sub_build_paid_adds_quantity);
                    TextView tvPaidName = paidAddsRecView.getChildAt(i).findViewById(R.id.sub_build_paid_adds_name_label);
                    int intQuantity = Integer.parseInt(tvQuantity.getText().toString());
                    if (intQuantity > 0) {
                        paidAdds = tvPaidName.getText().toString() + "_" + tvQuantity.getText().toString();
                    }
                }

                StringBuilder allVeges = new StringBuilder();
                for (CheckBox checked : vegeCBs) {
                    if (checked.isChecked()) {
                        String tempName = checked.getText().toString();
                        allVeges.append(tempName).append("_");
                    }
                }

                StringBuilder allSauces = new StringBuilder();
                for (CheckBox checkedSauce : saucesCB) {
                    if (checkedSauce.isChecked()) {
                        String tempName = checkedSauce.getText().toString();
                        allSauces.append(tempName).append("_");
                    }
                }

                StringBuilder choosenSandwichAndPrice = new StringBuilder();
                choosenSandwichAndPrice.append(sandwichName)
                        .append("_")
                        .append("BLA BLA BLA")
                        .append("_")
                        .append(finalPrice.getText().toString() + " PLN");
                SandwichListStorage.allCarriersTogether.remove(SandwichListStorage.positionFromToReplace);
                SandwichListStorage.allCarriersTogether.add(SandwichListStorage.positionFromToReplace, choosenSandwichAndPrice.toString());

                Log.d("Test fetching data", choosenSandwichAndPrice.toString());
                break;
        }
    }


    //TODO Add method for summing all of the selected item together
    //TODO Add Method to confirm and remember all selected values (Database with updates, etc.)

}
