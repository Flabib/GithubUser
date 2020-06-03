package id.practice.githubuser.views.adapter

import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.practice.githubuser.R
import id.practice.githubuser.data.database.favorite.Favorite
import kotlinx.android.synthetic.main.item_user.view.*

class ListFavoriteAdapter : RecyclerView.Adapter<ListFavoriteAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    var favorites = listOf<Favorite>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    interface OnItemClickCallback {
        fun onItemClicked(it: View, favorite: Favorite)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(favorite: Favorite) {
            with (itemView) {
                if (Patterns.WEB_URL.matcher(favorite.avatar_url).matches()) {
                    Glide.with(itemView.context)
                        .load(favorite.avatar_url)
                        .into(itemAvatar)
                } else {
                    itemAvatar.setImageResource(
                        resources.getIdentifier(
                            favorite.avatar_url.split("/")[1],
                            "drawable",
                            context.packageName
                        )
                    )
                }

                setOnClickListener {
                    onItemClickCallback?.onItemClicked(it, favorite)
                }

                itemName.text = favorite.login
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return favorites.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(favorites[position])
    }

}