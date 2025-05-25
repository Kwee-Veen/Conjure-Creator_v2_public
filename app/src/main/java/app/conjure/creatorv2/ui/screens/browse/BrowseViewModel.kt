package app.conjure.creatorv2.ui.screens.browse

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.conjure.creatorv2.data.CardModel
import app.conjure.creatorv2.data.repository.RoomRepository
import app.conjure.creatorv2.helpers.ImageDirectoryHelper.imageDirectory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

import kotlinx.coroutines.launch
import java.io.File

@HiltViewModel
class BrowseViewModel @Inject
constructor(private val repository: RoomRepository) : ViewModel() {
    private val _cards
            = MutableStateFlow<List<CardModel>>(emptyList())
    val uiCards: StateFlow<List<CardModel>>
            = _cards.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAll().collect { listOfCards ->
                _cards.value = listOfCards
            }
        }
    }

    fun deleteCard(card: CardModel) {
        val cardImage = File(imageDirectory, "${card.id}.jpg")
        val cardQR = File(imageDirectory, "${card.id}_QR.png")
        viewModelScope.launch {
            repository.delete(card)
            if (cardImage.exists()) {
                cardImage.delete()
            }
            if (cardQR.exists()) {
                cardQR.delete()
            }
        }
    }
}