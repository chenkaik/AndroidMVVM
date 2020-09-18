package com.android.lib.banner

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.banner.BannerRecyclerAdapter.NormalHolder
import com.android.lib.banner.RecyclerViewBannerBaseView.OnBannerItemClickListener
import com.android.lib.util.ImageLoader.load

/**
 * date: 2019/1/30
 * desc: banner使用的adapter
 */
class BannerRecyclerAdapter(
    context: Context,
    urlList: List<String>,
    private val onBannerItemClickListener: OnBannerItemClickListener?
) : BaseBannerAdapter<NormalHolder>(context, urlList) {

    override fun createCustomViewHolder(
        parent: ViewGroup?,
        viewType: Int
    ): NormalHolder { //        Logger.e(TAG, "createCustomViewHolder: " + viewType);
        return NormalHolder(ImageView(mContext))
    }

    override fun bindCustomViewHolder(
        holder: NormalHolder,
        position: Int
    ) { //        Logger.e(TAG, "bindCustomViewHolder: " + position);
        if (mUrlList.isEmpty()) {
            return
        }
        val url = mUrlList[position % mUrlList.size]
        //        ImageView imageView = (ImageView) holder.itemView;
        val imageView = holder.bannerItem
        load(url, imageView)
        // 点击事件
        imageView.setOnClickListener { onBannerItemClickListener?.onItemClick(position % mUrlList.size) }
    }

    inner class NormalHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        var bannerItem = itemView as ImageView

        init {
            val params = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            bannerItem.layoutParams = params
            // 把图片按照指定的大小在ImageView中显示，拉伸显示图片，不保持原比例，填满ImageView.
            bannerItem.scaleType = ImageView.ScaleType.FIT_XY
        }
    }


}