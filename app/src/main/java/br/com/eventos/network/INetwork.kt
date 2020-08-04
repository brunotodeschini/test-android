package br.com.eventos.network


import br.com.eventos.model.CheckInUser
import br.com.eventos.model.Event
import retrofit2.Response
import retrofit2.http.*

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 03/08/20
 */
interface INetwork {

    @GET("/events")
    suspend fun getAllEvents(): Response<List<Event>>

    @GET("/events/{id}")
    suspend fun getEvent(@Path("id") id: Int): Response<Event>


    @POST("/checkin")
    suspend fun doCheckin(@Body body: CheckInUser): Response<String>
}