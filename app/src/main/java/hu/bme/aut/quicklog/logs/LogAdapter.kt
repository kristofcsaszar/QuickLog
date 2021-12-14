package hu.bme.aut.quicklog.logs

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import hu.bme.aut.quicklog.dataModel.LogItem
import hu.bme.aut.quicklog.databinding.ItemLogBinding
import java.text.SimpleDateFormat

class LogAdapter(private val listener: LogItemClickListener) : RecyclerView.Adapter<LogAdapter.LogViewHolder>() {
    private val items = mutableListOf<LogItem>()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = LogViewHolder(
        ItemLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: LogViewHolder, position: Int) {
        val logItem = items[position]
        holder.binding.tvTitle.text = logItem.title
        holder.binding.tvDate.text = dateFormat.format(logItem.date)
        holder.binding.tvDescription.text = logItem.description
        holder.binding.tvScore.text = logItem.rating.toString()
        holder.binding.tvScore.setBackgroundColor(getBackgroundColor(logItem.rating))
    }

    private fun getBackgroundColor(score: Int): Int {
        val color: Int
        when (score) {
            1 -> color = Color.rgb(225,84,108)
            2 -> color =  Color.rgb(227,117,79)
            3 -> color = Color.YELLOW
            4 -> color =  Color.rgb(163,231,204)
            else -> color = Color.rgb(80,210,157)
        }
        return color
    }

    override fun getItemCount(): Int = items.size

    interface LogItemClickListener {
        fun onItemChanged(item: LogItem)
    }

    inner class LogViewHolder(val binding: ItemLogBinding) : RecyclerView.ViewHolder(binding.root)

    fun addItem(item: LogItem) {
        items.add(item)
        notifyItemInserted(items.size - 1)
    }

    fun update(shoppingItems: List<LogItem>) {
        items.clear()
        items.addAll(shoppingItems)
        notifyDataSetChanged()
    }
}
