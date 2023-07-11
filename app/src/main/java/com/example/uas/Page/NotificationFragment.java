package com.example.uas.Page;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.uas.System.Adapter.PostAdapter;
import com.example.uas.System.Adapter.RecyclerAdapterMargin;
import com.example.uas.System.Database.NotificationFCM;
import com.example.uas.System.Object.Post;
import com.example.uas.databinding.FragmentMainBinding;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class NotificationFragment extends Fragment {

    private FragmentMainBinding binding;
    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(LayoutInflater.from(requireActivity()));

        NotificationFCM.getNotificationList(new NotificationFCM.NotificationListCallback() {
            @Override
            public void onDataLoaded(ArrayList<Post> notificationList) {
                if(notificationList.isEmpty()) {
                    onDataError(true, "Your past notifications will appear here.");
                } else {
                    onDataError(false, null);
                    PostAdapter adapter = new PostAdapter(getContext(), notificationList);
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