package com.example.monopoly.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monopoly.MainActivity;
import com.example.monopoly.R;
import com.example.monopoly.data.GameHistory;
import com.example.monopoly.databinding.ViewHolderHistoryBinding;
import com.example.monopoly.util.DateFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final MainActivity mainActivity;
    private List<GameHistory> gameHistoryList = new ArrayList<>();

    public HistoryAdapter(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
    }
    public void setGameHistoryList(List<GameHistory> gameHistoryList) {
        this.gameHistoryList = gameHistoryList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HistoryAdapter.HistoryViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewHolderHistoryBinding viewHolderGameHistoryBinding = ViewHolderHistoryBinding.inflate(
                layoutInflater,
                parent,
                false);
        return new HistoryAdapter.HistoryViewHolder(viewHolderGameHistoryBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdapter.HistoryViewHolder holder, int position) {
        holder.bind(gameHistoryList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return gameHistoryList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        public ViewHolderHistoryBinding binding;

        public HistoryViewHolder(@NonNull ViewHolderHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(GameHistory gameHistory, int index) {
            binding.headline.setText("Game #" + (index + 1));
            binding.date.setText(DateFormatter.formatDate(gameHistory.getDate()));
            binding.duration.setText(DateFormatter.formatTime(new Date(gameHistory.getDuration())));
            for (int i = 0; i < gameHistory.getPlayers().size(); i++) {
                PlayerWinnerView pwv = new PlayerWinnerView(mainActivity,(i+1) +". " + gameHistory.getPlayers().get(i),i==gameHistory.getWinner());
                pwv.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                binding.playersContainer.addView(pwv);
            }
        }
    }
}
