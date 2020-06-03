package id.practice.githubuser.helper.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class EmptyRecyclerView : RecyclerView {

    private var emptyView: View? = null

    var mode: Int? = null
        set(value) {
            when (value) {
                View.GONE -> {
                    this.visibility = View.GONE
                    emptyView?.visibility = View.GONE
                }

                View.VISIBLE -> {
                    checkIfEmpty()
                }

                View.INVISIBLE -> {
                    this.visibility = View.INVISIBLE
                    emptyView?.visibility = View.INVISIBLE
                }
            }
            field = value
        }

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setEmptyView(view: View) {
        this.emptyView = view

        checkIfEmpty()
        Toast.makeText(context, "masuk", Toast.LENGTH_SHORT).show()
    }

    override fun setAdapter(adapter: Adapter<*>?) {
        val oldAdapter = getAdapter()

        oldAdapter?.unregisterAdapterDataObserver(observer)

        super.setAdapter(adapter)

        adapter?.registerAdapterDataObserver(observer)

        checkIfEmpty()
    }

    fun checkIfEmpty() {
        if (emptyView != null && adapter != null) {
            val emptyViewVisible = adapter!!.itemCount == 0

            emptyView!!.visibility = if (emptyViewVisible) View.VISIBLE else View.GONE
            visibility = if (emptyViewVisible) View.GONE else View.VISIBLE
        }
    }

    private val observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            checkIfEmpty()
        }

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            checkIfEmpty()
        }
    }
}