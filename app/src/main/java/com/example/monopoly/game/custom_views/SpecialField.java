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

public class SpecialField extends OwnableField {
    // Attributes -------------------------------------------
    private final String label;
    private final Drawable image;
    private final String price;


    @Override
    protected void init() {
        super.init();
   }

    public SpecialField(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.SpecialField,
                0, 0);

        try {
            boolean isTax = a.getBoolean(R.styleable.SpecialField_special_is_tax,false);

            this.label = a.getString(R.styleable.SpecialField_special_label) == null? "" :  a.getString(R.styleable.SpecialField_special_label);
            this.price = (isTax? "Pay $":"$") + a.getInteger(R.styleable.SpecialField_special_price, 999);
            this.image = a.getDrawable(R.styleable.SpecialField_special_logo);

        } finally {
            a.recycle();
        }

//        image = context.getResources().getDrawable(R.drawable.railroad);
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

        image.setBounds(0,
                textLayout.getHeight() + 5,
                canvas.getClipBounds().width(),
                textLayout.getHeight() + 5 + canvas.getClipBounds().width());

        image.draw(canvas);

        textLayout = new StaticLayout(
                this.price, textPaint, textWidth, Layout.Alignment.ALIGN_CENTER, 1.0f, 0.0f, false);

        canvas.save();
        canvas.translate(8, canvas.getClipBounds().height()-25);
        textLayout.draw(canvas);
        canvas.restore();


        super.drawComponents(canvas);

    }
}
