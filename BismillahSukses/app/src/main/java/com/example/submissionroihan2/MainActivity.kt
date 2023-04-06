package com.example.submissionroihan2

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionroihan2.adapter.UserAdapter
import com.example.submissionroihan2.api.ApiConfig
import com.example.submissionroihan2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val adapter = UserAdapter()

    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.rvUser.adapter = adapter

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


        adapter.setOnItemCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: ItemsItem) {
        val moveWithParcelableIntent = Intent(this@MainActivity, DetailActivity::class.java)
        moveWithParcelableIntent.putExtra(DetailActivity.ID, data)
        startActivity(moveWithParcelableIntent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search_menu).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_user)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                showLoading(true)
                val client = ApiConfig.getApiService().getGithubUser(query)
                client.enqueue(object : Callback<GithubResponse> {
                    override fun onResponse(
                        call: Call<GithubResponse>,
                        response: Response<GithubResponse>
                    ) {
                        showLoading(false)
                        if (response.isSuccessful) {
                            val responseBody = response.body()
                            Log.e(TAG, responseBody?.items.toString())
                            if (responseBody != null) {
                                adapter.setList(responseBody.items as ArrayList<ItemsItem>)
                            }
                        } else {
                            Log.e(TAG, "onFailure: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                        showLoading(false)
                        Log.e(TAG, "onFailure: ${t.message}")
                    }

                })
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}