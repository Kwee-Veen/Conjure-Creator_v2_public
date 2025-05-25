package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.EffectModel
import app.conjure.creatorv2.helpers.ChancePercentageTranslator
import app.conjure.creatorv2.helpers.GetDamageModifierIcon
import app.conjure.creatorv2.helpers.GetStatusEffectIcon
import app.conjure.creatorv2.helpers.TargetTranslator

@Composable
fun MoveEffect(
    effect: EffectModel,
    modifier: Modifier = Modifier
) {
    val cardHeight = if (!effect.effectDescription.isNullOrEmpty()) 100.dp else 50.dp
    var effectString = ""

    if (effect.effectChance != null) {
        effectString += ChancePercentageTranslator(effect.effectChance)
        if (effect.removeEffect) {
            effectString += " to remove from "
        } else {
            effectString += ", "
        }
    } else {
        if (effect.removeEffect) {
            effectString += "Remove from "
        }
    }

    // replaceFirstChar is a really handy tip from ChatGPT (30/12/2024);
    // TargetTranslator supplies strings in title case, while this sets the first letter to lower case if appropriate to do so
    effectString +=
        if (effect.effectChance != null || effect.removeEffect) TargetTranslator(effect.target).replaceFirstChar { it.lowercase() }
        else TargetTranslator(effect.target)

    if (effect.turns != null) {
        effectString += " for ${effect.turns}x turn"
    }

    effectString += ":"

    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.standard_jet)
        ),
        modifier = modifier
            .padding(vertical = 2.dp, horizontal = 6.dp)
            .height(cardHeight)
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentWidth()
                    .padding(top = 3.dp),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(
                    text = effectString,
                    style = MaterialTheme.typography.bodyMedium.copy( fontWeight = FontWeight.Medium ),
                    modifier = Modifier.wrapContentWidth(),
                    color = colorResource(R.color.standard_fawn),
                )

                if (effect.damageMod != null) {
                    Image(
                        painter = painterResource(id = GetDamageModifierIcon(effect.damageMod!!.damageType)!!),
                        contentDescription = "Damage modifier",
                        modifier = Modifier.size(48.dp),
                        contentScale = ContentScale.Fit,
                    )
                    Text(
                        text = effect.damageMod!!.modifier.toString(),
                        style = MaterialTheme.typography.headlineMedium.copy( fontWeight = FontWeight.Medium ),
                        modifier = Modifier.wrapContentWidth(),
                        color = colorResource(R.color.standard_light_grey),
                    )
                } else if (effect.statusEffect != null) {
                    Image(
                        painter = painterResource(id = GetStatusEffectIcon(effect.statusEffect)),
                        contentDescription = "Status effect",
                        modifier = Modifier.size(48.dp),
                        contentScale = ContentScale.Fit,
                    )
                }
            }
        }
        if (!effect.effectDescription.isNullOrEmpty()) {
            Row(
                modifier = Modifier
                    .heightIn(max = 70.dp)
                    .wrapContentHeight()
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            )
            {
                Text(
                    text = effect.effectDescription.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy( fontWeight = FontWeight.Light ),
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(R.color.standard_fawn),
                )
            }
        }
    }
}