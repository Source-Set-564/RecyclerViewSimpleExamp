package ss564.sample.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val adapter by lazy { SourceItemAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvItems.layoutManager = LinearLayoutManager(this)
        rvItems.adapter = adapter
        rvItems.addItemDecoration(SourceItemDecorator(this))

        adapter.setOnSourceCountChangeListener { _, uuid, count ->
            toast("Source id : $uuid, count : $count")
        }

        createDummies()
    }

    private fun createDummies() {
        val data = mutableListOf<SourceItemModel>()
        for (i in 0 until 20) {
            data.add(
                SourceItemModel(
                    title = DummyHelper.getTitle(),
                    imageRes = DummyHelper.getDrawable()
                )
            )
        }

        adapter.update(data)
    }
}