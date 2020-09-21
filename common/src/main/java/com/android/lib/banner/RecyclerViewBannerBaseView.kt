package com.android.lib.banner

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.os.Handler
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.android.lib.R

/**
 * date: 2019/1/30
 * desc: RecyclerView实现banner
 */
abstract class RecyclerViewBannerBaseView<L : RecyclerView.LayoutManager, A : BaseBannerAdapter<*>> @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var autoPlayDuration = 0 // 刷新间隔时间 = 0
    private var showIndicator = false // 是否显示指示器 = false
    private lateinit var indicatorContainer: RecyclerView
    protected var mSelectedDrawable: Drawable? = null
    protected var mUnselectedDrawable: Drawable? = null
    private lateinit var indicatorAdapter: IndicatorAdapter
    protected var indicatorMargin = 0 // 指示器间距 = 0
    private lateinit var mRecyclerView: RecyclerView
    private var adapter: A? = null
    protected lateinit var mLayoutManager: L
    private var whatAutoPlay: Int = 1000
    private var hasInit = false
    protected var bannerSize = 1
    protected var currentIndex = 0
    private var isPlaying = false
    private var isAutoPlaying = false
    private var onBannerItemClickListener: OnBannerItemClickListener? = null
    private lateinit var mHandler: Handler

    init {
        mHandler = Handler(Handler.Callback { msg ->
            if (msg.what == whatAutoPlay) {
                mRecyclerView.smoothScrollToPosition(++currentIndex)
                refreshIndicator()
                mHandler.sendEmptyMessageDelayed(whatAutoPlay, autoPlayDuration.toLong())
            }
            false
        })
        initView(context, attrs)
    }

    private fun initView(
        context: Context,
        attrs: AttributeSet?
    ) {
        val a =
            context.obtainStyledAttributes(attrs, R.styleable.RecyclerViewBannerBaseView)
        showIndicator =
            a.getBoolean(R.styleable.RecyclerViewBannerBaseView_showIndicator, true) // 是否显示指示器
        autoPlayDuration =
            a.getInt(R.styleable.RecyclerViewBannerBaseView_interval, 4000) // 自动播放的时间间隔
        isAutoPlaying =
            a.getBoolean(R.styleable.RecyclerViewBannerBaseView_autoPlaying, true) // 是否自动播放
        mSelectedDrawable =
            a.getDrawable(R.styleable.RecyclerViewBannerBaseView_indicatorSelectedSrc) // 滑动选中的样式
        mUnselectedDrawable =
            a.getDrawable(R.styleable.RecyclerViewBannerBaseView_indicatorUnselectedSrc) // 滑动未选中的样式
        if (mSelectedDrawable == null) { // 绘制默认选中状态图形
            val selectedGradientDrawable = GradientDrawable()
            selectedGradientDrawable.shape = GradientDrawable.OVAL
            selectedGradientDrawable.setColor(getColor(R.color.colorPrimaryDark))
            selectedGradientDrawable.setSize(dp2px(6), dp2px(6))
            selectedGradientDrawable.cornerRadius = dp2px(6) / 2.toFloat()
            mSelectedDrawable = LayerDrawable(arrayOf<Drawable>(selectedGradientDrawable))
        }
        if (mUnselectedDrawable == null) { // 绘制默认未选中状态图形
            val unSelectedGradientDrawable = GradientDrawable()
            unSelectedGradientDrawable.shape = GradientDrawable.OVAL
            unSelectedGradientDrawable.setColor(getColor(R.color.colorAccent))
            unSelectedGradientDrawable.setSize(dp2px(6), dp2px(6))
            unSelectedGradientDrawable.cornerRadius = dp2px(6) / 2.toFloat()
            mUnselectedDrawable = LayerDrawable(arrayOf<Drawable>(unSelectedGradientDrawable))
        }
        // 指示器间距
        indicatorMargin = a.getDimensionPixelSize(
            R.styleable.RecyclerViewBannerBaseView_indicatorSpaces,
            dp2px(4)
        )
        // 指示器左间距
        val marginLeft = a.getDimensionPixelSize(
            R.styleable.RecyclerViewBannerBaseView_indicatorMarginLeft,
            dp2px(0)
        )
        // 指示器右间距
        val marginRight = a.getDimensionPixelSize(
            R.styleable.RecyclerViewBannerBaseView_indicatorMarginRight,
            dp2px(0)
        )
        // 指示器离下面间距
        val marginBottom = a.getDimensionPixelSize(
            R.styleable.RecyclerViewBannerBaseView_indicatorMarginBottom,
            dp2px(10)
        )
        // 指示器的位置
        val g = a.getInt(R.styleable.RecyclerViewBannerBaseView_indicatorGravity, 3)
        val gravity: Int
        gravity = when (g) {
            0 -> {
                GravityCompat.START
            }
            2 -> {
                GravityCompat.END
            }
            else -> {
                Gravity.CENTER
            }
        }
        // 指示器排序
        val o = a.getInt(R.styleable.RecyclerViewBannerBaseView_orientation, 0)
        var orientation = 0
        if (o == 0) {
            orientation = LinearLayoutManager.HORIZONTAL
        } else if (o == 1) {
            orientation = LinearLayoutManager.VERTICAL
        }
        a.recycle()
        // recyclerView部分
        mRecyclerView = RecyclerView(context)
        PagerSnapHelper().attachToRecyclerView(mRecyclerView)
        mLayoutManager = getLayoutManager(context, orientation)
        mRecyclerView.layoutManager = mLayoutManager
        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            /**
             * dx、dy这两个参数，当向上滑动的时候dy是大于0的，
             * 向左滑动的时候dx是大于0的，反方向滑动则小于0，
             */
            override fun onScrolled(
                recyclerView: RecyclerView,
                dx: Int,
                dy: Int
            ) {
                onBannerScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(
                recyclerView: RecyclerView,
                newState: Int
            ) {
                onBannerScrollStateChanged(recyclerView, newState)
            }
        })
        val vpLayoutParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        addView(mRecyclerView, vpLayoutParams)
        // 指示器部分
        indicatorContainer = RecyclerView(context)
        val indicatorLayoutManager =
            LinearLayoutManager(context, orientation, false)
        indicatorContainer.layoutManager = indicatorLayoutManager
        indicatorAdapter = IndicatorAdapter()
        indicatorContainer.adapter = indicatorAdapter
        val params = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM or gravity
        params.setMargins(marginLeft, 0, marginRight, marginBottom)
        addView(indicatorContainer, params)
        if (!showIndicator) {
            indicatorContainer.visibility = View.GONE
        }
    }

    /**
     * 设置轮播间隔时间
     *
     * @param millisecond 时间毫秒
     */
    fun setIndicatorInterval(millisecond: Int) {
        autoPlayDuration = millisecond
    }

    /**
     * 设置是否自动播放（上锁）
     *
     * @param playing 开始播放
     */
    @Synchronized
    fun setPlaying(playing: Boolean) {
        if (isAutoPlaying && hasInit) {
            if (!isPlaying && playing && adapter != null && adapter!!.itemCount > 2) {
                mHandler.sendEmptyMessageDelayed(whatAutoPlay, autoPlayDuration.toLong())
                isPlaying = true
            } else if (isPlaying && !playing) {
                mHandler.removeMessages(whatAutoPlay)
                isPlaying = false
            }
        }
    }

    /**
     * 设置是否禁止滚动播放
     */
    fun setAutoPlaying(isAutoPlaying: Boolean) {
        this.isAutoPlaying = isAutoPlaying
    }

    fun isPlaying(): Boolean {
        return isPlaying
    }

    fun setShowIndicator(showIndicator: Boolean) {
        this.showIndicator = showIndicator
        indicatorContainer.visibility = if (showIndicator) View.VISIBLE else View.GONE
    }

    fun setOnBannerItemClickListener(onBannerItemClickListener: OnBannerItemClickListener?) {
        this.onBannerItemClickListener = onBannerItemClickListener
    }
    /**
     * 设置轮播数据集
     */
    /**
     * 设置轮播数据集
     */
    @JvmOverloads
    fun initBannerImageView(
        newList: List<String>,
        onBannerItemClickListener: OnBannerItemClickListener? = null
    ) { // 解决recyclerView嵌套问题
        hasInit = false
        visibility = View.VISIBLE
        setPlaying(false)
        adapter = getAdapter(context, newList, onBannerItemClickListener)
        mRecyclerView.adapter = adapter
        bannerSize = newList.size
        if (bannerSize > 1) {
            indicatorContainer.visibility = View.VISIBLE
            currentIndex = bannerSize * 10000
            mRecyclerView.scrollToPosition(currentIndex)
            indicatorAdapter.notifyDataSetChanged()
            setPlaying(true)
        } else {
            indicatorContainer.visibility = View.GONE
            currentIndex = 0
        }
        hasInit = true
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> setPlaying(false)
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> setPlaying(true)
        }
        // 解决recyclerView嵌套问题
        try {
            return super.dispatchTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
        return false
    }

    // 解决recyclerView嵌套问题
    override fun onTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
        return false
    }

    // 解决recyclerView嵌套问题
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        try {
            return super.onInterceptTouchEvent(ev)
        } catch (ex: IllegalArgumentException) {
            ex.printStackTrace()
        }
        return false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setPlaying(true)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        setPlaying(false)
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        if (visibility == View.VISIBLE) {
            setPlaying(true)
        } else {
            setPlaying(false)
        }
    }

    /**
     * 标示点适配器
     */
    private inner class IndicatorAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        var currentPosition = 0
        fun setPosition(currentPosition: Int) {
            this.currentPosition = currentPosition
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): RecyclerView.ViewHolder { //Logger.e(TAG, "onCreateViewHolder: " + viewType );
            val bannerPoint = ImageView(context)
            val lp = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            lp.setMargins(indicatorMargin, indicatorMargin, indicatorMargin, indicatorMargin)
            bannerPoint.layoutParams = lp
            return object : RecyclerView.ViewHolder(bannerPoint) {}
        }

        override fun onBindViewHolder(
            holder: RecyclerView.ViewHolder,
            position: Int
        ) { //Logger.e(TAG, "onBindViewHolder: " + position);
            val bannerPoint = holder.itemView as ImageView
            bannerPoint.setImageDrawable(if (currentPosition == position) mSelectedDrawable else mUnselectedDrawable)
        }

        override fun getItemCount(): Int {
            return bannerSize
        }

    }

    /**
     * 改变导航的指示点
     */
    @Synchronized
    protected fun refreshIndicator() {
        if (showIndicator && bannerSize > 1) {
            indicatorAdapter.setPosition(currentIndex % bannerSize)
            indicatorAdapter.notifyDataSetChanged()
        }
    }

    interface OnBannerItemClickListener {
        fun onItemClick(position: Int)
    }

    /**
     * dp转px
     *
     * @param dp
     * @return
     */
    private fun dp2px(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

    /**
     * 获取颜色
     */
    private fun getColor(@ColorRes color: Int): Int {
        return ContextCompat.getColor(context, color)
    }

    protected fun compareListDifferent(
        newTabList: List<String>,
        oldTabList: List<String>?
    ): Boolean {
        if (oldTabList == null || oldTabList.isEmpty()) {
            return true
        }
        if (newTabList.size != oldTabList.size) {
            return true
        }
        for (i in newTabList.indices) {
            if (TextUtils.isEmpty(newTabList[i])) {
                return true
            }
            if (newTabList[i] != oldTabList[i]) {
                return true
            }
        }
        return false
    }

    protected abstract fun onBannerScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    )

    protected abstract fun onBannerScrollStateChanged(
        recyclerView: RecyclerView,
        newState: Int
    )

    protected abstract fun getLayoutManager(context: Context, orientation: Int): L
    protected abstract fun getAdapter(
        context: Context,
        list: List<String>,
        onBannerItemClickListener: OnBannerItemClickListener?
    ): A

}