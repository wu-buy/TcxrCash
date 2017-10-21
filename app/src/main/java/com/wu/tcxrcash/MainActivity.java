package com.wu.tcxrcash;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.collect.Lists;
import com.wu.tcxrcash.adapter.CashAdapter;
import com.wu.tcxrcash.db.CashTransfer;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextDami;
    private TextView textViewTongdui;
    private Double rate0 = 0.13;
    private Double rate1 = 1.13;
    private Double transferRate = 0.0005;


    private EditText editTextCzje;
    private EditText editTextHdxm;
    private EditText editTextDays;
//    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private Handler handler = new Handler();
    private List<CashTransfer> data = Lists.newArrayList();
    private CashAdapter adapter = null;

    private int limit = 1000;
    private int offset = 0;
    private int count = 0;
    private boolean isLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    void init(){
        initViews();
    }

    void initViews(){

        editTextDami = (EditText) findViewById(R.id.et_dami);
        textViewTongdui = (TextView) findViewById(R.id.tv_dami_tongdui);

        Button btnTongdui = (Button) findViewById(R.id.btn_tongdui);
        btnTongdui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dami = editTextDami.getText().toString();
                if(StringUtils.isNotEmpty(dami)){
                    int damiInt = Integer.parseInt(dami);
//                    String cash = String.format("%.0f", damiInt/rate1);
                    Double d = damiInt/rate1;
                    int cash = d.intValue();

                    Double dd = damiInt - d;
                    int cashRate = dd.intValue();
                    textViewTongdui.setText("可提现：" + cash + "，通兑费率：" + cashRate + "。");
                }else{
                    Toast.makeText(MainActivity.this, "请输入大米数量！", Toast.LENGTH_SHORT).show();
                }
            }
        });

        editTextCzje = (EditText) findViewById(R.id.et_czje);
        editTextHdxm = (EditText) findViewById(R.id.et_hdxm);
        editTextDays = (EditText) findViewById(R.id.et_days);
        Button btnJssy = (Button) findViewById(R.id.btn_jssy);
        btnJssy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String czjeStr = editTextCzje.getText().toString();
                if(StringUtils.isEmpty(czjeStr)){
                    Toast.makeText(MainActivity.this, "请输入充值金额！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String hdxmStr = editTextHdxm.getText().toString();
                if(StringUtils.isEmpty(hdxmStr)){
                    Toast.makeText(MainActivity.this, "请输入获得小米数量！", Toast.LENGTH_SHORT).show();
                    return;
                }
                String daysStr = editTextDays.getText().toString();
                if(StringUtils.isEmpty(daysStr)){
                    Toast.makeText(MainActivity.this, "请输入经过天数！", Toast.LENGTH_SHORT).show();
                    return;
                }
                data.clear();
                int hdxm = Integer.parseInt(hdxmStr) * 10000;
                int damiSum = 0;
                int cashSum = 0;
                int days = Integer.parseInt(daysStr);
                for(int i=1;i<=days;i++){
                    CashTransfer o = new CashTransfer();
                    o.setDay(i);
                    double dami = hdxm * transferRate;
                    int damiInt = Integer.parseInt(String.format("%.0f", dami));
                    o.setDamiDay(damiInt);
                    damiSum += damiInt;
                    o.setDamiSum(damiSum);
                    double cash = damiInt/rate1;
                    int cashInt = Integer.parseInt(String.format("%.0f", cash));
                    o.setCashDay(cashInt);
                    cashSum += cashInt;
                    o.setCashSum(cashSum);
                    hdxm -= damiInt;
                    o.setXiaomiRest(hdxm);
                    data.add(o);
                }

                initRecyclerView();

            }
        });
    }

    void initRecyclerView(){
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl_cash);
        adapter = new CashAdapter(this, data);
        adapter.setAllDataCount(data.size());
        recyclerView = (RecyclerView)findViewById(R.id.rv_cash);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        if(adapter != null){
            recyclerView.setAdapter(adapter);
        }

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition();
                if(lastVisibleItemPosition + 1 >= count){
                    return;
                }

                if(adapter != null){
                    if (lastVisibleItemPosition + 1 == adapter.getItemCount()) {
                        /*boolean isRefreshing = swipeRefreshLayout.isRefreshing();
                        if (isRefreshing) {
                            adapter.notifyItemRemoved(adapter.getItemCount());
                            return;
                        }*/
                        if (!isLoading) {
                            isLoading = true;
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    getData();
                                    isLoading = false;
                                }
                            }, 1000);
                        }
                    }
                }

            }
        });
    }

    void getData() {

//        swipeRefreshLayout.setRefreshing(true);

        if(count == 0){
            count = data.size();
            if(adapter != null){
                adapter.setAllDataCount(count);
            }
        }
        if(offset <= count){
            offset += limit;
            if(adapter != null){
                adapter.notifyDataSetChanged();
                adapter.notifyItemRemoved(adapter.getItemCount());
            }
        }

//        swipeRefreshLayout.setRefreshing(false);
    }
}
