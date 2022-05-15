package com.example.monopoly.game.custom_views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monopoly.R;
import com.example.monopoly.game.Constants;

public abstract class OwnableField extends Field {
    protected final Paint ownedFramePaint = new Paint();

    private static final int HOUSE_GAP = 0;
    private static final float STROKE_WIDTH = 5f;

    @Override
    protected void init() {
        super.init();
        this.ownedFramePaint.setStrokeWidth(STROKE_WIDTH);
        this.ownedFramePaint.setStyle(Paint.Style.STROKE);
    }

    public static final int NOBODY = -1;
    // Attributes -------------------------------------------
    private int owner = NOBODY;
    private int houseCnt = 0;
    private int hotelCnt = 0;
    private Drawable drawableHouse;
    private Drawable drawableHotel;


    public void setOwner(int owner) {
        this.owner = owner;
        if (owner != NOBODY)
            this.ownedFramePaint.setColor(Constants.PLAYER_COLORS[owner]);
    }

    public void setHouseCnt(int houseCnt) {
        this.houseCnt = houseCnt;
    }

    public void setHotelCnt(int hotelCnt) {
        this.hotelCnt = hotelCnt;
    }

    public OwnableField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.drawableHouse = context.getResources().getDrawable(R.drawable.home_green);
        this.drawableHotel = context.getResources().getDrawable(R.drawable.home_red);
    }

    @Override
    protected void drawComponents(Canvas canvas) {

        if (this.owner != NOBODY) {

            int w = canvas.getClipBounds().width();
            int h = canvas.getClipBounds().height();
            int houseIconDim = (w-2*(int)STROKE_WIDTH)/4;
            canvas.drawRect(1, 1, w - 1, h - 1, this.ownedFramePaint);

            for (int i = 0; i < this.houseCnt; i++) {
                int left = (int)STROKE_WIDTH + i * (houseIconDim + HOUSE_GAP);
                int right = left + houseIconDim;
                this.drawableHouse.setBounds(left,h - (int)STROKE_WIDTH - houseIconDim, right, h - (int)STROKE_WIDTH);
                this.drawableHouse.draw(canvas);
            }
            for (int i = 0; i < this.hotelCnt; i++) {
                int left =(int)STROKE_WIDTH+ i * (houseIconDim + HOUSE_GAP);
                int right = left + houseIconDim;
                this.drawableHotel.setBounds(left,h - (int)STROKE_WIDTH - houseIconDim*2, right, h - (int)STROKE_WIDTH-houseIconDim);
                this.drawableHotel.draw(canvas);
            }
        }

    }

    public int getHouseCnt() {
        return this.houseCnt;
    }

    public int getHotelCnt() {
        return this.hotelCnt;
    }
}
