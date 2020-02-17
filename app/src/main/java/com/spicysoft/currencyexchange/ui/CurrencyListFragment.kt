package com.spicysoft.currencyexchange.ui

import android.app.SearchManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.spicysoft.currencyexchange.R
import kotlinx.android.synthetic.main.fragment_currency_list.*

class CurrencyListFragment: Fragment() {

    private lateinit var adapter: ExchangeRateAdapter

    private lateinit var viewModel: CurrencyListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_currency_list, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_main, menu)

        val searchView = menu?.findItem(R.id.menu_search).actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_hint)

        val view = searchView.findViewById<View>(androidx.appcompat.R.id.search_plate)
        view?.setBackgroundColor(Color.TRANSPARENT)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.filterCurrencyRates(newText ?: "")
                return true
            }
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)

        adapter = ExchangeRateAdapter()


        viewModel = ViewModelProvider(this)[CurrencyListViewModel::class.java]
        viewModel.rates.observe(this, Observer {
            adapter.submitList(it)

            swipeRefreshLayout.isRefreshing = false
        })

        viewModel.updateTime.observe(this, Observer {
            tvUpdatedTime.text = it.toString("yyyy/MM/dd hh:MM a")
        })

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply {
            setHasFixedSize(true)
            //addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = this@CurrencyListFragment.adapter
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary, null))
        } else {
            swipeRefreshLayout.setColorSchemeColors(resources.getColor(R.color.colorPrimary))
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.loadCurrencyRate()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        swipeRefreshLayout.isRefreshing = true

        viewModel.loadCurrencyRate()
    }

    private val adapterDataObserver = object : RecyclerView.AdapterDataObserver() {

        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            super.onItemRangeInserted(positionStart, itemCount)
            recyclerView?.scrollToPosition(0)
        }
    }

    override fun onStart() {
        super.onStart()
        adapter.registerAdapterDataObserver(adapterDataObserver)
    }

    override fun onStop() {
        super.onStop()
        adapter.unregisterAdapterDataObserver(adapterDataObserver)
    }

}