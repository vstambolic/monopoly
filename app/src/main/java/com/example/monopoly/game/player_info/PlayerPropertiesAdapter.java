package com.example.monopoly.game.player_info;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monopoly.databinding.ViewHolderPropertyBinding;
import com.example.monopoly.game.engine.fields.OwnableField;
import com.example.monopoly.game.engine.fields.PropertyField;


import java.util.ArrayList;
import java.util.List;

public class PlayerPropertiesAdapter extends RecyclerView.Adapter<PlayerPropertiesAdapter.PlayerPropertiesAdapterHolder> {

    private final PlayerInfoFragment playerInfoFragment;
    private List<OwnableField> ownables = new ArrayList<>();

    public PlayerPropertiesAdapter(PlayerInfoFragment playerInfoFragment) {
        this.playerInfoFragment = playerInfoFragment;
    }
    public void setOwnables(List<OwnableField> ownables) {
        this.ownables = ownables;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PlayerPropertiesAdapter.PlayerPropertiesAdapterHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderPropertyBinding viewHolderPropertyBinding = ViewHolderPropertyBinding.inflate(
                layoutInflater,
                parent,
                false);
        return new PlayerPropertiesAdapter.PlayerPropertiesAdapterHolder(viewHolderPropertyBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerPropertiesAdapter.PlayerPropertiesAdapterHolder holder, int position) {
        holder.bind(ownables.get(position));
    }

    @Override
    public int getItemCount() {
        return ownables.size();
    }

    public class PlayerPropertiesAdapterHolder extends RecyclerView.ViewHolder {

        public ViewHolderPropertyBinding binding;

        public PlayerPropertiesAdapterHolder(@NonNull ViewHolderPropertyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(OwnableField ownable) {
            binding.propertyNameTextview.setText(ownable.getLabel());
            binding.propertyDescriptionTextview.setText(ownable.getDescription());


            if (ownable instanceof PropertyField) {
                PropertyField propertyField = (PropertyField)ownable;
                binding.sellHotelButton.setText("SELL HOTEL FOR $" + propertyField.getHotelCost()/2);
                binding.sellHotelButton.setOnClickListener(v->{
                    if (propertyField.getHotelCnt() == 0) {
                        Toast.makeText(playerInfoFragment.getActivity(),"You don't own any hotels on this property.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        propertyField.setHotelCnt(propertyField.getHotelCnt()-1);
                        propertyField.getOwner().incBalance(propertyField.getHotelCost()/2);
                        playerInfoFragment.updatePlayerBalance();
                        setOwnables(ownables);
                    }
                });
                binding.sellHotelButton.setVisibility(View.VISIBLE);

                binding.sellHouseButton.setText("SELL HOUSE FOR $" + propertyField.getHouseCost()/2);
                binding.sellHouseButton.setOnClickListener(v->{
                    if (propertyField.getHouseCnt() == 0) {
                        Toast.makeText(playerInfoFragment.getActivity(),"You don't own any houses on this property.",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        propertyField.setHouseCnt(propertyField.getHouseCnt()-1);
                        propertyField.getOwner().incBalance(propertyField.getHouseCost()/2);
                        playerInfoFragment.updatePlayerBalance();
                        setOwnables(ownables);
                    }
                });
                binding.sellHouseButton.setVisibility(View.VISIBLE);

            }
            if (!ownable.isMortgaged()) {
                int mortgageValue =  ownable.calculateMortgage();
                binding.mortgageButton.setText("MORTGAGE FOR $" + mortgageValue);
                binding.mortgageButton.setOnClickListener(v->{
                    if (ownable instanceof PropertyField) {
                        PropertyField propertyField = (PropertyField)ownable;
                        if (propertyField.getHouseCnt() != 0 || propertyField.getHotelCnt() != 0) {
                            Toast.makeText(playerInfoFragment.getActivity(),"You must first sell all houses & hotels of this color set.",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }

                    ownable.setMortgaged(true);
                    ownable.getOwner().incBalance(mortgageValue);
                    playerInfoFragment.updatePlayerBalance();
                    setOwnables(ownables);
                });
            }
            else {
                int liftMortgageValue =  ownable.calculateLiftMortgage();
                binding.mortgageButton.setText("LIFT MORTGAGE FOR $" + liftMortgageValue);
                binding.mortgageButton.setOnClickListener(v->{
                    if (ownable.getOwner().getBalance() < liftMortgageValue) {
                        Toast.makeText(playerInfoFragment.getActivity(),"You don't have enought money for this transaction.",Toast.LENGTH_SHORT).show();
                        return;
                    }

                    ownable.setMortgaged(false);
                    ownable.getOwner().decBalance(liftMortgageValue);
                    playerInfoFragment.updatePlayerBalance();
                    setOwnables(ownables);
                });
            }
        }
    }
}
