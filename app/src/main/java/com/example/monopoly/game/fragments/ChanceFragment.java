package com.example.monopoly.game.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.monopoly.databinding.FragmentChanceBinding;
import com.example.monopoly.game.Constants;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.engine.Player;
import com.example.monopoly.game.engine.fields.ChanceField;


abstract class ChanceAction implements View.OnClickListener {
    private String message;
    protected GameEngine gameEngine;
    public ChanceAction(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setGameEngine(GameEngine gameEngine) {
        this.gameEngine = gameEngine;
    }

}

class AdvanceChanceAction extends ChanceAction {

    private final int fieldNumber;

    public AdvanceChanceAction(String message, int fieldNumber) {
        super(message);
        this.fieldNumber = fieldNumber;
    }

    @Override
    public void onClick(View v) {
        gameEngine.getGameFragment().enableNextTurnButton();
        gameEngine.moveToField(fieldNumber);
        v.setEnabled(false);
    }
}

class MoneyChanceAction extends ChanceAction {

    private final int money;
    private final boolean dec;

    public MoneyChanceAction(String message, int money, boolean dec) {
        super(message);
        this.money = money;
        this.dec = dec;
    }

    @Override
    public void onClick(View v) {
        final Player player = gameEngine.getCurrentPlayer();
        if (dec)
            if (player.getBalance() >= this.money) {
                player.decBalance(this.money);
                gameEngine.setTaxMoney(gameEngine.getTaxMoney() + this.money);
                gameEngine.getGameFragment().setPlayerBalance(player.getBalance());
                gameEngine.getGameFragment().enableNextTurnButton();
                v.setEnabled(false);
            }
            else
                if (player.getCapital() >= this.money) {
                    Toast.makeText(v.getContext(), "You do not have enough money for this transaction.", Toast.LENGTH_SHORT).show();
                }
                else {
                    player.setIsBankrupt(true);
                    Toast.makeText(v.getContext(), "You have gone bankrupt.\nYou will be eliminated after this turn.", Toast.LENGTH_SHORT).show();
                    gameEngine.getGameFragment().enableNextTurnButton();
                    v.setEnabled(false);
                }
        else {
            gameEngine.getGameFragment().enableNextTurnButton();
            player.incBalance(this.money);
            gameEngine.getGameFragment().setPlayerBalance(player.getBalance());
            v.setEnabled(false);
        }
    }
}

class GoToJailChanceAction extends ChanceAction {

    public GoToJailChanceAction(String message) {
        super(message);
    }

    @Override
    public void onClick(View v) {
        gameEngine.getGameFragment().enableNextTurnButton();
        gameEngine.getCurrentPlayer().setJailCnt(3);
        gameEngine.moveToField(10);
        v.setEnabled(false);
    }
}



public class ChanceFragment extends ControllerFragment {

    public static final String CHANCE_FRAGMENT_TAG = "CHANCE_FRAGMENT_TAG";
    private FragmentChanceBinding binding;
    private ChanceAction chanceAction;

    // -----------------

    public ChanceFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentChanceBinding.inflate(inflater, container, false);
        this.initView();
        return this.binding.getRoot();
    }

    @Override
    protected void initView() {
        super.initView();

        this.binding.fieldLabelTextview.setText(field.getLabel());
        this.binding.actionButton.setBackgroundColor(Constants.PLAYER_COLORS[this.gameEngine.getCurrentPlayer().getId()]);

        int randomChanceIndex = (int)(Math.random()*10);
        this.chanceAction = chanceActions[randomChanceIndex];
        this.chanceAction.setGameEngine(gameEngine);

        this.binding.messageTextview.setText(this.chanceAction.getMessage());
        this.binding.actionButton.setOnClickListener(this.chanceAction);
    }

    private static ChanceAction[] chanceActions = {
            new AdvanceChanceAction(" Advance to \"Start\". (Collect $200)",0),
            new AdvanceChanceAction("Advance to Illinois Ave. If you pass \"Start\", collect $200.",24),
            new AdvanceChanceAction("Advance to St. Charles Place. If you pass \"Start\", collect $200.",11),
            new MoneyChanceAction("Income tax refund. Collect $20.",20, false),
            new MoneyChanceAction("Bank pays you dividend of $50.",50, false),
            new MoneyChanceAction("You inherit $100.",100, false),
            new MoneyChanceAction("Pay poor tax of $15.",15, true),
            new MoneyChanceAction("Hospital Fees. Pay $50.",50, true),
            new MoneyChanceAction("School fees. Pay $50.",50, true),
            new GoToJailChanceAction("Go directly to jail.")
    };
}