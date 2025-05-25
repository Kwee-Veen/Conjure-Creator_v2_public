package app.conjure.creatorv2.data.repository

import app.conjure.creatorv2.data.CardModel
import app.conjure.creatorv2.data.room.CardDAO
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoomRepository @Inject
constructor(private val cardDAO: CardDAO) {
    fun getAll(): Flow<List<CardModel>>
            = cardDAO.getAll()

    fun get(id: String): Flow<CardModel> = cardDAO.get(id)

    suspend fun insert(card: CardModel) {
        cardDAO.insert(card)
    }

    suspend fun edit(card: CardModel) {
        cardDAO.edit(card.id, card.name, card.description, card.hp, card.ap, card.mp, card.type, card.cost, card.damageModifiers, card.moves)
    }

    suspend fun delete(card: CardModel) {
        cardDAO.delete(card)
    }
}