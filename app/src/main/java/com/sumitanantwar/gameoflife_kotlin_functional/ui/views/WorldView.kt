package com.sumitanantwar.gameoflife_kotlin_functional.ui.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.sumitanantwar.gameoflife_kotlin_functional.model.State
import com.sumitanantwar.gameoflife_kotlin_functional.model.World

class WorldView : View {

    //======= World =======
    private lateinit var world: World

    private var frameSize = 0F

    val density = 50

    //======= Paints =======
    private lateinit var alivePaint:    Paint
    private lateinit var deadPaint:     Paint
    private lateinit var unbornPaint:   Paint


    constructor(context: Context) : super(context) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialize(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initialize(context)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        world.cells.forEach { cell ->
            val x = cell.x * frameSize
            val y = cell.y * frameSize

            var paint = unbornPaint
            when(cell.state) {
                State.Alive -> paint = alivePaint
                State.Dead -> paint = deadPaint
                State.Unborn -> paint = unbornPaint
            }

            canvas?.drawRect(x, y, x + frameSize, y + frameSize, paint)
        }
    }

    //======= Private =======
    private fun initialize(context: Context) {

        world = World(20, 20)
        world.initialize()
        world.preConfigureRandomTenPercent()

        alivePaint = Paint()
        alivePaint.style = Paint.Style.FILL_AND_STROKE
        alivePaint.color = Color.BLUE
        alivePaint.strokeWidth = 0.5F

        deadPaint = Paint()
        deadPaint.style = Paint.Style.FILL_AND_STROKE
        deadPaint.color = Color.GRAY
        deadPaint.strokeWidth = 0.5F

        unbornPaint = Paint()
        unbornPaint.style = Paint.Style.FILL_AND_STROKE
        unbornPaint.color = Color.WHITE
        unbornPaint.strokeWidth = 0.5F

    }

    private fun update(forGlider: Boolean) {
        frameSize = measuredWidth / density.toFloat()

        val colCount = density
        val rowCount = (measuredHeight / frameSize).toInt()

        world = World(colCount, rowCount)
        world.initialize()

        if (forGlider) {
            world.preConfigureForGliderGun()
        }
        else {
            world.preConfigureRandomTenPercent()
        }

        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        update(false)
    }

    //======= Public =======
    fun nextStep() {
        world.react()

        invalidate()
    }

    fun reset(forGlider: Boolean) {
        update(forGlider)
    }
}
