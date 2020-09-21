package ss564.sample.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_source.view.*

class SourceItemAdapter : RecyclerView.Adapter<SourceItemAdapter.SourceItemViewHolder>() {

    private val items = mutableListOf<SourceItemModel>()
    private var countChangeListener: SourceItemViewHolder.OnItemCountChangeListener? = null

    fun update(data: List<SourceItemModel>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SourceItemViewHolder {
        return SourceItemViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_source, parent, false)
        )
    }

    override fun onBindViewHolder(holder: SourceItemViewHolder, position: Int) {
        val model = items[position]
        holder.setModel(model)
        holder.setOnChangeItemCountListener { holderPosition, uuid, count ->
            model.count = count
            items[holderPosition] = model
            notifyItemChanged(holderPosition, model)
            if (count >= 0) {
                countChangeListener?.onChange(holderPosition, uuid, count)
            }
        }
    }

    override fun onBindViewHolder(
        holder: SourceItemViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        when {
            payloads.isEmpty() -> super.onBindViewHolder(holder, position, payloads)
            payloads[0] is SourceItemModel -> holder.setModel(payloads[0] as SourceItemModel)
        }
    }

    override fun getItemCount(): Int = items.size

    fun setOnSourceCountChangeListener(l: (position: Int, uuid: String, count: Int) -> Unit) {
        countChangeListener = object : SourceItemViewHolder.OnItemCountChangeListener {
            override fun onChange(position: Int, uuid: String, count: Int) {
                l.invoke(position, uuid, count)
            }
        }
    }

    class SourceItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private lateinit var model: SourceItemModel

        private val modelIsInitiated: Boolean
            get() = ::model.isInitialized

        private var listener: OnItemCountChangeListener? = null

        init {
            itemView.btnBooking.setOnClickListener { _ ->
                if (!modelIsInitiated) return@setOnClickListener
                listener?.onChange(adapterPosition, model.uuid, 0)
            }
            itemView.btnLess.setOnClickListener {
                if (!modelIsInitiated) return@setOnClickListener
                if (model.count == 0) {
                    it.context.toast("Sorry can't less than 0")
                    listener?.onChange(adapterPosition, model.uuid, -1)
                    return@setOnClickListener
                }
                listener?.onChange(adapterPosition, model.uuid, model.count - 1)
            }
            itemView.btnAdd.setOnClickListener {
                if (!modelIsInitiated) return@setOnClickListener
                if (model.count == 99) {
                    it.context.toast("Sorry can't more than 99")
                    return@setOnClickListener
                }
                listener?.onChange(adapterPosition, model.uuid, model.count + 1)
            }
        }

        fun setModel(model: SourceItemModel) {
            this.model = model
            bind()
        }

        fun setOnChangeItemCountListener(l: (position: Int, uuid: String, count: Int) -> Unit) {
            listener = object : OnItemCountChangeListener {
                override fun onChange(position: Int, uuid: String, count: Int) {
                    l.invoke(position, uuid, count)
                }
            }
        }

        private fun bind() {
            itemView.ivImage.setImageResource(model.imageRes)
            itemView.tvTitle.text = model.title
            if (model.count >= 0) {
                if (itemView.btnBooking.visibility == View.VISIBLE) {
                    itemView.bookingCountGroup.visibility = View.VISIBLE
                    itemView.btnBooking.visibility = View.GONE
                }
                itemView.tvCount.text = model.count.toString()
            } else {
                itemView.btnBooking.visibility = View.VISIBLE
                itemView.bookingCountGroup.visibility = View.GONE
            }
        }

        interface OnItemCountChangeListener {
            fun onChange(position: Int, uuid: String, count: Int)
        }
    }
}