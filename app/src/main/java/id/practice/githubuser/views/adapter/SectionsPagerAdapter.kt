package id.practice.githubuser.views.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import id.practice.githubuser.R
import id.practice.githubuser.views.fragment.detail.components.DetailFollowerFragment
import id.practice.githubuser.views.fragment.detail.components.DetailFollowingFragment

class SectionsPagerAdapter (private val context: Context, fragmentManager: FragmentManager, private val username: String) : FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val titles = arrayListOf(R.string.follower, R.string.following)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        val bundle = Bundle()

        when (position) {
            0 -> fragment = DetailFollowerFragment()
            1 -> fragment = DetailFollowingFragment()
        }

        bundle.putString("LOGIN", username)

        fragment?.arguments = bundle

        return fragment as Fragment
    }

    override fun getCount(): Int = titles.size

    override fun getPageTitle(position: Int): CharSequence? = context.getString(titles[position])
}