package br.com.eventos.view.activity

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.eventos.R
import br.com.eventos.model.Event
import br.com.eventos.view.adapter.EventAdapter
import br.com.eventos.viewmodel.EventsViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: EventsViewModel
    private val eventsAdapter = EventAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(EventsViewModel::class.java)

        rvEventsList.apply {
            adapter = eventsAdapter
        }

        handleListeners()
        handleObservers()
        viewModel.loadEvents()
    }

    private fun handleObservers() {

        viewModel.error.observe(this, Observer { error ->
            error?.let {
                tvEventTitlePage.text = getString(R.string.error_title)
            }
        })

        viewModel.events.observe(this, Observer { events ->
            events?.let {
                eventsAdapter.update(it)
            }
        })

        viewModel.spinner.observe(this, Observer { spinner ->
            pbLoader.visibility = if (spinner == true) {
                View.VISIBLE
            } else View.GONE
        })
    }

    private fun handleListeners() {
        eventsAdapter.onItemClick = { id ->
            val intent = DetailActivity.newIntent(this, id)
            startActivity(intent)
        }

        eventsAdapter.onOpemMap = { event ->
            openMaps(event)
        }
    }

    private fun openMaps(event: Event?) {
        if (event == null) return
        val geo = "geo:${event.latitude},${event.longitude}?q=${event.latitude},${event.longitude}(Evento ${event.title})"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geo))
        startActivity(intent)
    }
}