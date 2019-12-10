package com.taypih.lurker.ui.adapter;

import androidx.databinding.ViewDataBinding;
import androidx.databinding.library.baseAdapters.BR;
import androidx.recyclerview.widget.RecyclerView;


public class ViewHolder extends RecyclerView.ViewHolder {
    private final ViewDataBinding binding;

    public ViewHolder(ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(Object model) {
        binding.setVariable(BR.model, model);
        binding.executePendingBindings();
    }
}
