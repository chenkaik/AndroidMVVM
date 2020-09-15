package com.android.lib.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

/**
 * date: 2019/1/30
 * desc: Glide加载图片
 */
object ImageLoader {

    /**
     * 加载资源图片
     *
     * @param resourceId 资源图片id
     * @param imageView  ImageView
     */
    fun load(@DrawableRes resourceId: Int, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(resourceId)
            .into(imageView)
    }

    /**
     * 加载配置好加载参数的资源图片
     *
     * @param resourceId 资源图片id
     * @param imageView  ImageView
     * @param options    配置好的加载参数
     */
    fun load(
        @DrawableRes resourceId: Int, imageView: ImageView,
        options: RequestOptions
    ) {
        Glide.with(imageView.context)
            .load(resourceId)
            .apply(options)
            .into(imageView)
    }

    /**
     * 加载本地图片
     *
     * @param path      图片路径
     * @param imageView ImageView
     */
    fun load(path: String?, imageView: ImageView) {
        Glide.with(imageView.context)
            .load(path)
            .thumbnail(0.1f)
            .into(imageView)
    }

    /**
     * 加载配置好的图片加载参数
     *
     * @param context   上下文
     * @param url       图片路径
     * @param imageView 控件
     * @param options   配置好的加载参数
     */
    fun load(
        context: Context,
        url: String?,
        imageView: ImageView,
        options: RequestOptions
    ) {
        Glide.with(context)
            .load(url)
            .apply(options)
            .into(imageView)
    }

    /**
     * 加载网络图片
     *
     * @param path             图片路径
     * @param imageView        ImageView
     * @param placeholderResId 占位图
     * @param errorResId       加载失败显示图片
     */
    fun load(
        path: String?,
        imageView: ImageView, @DrawableRes placeholderResId: Int, @DrawableRes errorResId: Int
    ) {
        val requestOptions = RequestOptions()
            .placeholder(placeholderResId) // 占位图
            .error(errorResId) // 下载错误
//                .diskCacheStrategy(DiskCacheStrategy.NONE) // 禁止Glide对图片进行硬盘缓存
//                .skipMemoryCache(true) // 禁用内存缓存
            .circleCrop() // centerCrop、fitCenter、circleCrop
        Glide.with(imageView.context)
            .load(path)
            .apply(requestOptions)
            .into(imageView)
        /**
         * DiskCacheStrategy.NONE： 表示不缓存任何内容。
         * DiskCacheStrategy.DATA： 表示只缓存原始图片。
         * DiskCacheStrategy.RESOURCE： 表示只缓存转换过后的图片。
         * DiskCacheStrategy.ALL ： 表示既缓存原始图片，也缓存转换过后的图片。
         * DiskCacheStrategy.AUTOMATIC： 表示让Glide根据图片资源智能地选择使用哪一种缓存策略（默认选项）。
         */
    }

    /**
     * 清除View加载的图片
     *
     * @param view 加载图片的View
     */
    fun clear(context: Context, view: View) {
        Glide.with(context).clear(view)
    }

    /**
     * 清除内存缓存
     *
     * @param context 上下文
     */
    fun clearMemory(context: Context) {
        Glide.get(context).clearMemory()
    }
    
}