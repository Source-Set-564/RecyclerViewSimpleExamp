package ss564.sample.recyclerview

import androidx.annotation.DrawableRes
import kotlin.random.Random

object DummyHelper {

    private val images = listOf(
        R.drawable.coffee2,
        R.drawable.food1,
        R.drawable.food3
    )

    private val titles = listOf(
        "Minuman Enak",
        "Masakan Mantap",
        "Kuliner Gila",
        "Aneka Rasa",
        "Minuman Mantap",
        "Makanan Enak",
        "Berbagai Rasa",
        "Yang penting Happy"
    )

    private val random = Random(System.currentTimeMillis())

    @DrawableRes
    fun getDrawable(): Int {
        return images[random.nextInt(images.size)]
    }

    fun getTitle(): String {
        return titles[random.nextInt(titles.size)]
    }
}