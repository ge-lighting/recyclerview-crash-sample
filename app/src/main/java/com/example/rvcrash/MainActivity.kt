package com.example.rvcrash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rvcrash.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        val adapter = SimpleAdapter()
        binding.recyclerView.adapter = adapter
        adapter.updateData(listOf("Second"))

        binding.updateButton.setOnClickListener {
            if (adapter.itemCount == 1) {
                adapter.updateData(listOf("First inserted", "Second"))
            } else {
                adapter.updateData(listOf("Second"))
            }
        }
    }
}

class SimpleAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var data: List<String> = emptyList()

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return object : RecyclerView.ViewHolder(
            LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        ) {}
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder.itemView as TextView).text = data[position]
    }

    fun updateData(data: List<String>) {
        val oldList = this.data
        this.data = data
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int = oldList.size

            override fun getNewListSize(): Int = data.size

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == data[newItemPosition]
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return oldList[oldItemPosition] == data[newItemPosition]
            }
        }).dispatchUpdatesTo(this)
    }
}
