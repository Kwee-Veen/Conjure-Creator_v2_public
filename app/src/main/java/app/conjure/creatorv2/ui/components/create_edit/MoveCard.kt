package app.conjure.creatorv2.ui.components.create_edit

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.MoveModel
import app.conjure.creatorv2.helpers.GetRangedOrPhysicalIcon
import app.conjure.creatorv2.helpers.TargetTranslator
import app.conjure.creatorv2.helpers.getAreaOfEffectTypeIcon
import app.conjure.creatorv2.helpers.getDamageTypeIcon

@Composable
fun MoveCard(
    move: MoveModel,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.standard_jet)
        ),
        modifier = Modifier.padding(2.dp)
    ) {
        MoveCardContent(
            move,
        )
    }
}

@Composable
private fun MoveCardContent(
    move: MoveModel,
) {
    val damageTypeIcon = getDamageTypeIcon(move.damageType)
    val linearIcon = if (move.linear) { R.drawable.target_linear_white } else null
    val aoeTypeIcon = getAreaOfEffectTypeIcon(move.areaOfEffectType)

    val moveCostIcon = when (move.altCost) {
        0 -> R.drawable.icon_ap
        1 -> R.drawable.icon_mp
        2 -> R.drawable.icon_hp
        else -> R.drawable.icon_ap
    }

    val range =
        if (move.lineOfSight == null) {
            ""
        }
        else if (move.minRange != null) {
            "${move.minRange}-${move.range}"
        } else {
            "${move.range}"
        }


    var listMaxHeight = (57 * move.effects.size).dp
    for (effect in move.effects) {
        if (!effect.effectDescription.isNullOrEmpty()) {
            listMaxHeight += 51.dp
        }
    }

    Box(
        Modifier.clickable(
            onClick = {
                // TODO: Set EditMove (EditViewModel) here, or StopEditingMove
            }
        )
    ) {
        Column(
            modifier = Modifier
                .heightIn(max = 115.dp + listMaxHeight)
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(horizontal = 25.dp, vertical = 5.dp)
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                )
        )
        {
            if (!move.name.isEmpty() && move.apCost != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(51.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    Row(
                        modifier = Modifier.fillMaxHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        Text(
                            text = move.name,
                            style = MaterialTheme.typography.headlineSmall
                                .copy(
                                    fontWeight = FontWeight.Bold
                                ),
                            color = colorResource(R.color.standard_fawn),
                            modifier = Modifier.wrapContentWidth(),
                        )
                    }

                    Row(
                        modifier = Modifier
                            .height(42.dp)
                            .width(80.dp)
                            .padding(end = 5.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // don't show this box if targeting self, i.e. when target == 0
                        if (move.target != 0) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .border(
                                        1.dp,
                                        colorResource(R.color.highlight_rose),
                                        CutCornerShape(10.dp)
                                    ),
                                verticalAlignment = Alignment.CenterVertically
                            )
                            {
                                Text(
                                    text = TargetTranslator(move.target),
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth(),
                                    color = colorResource(R.color.highlight_rose),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.Medium
                                    ),
                                )
                            }
                        }
                    }
                    if (move.apCost != null) {
                        Row(
                            modifier = Modifier
                                .border(
                                    1.dp,
                                    colorResource(R.color.highlight_sky_blue),
                                    CutCornerShape(10.dp)
                                )
                                .wrapContentWidth()
                                .height(42.dp)
                                .padding(end = 7.dp),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            Image(
                                painter = painterResource(id = moveCostIcon),
                                contentDescription = "Move AP cost",
                                modifier = Modifier.size(40.dp),
                                contentScale = ContentScale.Fit,
                            )
                            Text(
                                text = move.apCost.toString(),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.width(15.dp),
                                color = colorResource(R.color.highlight_sky_blue),
                            )
                        }
                    }
                }
            }
            if (move.damage != null || move.lineOfSight != null || (move.areaOfEffectSize != null && move.areaOfEffectSize!! > 0)) {
                Row(
                    modifier = Modifier
                        .padding(start = 2.dp)
                        .fillMaxWidth()
                        .height(51.dp)
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (move.damage != null) {
                        Row(
                            modifier = Modifier
                                .border(1.dp, colorResource(R.color.colorHP), CutCornerShape(10.dp))
                                .height(42.dp)
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            if (move.physical != null) {
                                Image(
                                    painter = painterResource(id = GetRangedOrPhysicalIcon(move.physical!!)),
                                    contentDescription = "Move physical or ranged icon",
                                    modifier = Modifier.size(35.dp),
                                    contentScale = ContentScale.Fit,
                                )
                            }
                            Text(
                                text = move.damage.toString(),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier
                                    .widthIn(max = 40.dp)
                                    .wrapContentWidth(),
                                color = colorResource(R.color.colorHP),
                            )
                            if (damageTypeIcon != null) {
                                Image(
                                    painter = painterResource(id = damageTypeIcon),
                                    contentDescription = "Damage type",
                                    modifier = Modifier.size(40.dp),
                                    contentScale = ContentScale.Fit,
                                )
                            } else {
                                Spacer(Modifier.padding(end = 8.dp))
                            }
                        }
                    } else {
                        Row(
                            modifier = Modifier
                                .height(42.dp)
                                .wrapContentWidth()
                                .widthIn(min = 50.dp)
                                .wrapContentHeight(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {}
                    }
                    if (move.lineOfSight != null && range.isNotEmpty()) {
                        Row(
                            modifier = Modifier
                                .border(1.dp, Color.White, CutCornerShape(10.dp))
                                .height(42.dp)
                                .padding(end = 8.dp)
                                .wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            if (move.lineOfSight != false) {
                                Image(
                                    painter = painterResource(id = R.drawable.target_range_white),
                                    contentDescription = "Move Range",
                                    modifier = Modifier.size(40.dp),
                                    contentScale = ContentScale.Fit,
                                )
                            } else {
                                Spacer(Modifier.padding(start = 8.dp))
                            }
                            Text(
                                text = range,
                                textAlign = TextAlign.Start,
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                modifier = Modifier.wrapContentWidth(),
                                color = Color.White,
                            )
                            if (linearIcon != null) {
                                Image(
                                    painter = painterResource(id = linearIcon),
                                    contentDescription = "Linear icon",
                                    modifier = Modifier
                                        .width(22.dp)
                                        .height(40.dp),
                                    contentScale = ContentScale.Fit,
                                )
                            }
                        }
                    }
                    Row(
                        modifier = Modifier
                            .height(42.dp)
                            .wrapContentWidth()
                            .widthIn(min = 50.dp)
                            .wrapContentHeight(),
                        verticalAlignment = Alignment.CenterVertically
                    )
                    {
                        if (move.areaOfEffectSize != null && move.areaOfEffectSize!! > 0 && aoeTypeIcon != null) {
                            Row(
                                modifier = Modifier
                                    .border(
                                        1.dp,
                                        colorResource(R.color.colorMP),
                                        CutCornerShape(10.dp)
                                    )
                                    .height(42.dp)
                                    .padding(start = 3.dp, end = 12.dp)
                                    .wrapContentWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = aoeTypeIcon),
                                    contentDescription = "Move Range",
                                    modifier = Modifier.size(30.dp),
                                    contentScale = ContentScale.Fit,
                                )
                                Text(
                                    text = move.areaOfEffectSize.toString(),
                                    textAlign = TextAlign.Start,
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    modifier = Modifier.width(15.dp),
                                    color = colorResource(R.color.colorMP),
                                )
                            }
                        }
                    }
                }
            }
            if (move.effects.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .height(listMaxHeight)
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.Top,
                    horizontalArrangement = Arrangement.SpaceBetween
                )
                {
                    MoveEffectListStatic(
                        effects = move.effects,
                    )
                }
            }
        }
    }
}