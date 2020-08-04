package br.com.eventos.network

import br.com.eventos.model.CheckInUser
import br.com.eventos.model.Event
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 03/08/20
 */

class EventsService {

    private val client = OkHttpClient.Builder().build()

    private val api: INetwork = Retrofit.Builder()
        .baseUrl("https://5b840ba5db24a100142dcd8c.mockapi.io/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()
        .create(INetwork::class.java)

    suspend fun getAllEvents(): Response<List<Event>> = api.getAllEvents()

    suspend fun getEvent(eventId: Int): Response<Event> = api.getEvent(eventId)

    suspend fun doCheckin(body: CheckInUser): Response<String> = api.doCheckin(body)
}