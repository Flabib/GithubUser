package id.practice.githubuser.views.fragment.home

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import id.practice.githubuser.R
import id.practice.githubuser.views.activities.SettingsActivity
import id.practice.githubuser.views.adapter.ListUserAdapter
import id.practice.githubuser.data.model.User
import kotlinx.android.synthetic.main.fragment_detail.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_home.progressBar

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    private var adapter = ListUserAdapter()
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(HomeViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setHasOptionsMenu(true)
        activity?.apply {
            title = resources.getString(R.string.home_title)
        }

        (activity as AppCompatActivity).supportActionBar?.elevation = 11F
        
        rv_users.setHasFixedSize(true)
        rv_users.setEmptyView(activity?.findViewById(R.id.emptyView) as View)
        rv_users.layoutManager = LinearLayoutManager(context)
        rv_users.adapter = adapter

        context?.let { homeViewModel.buildUsers(it) }
        homeViewModel.getUsers().observe(viewLifecycleOwner, Observer { users ->
            if (users != null) {
                adapter.users = users

                showLoading(false)
            }
        })

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(it: View, user: User) {
                val toDetailFragment = HomeFragmentDirections.actionHomeFragmentToDetailFragment()

                toDetailFragment.login = user.login

                Navigation.findNavController(it).navigate(toDetailFragment)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        inflater.inflate(R.menu.home_menu, menu)

        val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity?.componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                menu.findItem(R.id.search).collapseActionView()

                searchView.isIconified = true
                searchView.clearFocus()

                showLoading(true)

                homeViewModel.setUsers(query.toString())

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
//                Toolkit.changeTheme(context)
                startActivity(Intent(context, SettingsActivity::class.java))
            }

            R.id.favorite -> {
                val toFavoriteFragment = HomeFragmentDirections.actionHomeFragmentToFavoriteFragment()

                Navigation.findNavController(view as View).navigate(toFavoriteFragment)
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
