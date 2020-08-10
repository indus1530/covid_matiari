package edu.aku.hassannaqvi.covid_matiari.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import edu.aku.hassannaqvi.covid_matiari.R
import edu.aku.hassannaqvi.covid_matiari.databinding.ItemChildListBinding
import edu.aku.hassannaqvi.covid_matiari.models.Personal
import edu.aku.hassannaqvi.covid_matiari.utils.app_utils.getMemberIcon

class MemberListAdapter(private val mContext: Context, private var mList: List<Personal>) : RecyclerView.Adapter<MemberListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val bi: ItemChildListBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.item_child_list, viewGroup, false)
        return ViewHolder(bi)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        holder.bi.parentLayout.tag = i
        holder.bi.name.text = mList[i].memberName
        if (mList[i].cstatus != "1")
            holder.bi.containeridcard.setBackgroundColor(ContextCompat.getColor(mContext, R.color.shifted))
        Glide.with(mContext)
                .asBitmap()
                .load(getMemberIcon(mList[i].gender.toInt(), mList[i].agey))
                .into(holder.bi.childImage)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setMList(members: List<Personal>) {
        mList = members
        notifyDataSetChanged()
    }

    class ViewHolder(val bi: ItemChildListBinding) : RecyclerView.ViewHolder(bi.root)

}