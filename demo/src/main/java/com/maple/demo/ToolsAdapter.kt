package com.maple.demo

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maple.demo.databinding.ItemToolBinding
import com.maple.msdialog.adapter.BaseQuickAdapter

data class ToolBean(
    var iconRes: Int = R.drawable.ic_menu_camera,
    var title: String = "默认名称",
    var clsName: Class<*>
)

class ToolsAdapter(val mContext: Context) : BaseQuickAdapter<ToolBean, ToolsAdapter.RecordViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecordViewHolder {
        val binding = ItemToolBinding.inflate(LayoutInflater.from(mContext), parent, false)
        return RecordViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecordViewHolder, position: Int) {
        holder.bind(position)
    }

    inner class RecordViewHolder(val binding: ItemToolBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(index: Int) {
            bindViewClickListener(this)
            val item = getItem(index)
            binding.apply {
//                val param = root.layoutParams
//                if (param is GridLayoutManager.LayoutParams) {
//                    val defMargin = 5f.dp
//                    if (index == 0 || index == 1) {
//                        param.setMargins(defMargin, 50f.dp, defMargin, defMargin)
//                    } else {
//                        param.setMargins(defMargin, defMargin, defMargin, defMargin)
//                    }
//                    root.layoutParams = param
//                }
                tvName.text = item.title
                ivIcon.setImageResource(item.iconRes)
//                Glide.with(mContext)
//                    .load(item.thumbNailFile())
//                    .into(ivThumbnail)
            }
        }
    }
}


