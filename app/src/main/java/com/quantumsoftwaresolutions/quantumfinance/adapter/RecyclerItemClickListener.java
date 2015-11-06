package com.quantumsoftwaresolutions.quantumfinance.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onSingleClick(int position);
        void onLongClick(int position);
        void onFling(int position);
    }

    private final GestureDetector gestureDetector;

    public RecyclerItemClickListener(Context context, final RecyclerView view, OnItemClickListener clickListener) {
        listener = clickListener;
        gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override public boolean onSingleTapUp(MotionEvent e) {
                View childView = view.findChildViewUnder(e.getX(), e.getY());
                if (childView != null){
                    listener.onSingleClick(view.getChildAdapterPosition(childView));
                    return true;
                }
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View childView = view.findChildViewUnder(e.getX(), e.getY());
                if (childView != null){
                    listener.onLongClick(view.getChildAdapterPosition(childView));
                }
                super.onLongPress(e);
            }


            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                velocityX *= 5;
                velocityY *= 5;
                View childView = view.findChildViewUnder(e1.getX(), e1.getY());
                if (childView != null){
                    listener.onFling(view.getChildAdapterPosition(childView));
                }
                return super.onFling(e1, e2, velocityX, velocityY);
            }
        });
    }

    @Override public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        return (listener != null && gestureDetector.onTouchEvent(e));
    }

    @Override public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

    }
}
