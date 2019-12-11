package com.egorovsoft.myrain.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.egorovsoft.myrain.R;

import androidx.annotation.Nullable;

public class SunView extends View {
    public static final String TAG = "SUN_VIEW";
    public static final String DEF_TEXT = "0";
    public static final int DEF_COLOR = Color.BLUE;
    public static final int DEF_LENGTH = 5;
    public static final int DEF_RADIUS = 10;
    public static final int Y = 100;
    public static final int X = 100;

    private Paint paint;
    private int radius;
    private int length;
    private int color;
    private String text;

    public void setText(String text) {
        this.text = text;
    }

    public SunView(Context context) {
        super(context);
        init(DEF_COLOR, DEF_RADIUS, DEF_LENGTH, DEF_TEXT);
    }

    public SunView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        initAttr(context, attrs);
    }

    public SunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, Paint paint) {
        super(context, attrs, defStyleAttr);

        initAttr(context, attrs);
        this.paint = paint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int x = X + radius;
        int y = Y + radius;
        canvas.drawCircle(x, y, radius, paint);

        canvas.drawLine(x - radius - 10, y, x - radius - length, y, paint);
        canvas.drawLine(x, y - radius - 10, x, y - radius - length, paint);
        canvas.drawLine(x + radius + 10, y, x + radius + length, y, paint);
        canvas.drawLine(x, y + radius + 10 , x, y + radius + length, paint);
        canvas.drawLine(x - radius, y - radius, x - radius - length, y - radius - length, paint);
        canvas.drawLine(x + radius, y + radius, x + radius + length, y + radius + length, paint);
        canvas.drawLine(x - radius, y + radius, x - radius - length, y + radius + length, paint);
        canvas.drawLine(x + radius, y - radius, x + radius + length, y - radius - length, paint);

        canvas.drawText(text, x - text.length() * radius / 3, y + radius / 3, paint);
    }

    private void init(int c, int r, int l, String t){
        Log.d(TAG, "Constructor");

        paint = new Paint();
        paint.setColor(c);
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(r);

        this.radius = r;
        this.length = l;
        this.color = c;
        this.text = t;
    }

    private  void initAttr(Context context, @Nullable AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(
                attrs,
                R.styleable.SunView,
                0,
                0
        );

        int r = typedArray.getInt(R.styleable.SunView_sunview_radius, DEF_RADIUS);
        int l = typedArray.getInt(R.styleable.SunView_sunview_length_line, DEF_LENGTH);
        int c = typedArray.getInt(R.styleable.SunView_sunview_color, DEF_COLOR);
        String t = typedArray.getString(R.styleable.SunView_sunview_text);

        if(t == null) {
            t= DEF_TEXT;
        }

        init(c, r, l, t);
    }

    @Override
    protected void onAttachedToWindow() {
        Log.d(TAG, "onAttachedToWindow");
        super.onAttachedToWindow();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d(TAG, "onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int height = getMeasuredHeight();	// высота
        final int width = getMeasuredWidth();	// ширина

        ///{{ В дальнейшем нужно ещё разобраться с getMode() и getSize()

        setMeasuredDimension(width, Math.max(width, height));
    }

    @Override
    public void layout(int l, int t, int r, int b) {
        Log.d(TAG, "layout");
        super.layout(l, t, r, b);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        Log.d(TAG, "onLayout");
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.d(TAG, "draw");
        super.draw(canvas);
    }
}
