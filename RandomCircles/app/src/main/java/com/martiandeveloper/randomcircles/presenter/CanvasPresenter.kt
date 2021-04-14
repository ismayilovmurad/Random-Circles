package com.martiandeveloper.randomcircles.presenter

import android.content.Context
import com.martiandeveloper.randomcircles.interactor.ShapesInteractor
import com.martiandeveloper.randomcircles.model.Shape.Type
import com.martiandeveloper.randomcircles.view.CustomViewKotlin

class CanvasPresenter(canvas: CustomViewKotlin, mContext: Context) {

    init {
        initializeUIComponents(canvas, mContext)
    }

    private fun initializeUIComponents(canvas: CustomViewKotlin, mContext: Context) {
        ShapesInteractor.getInstance().canvas = canvas
        ShapesInteractor.getInstance().setContext(mContext)
    }

    fun setMaxX(maxX: Int) {
        ShapesInteractor.getInstance().maxX = maxX
    }

    fun setMaxY(maxY: Int) {
        ShapesInteractor.getInstance().maxY = maxY
    }

    fun addShapeRandom(type: Type) {
        ShapesInteractor.getInstance().addShapeRandom(type)
    }

    fun undo() {
        ShapesInteractor.getInstance().undo()
    }

}
