package br.com.eventos.repository

import br.com.eventos.model.CheckInUser
import br.com.eventos.model.Event
import br.com.eventos.network.EventsService
import okhttp3.MultipartBody

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 03/08/20
 */

class EventsRepository {

    private val service = EventsService()

    suspend fun getEvents(): List<Event> {
        val response = service.getAllEvents()

        val body = response.body()
        val statusCode = response.code()

        return if (body != null && statusCode in 200..299) {
            body
        } else {
            throw RuntimeException(response.message())
        }
    }

    suspend fun getEvent(id: Int): Event {
        val response = service.getEvent(id)

        val body = response.body()
        val statusCode = response.code()

        return if (body != null && statusCode in 200..299) {
            body
        } else {
            throw RuntimeException(response.message())
        }
    }

    suspend fun doCheckin(eventId: String, name: String, email: String): String {

        val checkInUser = CheckInUser(eventId, name, email)

        val response = service.doCheckin(checkInUser)

        return if(response.isSuccessful && response.body() != null)
            response.body().toString()
        else
            throw RuntimeException(response.message())
    }
 }