package com.example.uas.Page;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uas.System.Adapter.PostAdapter;
import com.example.uas.System.Adapter.RecyclerAdapterMargin;
import com.example.uas.System.Database.PostJSON;
import com.example.uas.System.Object.Post;
import com.example.uas.databinding.FragmentMainBinding;

import java.util.ArrayList;

public class PostFragment extends Fragment {

    private FragmentMainBinding binding;

    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(inflater, container, false);

        PostJSON parser = new PostJSON(getContext());
        parser.parseData(new PostJSON.PostJSONCallback() {
            @Override
            public void onDataLoaded(ArrayList<Post> postList) {
                if (postList.isEmpty()) {
                    onDataError(true, "Your posts will appear here.");
                } else {
                    onDataError(false, null);
                    PostAdapter adapter = new PostAdapter(getContext(), postList);
                    binding.recyclerView.setAdapter(adapter);
                    binding.recyclerView.addItemDecoration(new RecyclerAdapterMargin(30));
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                }
            }
            @Override
            public void onDataError(boolean status, @Nullable String errorMessage) {
                if(status) {
                    binding.errorText.setText(errorMessage);
                    binding.errorText.setVisibility(View.VISIBLE);
                    binding.recyclerView.setVisibility(View.GONE);
                } else {
                    binding.errorText.setVisibility(View.GONE);
                    binding.recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        return binding.getRoot();
    }
}