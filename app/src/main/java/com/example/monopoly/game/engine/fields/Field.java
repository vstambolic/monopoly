package com.example.monopoly.game.engine.fields;

import androidx.fragment.app.FragmentManager;

import com.example.monopoly.R;
import com.example.monopoly.game.engine.GameEngine;
import com.example.monopoly.game.fragments.ControllerFragment;

import java.io.Serializable;

public abstract class Field implements Serializable {
    public static final int TOTAL_FIELD_CNT = 40;
    public static final Field[] fields = new Field[TOTAL_FIELD_CNT];

    protected String label;
    private final int id;

    static {
        fields[0] = new NoActionField(0,"START", "You received a $200 salary!");
        fields[1] = new PropertyField(1,"MEDITERANEAN AVENUE",0,2,60,2,5,new int[]{10,30,90,160},250,50,100);
        fields[2] = new ChanceField(2,"COMMUNITY CHEST");
        fields[3] = new PropertyField(3,"BALTIC AVENUE",0,2,60,4,10,new int[]{20,60,180,320},450,50,100);
        fields[4] = new TaxField(4,"INCOME TAX", "You must pay $200 for taxes!",200);
        fields[5] = new RailroadField(5,"READING RAILROAD");
        fields[6] = new PropertyField(6,"ORIENTAL AVENUE",1,3,100,6,15,new int[]{30,90,270,400},550,50,100);
        fields[7] = new ChanceField(7,"CHANCE");
        fields[8] = new PropertyField(8,"VERMONT AVENUE",1,3,100,6,15,new int[]{30,90,270,400 },550,50,100);
        fields[9] = new PropertyField(9,"CONNECTICUT AVENUE",1,3,120,8,20,new int[]{40,100,300,450},600,50,100);

        fields[10] = new NoActionField(10,"IN JAIL", "Just visiting!");
        fields[11] = new PropertyField(11,"ST. CHARLES PLACE",2,3,140,10,25,new int[]{50,150,150,625},750,100,200);
        fields[12] = new UtilityField(12,"ELECTRIC COMPANY");
        fields[13] = new PropertyField(13,"STATES AVENUE",2,3,140,10,25,new int[]{50,150,150,625},750,100,2000);
        fields[14] = new PropertyField(14,"VIRGINIA AVENUE",2,3,160,12,30,new int[]{60,180,500,700},900,100,200);
        fields[15] = new RailroadField(15,"PENNSYLVANIA RAILROAD");
        fields[16] = new PropertyField(16,"ST. JAMES PLACE",3,3,180,14,35,new int[]{70,200,550,750},950,100,200);
        fields[17] = new ChanceField(17,"COMMUNITY CHEST");
        fields[18] = new PropertyField(18,"TENNESSEE AVENUE",3,3,180,14,35,new int[]{70,200,550,750},950,100,200);
        fields[19] = new PropertyField(19,"NEW YORK AVENUE",3,3,200,16,40,new int[]{80,220,600,800},1000,150,300);

        fields[20] = new NoActionField(20,"FREE PARKING", "This is a \"free\" resting place!");
        fields[21] = new PropertyField(21,"KENTUCKY AVENUE",4,3,220,18,45,new int[]{90,250,700,875},1050,150,300);
        fields[22] = new ChanceField(22,"CHANCE");
        fields[23] = new PropertyField(23,"INDIANA AVENUE",4,3,220,18,45,new int[]{90,250,700,875},1050,150,300);
        fields[24] = new PropertyField(24,"ILLINOIS AVENUE",4,3,240,20,50,new int[]{100,300,750,925},1100,150,300);
        fields[25] = new RailroadField(25,"B. & O. RAILROAD");
        fields[26] = new PropertyField(26,"ATLANTIC AVENUE",5,3,260,22,55,new int[]{110,330,800,975},1150,150,300);
        fields[27] = new PropertyField(27,"VENTNOR AVENUE",5,3,260,22,55,new int[]{110,330,800,975},1150,150,300);
        fields[28] = new UtilityField(28,"Start");
        fields[29] = new PropertyField(29,"MARVIN GARDENS",5,3,280,24,60,new int[]{120,360,850,1025},1200,150,300);

        fields[30] = new GoToJailField(30,"GO TO JAIL");
        fields[31] = new PropertyField(31,"PACIFIC AVENUE",6,3,300,26,65,new int[]{130,390,900,1100},1275,200,400);
        fields[32] = new PropertyField(32,"NORTH CAROLINA AVENUE",6,3,300,26,65,new int[]{130,390,900,1100},1275,200,400);
        fields[33]= new ChanceField(33,"COMMUNITY CHEST");
        fields[34] = new PropertyField(34,"PENNSYLVANIA AVENUE",6,3,320,28,75,new int[]{150,450,1000,1200},1400,200,400);
        fields[35] = new RailroadField(35,"SHORT LINE");
        fields[36] = new ChanceField(36,"CHANCE");
        fields[37] = new PropertyField(37,"PARK PLACE",7,2,350,35,85,new int[]{175,500,1100,1300},1500,200,400);
        fields[38] = new TaxField(38,"LUXURY TAX", "You must pay $100 for taxes!",100);
        fields[39] = new PropertyField(39,"BOARDWALK",7,2,400,50,100,new int[]{200,600,1400,1700},2000,200,400);
    }

    public Field(int id, String label) {
        this.id=id;
        this.label = label;
    }

    public abstract void action(GameEngine gameEngine);

    public  String getLabel() {
        return this.label;
    }
    public int getId() {
        return id;
    }
    protected void action(GameEngine gameEngine, ControllerFragment controllerFragment) {
        FragmentManager fragmentManager = gameEngine.getGameFragment().getChildFragmentManager();
        controllerFragment.init(gameEngine,this);
        fragmentManager
                .beginTransaction()
                .replace(R.id.controller_frame,controllerFragment)
                .commit();
    }


}
