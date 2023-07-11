package com.example.uas.Page;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;
import android.view.LayoutInflater;

import com.example.uas.R;
import com.example.uas.System.Database.NotificationFCM;
import com.example.uas.databinding.ActivityMainBinding;

public class MainActivity extends FragmentActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        NotificationFCM.getToken(this);

        binding.navbar.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.home_fragment:
                    getSupportFragmentManager().beginTransaction().replace(binding.frameLayout.getId(), new PostFragment()).commit();
                    return true;
                case R.id.notification_fragment:
                    getSupportFragmentManager().beginTransaction().replace(binding.frameLayout.getId(), new NotificationFragment()).commit();
                    return true;
                default:
                    return false;
            }
        });
        binding.navbar.setSelectedItemId(R.id.home_fragment);
    }

}