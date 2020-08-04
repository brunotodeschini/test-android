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
import java.lang.Exception

/**
 * @author Todeschini
 * @author obruno1997@gmail.com
 * @since 04/08/20
 */
class DetailViewModel: ViewModel() {

    private val _event = MutableLiveData<Event>()
    val event: LiveData<Event>
        get() = _event

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _spinner = MutableLiveData<Boolean>()
    val spinner: LiveData<Boolean>
        get() = _spinner

    private val _errorCheckin = MutableLiveData<Boolean>()
    val errorCheckin: LiveData<Boolean>
        get() = _errorCheckin

    private val _spinnerCheckin = MutableLiveData<Boolean>()
    val spinnerCheckin: LiveData<Boolean>
        get() = _spinnerCheckin


    fun loadEvent(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _spinner.postValue(true)
            try {
                val response = EventsRepository().getEvent(id)
                _event.postValue(response)
            } catch (e: Exception) {
                _error.postValue(e.message)
            } finally {
                _spinner.postValue(false)
            }
        }
    }

    fun doChecking(eventId: String, name: String, email: String) {
        if (eventId == null) return
        viewModelScope.launch(Dispatchers.IO) {
            _spinnerCheckin.postValue(true)
            try {
                EventsRepository().doCheckin(eventId, name, email)
                _errorCheckin.postValue(false)
            } catch (e: Exception) {
                _errorCheckin.postValue(true)
            } finally {
                _spinnerCheckin.postValue(false)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.coroutineContext.cancel()
    }
}