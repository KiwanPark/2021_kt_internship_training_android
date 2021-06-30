package util

import android.content.Context
import android.util.TypedValue

class NewsUtil(val mContext: Context) {
    fun dpTopx(a: Int): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            a.toFloat(),
            mContext.resources.displayMetrics
        )
    }
}