package com.example.monopoly.game.custom_views;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.monopoly.R;
import com.example.monopoly.databinding.MonopolyBinding;
import com.example.monopoly.databinding.PlayerWinnerBinding;

public class Monopoly extends ConstraintLayout {

    public Monopoly(@NonNull Context context) {
        super(context);
        this.init(context);
    }

    public Monopoly(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public Monopoly(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    public Monopoly(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init(context);
    }

    private MonopolyBinding binding;


    private void init(Context context) {
        this.binding = MonopolyBinding.inflate(LayoutInflater.from(context), this, true);


          int x= ((ConstraintLayout)this.getChildAt(0)).getChildAt(0).getId();
          int y=   ((ConstraintLayout)this.getChildAt(0)).getChildAt(1).getId();
//       String t = childAt.getText().toString();
//                Button childAt1 = (Button)this.getChildAt(1);
//        String t1 = childAt.getText().toString();
//                Button childAt2 = (Button)this.getChildAt(2);
//        String t2 = childAt.getText().toString();
//                Button childAt3 = (Button)this.getChildAt(3);
//        String t3 = childAt.getText().toString();
//                Button childAt4 = (Button)this.getChildAt(4);
//        String t4 = childAt.getText().toString();

    }

    void log(int x) {
        switch (x) {
            case MeasureSpec.UNSPECIFIED:
                Log.d("", "uncpecified");
                break;
            case MeasureSpec.AT_MOST:
                Log.d("","at most");
break;
            case MeasureSpec.EXACTLY:
                Log.d("","exactcly");
break;

        }
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthPixels = View.MeasureSpec.getSize( widthMeasureSpec );
        int h = View.MeasureSpec.getSize( heightMeasureSpec );
        int spec = h>widthPixels? widthMeasureSpec:heightMeasureSpec;
        super.onMeasure(spec, spec);
    }
}
