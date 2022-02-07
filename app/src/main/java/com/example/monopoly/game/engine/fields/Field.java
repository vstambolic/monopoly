package com.example.monopoly.game.engine.fields;

import androidx.fragment.app.FragmentManager;

import com.example.monopoly.game.engine.GameEngine;

import java.util.ArrayList;
import java.util.List;

public abstract class Field {
    public static final int TOTAL_FIELD_CNT = 40;

    protected String label;
    public static final Field[] fields = new Field[TOTAL_FIELD_CNT];

    static {
        fields[0] = new NoActionField("START", "You received a $200 salary!");
        fields[1] = new GoToJailField("Start"); // TODO
        fields[2] = new GoToJailField("Start"); // TODO
        fields[3] = new GoToJailField("Start"); // TODO
        fields[4] = new TaxField("INCOME TAX", "You must pay $200 for taxes!",200);
        fields[5] = new GoToJailField("Start"); // TODO
        fields[6] = new GoToJailField("Start"); // TODO
        fields[7] = new GoToJailField("Start"); // TODO
        fields[8] = new GoToJailField("Start"); // TODO
        fields[9] = new GoToJailField("Start"); // TODO
        fields[10] = new NoActionField("IN JAIL", "Just visiting!");
        fields[11] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[12] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[13] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[14] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[15] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[16] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[17] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[18] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[19] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[20] = new NoActionField("FREE PARKING", "This is a \"free\" resting place!");
        fields[21] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[22] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[23] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[24] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[25] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[26] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[27] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[28] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[29] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[30] = new GoToJailField("GO TO JAIL");
        fields[31] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[32] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[33] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[34] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[35] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[36] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[37] = new NoActionField("Start", "You received a $200 salary!"); // TODO
        fields[38] = new TaxField("LUXURY TAX", "You must pay $100 for taxes!",100);
        fields[39] = new NoActionField("Start", "You received a $200 salary!"); // TODO
    }

    public Field(String label) {
        this.label = label;
    }

    public abstract void action(GameEngine gameEngine);
    /*
        NoActionField -> Start, Jail
        OwnableField  ->
            PropertyField
        TaxField ->
        FreeParkingField ->
        ChanceField ->
     */
}
