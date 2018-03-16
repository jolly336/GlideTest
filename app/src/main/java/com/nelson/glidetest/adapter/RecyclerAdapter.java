package com.nelson.glidetest.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.nelson.glidetest.BR;
import com.nelson.glidetest.R;
import com.nelson.glidetest.databinding.RecyclerItemBinding;
import com.nelson.glidetest.handler.Presenter;
import com.nelson.glidetest.model.RecyclerItem;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nelson on 2018/1/29.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.BindingHolder> {

    private static final String ACTION_PRE = "com.nelson.glidetest.";

    private String[] mType = new String[]{
            "glidetest",
            "simpleusing"
    };

    private List<RecyclerItem> mRecyclerItemList = new ArrayList<>();

    public RecyclerAdapter() {
        mRecyclerItemList.clear();
        for (String str : mType) {
            RecyclerItem mRecyclerItem = new RecyclerItem(str, ACTION_PRE + str);
            mRecyclerItemList.add(mRecyclerItem);
        }
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerItemBinding binding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.recycler_item, parent, false);

        Presenter presenter = new Presenter();
        binding.setPresenter(presenter);

        BindingHolder holder = new BindingHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        // 动态绑定变量
        holder.getBinding().setVariable(BR.item, mRecyclerItemList.get(position));
        holder.getBinding().executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mRecyclerItemList.size();
    }

    public class BindingHolder extends RecyclerView.ViewHolder {

        private RecyclerItemBinding binding;

        public BindingHolder(View itemView) {
            super(itemView);
        }

        public RecyclerItemBinding getBinding() {
            return binding;
        }

        public void setBinding(RecyclerItemBinding binding) {
            this.binding = binding;
        }
    }
}
