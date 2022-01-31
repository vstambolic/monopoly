package com.example.monopoly.game.custom_views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.text.StaticLayout;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.monopoly.R;

public class SquareField extends Field{
    // Attributes -------------------------------------------
    private final Drawable drawable;

    @Override
    protected void init() {
        super.init();
    }

    public SquareField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SquareField,
                0, 0);

        try {
            this.drawable = a.getDrawable(R.styleable.SquareField_square_logo);

        } finally {
            a.recycle();
        }

        this.init();
    }

    @Override
    protected void drawComponents(Canvas canvas) {
        this.drawable.setBounds(0,0,canvas.getClipBounds().width(),canvas.getClipBounds().height());
        this.drawable.draw(canvas);
    }
}
