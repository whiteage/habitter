package com.example.habit

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.example.habit.databinding.HabitCardBinding


class HabitAdapter(private val context: Context) : RecyclerView.Adapter<HabitAdapter.HabitHolder>() {
    val habitList = ArrayList<HabitCard>()


    var listener: ((HabitCard) -> Unit)? = null
        inner class HabitHolder(item : View) : RecyclerView.ViewHolder(item) {
            val binding = HabitCardBinding.bind(item)
            fun bind(habitCard: HabitCard){
                binding.cardName.setText(habitCard.name)
                binding.cardDesc.setText(habitCard.desc)
            }

            init{
                item.setOnClickListener{
                    listener?.invoke(habitList[adapterPosition])
                }
            }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.habit_card, parent, false)

        return HabitHolder(view)
    }

    override fun getItemCount(): Int {
        return habitList.size
    }

    override fun onBindViewHolder(holder: HabitHolder, position: Int) {
        if(position >= habitList.size ){
            return
        }

        holder.bind(habitList[position])
        holder.binding.cardView.setOnClickListener{

            val context = holder.itemView.context
            val intent = Intent(context, EditActivity::class.java).apply {
                putExtra("name",habitList[position].name)
                putExtra("desc",habitList[position].desc)
            }

            context.startActivity(intent)
        }

        holder.binding.cardView.setOnLongClickListener {
            view ->
            showPopUp(view, habitList[position])
            true
        }

    }

    fun addCard(habitCard: HabitCard){
        habitList.add(habitCard)
        notifyItemInserted(habitList.size - 1)
    }

    fun clearItems(){
        habitList.clear()
        notifyDataSetChanged()
    }

    fun showPopUp(view: View, item: HabitCard){
        Log.e("HabitAdapter", "index выбран ${habitList.indexOf(item)}, размер листа: ${habitList.size}")
        val popUp = PopupMenu(context, view )
        popUp.menuInflater.inflate(R.menu.popup, popUp.menu)
        popUp.setOnMenuItemClickListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.delItem -> {
                    delItem(item)

                    true
                }
                else -> false
            }

        }
        popUp.show()
    }

    private fun delItem(sth: HabitCard) {
        val index = habitList.indexOf(sth)
        if( index != -1) {
            habitList.removeAt(index)
            Log.e("HabitAdapter", "index удален $index")
            notifyItemRemoved(index)
            notifyItemRangeChanged(index, habitList.size)
        }

        val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove(sth.name).apply()
    }


}