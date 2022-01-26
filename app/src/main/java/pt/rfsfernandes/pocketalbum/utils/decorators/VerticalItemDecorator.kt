package pt.rfsfernandes.pocketalbum.utils.decorators

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


/**
 *   Class VerticalItemDecorator created at 24/01/2022 00:22 for the project PocketAlbum
 *   By: rodrigofernandes
 */
class VerticalItemDecorator : ItemDecoration {
  private var divider: Drawable?

  /**
   * Default divider will be used
   */
  constructor(context: Context?) {
    context.let {
      val styledAttributes: TypedArray = context!!.obtainStyledAttributes(ATTRS)
      divider = styledAttributes.getDrawable(0)
      styledAttributes.recycle()
    }

  }

  /**
   * Custom divider will be used
   */
  constructor(context: Context?, resId: Int) {
    divider = context?.let { ContextCompat.getDrawable(it, resId) }
  }

  override fun getItemOffsets(
    outRect: Rect,
    view: View,
    parent: RecyclerView,
    state: RecyclerView.State
  ) {

    if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount
        ?.minus(1) ?: 0
    ) {
      super.getItemOffsets(outRect, view, parent, state)
    }
  }

  override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
    val dividerLeft = parent.paddingLeft
    val dividerRight = parent.width - parent.paddingRight
    val childCount = parent.childCount
    for (i in 0..childCount - 2) {
      val child = parent.getChildAt(i)
      val params = child.layoutParams as RecyclerView.LayoutParams
      val dividerTop = child.bottom + params.bottomMargin
      val dividerBottom: Int = dividerTop + divider?.intrinsicHeight!!
      divider?.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
      divider?.draw(c)
    }
  }

  companion object {
    private val ATTRS = intArrayOf(1)
  }
}