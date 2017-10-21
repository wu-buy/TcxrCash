package com.wu.tcxrcash.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wu.tcxrcash.R;
import com.wu.tcxrcash.db.CashTransfer;

import java.util.List;

public class CashAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "CashAdapter";

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NOMOREDATA = 2;
    private static final int TYPE_NODATA = 3;
    private Context context;
    private List<CashTransfer> data;
    private int allDataCount;

    public CashAdapter(Context context, List<CashTransfer> data) {
        this.context = context;
        this.data = data;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
//        Log.d(TAG, "getItemViewType: position=" + position);
        if(allDataCount == 0){
            return TYPE_NODATA;
        }
        if(position  == allDataCount){
            return TYPE_NOMOREDATA;
        }else if (position == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(context).inflate(R.layout.cash_item, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(context).inflate(R.layout.cash_item_foot, parent, false);
            return new FootViewHolder(view);
        } else if(viewType == TYPE_NOMOREDATA){
            View view = LayoutInflater.from(context).inflate(R.layout.cash_item_nomoredata, parent, false);
            return new NomoredataViewHolder(view);
        } else if(viewType == TYPE_NODATA){
            View view = LayoutInflater.from(context).inflate(R.layout.cash_item_nodata, parent, false);
            return new NodataViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            //holder.tv.setText(data.get(position));
//            Log.d(TAG, "onBindViewHolder: datasize=====" + data.size());
            if(position == data.size()){
                return;
            }
            CashTransfer cash = data.get(position);
            ItemViewHolder h = (ItemViewHolder)holder;
            h.day.setText(cash.getDay().toString());
            h.cashDay.setText(cash.getCashDay().toString());
            h.cashSum.setText(cash.getCashSum().toString());
            h.damiDay.setText(cash.getDamiDay().toString());
            h.damiSum.setText(cash.getDamiSum().toString());
            h.xiaomiRest.setText(cash.getXiaomiRest().toString());

            if (onItemClickListener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemClick(holder.itemView, position);
                    }
                });

                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        int position = holder.getLayoutPosition();
                        onItemClickListener.onItemLongClick(holder.itemView, position);
                        return false;
                    }
                });
            }
        }
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView day;
        TextView cashDay;
        TextView cashSum;
        TextView damiDay;
        TextView damiSum;
        TextView xiaomiRest;

        public ItemViewHolder(View view) {
            super(view);
            day = (TextView) view.findViewById(R.id.ci_tv_day);
            cashDay = (TextView) view.findViewById(R.id.ci_tv_cashDay);
            cashSum = (TextView) view.findViewById(R.id.ci_tv_cashSum);
            damiDay = (TextView) view.findViewById(R.id.ci_tv_damiDay);
            damiSum = (TextView) view.findViewById(R.id.ci_tv_damiSum);
            xiaomiRest = (TextView) view.findViewById(R.id.ci_tv_xiaomiRest);

        }
    }


    static class FootViewHolder extends RecyclerView.ViewHolder {

        public FootViewHolder(View view) {
            super(view);
        }
    }

    static class NomoredataViewHolder extends RecyclerView.ViewHolder {

        public NomoredataViewHolder(View view) {
            super(view);
        }
    }

    static class NodataViewHolder extends RecyclerView.ViewHolder {

        public NodataViewHolder(View view) {
            super(view);
        }
    }

    public void setAllDataCount(int allDataCount) {
        this.allDataCount = allDataCount;
    }
}

