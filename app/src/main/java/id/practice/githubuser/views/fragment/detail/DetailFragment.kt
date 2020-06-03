package id.practice.githubuser.views.fragment.detail

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import id.practice.githubuser.R
import id.practice.githubuser.views.activities.SettingsActivity
import id.practice.githubuser.views.adapter.SectionsPagerAdapter
import id.practice.githubuser.data.database.favorite.Favorite
import id.practice.githubuser.data.model.User
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.progressBar

/**
 * A simple [Fragment] subclass.
 */
class DetailFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var favorite = true
    private lateinit var user: User

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val params = DetailFragmentArgs.fromBundle(arguments as Bundle)

        setHasOptionsMenu(true)
        activity?.apply {
            title = resources.getString(R.string.loading)
        }

        showLoading(true)

        detailViewModel.setUser(params.login)

        detailViewModel.getFavorite().observe(viewLifecycleOwner, Observer { favorite ->
            itemFavorite.isFavorite = favorite

            this.favorite = favorite

            itemFavorite.setOnClickListener {
                if (itemFavorite.isFavorite) {
                    detailViewModel.delete(user.login)
                    itemFavorite.isFavorite = false
                } else {
                    detailViewModel.insert(Favorite(user.login, user.avatar_url))
                    itemFavorite.isFavorite = true
                }
            }
        })

        detailViewModel.getUser().observe(viewLifecycleOwner, Observer { user ->
            activity?.title = ""

            (activity as AppCompatActivity).supportActionBar?.elevation = 0F

            this.user = user

            context?.let {
                Glide.with(it)
                    .load(user.avatar_url)
                    .into(itemAvatar)
            }

            if (user?.name == null) itemName.text = user?.login
            else itemName.text = user.name

            if (user?.name == null) itemLocation.text = user?.company
            else itemLocation.text = user.location

            itemFollower.text = user?.followers.toString()
            itemFollowing.text = user?.following.toString()

            view_pager.adapter = context?.let {
                SectionsPagerAdapter(it, childFragmentManager, user.login)
            }

            tabs.setupWithViewPager(view_pager)

            showLoading(false)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
//                Toolkit.changeTheme(context)
                startActivity(Intent(context, SettingsActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
            contentPanel.visibility = View.GONE
            contentHeader.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
            contentPanel.visibility = View.VISIBLE
            contentHeader.visibility = View.VISIBLE
        }
    }
}
