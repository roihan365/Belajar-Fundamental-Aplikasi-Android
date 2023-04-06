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

    companion object{
        private const val TAG = "MainActivity"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)

        val items = arrayListOf<ItemsItem>()
        val userAdapter = UserAdapter(items, object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
            }
        })
        binding.rvUser.adapter = userAdapter

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
                                setUserData(responseBody.items as ArrayList<ItemsItem>)
                                binding.apply {
                                    tvHome.visibility = View.INVISIBLE
                                    ivLogo.visibility = View.INVISIBLE
                                }
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
        when (item.itemId) {
            R.id.favorite_menu -> favorite()
            R.id.setting_menu -> setting()
        }
        return true
    }

    private fun favorite() {
        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
        startActivity(intent)
    }

    private fun setting() {
        val intent = Intent(this@MainActivity, SettingModeActivity::class.java)
        startActivity(intent)
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUserData(users: List<ItemsItem>?) {
        val listUserAdapter = UserAdapter(users as ArrayList<ItemsItem>, object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                showSelectedUser(data)
            }
        })
        binding.rvUser.adapter = listUserAdapter

    }

}