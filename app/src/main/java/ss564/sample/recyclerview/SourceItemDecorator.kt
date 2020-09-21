package ss564.sample.recyclerview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.round

class SourceItemDecorator(context: Context) : RecyclerView.ItemDecoration() {

    private val margin: Int = context.resources.getDimensionPixelSize(R.dimen.default_half_margin)

    private val dividerHeight: Int

    private var bounds = Rect()

    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        dividerHeight = round(context.resources.displayMetrics.density * 1F).toInt()
        linePaint.color = 0xFFDFDFDF.toInt()
        linePaint.style = Paint.Style.STROKE
        linePaint.strokeCap = Paint.Cap.ROUND
        linePaint.strokeJoin = Paint.Join.ROUND
        linePaint.strokeWidth = dividerHeight.toFloat()
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager == null) return
        drawDivider(c, parent)
    }

    private fun drawDivider(canvas: Canvas, parent: RecyclerView) {
        canvas.save()
        val left: Int
        val right: Int
        if (parent.clipToPadding) {
            left = parent.paddingLeft
            right = parent.width - parent.paddingRight
            canvas.clipRect(left, parent.paddingTop, right, parent.height - parent.paddingBottom)
        } else {
            left = 0
            right = parent.width
        }
        val childCount = parent.childCount
        for (index in 0 until childCount - 1) {
            val child = parent.getChildAt(index)
            parent.getDecoratedBoundsWithMargins(child, bounds)
            val bottom = bounds.bottom + round(child.translationY)
            val top = bottom - dividerHeight
            canvas.drawLine(left.toFloat(), top, right.toFloat(), bottom, linePaint)
        }
        canvas.restore()
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        if (parent.layoutManager == null) return
        outRect.top = margin
        outRect.left = margin
        outRect.right = margin
        outRect.bottom = margin - dividerHeight
    }
}