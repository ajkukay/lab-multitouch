package edu.uw.multitouchlab;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";

    private DrawingSurfaceView view;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (DrawingSurfaceView)findViewById(R.id.drawingView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY() - getSupportActionBar().getHeight(); //closer to center...

        int action = event.getActionMasked();
        switch(action) {
            case (MotionEvent.ACTION_DOWN) : //put finger down
                //Log.v(TAG, "finger down");
                view.ball.cx = x;
                view.ball.cy = y;
                view.addTouch(event.getPointerId(0),x,y);
                return true;
            case (MotionEvent.ACTION_POINTER_DOWN) : //additional finger down
                Log.v(TAG, "additional finger down");
                int pointerIndexDown = event.getActionIndex();
                int pointerIdDown = event.getPointerId(pointerIndexDown);
                view.addTouch(pointerIdDown,event.getX(pointerIndexDown),event.getY(pointerIndexDown));
                return true;
            case (MotionEvent.ACTION_POINTER_UP) : //additional finger up
                Log.v(TAG, "additional finger up");
                int pointerIndexUp = event.getActionIndex();
                int pointerIdUp = event.getPointerId(pointerIndexUp);
                view.removeTouch(pointerIdUp);
                return true;
            case (MotionEvent.ACTION_MOVE) : //move finger
                //Log.v(TAG, "finger move");
                view.ball.cx = x;
                view.ball.cy = y;
                for (int i = 0; i < event.getPointerCount(); i++) {
                   view.moveTouch(i,event.getX(i),event.getY(i));
                }

                return true;
            case (MotionEvent.ACTION_UP) : //lift finger up
                view.removeTouch(event.getPointerId(0));
                return true;
            case (MotionEvent.ACTION_CANCEL) : //aborted gesture
            case (MotionEvent.ACTION_OUTSIDE) : //outside bounds
            default :
                return super.onTouchEvent(event);
        }
    }
}