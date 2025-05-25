package app.conjure.creatorv2.data

data class MoveModel(
    var name: String = "",
    var apCost: Int? = null,

    // 0 = move costs AP, 1 = move costs MP, 2 = move costs HP
    var altCost: Int = 0,
    var damage: Int? = null,

    var damageType: Int? = null,
    var physical: Boolean? = null,

    var range: Int? = null,
    var minRange: Int? = null,

    var lineOfSight: Boolean? = null,
    var linear: Boolean = false,

    var areaOfEffectType: Int? = null,
    var areaOfEffectSize: Int? = null,

    var effects: List<EffectModel> = listOf(
        EffectModel(1, 95, false, 10, null, 2, "Description field - talk about the move here"),
        EffectModel(2, null, true, null, DamageModifierModel(1, -1), null,  null)
    ),

    // 0 = self, 1 = enemiesOnly, 2 = alliesOnly, 3 = all
    var target: Int = 1,

    val id: String = makeMiniID(),
)