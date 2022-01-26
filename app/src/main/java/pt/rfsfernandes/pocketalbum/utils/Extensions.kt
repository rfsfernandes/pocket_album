package pt.rfsfernandes.pocketalbum.utils

import android.animation.Animator
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import pt.rfsfernandes.pocketalbum.data.models.generic.LastFMImage


/**
 *   File Extensions created at 23/01/2022 16:00 for the project PocketAlbum
 *   By: rodrigofernandes
 */

fun Fragment.hideKeyboard() {
  view?.let { activity?.hideKeyboard(it) }
}

fun Activity.hideKeyboard() {
  hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
  val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
  inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Int.toDp(): Int = (this / Resources.getSystem().displayMetrics.density).toInt()

fun Int.toTrackTime(): String {
  val hours = this / 3600f
  val fullHours = hours.toInt()

  val minutes = (hours - fullHours) * 60f
  val fullMinutes = minutes.toInt()

  val seconds = (minutes - fullMinutes) * 60f
  val fullSeconds = seconds.toInt()

  return if (fullHours != 0)
    String.format(HH_MM_SS, fullHours, fullMinutes, fullSeconds)
  else
    String.format(MM_SS, fullMinutes, fullSeconds)

}

fun LinearLayout.expand() {
  //set Visible
  this.visibility = View.VISIBLE
  val widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
  val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
  this.measure(widthSpec, heightSpec)
  val mAnimator = slideAnimator(this, 0, this.measuredHeight)
  mAnimator.start()
}

fun LinearLayout.collapse() {
  val widthSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
  val heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
  this.measure(widthSpec, heightSpec)
  val mAnimator = slideAnimator(this, this.measuredHeight, 0)
  mAnimator.addListener(object : Animator.AnimatorListener {
    override fun onAnimationStart(p0: Animator?) {}

    override fun onAnimationEnd(p0: Animator?) {
      this@collapse.visibility = View.VISIBLE
    }

    override fun onAnimationCancel(p0: Animator?) {}

    override fun onAnimationRepeat(p0: Animator?) {}

  })
  mAnimator.start()
}

private fun slideAnimator(view: View, start: Int, end: Int): ValueAnimator {
  val animator = ValueAnimator.ofInt(start, end)
  animator.addUpdateListener { valueAnimator -> //Update Height
    val value = valueAnimator.animatedValue as Int
    val layoutParams: ViewGroup.LayoutParams = view.layoutParams
    layoutParams.height = value
    view.layoutParams = layoutParams
  }
  return animator
}

fun List<LastFMImage>.getUsablePhotoUrl(): String {
  var url: String = ""
  this.find { it.size == "large" }?.text?.let {
    if (it.isEmpty()) {
      this.find { it.size == "medium" }?.text?.let { medium ->
        url = medium
      }
    } else
      url = it
  }

  return url
}