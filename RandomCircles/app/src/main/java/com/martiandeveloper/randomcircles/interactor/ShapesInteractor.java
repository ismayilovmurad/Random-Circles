package com.martiandeveloper.randomcircles.interactor;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.martiandeveloper.randomcircles.model.Shape;
import com.martiandeveloper.randomcircles.utils.Constants;
import com.martiandeveloper.randomcircles.view.CustomViewKotlin;

import java.util.LinkedList;
import java.util.Random;

public class ShapesInteractor {
    private static final ShapesInteractor ourInstance = new ShapesInteractor();
    private static final String LOG_TAG = "";
    private Context mContext;

    public static ShapesInteractor getInstance() {
        return ourInstance;
    }

    private ShapesInteractor() {
    }
    private CustomViewKotlin canvas;
    private int maxX;
    private int maxY;

    private static LinkedList<Shape> historyList = new LinkedList<>();
    private int actionSequence = 0;

    /*
    Generate random x,y from 0,0 to screen max width and height
     */
    private int[] generateRandomXAndY() {
        int x, y;
        Random rn = new Random();
        int diff = maxX - Constants.INSTANCE.getRADIUS();
        x = rn.nextInt(diff + 1);
        x += Constants.INSTANCE.getRADIUS();

        rn = new Random();
        diff = maxY - Constants.INSTANCE.getRADIUS();
        y = rn.nextInt(diff + 1);
        y += Constants.INSTANCE.getRADIUS();
        Log.d(LOG_TAG, " Random x= " + x + "y" + y);
        return new int[]{x, y};
    }

    public void addShapeRandom(Shape.Type type) {
        int[] randomCordinates = generateRandomXAndY();
        Shape shape = createShape(type, randomCordinates[0], randomCordinates[1]);
        upDateCanvas(shape);
    }

    @NonNull
    private Shape createShape(Shape.Type type, int x, int y) {
        Shape shape = new Shape(x, y, Constants.INSTANCE.getRADIUS());
        shape.setType(type);
        return shape;
    }

    public void undo() {

        if (getHistoryList().size() > 0) {
            actionSequence--;
            Shape toDeleteShape = getHistoryList().getLast();
            if (toDeleteShape.getLastTranformationIndex() != Constants.INSTANCE.getACTION_CREATE()) {
                int lastVisibleIndex = toDeleteShape.getLastTranformationIndex();
                if(lastVisibleIndex < getHistoryList().size()) {
                    Shape lastVisibleShape = getHistoryList().get(lastVisibleIndex);
                    if (lastVisibleShape != null) {
                        lastVisibleShape.setVisibility(true);
                        getHistoryList().set(lastVisibleIndex, lastVisibleShape);
                    }
                }
            }
            getHistoryList().removeLast();
            canvas.setHistoryList(getHistoryList());
            canvas.invalidate();
        }
    }

    private void upDateCanvas(Shape shape) {
        Log.d(LOG_TAG, " upDateCanvas " + shape.getType() + " actiontype = " + actionSequence);
        shape.setActionNumber(actionSequence++);
        getHistoryList().add(shape);
        canvas.setHistoryList(getHistoryList());
        canvas.invalidate();
    }

    private LinkedList<Shape> getHistoryList() {
        return historyList;
    }


    public CustomViewKotlin getCanvas() {
        return canvas;
    }

    public void setCanvas(CustomViewKotlin canvas) {
        this.canvas = canvas;
    }

    public Context getmContext() {
        return mContext;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }


    public void setContext(Context mContext) {
        this.mContext = mContext;
    }

}
