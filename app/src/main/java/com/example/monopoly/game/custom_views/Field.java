package com.example.monopoly.game.custom_views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.monopoly.R;
import com.example.monopoly.game.Constants;

import java.util.ArrayList;

public abstract class Field extends View {

    // Attributes -------------------------------------------
    protected final int rotation;

    // Paints -------------------------------------------

    protected final Paint framePaint = new Paint();
    protected final Paint frameThinPaint = new Paint();
    protected final Paint playerPaint = new Paint();
    protected final TextPaint textPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);

    // Players -------------------------------------------

    protected ArrayList<Integer> players = new ArrayList<>();
    public void addPlayer(Integer playerNumber) {
        this.players.add(playerNumber);
    }
    public void removePlayer(Integer playerNumber) {
        this.players.remove(playerNumber);
    }

    private static final int PLAYER_RADIUS = 16;

    protected void init() {
        this.frameThinPaint.setColor(Color.BLACK);
        this.frameThinPaint.setStrokeWidth(1f);
        this.frameThinPaint.setStyle(Paint.Style.STROKE);

        this.framePaint.setColor(Color.BLACK);
        this.framePaint.setStrokeWidth(1.5f);
        this.framePaint.setStyle(Paint.Style.STROKE);

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize((int) (12 * 1));
        textPaint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
    }

    public Field(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Field,
                0, 0);

        try {
            this.rotation = a.getInteger(R.styleable.Field_field_position, 0);
        } finally {
            a.recycle();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        this.rotateCanvas(canvas);
        this.drawComponents(canvas);
        this.drawPlayers(canvas);
        canvas.restore();

        canvas.drawRect(0, 0, getWidth(), getHeight(), this.framePaint);
    }


    protected void drawPlayers(Canvas canvas) {
        int whalf = canvas.getClipBounds().width() / 2;
        int hhalf = canvas.getClipBounds().height() / 2;
        /*
            Coordinates:
            1: cx = (w/2 - (5+r))    |    cy = (h/2 - (5 + r))
            2: cx = (w/2 + (5+r))    |    cy = (h/2 - (5 + r))
            1: cx = (w/2 - (5+r))    |    cy = (h/2 + (5 + r))
            1: cx = (w/2 + (5+r))    |    cy = (h/2 + (5 + r))
         */

        for (short i = 0, signx = -1, signy = -1; i < this.players.size(); i++, signx *= -1, signy *= signx) {
            this.playerPaint.setColor(Constants.PLAYER_COLORS[this.players.get(i)]);
            float cx = whalf + signx * (3 + PLAYER_RADIUS);
            float cy = hhalf + signy * (3 + PLAYER_RADIUS);
            canvas.drawCircle(cx,cy,PLAYER_RADIUS, this.playerPaint);
            canvas.drawCircle(cx,cy,PLAYER_RADIUS, this.frameThinPaint);
        }
    }

    protected abstract void drawComponents(Canvas canvas);

    protected void rotateCanvas(Canvas canvas) {
        canvas.translate(
                this.rotation == 90 || this.rotation == 180 ? getWidth() : 0
                , this.rotation == 180 || this.rotation == 270 ? getHeight() : 0);
        canvas.rotate(this.rotation);
        /*             Translation

                |     x       |       y
        -----------------------------------------
        0       |     0       |       0
        -----------------------------------------
        90      |     W       |       0
        -----------------------------------------
        180     |     W       |       H
        -----------------------------------------
        270     |     0       |       H             */
    }

    public void clear() {
        this.players.clear();
        this.invalidate();
    }
}
