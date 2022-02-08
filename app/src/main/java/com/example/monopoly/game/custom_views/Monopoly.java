package com.example.monopoly.game.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.monopoly.databinding.MonopolyBinding;
import com.example.monopoly.game.engine.Player;

public class Monopoly extends ConstraintLayout {
    public static final int TOTAL_FIELD_CNT = 40;

    public Monopoly(@NonNull Context context) {
        super(context);
        this.init(context);
    }

    public Monopoly(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    private MonopolyBinding binding;
    private Field[] fields = new Field[TOTAL_FIELD_CNT];

    private void init(Context context) {
        this.binding = MonopolyBinding.inflate(LayoutInflater.from(context), this, true);
        for (int i = 0; i < TOTAL_FIELD_CNT; i++)
            this.fields[i] = (Field) this.binding.fieldWrapper.getChildAt(i);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthPixels = View.MeasureSpec.getSize( widthMeasureSpec );
        int h = View.MeasureSpec.getSize( heightMeasureSpec );
        int spec = h>widthPixels? widthMeasureSpec:heightMeasureSpec;
        super.onMeasure(spec, spec);
    }


    public void addPlayer(Player player) {
        Field field = this.fields[player.getCurrentPosition()];
        field.addPlayer(player.getId());
        field.postInvalidate();
    }
    public void removePlayer(Player player) {
        Field field = this.fields[player.getCurrentPosition()];
        field.removePlayer(player.getId());
        field.postInvalidate();
    }

    public void markAsBought(Player currentPlayer) {
        PropertyField field = (PropertyField) this.fields[currentPlayer.getCurrentPosition()];
        field.setOwner(currentPlayer.getId());
        field.invalidate();
    }

    public void houseBought(Player currentPlayer) {
        PropertyField field = (PropertyField) this.fields[currentPlayer.getCurrentPosition()];
        field.setHouseCnt(field.getHouseCnt() + 1);
        field.invalidate();
    }

    public void hotelBought(Player currentPlayer) {
        PropertyField field = (PropertyField) this.fields[currentPlayer.getCurrentPosition()];
        field.setHouseCnt(0);
        field.setHotelCnt(field.getHotelCnt() + 1);
        field.invalidate();
    }
}
