package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.EffectModel
import app.conjure.creatorv2.data.MoveModel
import app.conjure.creatorv2.ui.components.general.AltCostPicker
import app.conjure.creatorv2.ui.components.general.AmountPicker
import app.conjure.creatorv2.ui.components.general.AreaOfEffectPicker
import app.conjure.creatorv2.ui.components.general.DamageTypePicker
import app.conjure.creatorv2.ui.components.general.LineOfSightPicker
import app.conjure.creatorv2.ui.components.general.LinearPicker
import app.conjure.creatorv2.ui.components.general.PhysicalOrRangedPicker
import app.conjure.creatorv2.ui.components.general.TargetPicker
import app.conjure.creatorv2.ui.screens.edit.ShowEditSupportText

@Composable
fun AddMoveSection(
    onAddMove: (MoveModel) -> Unit,
) {
    var name by remember { mutableStateOf("") }
    var apCost by remember { mutableIntStateOf(0) }
    var altCost by remember { mutableIntStateOf(0) }

    var damage by remember { mutableIntStateOf(0) }
    var damageType by remember { mutableIntStateOf(0) }
    var physical by remember { mutableStateOf(true) }
    var range by remember { mutableIntStateOf(0) }
    var minRange by remember { mutableIntStateOf(0) }
    var lineOfSight by remember { mutableStateOf<Boolean?>(true) }
    var linear by remember { mutableStateOf(false) }
    var areaOfEffectType by remember { mutableIntStateOf(0) }
    var areaOfEffectSize by remember { mutableIntStateOf(0) }
    var effects by remember { mutableStateOf<List<EffectModel>>(emptyList()) }
    var target by remember { mutableIntStateOf(1) }

    var showError by remember { mutableStateOf(false) }
    val colorStandardFawn = colorResource(id = R.color.standard_fawn)
    val colorHighlightPink = colorResource(id = R.color.highlight_pink)
    val colorHighlightRose = colorResource(id = R.color.highlight_rose)

    var listMaxHeight = 700.dp + (57 * effects.size).dp
    for (effect in effects) {
        if (!effect.effectDescription.isNullOrEmpty()) {
            listMaxHeight += 51.dp
        }
    }

    fun addEffect(effect: EffectModel) {
        effects = effects.toMutableList().apply { add(effect) }
    }

    fun deleteEffect(effect: EffectModel) {
        effects = effects.toMutableList().apply {
            val index = indexOf(effect)
            if (index != -1) {
                removeAt(index)
            }
        }
    }

    Column(
        modifier = Modifier
            .heightIn(max = listMaxHeight)
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(90.dp)
                .padding(bottom = 10.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Row(
                modifier = Modifier.weight(3f)
            )
            {
                OutlinedTextField(
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = colorHighlightRose,
                        unfocusedBorderColor = colorStandardFawn,
                        focusedTextColor = colorHighlightPink,
                        unfocusedTextColor = colorStandardFawn,
                    ),
                    value = name,
                    onValueChange = {
                        name = it
                        showError = false
                    },
                    isError = showError,
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .padding(end = 30.dp),
                    label = { Text(stringResource(id = R.string.text_moveNameHint)) },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    supportingText = { ShowEditSupportText(showError) },
                )
            }
            Row(
                modifier = Modifier.weight(1f)
            ) {
                TargetPicker(
                    onAmountChange = { target = it },
                    target,
                    colorResource(R.color.highlight_rose),
                    colorResource(R.color.highlight_rose),
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
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AltCostPicker(
                        onAmountChange = { altCost = it },
                        altCost
                    )
                    AmountPicker(
                        onAmountChange = { apCost = it },
                        "zeroToNine",
                        colorResource(R.color.highlight_sky_blue),
                        apCost
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        PhysicalOrRangedPicker(
                            onAmountChange = { physical = it },
                            physical
                        )
                        Spacer(modifier = Modifier.padding(start = 10.dp))
                        DamageTypePicker(
                            onAmountChange = { damageType = it },
                            damageType
                        )
                    }
                    AmountPicker(
                        onAmountChange = { damage = it },
                        "twentyFour",
                        colorResource(R.color.colorHP),
                        damage
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        LineOfSightPicker(
                            onAmountChange = { lineOfSight = it },
                            inputValue = lineOfSight
                        )
                        Spacer(modifier = Modifier.padding(start = 10.dp))
                        LinearPicker(
                            onAmountChange = { linear = it },
                            inputValue = linear
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        AmountPicker(
                            onAmountChange = { minRange = it },
                            "",
                            Color.White,
                            minRange
                        )
                        Text(
                            text = "-",
                            textAlign = TextAlign.Start,
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.wrapContentWidth(),
                            color = Color.White,
                        )
                        AmountPicker(
                            onAmountChange = { range = it },
                            "",
                            Color.White,
                            range
                        )
                    }
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    AreaOfEffectPicker(
                        onAmountChange = { areaOfEffectType = it },
                        areaOfEffectType
                    )
                    AmountPicker(
                        onAmountChange = { areaOfEffectSize = it },
                        "zeroToNine",
                        colorResource(R.color.colorMP),
                        areaOfEffectSize
                    )
                }
            }
        }
        if (effects.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .heightIn(max = listMaxHeight)
                    .wrapContentHeight(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.SpaceBetween
            )
            {
                MoveEffectList(
                    effects = effects,
                    onSwipeDeleteEffect = { effect: EffectModel ->
                        deleteEffect(effect)
                    }
                )
            }
        }
        Row(Modifier.padding(top = 25.dp))
        {
            Column(
                modifier = Modifier.wrapContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AddEffectSection(
                    onAddEffect = { effect: EffectModel ->  addEffect(effect) }
                )
                Row(modifier = Modifier
                    .heightIn(max = 100.dp)
                    .padding(vertical = 10.dp)
                    .wrapContentHeight(),
                )
                {
                    AddMoveButton(
                        onAddMove = {
                            onAddMove(
                                MoveModel(
                                    name = name,
                                    apCost = if (apCost > 0) apCost else null,
                                    altCost = if (altCost < 0 || altCost > 2) 0 else altCost,
                                    damage = if (damage > 0) damage else null,
                                    damageType =  if (damage > 0) damageType else null,
                                    physical = if (damage > 0) physical else null,
                                    lineOfSight = lineOfSight,
                                    range = if (lineOfSight != null) range else null,
                                    minRange = if (lineOfSight != null && minRange > 0) minRange else null,
                                    linear = if (lineOfSight != null) linear else false,
                                    areaOfEffectType = if (areaOfEffectSize > 0) areaOfEffectType else null,
                                    areaOfEffectSize = if (areaOfEffectSize > 0) areaOfEffectSize else null,
                                    effects = effects,
                                    target = target
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

