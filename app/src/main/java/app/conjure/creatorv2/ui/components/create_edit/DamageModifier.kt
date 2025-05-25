package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.DamageModifierModel
import app.conjure.creatorv2.data.exampleCards
import app.conjure.creatorv2.ui.theme.ConjureTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import app.conjure.creatorv2.ui.components.general.AmountPicker

@Composable
fun DamageModifier(
    damageModifier: DamageModifierModel,
    onModChange: () -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.standard_jet)
        ),
        modifier = Modifier.padding(2.dp)
    ) {
        DamageModifierContent(
            damageModifier,
            onModChange,
        )
    }
}

@Composable
private fun DamageModifierContent(
    damageModifier: DamageModifierModel,
    onModChange: () -> Unit,
) {
    val defenceIcons = listOf(
        R.drawable.defence_fire,
        R.drawable.defence_ice,
        R.drawable.defence_wind,
        R.drawable.defence_earth,
        R.drawable.defence_lightning,
        R.drawable.defence_water,
        R.drawable.defence_psychic,
        R.drawable.defence_nature,
        R.drawable.defence_light,
        R.drawable.defence_dark,
        R.drawable.defence,
        R.drawable.defence_physical,
        R.drawable.defence_ranged,
    )
    var expanded by remember { mutableStateOf(false) }
    var pickerIndex by remember { mutableIntStateOf(damageModifier.damageType) }

    Row(
        modifier = Modifier
            .height(130.dp)
            .widthIn(min = 200.dp)
            .padding(end = 4.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(Modifier.size(110.dp), contentAlignment = Alignment.Center) {
            IconButton(onClick = {
                expanded = true
            }, modifier = Modifier.fillMaxSize().background(Color.Transparent)) {
                Image(
                    painter = painterResource(defenceIcons[pickerIndex]),
                    contentDescription = "Current defence type icon",
                )
            }
            DropdownMenu(
                modifier = Modifier.background(colorResource(R.color.standard_jet))
                    .align(Alignment.Center),
                expanded = expanded,
                onDismissRequest = { expanded = false },
            ) {
                defenceIcons.forEachIndexed { index, icon ->
                    DropdownMenuItem(
                        text = {
                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = "Type icon",
                                modifier = Modifier.size(100.dp),
                            )
                        },
                        onClick = {
                            pickerIndex = index
                            damageModifier.damageType = index
                            onModChange()
                            expanded = false
                        }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.padding(start = 5.dp))
        AmountPicker(
            onAmountChange = { damageModifier.modifier = it },
            "twelveToMinusTwelve",
            colorResource(R.color.colorHP),
            damageModifier.modifier
        )
    }
}

@Preview
@Composable
fun DamageModifierPreview() {
    ConjureTheme {
        DamageModifier(
            damageModifier = exampleCards[1].damageModifiers[0],
            onModChange = {},
        )
    }
}


