package com.example.submissionroihan2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.submissionroihan2.adapter.FollsAdapter
import com.example.submissionroihan2.databinding.FragmentFollowerBinding
import com.example.submissionroihan2.viewModel.FollowerViewModel

class FollowerFragment : Fragment() {
    private lateinit var binding: FragmentFollowerBinding
    private lateinit var rvUser: RecyclerView
    private var position: Int? = null
    private lateinit var username: String
    private val folls = ArrayList<ItemsItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUser = view.findViewById(R.id.rv_folls)
        rvUser.setHasFixedSize(true)

//        val tvLabel: TextView = view.findViewById(R.id.tv_follower)
//        val index = arguments?.getInt(ARG_SECTION_NUMBER, 0)
//        val name = arguments?.getString(ARG_USERNAME)
//        tvLabel.text = getString(R.string.fragmentt, index)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME).toString()
        }

        val followerViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(FollowerViewModel::class.java)

        if (position == 1){
//            binding.testUsername.text = "Get Follower $username"
            followerViewModel.getFollower(username)
            followerViewModel.listFollower.observe(viewLifecycleOwner) {listFolls ->
                setFollsData(listFolls)
            }
        } else {
//            binding.testUsername.text = "Get Following $username"
            followerViewModel.getFollowing(username)
            followerViewModel.listFollowing.observe(viewLifecycleOwner) {listFolls ->
                setFollsData(listFolls)
            }
        }
        followerViewModel.isLoading.observe(viewLifecycleOwner) {showLoading(it)}
        followerViewModel.getFollower(arguments?.getString(DetailActivity.FOLLS).toString())


        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFolls.layoutManager = layoutManager

        showRecyclerList()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowerBinding.inflate(layoutInflater)
        return binding.root
    }

    companion object {
       const val ARG_POSITION = "section_number"
        const val ARG_USERNAME = "username"
    }

    private fun setFollsData(folls: List<ItemsItem>) {
        val listUser = ArrayList<ItemsItem>()
        with(binding) {
            for (user in folls) {
                listUser.clear()
                listUser.addAll(folls)
            }
            rvFolls.layoutManager = LinearLayoutManager(context)
            val adapter = FollsAdapter(folls)
            rvFolls.adapter = adapter
        }
    }

    private fun showRecyclerList() {
        rvUser.layoutManager = LinearLayoutManager(context)
        val listHeroAdapter = FollsAdapter(folls)
        rvUser.adapter = listHeroAdapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}