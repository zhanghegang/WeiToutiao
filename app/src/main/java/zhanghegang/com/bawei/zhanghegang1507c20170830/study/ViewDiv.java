package zhanghegang.com.bawei.zhanghegang1507c20170830.study;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by asus on 2017/9/1.
 */

public class ViewDiv extends View {

    private int ysize;
    private int xsize;
    private int ban=50;

    public ViewDiv(Context context) {
        this(context,null);
    }

    public ViewDiv(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewDiv(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = this.getWidth();
        int height = this.getHeight();
        xsize = width/2;
        ysize = height/2;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint=new Paint();
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(2);
        paint.setAntiAlias(true);
        canvas.drawCircle(xsize,ysize,ban,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y= (int) event.getY();
                boolean b = onIsBall(x, y);
                if(b)

                {
                    Toast.makeText(getContext(), "范围内", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getContext(), "范围外", Toast.LENGTH_SHORT).show();

                }

                break;
            case MotionEvent.ACTION_MOVE:
                int ydX = (int) event.getX();
                int ydY= (int) event.getY();
                xsize=ydX;
                ysize=ydY;
                break;
        }

        return super.onTouchEvent(event);
    }

    private boolean onIsBall(int x, int y) {
        double sqrt = Math.sqrt((x - xsize) * (x - xsize) + (y - ysize) * (y - ysize));
   if(sqrt<ban)
   {
       return true;
   }
   return false;
    }
}
