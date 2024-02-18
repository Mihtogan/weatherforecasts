package com.example.weatherforecasts.CustomView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.weatherforecasts.R
import kotlin.math.ceil
import kotlin.math.floor

class CustomViewChart @JvmOverloads constructor(
    context: Context, attributes: AttributeSet
) : View(context, attributes) {

    private val colorsPoint = listOf(
        Color.GREEN,
        Color.BLUE,
        Color.RED,
        Color.CYAN,
        Color.LTGRAY,
        Color.MAGENTA
    )

    private var backgroundColor = Color.GRAY
    private var lineColor = Color.BLACK
    private var lineWidth = 5f
    private var textColor = Color.BLACK
    private var myTextSize = 40f
    private var circleSize = 40f

    private var unitOfMeasureAbscissa = ""
    private var unitOfMeasureOrdinate = ""
    private var points: List<DataForChart>? = null

    private var maxHorizontalValue = 0f
    private var maxVerticalValue = 0f
    private var minVerticalValue = 0f
    private var numberOfSources = 0
    private var strokeHeight = 0f
    private var strokeWidth = 0f
    private var newPointZeroX = 0f
    private var newPointZeroY = 0f

    private var numberOfHorizontalLines = 0
    private var numberOfVerticalLines = 0
    private var stepLineX = 0f
    private var stepLineY = 0f

    init {
        val typedArray = context.obtainStyledAttributes(attributes, R.styleable.CustomViewChart)

        backgroundColor = typedArray.getColor(
            R.styleable.CustomViewChart_backgroundColor, backgroundColor
        )

        lineColor = typedArray.getColor(
            R.styleable.CustomViewChart_lineColor, lineColor
        )

        textColor = typedArray.getColor(
            R.styleable.CustomViewChart_textColor, textColor
        )

        myTextSize = typedArray.getDimension(
            R.styleable.CustomViewChart_textSize, myTextSize
        )

        lineWidth = typedArray.getDimension(
            R.styleable.CustomViewChart_separator_line, lineWidth
        )

        unitOfMeasureAbscissa = typedArray.getString(
            R.styleable.CustomViewChart_unit_of_measure_abscissa
        ).toString()

        unitOfMeasureOrdinate = typedArray.getString(
            R.styleable.CustomViewChart_unit_of_measure_ordinate
        ).toString()

        circleSize = myTextSize / 4

        typedArray.recycle()
    }

    private val paintBack = Paint().apply {
        color = backgroundColor
        style = Paint.Style.FILL
    }

    private val paintLine = Paint().apply {
        color = lineColor
        style = Paint.Style.STROKE
        strokeWidth = lineWidth
    }

    private val paintText = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = textColor
        textSize = myTextSize
    }

    private val paintPoint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = colorsPoint[0]
        style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val defWidth = 800
        val defHeight = 600

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            MeasureSpec.EXACTLY -> widthSize // Задан конкретный размер для ширины
            MeasureSpec.AT_MOST -> Integer.min(
                defWidth,
                widthSize
            ) // Размер не должен превышать заданный размер
            else -> defWidth // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        val height = when (heightMode) {
            MeasureSpec.EXACTLY -> heightSize // Задан конкретный размер для высоты
            MeasureSpec.AT_MOST -> Integer.min(
                defHeight,
                heightSize
            ) // Размер не должен превышать заданный размер
            else -> defHeight // Задать предпочтительный размер, если точного или максимального размера не задано
        }

        strokeHeight = myTextSize * 1.2f
        strokeWidth = myTextSize * 1.2f
        newPointZeroX = myTextSize * 2
        newPointZeroY = height - 2 * strokeHeight


        numberOfHorizontalLines = floor(newPointZeroY / strokeHeight).toInt()
        numberOfVerticalLines = floor((width - newPointZeroX) / strokeHeight).toInt()
        stepLineX = (width - newPointZeroX) / numberOfVerticalLines
        stepLineY = newPointZeroY / numberOfHorizontalLines

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if (canvas == null) return

        drawBase(canvas)


        points?.let {
            drawScales(canvas)

            var numberSource = 0

            it.forEach { source ->
                paintPoint.color = colorsPoint[numberSource]

                canvas.drawCircle(
                    /* cx = */ strokeWidth / 2 +
                            strokeHeight * numberSource,
                    /* cy = */ height - strokeHeight / 2,
                    /* radius = */ circleSize,
                    /* paint = */ paintPoint
                )
                if (numberSource == colorsPoint.size)
                    numberSource = 0 else numberSource++

                source.coordinatesXY.forEach { point ->
                    canvas.drawCircle(
                        /* cx = */ convertRange(
                            number = point.x,
                            minOfOldRange = 0f,
                            maxOfOldRange = maxHorizontalValue,
                            minOfNewRange = newPointZeroX + stepLineX,
                            maxOfNewRange = width - stepLineX
                        ),
                        /* cy = */ convertRange(
                            number = point.y,
                            minOfOldRange = minVerticalValue,
                            maxOfOldRange = maxVerticalValue,
                            minOfNewRange = newPointZeroY - stepLineY,
                            maxOfNewRange = stepLineY
                        ),
                        /* radius = */ circleSize,
                        /* paint = */ paintPoint
                    )
                }
            }
        }
    }

    private fun drawBase(canvas: Canvas) {
        canvas.apply {
            drawColor(backgroundColor)

            drawLine(
                /* startX = */ 0f,
                /* startY = */ height.toFloat() - strokeHeight,
                /* stopX = */ width.toFloat(),
                /* stopY = */height.toFloat() - strokeHeight,
                /* paint = */ paintLine
            )

            drawLine(
                /* startX = */ 0f,
                /* startY = */  newPointZeroY,
                /* stopX = */ width.toFloat(),
                /* stopY = */  newPointZeroY,
                /* paint = */ paintLine
            )

            drawLine(                           //Vertica lLine
                /* startX = */ newPointZeroX,
                /* startY = */ 0f,
                /* stopX = */ newPointZeroX,
                /* stopY = */ height - strokeHeight,
                /* paint = */ paintLine
            )

            drawLine(
                /* startX = */ newPointZeroX,
                /* startY = */ newPointZeroY,
                /* stopX = */ 0f,
                /* stopY = */ height - strokeHeight,
                /* paint = */ paintLine
            )

            paintText.textSize = myTextSize * 0.65f
            drawText(
                /* text = */ unitOfMeasureOrdinate,
                /* x = */ 4f,
                /* y = */ height - strokeHeight * 1.4f,
                /* paint = */ paintText
            )
            paintText.textSize = myTextSize

            paintLine.strokeWidth *= 0.3f
            for (i in 1 until numberOfHorizontalLines) {
                drawLine(
                    /* startX = */ newPointZeroX,
                    /* startY = */ stepLineY * i,
                    /* stopX = */ width.toFloat(),
                    /* stopY = */ stepLineY * i,
                    /* paint = */ paintLine
                )
            }
            for (i in 1 until numberOfVerticalLines) {
                drawLine(
                    /* startX = */ newPointZeroX + stepLineX * i,
                    /* startY = */ 0f,
                    /* stopX = */ newPointZeroX + stepLineX * i,
                    /* stopY = */ newPointZeroY,
                    /* paint = */ paintLine
                )
            }
        }
    }

    private fun drawScales(canvas: Canvas) {

        paintText.textAlign = Paint.Align.RIGHT
        canvas.drawText(
            /* text = */ (maxVerticalValue)
                .toInt().toString(),
            /* x = */ newPointZeroX * 0.9f,
            /* y = */ stepLineY * 1.3f,
            /* paint = */ paintText
        )

        canvas.drawText(
            /* text = */ (minVerticalValue)
                .toInt().toString(),
            /* x = */ newPointZeroX * 0.9f,
            /* y = */ newPointZeroY - stepLineY * 0.7f,
            /* paint = */ paintText
        )
        paintText.textAlign = Paint.Align.LEFT

        /* val stepScaleY = (maxVerticalValue.toFloat() - minVerticalValue.toFloat()) /
                 numberOfHorizontalLines.toFloat()

         with(canvas) {
             for (i in 0..numberOfHorizontalLines) {
                 drawText(
                     /* text = */ (stepScaleY * i + minVerticalValue).toInt().toString(),
                     /* x = */ newPointZeroX / 5,
                     /* y = */ convertRange(
                         number = i.toFloat(),
                         minOfOldRange = 0f,
                         maxOfOldRange = numberOfHorizontalLines.toFloat(),
                         minOfNewRange = newPointZeroY - stepLineY,
                         maxOfNewRange = stepLineY
                     ) + stepLineY *0.3f,
                     /* paint = */ paintText
                 )
             }
         }*/
    }

    fun setData(points: List<DataForChart>) {
        this.points = points

        numberOfSources = points.size
        maxVerticalValue = points[0].coordinatesXY[0].y
        minVerticalValue = points[0].coordinatesXY[0].y
        maxHorizontalValue = points[0].coordinatesXY[0].x

        points.forEach { source ->
            source.coordinatesXY.forEach { points ->
                if (points.x > maxHorizontalValue)
                    maxHorizontalValue = points.x

                if (points.y > maxVerticalValue)
                    maxVerticalValue = points.y

                if (points.y < minVerticalValue)
                    minVerticalValue = points.y
            }
        }

        maxVerticalValue = ceil(maxVerticalValue)
        minVerticalValue = floor(minVerticalValue)

        invalidate()
    }

    private fun convertRange(
        number: Float,
        minOfOldRange: Float,
        maxOfOldRange: Float,
        minOfNewRange: Float,
        maxOfNewRange: Float
    ): Float {
        val a = (number - minOfOldRange) / (maxOfOldRange - minOfOldRange)

        return (maxOfNewRange - minOfNewRange) * a + minOfNewRange
    }
}