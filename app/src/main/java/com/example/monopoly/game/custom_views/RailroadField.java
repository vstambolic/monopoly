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

public class RailroadField extends Field {
    private static final int RECT_HEIGHT = 20;
    // Attributes -------------------------------------------
    private final String railroadName;

    private final TextPaint textPaint =new TextPaint(Paint.ANTI_ALIAS_FLAG);
    private Drawable railroadImage;




    @Override
    protected void init() {
        super.init();

        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize((int) (13 * 1));
        textPaint.setShadowLayer(1f, 0f, 1f, Color.WHITE);
    }

    public RailroadField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.RailroadField,
                0, 0);

        try {
            this.railroadName = a.getString(R.styleable.RailroadField_railroad_name) == null? "" :  a.getString(R.styleable.RailroadField_railroad_name);
        } finally {
            a.recycle();
        }

        railroadImage = context.getResources().getDrawable(R.drawable.railroad);
        this.init();
    }

    @Override
    protected void drawComponents(Canvas canvas) {

        // set text width to canvas width minus 15dp padding
        int textWidth = canvas.getClipBounds().width()- (int) (15 * 1);
        // init StaticLayout for text
        StaticLayout textLayout = new StaticLayout(
                this.railroadName, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);
        // draw text to the Canvas center
        canvas.save();
        canvas.translate(8, 8);
        textLayout.draw(canvas);
        canvas.restore();

        railroadImage.setBounds(0,
                textLayout.getHeight() + 5,
                canvas.getClipBounds().width(),
                textLayout.getHeight() + 5 + canvas.getClipBounds().width());

        railroadImage.draw(canvas);

        textLayout = new StaticLayout(
                "$200", textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        canvas.save();
        canvas.translate(8, canvas.getClipBounds().height()-25);
        textLayout.draw(canvas);
        canvas.restore();
    }
}
