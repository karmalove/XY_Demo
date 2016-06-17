package com.example.kevin.xy_demo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @author : bat19931026@gmail.com
 * @ClassName : CoordinatesView
 */
public class Coordinates extends View {
    private int x1, y1, z1;


    /*
     * 自定义控件一般写两个构造方法 CoordinatesView(Context context)用于java硬编码创建控件
     * 如果想要让自己的控件能够通过xml来产生就必须有第2个构造方法 CoordinatesView(Context context,
     * AttributeSet attrs) 因为框架会自动调用具有AttributeSet参数的这个构造方法来创建继承自View的控件
     */
    public Coordinates(Context context) {
        super(context, null);



    }

    public Coordinates(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     * 两个外部数据
     */
    Point pa = new Point(10, 10);
    Point pb = new Point(20, 40);

    /*
     * 圆心（坐标值是相对与控件的左上角的）
     */
    Point po = new Point();


    /*
     * 控件的中心点
     */
    int centerX, centerY;

    /*
     * 控件创建完成之后，在显示之前都会调用这个方法，此时可以获取控件的大小 并得到中心坐标和坐标轴圆心所在的点。
     */
    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w / 2;
        centerY = h / 2;
        po.set(centerX, centerY);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /*
     * 自定义控件一般都会重载onDraw(Canvas canvas)方法，来绘制自己想要的图形
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(10);
        paint.setColor(Color.BLACK);


        // 画坐标轴
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            // 画直线
            canvas.drawLine(centerX, centerY, centerX+150, centerY, paint);
            canvas.drawLine(centerX, centerY, centerX,  centerY-150, paint);
            // 画X轴箭头
            int x = 155 + centerX, y = centerY;
            drawTriangle(canvas, new Point(x, y), new Point(x - 40, y - 30),
                    new Point(x - 40, y + 30));

            x = centerX;
            y = centerY - 155;
            drawTriangle(canvas, new Point(x, y), new Point(x - 30, y + 40),
                    new Point(x + 30, y + 40));

            paint.setStrokeWidth(6);
            paint.setTextSize(40);
            paint.setColor(Color.GRAY);
            canvas.drawText("X: " + x1 + " Y: " + y1 + " Z: " + z1, centerX-40, centerY + 80, paint);
        }


        if (canvas != null) {
            /*
             * TODO 画数据 所有外部需要在坐标轴上画的数据，都在这里进行绘制
             */

            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(3);
            canvas.drawCircle(centerX + 50, centerY - 50, 10, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setAntiAlias(true);
            paint.setStrokeWidth(6);
            canvas.drawCircle(centerX + 50, centerY - 50, 30, paint);

        }

    }

    /**
     * 画三角形 用于画坐标轴的箭头
     */
    private void drawTriangle(Canvas canvas, Point p1, Point p2, Point p3) {
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.close();

        Paint paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        // 绘制这个多边形
        canvas.drawPath(path, paint);
    }

    public void setData(Bundle bundle){
        x1 = bundle.getInt("x");
        y1 = bundle.getInt("y");
        z1 = bundle.getInt("z");
        Log.i("三轴加速数据---->","X = "+x1+", Y = "+y1+", Z = "+z1);
    }

}