package com.example.monopoly.game.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monopoly.R;

public class PropertyField extends OwnableField {
    private static final int RECT_HEIGHT = 20;
    // Attributes -------------------------------------------
    private final int rectColor;
    private final String propertyPrice;
    private final String propertyName;

    private final Paint rectPaint = new Paint();

    @Override
    protected void init() {
        super.init();
        this.rectPaint.setColor(rectColor);
    }

    public PropertyField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.PropertyField,
                0, 0);

        try {
            this.rectColor = a.getColor(R.styleable.PropertyField_field_color,0x55FF0000);
            this.propertyName = a.getString(R.styleable.PropertyField_field_text) == null? "" :  a.getString(R.styleable.PropertyField_field_text);
            this.propertyPrice ="$"+ a.getInteger(R.styleable.PropertyField_field_price, 999);

        } finally {
            a.recycle();
        }
        this.init();
    }

    @Override
    protected void drawComponents(Canvas canvas) {
        canvas.drawRect(0, 0, canvas.getClipBounds().width(), RECT_HEIGHT, this.rectPaint);
        canvas.drawRect(0, 0, canvas.getClipBounds().width(), RECT_HEIGHT, this.framePaint);
        // set text width to canvas width minus 15dp padding
        int textWidth = canvas.getClipBounds().width()- (int) (15 * 1);
        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                this.propertyName, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        // draw text to the Canvas center
        canvas.save();
        canvas.translate(8, 25);
        textLayout.draw(canvas);
        canvas.restore();

        textLayout = new StaticLayout(
                this.propertyPrice, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        canvas.save();
        canvas.translate(8, canvas.getClipBounds().height()-25);
        textLayout.draw(canvas);
        canvas.restore();

        super.drawComponents(canvas);
    }
}
