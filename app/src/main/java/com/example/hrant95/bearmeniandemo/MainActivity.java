package com.example.hrant95.bearmeniandemo;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.graphics.*;

public class MainActivity extends AppCompatActivity {

    private Bitmap mBitmap;
    private Bitmap mBackgroundImageBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyView myView = new MyView(this);
        setContentView(myView);
        Thread thread = new Thread(myView);
        thread.run();

        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.zenta);
        mBackgroundImageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.girl);

    }

    private class MyView extends SurfaceView implements Runnable {

        public MyView(Context context) {
            super(context);
        }

        @Override
        public void run() {

            SurfaceHolder holder = getHolder();

            holder.addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder holder) {

                    Display display = getWindowManager().getDefaultDisplay();

                    int displayWidth = display.getWidth();
                    int displayHeight = display.getHeight();
                    int bitmapWidth = mBitmap.getWidth();
                    int bitmapHeight = mBitmap.getHeight();

                    int columnsCount = displayWidth / bitmapWidth;
                    int rowsCount = displayHeight / bitmapHeight;
                    int columnPadding = displayWidth - (columnsCount * bitmapWidth);
                    int itemRightPadding = columnPadding / columnsCount;

                    int rowPadding = displayHeight - (rowsCount * bitmapHeight);
                    int itemBottomPadding = rowPadding / rowsCount;

                    int left = itemRightPadding;
                    int top = itemBottomPadding;

                    Canvas canvas = holder.lockCanvas();
                    canvas.drawBitmap(mBackgroundImageBitmap, 0, 0, null);

                    for (int i = 0; i < rowsCount - 1 /* -1 vorovhetev actionbary xangaruma*/; i++) {
                        for (int j = 0; j < columnsCount; j++) {
                            canvas.drawBitmap(mBitmap, left, top, null);
                            left += bitmapWidth + itemRightPadding;
                        }
                        left = 0;
                        top += bitmapHeight + itemBottomPadding;
                    }

                    holder.unlockCanvasAndPost(canvas);

                }

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {

                }
            });

        }
    }


}
