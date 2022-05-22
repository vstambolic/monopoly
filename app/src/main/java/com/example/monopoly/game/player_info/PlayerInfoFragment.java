package com.example.monopoly.game.player_info;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.monopoly.databinding.FragmentPlayerInfoBinding;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.OwnableField;

import java.util.List;


public class PlayerInfoFragment extends Fragment {

    private FragmentPlayerInfoBinding binding;
    private Player player;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlayerFromArgs();
    }


    private void getPlayerFromArgs() {
        this.player = PlayerInfoFragmentArgs.fromBundle(requireArguments()).getPlayer();
    }
    
    
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentPlayerInfoBinding.inflate(inflater, container, false);
        binding.playerNameTextview.setText(this.player.getName());
        updatePlayerBalance();

        PlayerPropertiesAdapter adapter = new PlayerPropertiesAdapter(this);

        List<OwnableField> ownableFields = this.player.getOwnables();
//        LiveData<List<OwnableField>> listLiveData = new MutableLiveData<>(ownableFields);
//        listLiveData.observe(getViewLifecycleOwner(),adapter::setOwnables);
////        historyViewModel.getHistoryList().observe(
////                getViewLifecycleOwner(),
////                layerInfoAdapter::setGameHistoryList);
//
        adapter.setOwnables(ownableFields);

        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        return binding.getRoot();
    }

    public void updatePlayerBalance() {
        binding.playerBalance.setText("Balance: $" + this.player.getBalance());
    }
}