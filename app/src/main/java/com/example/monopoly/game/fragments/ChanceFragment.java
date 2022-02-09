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

    public MoneyChanceAction(String message, int money) {
        super(message);
        this.money = money;
    }

    @Override
    public void onClick(View v) {
        final Player player = gameEngine.getCurrentPlayer();
        if (this.money < 0)
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



public class ChanceFragment extends Fragment {

    public static final String CHANCE_FRAGMENT_TAG = "CHANCE_FRAGMENT_TAG";
    private FragmentChanceBinding binding;
    private GameEngine gameEngine;
    private ChanceField chanceField;
    private ChanceAction chanceAction;


    // -----------------

    public ChanceFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.binding = FragmentChanceBinding.inflate(inflater, container, false);
        return this.binding.getRoot();
    }

    public void init(GameEngine gameEngine, ChanceField chanceField) {
        this.gameEngine = gameEngine;
        this.chanceField = chanceField;

        this.binding.fieldLabelTextview.setText(chanceField.getLabel());
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
            new MoneyChanceAction("Income tax refund. Collect $20.",20),
            new MoneyChanceAction("Bank pays you dividend of $50.",50),
            new MoneyChanceAction("You inherit $100.",100),
            new MoneyChanceAction("Pay poor tax of $15.",-15),
            new MoneyChanceAction("Hospital Fees. Pay $50.",-50),
            new MoneyChanceAction("School fees. Pay $50.",-50),
            new GoToJailChanceAction("Go directly to jail.")
    };



}