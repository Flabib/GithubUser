package id.practice.githubuser.views.adapter

import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.practice.githubuser.R
import id.practice.githubuser.data.model.User
import kotlinx.android.synthetic.main.item_user.view.*

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ListViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null
    var users = listOf<User>()
        set(value) {
            field = value

            notifyDataSetChanged()
        }

    interface OnItemClickCallback {
        fun onItemClicked(it: View, user: User)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {
            with (itemView) {
                if (Patterns.WEB_URL.matcher(user.avatar_url).matches()) {
                    Glide.with(itemView.context)
                        .load(user.avatar_url)
                        .into(itemAvatar)
                } else {
                    itemAvatar.setImageResource(
                        resources.getIdentifier(user.avatar_url.split("/")[1], "drawable", context.packageName)
                    )
                }

                itemName.text = user.login

                setOnClickListener {
                    onItemClickCallback?.onItemClicked(it, user)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUserAdapter.ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false))
    }

    override fun getItemCount(): Int {
        return users.size
    }

    override fun onBindViewHolder(holder: ListUserAdapter.ListViewHolder, position: Int) {
        holder.bind(users[position])
    }
}