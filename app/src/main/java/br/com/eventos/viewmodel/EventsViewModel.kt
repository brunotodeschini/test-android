package br.com.eventos.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.eventos.model.Event
import br.com.eventos.repository.EventsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 03/08/20
 */
class EventsViewModel: ViewModel() {

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>>
    get() = _events

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
    get() = _error

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
    get() = _spinner

    fun loadEvents() {
        viewModelScope.launch(Dispatchers.IO) {
            _spinner.postValue(true)
            try {
                val response = EventsRepository().getEvents()
                _events.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _spinner.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }

}