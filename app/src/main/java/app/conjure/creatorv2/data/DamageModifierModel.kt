package app.conjure.creatorv2.data

data class DamageModifierModel(
    var damageType: Int = 0,
    var modifier: Int = -1,
    var id: String = makeMiniID(),
)