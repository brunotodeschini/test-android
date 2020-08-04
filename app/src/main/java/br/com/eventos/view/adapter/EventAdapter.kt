package br.com.eventos.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import br.com.eventos.R
import br.com.eventos.model.Event
import br.com.eventos.utils.formatDate
import br.com.eventos.utils.formattedPrice
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.event_list_card.view.*

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 03/08/20
 */
class EventAdapter(
    private val events: ArrayList<Event>
): RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    var onItemClick: ((id: Int) -> Unit)? = null

    var onOpemMap: ((event: Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = EventViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.event_list_card, parent, false)
    )

    override fun getItemCount() = events.size

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.bind(events[position])
    }

    fun update(mEvents: List<Event>) {
        events.clear()
        events.addAll(mEvents)
        notifyDataSetChanged()
    }


    inner class EventViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        init {
            itemView.tvGoToDetails.setOnClickListener {
                onItemClick?.invoke(events[adapterPosition].id.toInt())
            }

            itemView.ivDirectionsCard.setOnClickListener {
                onOpemMap?.invoke(events[adapterPosition])
            }
        }

        fun bind(event: Event) {
            itemView.tvEventName.text = event.title
            itemView.tvDate.text = event.date.formatDate()
            itemView.tvPrice.text = event.price.formattedPrice()

            val imgView = itemView.ivCover
            val imageUrl = event.image.toUri().buildUpon().scheme("http").build()
            Glide.with(imgView.context)
                .load(imageUrl)
                .apply(
                    RequestOptions()
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.ic_broken_image)
                )
                .into(imgView)
        }

    }
}