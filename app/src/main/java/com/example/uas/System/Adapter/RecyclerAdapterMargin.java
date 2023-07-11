package com.example.uas.System.Adapter;

import android.content.Context;
import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.uas.databinding.ItemPostBinding;

import java.util.Objects;

public class RecyclerAdapterMargin extends RecyclerView.ItemDecoration {
    private final int margin;

    public RecyclerAdapterMargin(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int itemCount = Objects.requireNonNull(parent.getAdapter()).getItemCount();
        int itemPosition = parent.getChildAdapterPosition(view);

        if (itemPosition == 0) {
            outRect.top = margin;
        }

        if (itemPosition == itemCount - 1) {
            outRect.bottom = margin;
        }
    }
}