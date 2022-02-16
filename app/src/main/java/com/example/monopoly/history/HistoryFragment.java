package com.example.monopoly.history;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monopoly.MainActivity;
import com.example.monopoly.data.GameHistory;
import com.example.monopoly.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private MainActivity mainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainActivity = (MainActivity) requireActivity();
    }
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        HistoryAdapter historyAdapter = new HistoryAdapter(this.mainActivity);
//        historyViewModel.getHistoryList().observe(
//                getViewLifecycleOwner(),
//                historyAdapter::setGameHistoryList);

        // todo preko modela iz baze
        ArrayList<String> players = new ArrayList<>();
        players.add("Zitorad");
        players.add("Milica");
        players.add("Zorica");

        List<GameHistory> l = new ArrayList<>();
        l.add(new GameHistory(0,new Date(),3600000,players, 1));
        l.add(new GameHistory(0,new Date(),4800000,players, 1));
        l.add(new GameHistory(0,new Date(),3600000,players, 1));
        l.add(new GameHistory(0,new Date(),3600000,players, 1));
        historyAdapter.setGameHistoryList(l);
        binding.recyclerView.setAdapter(historyAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.mainActivity));

        return binding.getRoot();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}