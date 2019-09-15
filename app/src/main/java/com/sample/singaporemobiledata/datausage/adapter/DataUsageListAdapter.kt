package com.sample.singaporemobiledata.datausage.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.sample.singaporemobiledata.R
import com.sample.singaporemobiledata.databinding.DataUsageBinding
import com.sample.singaporemobiledata.datausage.model.QuarterModel


class DataUsageListAdapter(private  val ctx : Context) : RecyclerView.Adapter<DataUsageListAdapter.ViewHolder>() {

    private val mQuarterModel = ArrayList<QuarterModel>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding = DataUsageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)

    }

    class ViewHolder(private val binding: DataUsageBinding) : RecyclerView.ViewHolder(binding.root){


        val year = binding.txtYear
        val quarterOne = binding.txtQuarter1
        val quarterTwo = binding.txtQuarter2
        val quarterThree = binding.txtQuarter3
        val quarterFour = binding.txtQuarter4

        fun bindView(quarterModel: QuarterModel?) {
            binding.dataUsageList = quarterModel
        }


    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(mQuarterModel[position])
        val rowPosition = holder.adapterPosition
        if(rowPosition == 0){
            holder.year.text = ctx.getString(R.string.year)
            holder.quarterOne.text = ctx.getString(R.string.quarterOne)
            holder.quarterTwo.text = ctx.getString(R.string.quarterTwo)
            holder.quarterThree.text = ctx.getString(R.string.quarterThree)
            holder.quarterFour.text = ctx.getString(R.string.quarterFour)
        }else{

            holder.year.text = mQuarterModel[position].year
            holder.quarterOne.text = mQuarterModel[position].quarterOne.keys.firstOrNull()
            holder.quarterTwo.text = mQuarterModel[position].quarterTwo.keys.firstOrNull()
            holder.quarterThree.text = mQuarterModel[position].quarterThree.keys.firstOrNull()
            holder.quarterFour.text = mQuarterModel[position].quarterFour.keys.firstOrNull()

        }

    }

    fun setData(data: MutableList<QuarterModel>) {
        mQuarterModel.clear()
        mQuarterModel.addAll(data)
        notifyDataSetChanged()

    }

    override fun getItemCount(): Int {
        return mQuarterModel.size
    }
}