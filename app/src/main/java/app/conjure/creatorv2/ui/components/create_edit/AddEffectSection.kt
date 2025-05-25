package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.DamageModifierModel
import app.conjure.creatorv2.data.EffectModel
import app.conjure.creatorv2.data.makeMiniID
import app.conjure.creatorv2.ui.components.general.ChancePicker
import app.conjure.creatorv2.ui.components.general.IsAStatusEffectPicker
import app.conjure.creatorv2.ui.components.general.MiniDamageModifierPicker
import app.conjure.creatorv2.ui.components.general.ModifierPicker
import app.conjure.creatorv2.ui.components.general.RemoveEffectPicker
import app.conjure.creatorv2.ui.components.general.StatusEffectPicker
import app.conjure.creatorv2.ui.components.general.TargetPicker
import app.conjure.creatorv2.ui.components.general.TurnsPicker

@Composable
fun AddEffectSection(
    onAddEffect: (EffectModel) -> Unit,
) {
    var target by remember { mutableStateOf<Int?>(null) }
    var effectDescription by remember { mutableStateOf<String?>(null) }
    var effectChance by remember { mutableStateOf<Int?>(100) }
    var removeEffect by remember { mutableStateOf(false) }
    var statusEffect by remember { mutableStateOf<Int?>(null) }
    var damageMod by remember { mutableStateOf(DamageModifierModel(10,0)) }
    var turns by remember { mutableIntStateOf(1) }
    var id by remember { mutableStateOf(makeMiniID()) }

    // if this is true, will see and add a status effect; if false, will see and add a damage modifier.
    var effectIsAStatus by remember { mutableStateOf(true) }

    val colorStandardFawn = colorResource(id = R.color.standard_fawn)
    val colorHighlightPink = colorResource(id = R.color.highlight_pink)
    val colorHighlightRose = colorResource(id = R.color.highlight_rose)

    Row(
        modifier = Modifier
            .heightIn(max = 1000.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier
                .weight(3f),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .padding(bottom = 20.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                Row{
                    TargetPicker(
                        onAmountChange = { target = it },
                        target ?: 0
                    )
                }
                Row{
                    ChancePicker(
                        onAmountChange = { effectChance = it },
                        effectChance ?: 24
                    )
                }
                Row{
                    RemoveEffectPicker(
                        onAmountChange = { removeEffect = it },
                        removeEffect
                    )
                }
                Row{
                    TurnsPicker(
                        onAmountChange = { turns = it },
                        turns
                    )
                }
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            )
            {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 110.dp)
                        .wrapContentHeight(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = colorHighlightRose,
                            unfocusedBorderColor = colorStandardFawn,
                            focusedTextColor = colorHighlightPink,
                            unfocusedTextColor = colorStandardFawn,
                        ),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Light),
                        value = effectDescription ?: "",
                        // it.take(100) -> limits description to 100 chars.
                        // Reference: https://stackoverflow.com/questions/67136058/textfield-maxlength-android-jetpack-compose
                        onValueChange = { effectDescription = (it.take(100)) },
                        label = { Text(stringResource(id = R.string.text_effectDescriptionHint)) },
                        keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
                    )
                }
            }
        }
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Column(){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    Row {
                        IsAStatusEffectPicker(
                            onAmountChange = { effectIsAStatus = it },
                            effectIsAStatus,
                        )
                    }
                    if (!effectIsAStatus) {
                        Row {
                            ModifierPicker(
                                onAmountChange = { damageMod.modifier = it },
                                damageMod.modifier,
                            )
                        }
                    }
                }
                if (effectIsAStatus) {
                    StatusEffectPicker(
                        onAmountChange = { statusEffect = it },
                        statusEffect ?: -1
                    )
                } else {
                    MiniDamageModifierPicker(
                        onModChange = { damageMod.damageType = it },
                        damageModifierType = damageMod.damageType,
                    )
                }
            }
        }
    }
    Row(
        modifier = Modifier
            .heightIn(max = 130.dp)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(vertical = 5.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        AddEffectButton(
            onAddEffect = {
                onAddEffect(
                    EffectModel(
                        target = target ?: 0,
                        effectChance = if ((effectChance != null) && (effectChance!! > 0) && (effectChance!! < 100)) effectChance else null,
                        removeEffect = removeEffect,
                        statusEffect = if (effectIsAStatus && statusEffect != null && statusEffect!! >= 0) statusEffect else null,
                        damageMod = if (!effectIsAStatus && damageMod.modifier != 0) damageMod else null,
                        // if removing an effect, 'turns' is set to null (i.e. infinite), as the removal isn't temporary - removed effects are gone
                        turns = if (turns > 0 && !removeEffect) turns else null,
                        effectDescription = if (!effectDescription.isNullOrEmpty()) effectDescription else null,
                        id = id,
                    )
                )
                // reset id, damageMod & statusEffect to new values
                id = makeMiniID()
                damageMod = DamageModifierModel(damageMod.damageType, damageMod.modifier)
            }
        )
    }
}