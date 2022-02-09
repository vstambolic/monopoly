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
        fields[1] = new PropertyField("MEDITERANEAN AVENUE",0,2,60,2,5,new int[]{10,30,90,160},250,50,100);
        fields[2] = new ChanceField("COMMUNITY CHEST"); 
        fields[3] = new PropertyField("BALTIC AVENUE",0,2,60,4,10,new int[]{20,60,180,320},450,50,100);
        fields[4] = new TaxField("INCOME TAX", "You must pay $200 for taxes!",200);
        fields[5] = new RailroadField("READING RAILROAD");
        fields[6] = new PropertyField("ORIENTAL AVENUE",1,3,100,6,15,new int[]{30,90,270,400},550,50,100);
        fields[7] = new ChanceField("CHANCE");
        fields[8] = new PropertyField("VERMONT AVENUE",1,3,100,6,15,new int[]{30,90,270,400 },550,50,100);
        fields[9] = new PropertyField("CONNECTICUT AVENUE",1,3,120,8,20,new int[]{40,100,300,450},600,50,100);

        fields[10] = new NoActionField("IN JAIL", "Just visiting!");
        fields[11] = new PropertyField("ST. CHARLES PLACE",2,3,140,10,25,new int[]{50,150,150,625},750,100,200);
        fields[12] = new UtilityField("ELECTRIC COMPANY");
        fields[13] = new PropertyField("STATES AVENUE",2,3,140,10,25,new int[]{50,150,150,625},750,100,2000);
        fields[14] = new PropertyField("VIRGINIA AVENUE",2,3,160,12,30,new int[]{60,180,500,700},900,100,200);
        fields[15] = new RailroadField("PENNSYLVANIA RAILROAD");
        fields[16] = new PropertyField("ST. JAMES PLACE",3,3,180,14,35,new int[]{70,200,550,750},950,100,200);
        fields[17] = new ChanceField("COMMUNITY CHEST");
        fields[18] = new PropertyField("TENNESSEE AVENUE",3,3,180,14,35,new int[]{70,200,550,750},950,100,200);
        fields[19] = new PropertyField("NEW YORK AVENUE",3,3,200,16,40,new int[]{80,220,600,800},1000,150,300);

        fields[20] = new NoActionField("FREE PARKING", "This is a \"free\" resting place!");
        fields[21] = new PropertyField("KENTUCKY AVENUE",4,3,220,18,45,new int[]{90,250,700,875},1050,150,300);
        fields[22] = new ChanceField("CHANCE");
        fields[23] = new PropertyField("INDIANA AVENUE",4,3,220,18,45,new int[]{90,250,700,875},1050,150,300);
        fields[24] = new PropertyField("ILLINOIS AVENUE",4,3,240,20,50,new int[]{100,300,750,925},1100,150,300);
        fields[25] = new RailroadField("B. & O. RAILROAD");
        fields[26] = new PropertyField("ATLANTIC AVENUE",5,3,260,22,55,new int[]{110,330,800,975},1150,150,300);
        fields[27] = new PropertyField("VENTNOR AVENUE",5,3,260,22,55,new int[]{110,330,800,975},1150,150,300);
        fields[28] = new UtilityField("Start");
        fields[29] = new PropertyField("MARVIN GARDENS",5,3,280,24,60,new int[]{120,360,850,1025},1200,150,300);

        fields[30] = new GoToJailField("GO TO JAIL");
        fields[31] = new PropertyField("PACIFIC AVENUE",6,3,300,26,65,new int[]{130,390,900,1100},1275,200,400);
        fields[32] = new PropertyField("NORTH CAROLINA AVENUE",6,3,300,26,65,new int[]{130,390,900,1100},1275,200,400);
        fields[33]= new ChanceField("COMMUNITY CHEST");
        fields[34] = new PropertyField("PENNSYLVANIA AVENUE",6,3,320,28,75,new int[]{150,450,1000,1200},1400,200,400);
        fields[35] = new RailroadField("SHORT LINE");
        fields[36] = new ChanceField("CHANCE");
        fields[37] = new PropertyField("PARK PLACE",7,2,350,35,85,new int[]{175,500,1100,1300},1500,200,400);
        fields[38] = new TaxField("LUXURY TAX", "You must pay $100 for taxes!",100);
        fields[39] = new PropertyField("BOARDWALK",7,2,400,50,100,new int[]{200,600,1400,1700},2000,200,400);
    }

    public Field(String label) {
        this.label = label;
    }

    public abstract void action(GameEngine gameEngine);

    public  String getLabel() {
        return this.label;
    }
}
