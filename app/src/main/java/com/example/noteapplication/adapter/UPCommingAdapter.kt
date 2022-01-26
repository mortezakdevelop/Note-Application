import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapplication.R
import com.example.noteapplication.adapter.CardClickListener
import com.example.noteapplication.databinding.UpcomingRvItemsBinding
import com.example.noteapplication.room.entity.NoteEntity

class UPCommingAdapter(private var data: ArrayList<NoteEntity>,private var listener:CardClickListener) :RecyclerView.Adapter<UPCommingAdapter.UpCommingViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpCommingViewHolder {
        val upCommingRvItemBinding:UpcomingRvItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.upcoming_rv_items,parent,false)
        return UpCommingViewHolder(upCommingRvItemBinding)
    }

    override fun onBindViewHolder(holder: UpCommingViewHolder, position: Int) {
        holder.bind(data[position],listener)
    }

    override fun getItemCount(): Int {
        return data.size
    }
    class UpCommingViewHolder(val binding:UpcomingRvItemsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(noteEntity: NoteEntity, listener: CardClickListener) {
            binding.upcommingCardView.setCardBackgroundColor(Color.parseColor(noteEntity.noteModels.color))
            binding.textTitle.text = noteEntity.noteModels.title
            binding.textNote.text = noteEntity.noteModels.note

            binding.upcommingCardView.setOnClickListener {
                listener.onItemRVClickListener(noteEntity)
            }

//            binding.imageFilterButton2.setOnClickListener {
//                listener.onMenuItemClickListener(it,noteEntity)
//            }
            binding.executePendingBindings()
        }
    }
}