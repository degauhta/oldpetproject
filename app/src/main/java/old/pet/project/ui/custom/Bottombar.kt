package old.pet.project.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.shape.MaterialShapeDrawable
import old.pet.project.R

class Bottombar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    init {
        View.inflate(context, R.layout.layout_bottombar, this)
        val materialBg = MaterialShapeDrawable.createWithElevationOverlay(context)
        materialBg.elevation = elevation
        background = materialBg
    }
}