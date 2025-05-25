package app.conjure.creatorv2.ui.components.browse

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.CardModel
import app.conjure.creatorv2.helpers.DisplayImage
import app.conjure.creatorv2.helpers.ImageDirectoryHelper.imageDirectory
import app.conjure.creatorv2.ui.components.general.TypePicker
import java.io.File


@Composable
fun MiniCard(
    card: CardModel,
    onClickCard:() -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.standard_jet)
        ),
        modifier = Modifier
            .padding(2.dp)
            .clickable(onClick = onClickCard)
    ) {
        MiniCardContent(
            card,
        )
    }
}

@Composable
private fun MiniCardContent(
    card: CardModel,
) {
    val file by remember { mutableStateOf(File(imageDirectory, "${card.id}.jpg")) }

    Row(
        modifier = Modifier
            .height(130.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(7f)
                .fillMaxHeight()
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = card.name,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.ExtraBold
                    ),
                    color = colorResource(R.color.standard_fawn),
                )
                Spacer(modifier = Modifier.weight(4f))
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Image(
                    painter = painterResource(id = R.drawable.icon_crystal),
                    contentDescription = "Placeholder",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = card.cost.toString(),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.width(45.dp),
                    color = colorResource(R.color.colorCrystal),
                )
                Image(
                    painter = painterResource(id = R.drawable.icon_hp),
                    contentDescription = "Placeholder",
                    modifier = Modifier.size(40.dp),
                    contentScale = ContentScale.Fit,
                )
                Text(
                    text = card.hp.toString(),
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.width(45.dp),
                    color = colorResource(R.color.colorHP),
                )
                Spacer(modifier = Modifier.weight(4f))
            }
        }
        Column(
            modifier = Modifier
                .weight(4f)
                .fillMaxHeight(),
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center
        ) {
            if (file.exists()) {
                DisplayImage(file.toString())
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Top
        ) {
            Row(
                modifier = Modifier
                    .weight(1f),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            ) {
                TypePicker(
                    onAmountChange = {  },
                    inputValue = card.type,
                    true
                )
            }
            Row( modifier = Modifier.weight(3f)) {}
        }
    }
}