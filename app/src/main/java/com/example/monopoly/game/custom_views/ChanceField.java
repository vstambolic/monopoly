package com.example.monopoly.game.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monopoly.R;

public class ChanceField extends Field {
    // Attributes -------------------------------------------
    private final String label;
    private final Drawable drawable;

    private final int paddingTop;
    private final int paddingBottom;
    private final int paddingLeft;
    private final int paddingRight;

    @Override
    protected void init() {
        super.init();

    }

    public ChanceField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ChanceField,
                0, 0);

        try {
            String type = a.getString(R.styleable.ChanceField_chance_type) == null? "" :  a.getString(R.styleable.ChanceField_chance_type);
            if (type.equals("chance")) {
                this.drawable = context.getResources().getDrawable(R.drawable.chance);
                this.label = "CHANCE";
                this.paddingTop=8;
                this.paddingBottom=0;
                this.paddingLeft=8;
                this.paddingRight=8;

            }
            else {
                this.drawable = context.getResources().getDrawable(R.drawable.chest);
                this.label = "COMMUNITY CHEST";
                this.paddingTop=8;
                this.paddingBottom=8;
                this.paddingLeft=8;
                this.paddingRight=8;

            }
        } finally {
            a.recycle();
        }

        this.init();
    }

    @Override
    protected void drawComponents(Canvas canvas) {

        // set text width to canvas width minus 15dp padding
        int textWidth = canvas.getClipBounds().width()- (int) (15 * 1);
        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                this.label, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        // draw text to the Canvas center
        canvas.save();
        canvas.translate(8, 8);
        textLayout.draw(canvas);
        canvas.restore();


        this.drawable.setBounds(0+paddingLeft,
                textLayout.getHeight() + 5+paddingTop,
                canvas.getClipBounds().width()-paddingRight,
                textLayout.getHeight() + 5 + canvas.getClipBounds().width()-paddingBottom);

        this.drawable.draw(canvas);
    }
}
