package sg.carpark.looq.utils.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

import sg.carpark.looq.R;

public class MyView extends androidx.appcompat.widget.AppCompatImageView {

    Paint paint;
    Path path;

    public MyView(Context context) {
        super(context);
        init();
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.colorPrimary));
        paint.setStrokeWidth(20);
        paint.setStyle(Paint.Style.STROKE);

        path = new Path();
//        path.moveTo(Utils.pxToDp(110), Utils.pxToDp(505));
//        path.lineTo(Utils.pxToDp(112), Utils.pxToDp(347));
//        path.moveTo(Utils.pxToDp(191), Utils.pxToDp(1025));
//        path.lineTo(196, 700);
//        path.lineTo(250, 700);
//        path.lineTo(250, 304);
//        path.lineTo(208, 300);

//        path.moveTo(Utils.convertCoordinate(MyView.this, 79, true),Utils.convertCoordinate(MyView.this, 371, false)); //560 dpi
//        path.lineTo(Utils.convertCoordinate(MyView.this, 87, true),Utils.convertCoordinate(MyView.this, 224, false)); //560 dpi
//        path.lineTo(87, 224); //440 dpi

//        path.moveTo(86, 397);
//        path.lineTo(81, 269
//        path.moveTo(256, 1300);
//        path.lineTo(233, 908);

        //pixel 4
//        int x1 = 193;
//        int y1 = 1024;
////        int x1 = Utils.convertCoordinate(MyView.this, 256, true);
////        int y1 = Utils.convertCoordinate(MyView.this, 1300, false);
//
//        path.moveTo(x1, y1);
//        path.lineTo(193, 695);
//        path.lineTo(254, 695);
//        path.lineTo(254, 316);
//        path.lineTo(212, 316);

        int x1 = 193;
        int y1 = 948;
//        int x1 = Utils.convertCoordinate(MyView.this, 256, true);
//        int y1 = Utils.convertCoordinate(MyView.this, 1300, false);

//        path.moveTo(x1, y1);
//        path.lineTo(193, 685);
//        path.lineTo(254, 685);
//        path.lineTo(254, 319);
//        path.lineTo(571, 332);
//        path.lineTo(571, 433);
//        path.lineTo(541, 433);

//        path.moveTo(257, 1321);
//        path.lineTo(257, 995);
//        path.lineTo(339, 995);
//        path.lineTo(339, 486);
//        path.lineTo(767, 486);
//        path.lineTo(767, 634);
//        path.lineTo(726, 634);

        path.moveTo(215, 537);
        path.lineTo(215, 1036);
        path.lineTo(951, 1036);
        path.lineTo(951, 1107);

//        canvas.drawLine(110,505,112,347, p);


    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        // Get image matrix values and place them in an array
//        float[] f = new float[9];
//        getImageMatrix().getValues(f);
//
//        // Extract the scale values using the constants (if aspect ratio maintained, scaleX == scaleY)
//        final float scaleX = f[Matrix.MSCALE_X];
//        final float scaleY = f[Matrix.MSCALE_Y];
//
//        // Get the drawable (could also get the bitmap behind the drawable and getWidth/getHeight)
//        final Drawable d = getDrawable();
//        final int origW = d.getIntrinsicWidth();
//        final int origH = d.getIntrinsicHeight();
//
//        // Calculate the actual dimensions
//        final int actW = Math.round(origW * scaleX);
//        final int actH = Math.round(origH * scaleY);
//
//        Log.e("DBG", "["+origW+","+origH+"] -> ["+actW+","+actH+"] & scales: x="+scaleX+" y="+scaleY);
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);

    }

}