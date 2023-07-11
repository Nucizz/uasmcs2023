package com.example.uas.System.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.uas.System.Object.Post;
import com.example.uas.databinding.ItemPostBinding;

import java.util.ArrayList;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private Context ctx;
    private ArrayList<Post> data;

    public PostAdapter(Context ctx, ArrayList<Post> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ItemPostBinding binding = ItemPostBinding.inflate(layoutInflater, parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindView(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder{
        private ItemPostBinding binding;

        protected ViewHolder(@NonNull ItemPostBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        protected void bindView(Post item) {
            binding.itemTitle.setText(item.getTitle());
            binding.itemDescription.setText(item.getDescription());

            binding.onClick.setOnClickListener(n -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle(item.getTitle());
                builder.setMessage( "Post ID: " + item.getId() +
                                    "\n\nUser ID: " + item.getUserId() +
                                    "\n\nFull Title:\n" + item.getTitle() +
                                    "\n\nDescription:\n" + item.getDescription());
                builder.setPositiveButton("Finish", (dialog, which) -> {
                    dialog.dismiss();
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            });
        }

    }

}
