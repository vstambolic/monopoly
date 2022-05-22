package com.example.monopoly.history;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.monopoly.MainActivity;
import com.example.monopoly.data.Game;
import com.example.monopoly.data.GameRepository;
import com.example.monopoly.data.MonopolyDatabase;
import com.example.monopoly.databinding.FragmentHistoryBinding;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HistoryFragment extends Fragment {

    private FragmentHistoryBinding binding;
    private MainActivity mainActivity;

    private GameRepository gameRepo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mainActivity = (MainActivity) requireActivity();
        // INIT DB ---------------------------------------------------------------------------------
        MonopolyDatabase monopolyDatabase = MonopolyDatabase.getInstance(requireContext());
        gameRepo = new GameRepository(monopolyDatabase.gameDao());
    }
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHistoryBinding.inflate(inflater, container, false);

        HistoryAdapter historyAdapter = new HistoryAdapter(mainActivity,this);
        gameRepo.getAllLiveData().observe(getViewLifecycleOwner(), historyAdapter::setGameHistoryList);
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