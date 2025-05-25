package app.conjure.creatorv2.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity
data class CardModel(
    @PrimaryKey
    val id: String = makeMiniID(),
    var name : String = "",
    var description: String? = null,
    var hp: Int? = null,
    var ap: Int? = null,
    var mp: Int? = null,
    var cost: Int = 1,
    var type: Int? = null,
    var damageModifiers: List<DamageModifierModel> = listOf(),
    var moves: List<MoveModel> = listOf()
)

fun makeMiniID(): String {
    val id = UUID.randomUUID().toString().substring(0, 8)
    return id
}

val exampleCards = List(5) { i ->
    CardModel(
        name = "Card $i",
        description = "Example description $i",
        hp = i + 3,
        ap = i + 2,
        mp = i + 1,
        cost = i,
        type = 2,
        damageModifiers = listOf(
            DamageModifierModel(damageType = 0, modifier = -1),
            DamageModifierModel(damageType = 1, modifier = 1),
            DamageModifierModel(damageType = 2, modifier = 2),
            DamageModifierModel(damageType = 3, modifier = -2),
        )
    )
}