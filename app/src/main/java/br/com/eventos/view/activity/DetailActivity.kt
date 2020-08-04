package br.com.eventos.view.activity

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.com.eventos.R
import br.com.eventos.model.Event
import br.com.eventos.utils.formatDate
import br.com.eventos.utils.formattedPrice
import br.com.eventos.viewmodel.DetailViewModel
import br.com.eventos.viewmodel.EventsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.dialog_checkin.*
import kotlinx.android.synthetic.main.event_list_card.*

class DetailActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_EVENT = "br.com.eventos.model.event"

        fun newIntent(packageContext: Context, id: Int): Intent {
            return Intent(packageContext, DetailActivity::class.java).apply {
                putExtra(EXTRA_EVENT, id)
            }
        }
    }

    lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        val id = intent.getIntExtra(EXTRA_EVENT, 0)

        viewModel.loadEvent(id)

        setupObservers()
        setupListeners()

    }

    private fun setupListeners() {
        ivDirections.setOnClickListener {
            openMaps(viewModel.event.value)
        }

        ivShare.setOnClickListener {
            val event = viewModel.event.value
            val textToSend = "${event?.title} \n ${event?.date} - ${event?.price.formattedPrice()}"
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textToSend)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        tvCheckin.setOnClickListener {

            val dialog = AlertDialog.Builder(this)
            val inflater = this.layoutInflater
            val view = inflater.inflate(R.layout.dialog_checkin, null)

            dialog.setView(view)

            dialog.setPositiveButton("Check-in") {dialog, _ ->
                val eventId = viewModel.event.value?.id
                val etName = view.findViewById<EditText>(R.id.etName)
                val name = etName.text.toString()

                val etEmail = view.findViewById<EditText>(R.id.etEmail)
                val email = etEmail.text.toString()

                if (name.isNullOrEmpty() || email.isNullOrEmpty() || eventId.isNullOrEmpty()) return@setPositiveButton

                viewModel.doChecking(eventId, name, email)
                dialog.dismiss()
            }

            dialog.setNegativeButton("Cancelar") { dialog, _ ->
                dialog.dismiss()
            }

            dialog.show()
        }

    }

    private fun setupObservers() {

        viewModel.event.observe(this, Observer {event ->
            if (event != null) {
                tvEventTitlePage.text = event.title

                val imageUrl = event.image.toUri().buildUpon().scheme("http").build()
                Glide.with(this)
                    .load(imageUrl)
                    .apply(RequestOptions()
                        .centerCrop()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                    )
                    .into(ivEventCover)

                tvCurrentEventName.text = event.title
                tvEventDate.text = event.date.formatDate()
                tvEventTicketPrice.text = event.price.formattedPrice()
                tvEventDescription.text = event.description
            }
        })

        viewModel.spinner.observe(this, Observer {
            if (it == true) {
                pbLoaderDetail.visibility = View.VISIBLE
                clDetails.visibility = View.GONE
            } else {
                pbLoaderDetail.visibility = View.GONE
                clDetails.visibility = View.VISIBLE
            }
        })

        viewModel.errorCheckin.observe(this, Observer {
            if (it == true) {
                Toast.makeText(
                    this, getString(R.string.error_checkin), Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(
                    this, getString(R.string.success_check_in), Toast.LENGTH_LONG)
                    .show()
            }
        })
    }

    private fun openMaps(event: Event?) {
        if (event == null) return
        val geo = "geo:${event.latitude},${event.longitude}?q=${event.latitude},${event.longitude}(Evento ${event.title})"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(geo))
        startActivity(intent)
    }


}