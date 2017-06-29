package com.example.sujaynaik.myapplication.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by sujaynaik on 6/5/17.
 */

public class MyFont {

    private Context context;
    private Typeface mTypeface;

    private static final String CaviarDreams = "CaviarDreams.ttf";
    private static final String CaviarDreamsBold = "Caviar_Dreams_Bold.ttf";

    private static final String DEFAULT_FONT = CaviarDreams;
    private final String folder = "font/";

    public MyFont(Context context) {
        this.context = context;
        mTypeface = Typeface.createFromAsset(context.getAssets(), folder + DEFAULT_FONT);
    }

    public MyFont(Context context, boolean isFontLight) {
        this.context = context;
        String font;

        if (isFontLight) {
            font = folder + CaviarDreams;
        } else {
            font = folder + CaviarDreamsBold;
        }

        mTypeface = Typeface.createFromAsset(context.getAssets(), font);
    }

    public void setFontRegular() {
        String font = folder + DEFAULT_FONT;
        mTypeface = Typeface.createFromAsset(context.getAssets(), font);
    }

    public void setFontLight() {
        String font = folder + CaviarDreams;
        mTypeface = Typeface.createFromAsset(context.getAssets(), font);
    }

    public void setFontBold() {
        String font = folder + CaviarDreamsBold;
        mTypeface = Typeface.createFromAsset(context.getAssets(), font);
    }

    public Typeface getFontRegular() {
        String font = folder + DEFAULT_FONT;
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public Typeface getFontLight() {
        String font = folder + CaviarDreams;
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public Typeface getFontBold() {
        String font = folder + CaviarDreamsBold;
        return Typeface.createFromAsset(context.getAssets(), font);
    }

    public void setTypeface(ViewGroup viewTree) {
        View child;
        for (int i = 0; i < viewTree.getChildCount(); ++i) {
            child = viewTree.getChildAt(i);

            if (child instanceof ViewGroup) {
                setTypeface((ViewGroup) child);

            } else if (child instanceof TextView) {
                setTypeface((TextView) child);

            } else if (child instanceof EditText) {
                setTypeface((EditText) child);

            } else if (child instanceof Button) {
                setTypeface((Button) child);

            } else if (child instanceof SwitchCompat) {
                setTypeface((SwitchCompat) child);
            }
        }
    }

    public void setTypeface(TextView textView) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(EditText textView) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(Button textView) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(SwitchCompat textView) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(ViewGroup viewTree, Typeface mTypeface) {
        View child;
        for (int i = 0; i < viewTree.getChildCount(); ++i) {
            child = viewTree.getChildAt(i);

            if (child instanceof ViewGroup) {
                setTypeface((ViewGroup) child, mTypeface);

            } else if (child instanceof TextView) {
                setTypeface((TextView) child, mTypeface);

            } else if (child instanceof EditText) {
                setTypeface((EditText) child, mTypeface);

            } else if (child instanceof Button) {
                setTypeface((Button) child, mTypeface);

            } else if (child instanceof SwitchCompat) {
                setTypeface((SwitchCompat) child, mTypeface);
            }
        }
    }

    public void setTypeface(TextView textView, Typeface mTypeface) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(EditText textView, Typeface mTypeface) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(Button textView, Typeface mTypeface) {
        textView.setTypeface(mTypeface);
    }

    public void setTypeface(SwitchCompat textView, Typeface mTypeface) {
        textView.setTypeface(mTypeface);
    }
}
