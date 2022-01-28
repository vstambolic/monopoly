package com.example.monopoly.history;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.example.monopoly.R;
import com.example.monopoly.databinding.PlayerWinnerBinding;

public class PlayerWinnerView extends LinearLayout {

    private PlayerWinnerBinding binding;
    public PlayerWinnerView(Context context, String name, boolean isWinner) {
        super(context);
        this.binding = PlayerWinnerBinding.inflate(LayoutInflater.from(context), this, true);
        this.binding.textviewPlayer.setText(name);
        if (isWinner) {
            final ImageView iv = new ImageView(context);
            iv.setLayoutParams(new LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            iv.setImageResource(R.drawable.baseline_emoji_events_20);
            this.binding.getRoot().addView(iv);
        }
    }

}
