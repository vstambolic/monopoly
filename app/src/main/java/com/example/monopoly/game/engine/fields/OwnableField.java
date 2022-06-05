package com.example.monopoly.game.engine.fields;

import com.example.monopoly.game.engine.Player;

public abstract class OwnableField extends Field {
    protected boolean mortgaged = false;
    protected transient Player owner = null;
    public Player getOwner() {
        return owner;
    }
    public boolean hasOwner() {
        return this.owner != null;
    }

    public void setOwner(Player currentPlayer) {
        this.owner = currentPlayer;
    }

    public OwnableField(int id, String label) {
        super(id, label);
    }

    public abstract String getDescription();

    public abstract int calculateMortgage();
    public abstract int calculateNetWorth();

    public boolean isMortgaged() {
        return this.mortgaged;
    }
    public void setMortgaged(boolean mortgaged) {
        this.mortgaged = mortgaged;
    }

    public abstract int calculateLiftMortgage();
}
