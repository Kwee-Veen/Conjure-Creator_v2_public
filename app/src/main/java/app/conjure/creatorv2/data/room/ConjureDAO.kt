package app.conjure.creatorv2.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import app.conjure.creatorv2.data.CardModel
import app.conjure.creatorv2.data.DamageModifierModel
import app.conjure.creatorv2.data.MoveModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CardDAO {
    @Query("SELECT * FROM cardmodel")
    fun getAll(): Flow<List<CardModel>>

    @Query("SELECT * FROM cardmodel WHERE id=:id")
    fun get(id: String): Flow<CardModel>

    @Insert
    suspend fun insert(card: CardModel)

    @Query("UPDATE cardmodel SET name=:name, description=:description, hp=:hp, ap=:ap, mp=:mp, type=:type, cost=:cost, damageModifiers=:damageModifiers, moves=:moves WHERE id = :id")
    suspend fun edit(id: String, name:String, description:String?, hp:Int?, ap:Int?, mp: Int?, type:Int?, cost:Int?, damageModifiers:List<DamageModifierModel>, moves:List<MoveModel>)

    @Delete
    suspend fun delete(card: CardModel)
}