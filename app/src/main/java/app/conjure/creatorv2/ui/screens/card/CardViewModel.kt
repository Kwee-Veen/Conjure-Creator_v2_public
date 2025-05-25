package app.conjure.creatorv2.ui.screens.card

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
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
class CardViewModel @Inject
constructor(private val repository: RoomRepository) : ViewModel() {

    var name by mutableStateOf("")
    var description by mutableStateOf("")
    var hp by mutableIntStateOf(0)
    var ap by mutableIntStateOf(0)
    var mp by mutableIntStateOf(0)
    var type by mutableIntStateOf(1)
    var cost by mutableIntStateOf(1)
    var modifiers by mutableStateOf<List<DamageModifierModel>>(emptyList())
    var tempMod by mutableStateOf(DamageModifierModel(damageType = 10, modifier = 1))
    var moves by mutableStateOf<List<MoveModel>>(emptyList())

    fun insert() = viewModelScope.launch {
        repository.insert(
            CardModel(
                name = name,
                description = description,
                hp = hp,
                ap = ap,
                mp = mp,
                type = type,
                cost = cost,
                damageModifiers = modifiers,
                moves = moves
            )
        )
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
}