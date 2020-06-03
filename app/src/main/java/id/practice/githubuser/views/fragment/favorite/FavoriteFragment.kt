package id.practice.githubuser.views.fragment.favorite

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import id.practice.githubuser.R
import id.practice.githubuser.views.activities.SettingsActivity
import id.practice.githubuser.views.adapter.ListFavoriteAdapter
import id.practice.githubuser.data.database.favorite.Favorite
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_favorite.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.emptyView
import kotlinx.android.synthetic.main.fragment_home.progressBar
import kotlinx.android.synthetic.main.fragment_home.rv_users

/**
 * A simple [Fragment] subclass.
 */
class FavoriteFragment : Fragment() {

    private var adapter = ListFavoriteAdapter()
    lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FavoriteViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        activity?.apply {
            title = resources.getString(R.string.favorite_title)
        }

        (activity as AppCompatActivity).supportActionBar?.elevation = 11F

        rv_users.setHasFixedSize(true)
        rv_users.setEmptyView(activity?.findViewById(R.id.emptyView) as View)
        rv_users.layoutManager = LinearLayoutManager(context)
        rv_users.adapter = adapter

        favoriteViewModel.read()
        favoriteViewModel.favorites.observe(viewLifecycleOwner, Observer { favorites ->
            if (favorites != null) {
                adapter.favorites = favorites

                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : ListFavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(it: View, favorite: Favorite) {
                val toDetailFragment = FavoriteFragmentDirections.actionFavoriteFragmentToDetailFragment()

                toDetailFragment.login = favorite.login

                Navigation.findNavController(it).navigate(toDetailFragment)
            }
        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.favorite_menu, menu)

        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                menu.findItem(R.id.search).collapseActionView()

                searchView.isIconified = true
                searchView.clearFocus()

                showLoading(true)

                favoriteViewModel.read(query.toString())

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                startActivity(Intent(context, SettingsActivity::class.java))
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            rv_users.mode = View.GONE

            progressBar.visibility = View.VISIBLE
        } else {
            rv_users.mode = View.VISIBLE

            progressBar.visibility = View.GONE
        }
    }
}
