package com.example.monopoly.history;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.monopoly.MainActivity;
import com.example.monopoly.data.Game;
import com.example.monopoly.databinding.ViewHolderHistoryBinding;
import com.example.monopoly.util.DateFormatter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {

    private final MainActivity mainActivity;
    private final HistoryFragment historyFragment;
    private List<Game> gameList = new ArrayList<>();

    public HistoryAdapter(MainActivity mainActivity, HistoryFragment historyFragment) {
        this.mainActivity = mainActivity;
        this.historyFragment = historyFragment;
    }
    public void setGameHistoryList(List<Game> gameList) {
        this.gameList = gameList;
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
        holder.bind(gameList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return gameList.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        public ViewHolderHistoryBinding binding;

        public HistoryViewHolder(@NonNull ViewHolderHistoryBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Game game, int index) {
            binding.headline.setText("Game #" + (index + 1));
            binding.date.setText(DateFormatter.formatDate(game.getDate()));
            binding.duration.setText(DateFormatter.formatTime(new Date(game.getDuration())));
            binding.playersContainer.removeAllViews();
            for (int i = 0; i < game.getPlayers().size(); i++) {
                PlayerWinnerView pwv = new PlayerWinnerView(mainActivity,(i+1) +". " + game.getPlayers().get(i),i== game.getWinner());
                pwv.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
                binding.playersContainer.addView(pwv);
            }

            binding.simulationButton.setOnClickListener(view -> {
                NavHostFragment
                        .findNavController(HistoryAdapter.this.historyFragment)
                        .navigate(HistoryFragmentDirections.actionHistoryFragmentToGameSimulationFragment(game.getId()));
            });
        }
    }
}
