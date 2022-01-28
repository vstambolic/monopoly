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

public abstract class Field extends View {

    // Attributes -------------------------------------------
    protected final int rotation;

    protected final Paint framePaint = new Paint();

    protected void init() {
        this.framePaint.setColor(Color.BLACK);
        this.framePaint.setStrokeWidth( 1.5f );
        this.framePaint.setStyle( Paint.Style.STROKE );
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
        canvas.restore();


        canvas.drawRect(0,0,getWidth(),getHeight(),this.framePaint);
    }

    protected abstract void drawComponents(Canvas canvas);

    protected void rotateCanvas(Canvas canvas) {
            /* Translation

                |     x       |       y
        -----------------------------------------
        0       |     0       |       0
        -----------------------------------------
        90      |     W       |       0
        -----------------------------------------
        180     |     W       |       H
        -----------------------------------------
        270     |     0       |       H
         */
        canvas.translate(
                this.rotation == 90 || this.rotation == 180 ? getWidth() : 0
                , this.rotation == 180 || this.rotation == 270 ? getHeight() : 0);

        canvas.rotate(this.rotation);

    }
}
