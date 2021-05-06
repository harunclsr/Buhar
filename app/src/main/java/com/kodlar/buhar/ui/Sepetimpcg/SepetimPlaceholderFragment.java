package com.kodlar.buhar.ui.Sepetimpcg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kodlar.buhar.R;
import com.kodlar.buhar.ui.KisiselBakimpcg.KisiselPageViewModel;
import com.kodlar.buhar.ui.KisiselBakimpcg.KisiselPlaceholderFragment;

public class SepetimPlaceholderFragment  extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private SepetimPageViewModel sepetimPageViewModel;

    public static SepetimPlaceholderFragment newInstance(int index) {
        SepetimPlaceholderFragment fragment = new SepetimPlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sepetimPageViewModel = new ViewModelProvider(this).get(SepetimPageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        sepetimPageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_kisisel_bakim, container, false);
        final TextView textView = root.findViewById(R.id.section_label);
        sepetimPageViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}