package app.conjure.creatorv2.ui.screens.edit

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.conjure.creatorv2.data.CardModel
import app.conjure.creatorv2.data.DamageModifierModel
import app.conjure.creatorv2.data.MoveModel
import app.conjure.creatorv2.data.repository.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditViewModel @Inject
constructor(private val repository: RoomRepository, savedStateHandle: SavedStateHandle) : ViewModel() {

    var card by mutableStateOf(CardModel())
    val id: String? = savedStateHandle["id"]

    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var hp by mutableIntStateOf(0)
    var ap by mutableIntStateOf(0)
    var mp by mutableIntStateOf(0)
    var type by mutableIntStateOf(0)
    var cost by mutableIntStateOf(1)
    var modifiers by mutableStateOf<List<DamageModifierModel>>(emptyList())
    var moves by mutableStateOf<List<MoveModel>>(emptyList())

    var tempMod by mutableStateOf(DamageModifierModel(damageType = 10, modifier = 1))

    init {
        viewModelScope.launch {
            if (id != null) {
                repository.get(id).collect { objCard ->
                    card = objCard
                    name = objCard.name
                    description = objCard.description ?: ""
                    hp = objCard.hp ?: 0
                    ap = objCard.ap ?: 0
                    mp = objCard.mp ?: 0
                    type = objCard.type ?: 0
                    cost = objCard.cost
                    modifiers = objCard.damageModifiers
                    moves = objCard.moves
                }
            }
        }
    }

    fun edit() {
        val updatedCard = card.copy(
            name = name,
            description = description,
            hp = hp,
            ap = ap,
            mp = mp,
            type = type,
            cost = cost,
            damageModifiers = modifiers,
            moves = moves,
        )
        viewModelScope.launch {
            repository.edit(updatedCard)
        }
    }

    fun addModifier() {
        val index = modifiers.indexOfFirst {
            it.damageType == tempMod.damageType
        }
        modifiers = modifiers.toMutableList().apply {
            if (index == -1) {
                add(tempMod)
            } else {
                removeAt(index)
                add(index, tempMod)
            }
        }
        // refresh tempMod to a new DamageModifierModel (with a new id)
        tempMod = DamageModifierModel(damageType = tempMod.damageType, modifier = tempMod.modifier)
    }

    fun deleteModifier(mod: DamageModifierModel) {
        modifiers =
            modifiers.toMutableList().apply {
                val index = indexOf(mod)
                if (index != -1) {
                    removeAt(index)
                }
            }.toList()
    }

    fun addMove(move: MoveModel) {
        moves = moves.toMutableList().apply { add(move) }
    }
    
    fun deleteMove(move: MoveModel) {
        moves =
            moves.toMutableList().apply {
                val index = indexOf(move)
                if (index != -1) {
                    removeAt(index)
                }
            }.toList()
    }
}