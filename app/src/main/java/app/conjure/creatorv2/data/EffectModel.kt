package app.conjure.creatorv2.data

data class EffectModel(
    // 0 = self, 1 = enemiesOnly, 2 = alliesOnly, 3 = all
    var target: Int = 0,

    // Chance in % between 0 - 100%. If null, it's 100% chance
    var effectChance: Int? = null,

    // true = taking the specified away, rather than bestowing it
    var removeEffect: Boolean = false,

    // 0 = poison, 1 = bleed, 2 = concuss, 3 = leech, 4 = slow, 5 = immobilise, 6 = solidify,
    // 7 = blind, 8 = silence, 9 = sleep, 10 = confuse, 11 = paralyse, 12 = heavy, 13 = bind, 14 = fly
    var statusEffect: Int? = null,

    // Apply a damageModifier change
    var damageMod: DamageModifierModel? = null,

    // how long the effect lasts for
    var turns: Int? = null,

    // Catch-all; can describe in text anything not captured by the other fields
    var effectDescription: String? = null,

    val id: String = makeMiniID(),
)