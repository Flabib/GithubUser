package id.practice.githubuser.views.fragment.detail.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import id.practice.githubuser.R
import id.practice.githubuser.views.adapter.ListUserAdapter
import id.practice.githubuser.data.model.User
import id.practice.githubuser.views.fragment.detail.DetailFragmentDirections
import id.practice.githubuser.views.fragment.detail.DetailViewModel
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class DetailFollowingFragment : Fragment() {

    private lateinit var detailViewModel: DetailViewModel
    private var adapter = ListUserAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rv_users.setHasFixedSize(true)
        rv_users.layoutManager = LinearLayoutManager(context)
        rv_users.adapter = adapter

        val args = arguments

        args?.getString("LOGIN")?.let { detailViewModel.setFollowing(it) }
        detailViewModel.getFollowing().observe(viewLifecycleOwner, Observer { users ->
            if (users != null) adapter.users = users
        })

        adapter.setOnItemClickCallback(object : ListUserAdapter.OnItemClickCallback {
            override fun onItemClicked(it: View, user: User) {
                val toDetailFragment = DetailFragmentDirections.actionDetailFragmentSelf()

                toDetailFragment.login = user.login

                Navigation.findNavController(it).navigate(toDetailFragment)
            }
        })
    }
}
