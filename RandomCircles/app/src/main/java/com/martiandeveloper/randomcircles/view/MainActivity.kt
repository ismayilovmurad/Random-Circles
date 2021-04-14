package com.martiandeveloper.randomcircles.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import com.martiandeveloper.randomcircles.R
import com.martiandeveloper.randomcircles.databinding.ActivityMainBinding
import com.martiandeveloper.randomcircles.hasDrawMTBNClicked
import com.martiandeveloper.randomcircles.model.Shape
import com.martiandeveloper.randomcircles.presenter.CanvasPresenter
import com.martiandeveloper.randomcircles.utils.Constants
import com.martiandeveloper.randomcircles.utils.EventObserver
import com.martiandeveloper.randomcircles.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var binding: ActivityMainBinding


    private val TAG: String =
        MainActivity::class.java.getSimpleName()
    private var canvas: CustomViewKotlin? = null
    var canvasPresenter: CanvasPresenter? = null
    private var maxY = 800 // average screen height

    private var maxX = 600 //average screen height


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.let {
            it.viewModel = viewModel
            it.lifecycleOwner = this
        }

        observe()

        canvas = binding.activityMainMainCV

        canvasPresenter = CanvasPresenter(canvas!!, this)

        getCanvasWidthAndHeight()

    }

    private fun observe() {

        with(viewModel) {

            viewModel.drawMBTNClick.observe(this@MainActivity, EventObserver {

                hasDrawMTBNClicked = true
                Log.d(TAG,"draw btn clicked")

                if (!number.value.isNullOrEmpty())

                    if (number.value!!.toInt() in 1..20) {
                        (getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(
                            (currentFocus ?: View(this@MainActivity)).windowToken,
                            0
                        )

                        for (i in 1..20) {
                            canvasPresenter!!.undo()
                        }

                        for (i in 1..number.value!!.toInt()) {
                            canvasPresenter!!.addShapeRandom(Shape.Type.CIRCLE)
                        }

                        Handler(Looper.getMainLooper()).postDelayed({
                            hasDrawMTBNClicked = false
                            Log.d(TAG,"draw btn not clicked")
                        }, 1000)



                    } else Toast.makeText(
                        this@MainActivity,
                        "Please enter a number between 1 and 20",
                        Toast.LENGTH_SHORT
                    ).show()



            })

        }

    }

    private fun getCanvasWidthAndHeight() {
        val viewTreeObserver = canvas!!.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
                @SuppressLint("RestrictedApi")
                override fun onGlobalLayout() {
                    canvas!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    maxY = canvas!!.height
                    maxX = canvas!!.width
                    //Reduce radius so that shape isn't left incomplete at the edge
                    canvasPresenter!!.setMaxX(maxX - Constants.RADIUS)
                    val bottomButtonHeight = 100
                    canvasPresenter!!.setMaxY(maxY - Constants.RADIUS - bottomButtonHeight)

                    canvas!!.viewTreeObserver.removeOnGlobalLayoutListener(this)

                    Log.d(
                        TAG,
                        " Screen max x= $maxX maxy = $maxY"
                    )
                }
            })
        }
    }


}
