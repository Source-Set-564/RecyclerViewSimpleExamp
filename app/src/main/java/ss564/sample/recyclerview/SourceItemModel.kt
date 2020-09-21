package ss564.sample.recyclerview

import androidx.annotation.DrawableRes
import java.util.*

data class SourceItemModel(
    val uuid: String = UUID.randomUUID().toString(),
    @DrawableRes val imageRes: Int,
    val title: String,
    var count: Int = -1
)