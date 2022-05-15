package com.example.monopoly.game.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.monopoly.databinding.MonopolyBinding;
import com.example.monopoly.game.Constants;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.RailroadField;
import com.example.monopoly.game.engine.fields.UtilityField;

import java.util.List;

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

    public void init(Context context) {
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
        OwnableField field = (OwnableField) this.fields[currentPlayer.getCurrentPosition()];
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

    public void init(List<Player> playerList) {
        for (Player p : playerList) {
            this.addPlayer(p);
            for (com.example.monopoly.game.engine.fields.PropertyField field : p.getProperties()) {
                OwnableField f = (OwnableField) this.fields[field.getId()];
                f.setOwner(p.getId());
                f.setHouseCnt(field.getHouseCnt());
                f.setHotelCnt(field.getHotelCnt());
            }
            for (RailroadField field: p.getRailroads()) {
                OwnableField f = (OwnableField) this.fields[field.getId()];
                f.setOwner(p.getId());
            }
            for (UtilityField field: p.getUtilities()) {
                OwnableField f = (OwnableField) this.fields[field.getId()];
                f.setOwner(p.getId());
            }

        }

        this.invalidate();
    }


    public void removePlayerAndProperties(Player player) {
        this.removePlayer(player);
        for (com.example.monopoly.game.engine.fields.OwnableField ownableField : player.getOwnables()) {
            OwnableField of = (OwnableField) this.fields[ownableField.getId()];
            of.setOwner(OwnableField.NOBODY);
            of.setHotelCnt(0);
            of.setHouseCnt(0);
            of.invalidate();
        }

    }
}
