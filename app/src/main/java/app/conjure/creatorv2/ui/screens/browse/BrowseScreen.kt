package app.conjure.creatorv2.ui.screens.browse

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import app.conjure.creatorv2.R
import app.conjure.creatorv2.data.CardModel
import app.conjure.creatorv2.ui.components.browse.MiniCardList
import app.conjure.creatorv2.ui.components.general.Centre

@Composable
fun BrowseScreen(modifier: Modifier = Modifier,
                 browseViewModel: BrowseViewModel = hiltViewModel(),
                 onClickCard: (String) -> Unit,
) {
    val cards = browseViewModel.uiCards.collectAsState().value
    var filterText by remember { mutableStateOf("") }
    val displayedCards = cards.filter { it.name.contains(filterText, ignoreCase = true) }

    var page by remember { mutableIntStateOf(1) }
    val maxCardsPerPage = 4
    val totalPages = (displayedCards.size + maxCardsPerPage - 1) / maxCardsPerPage

    // uses .chunked to split the displayedCards list into chunks of 4 cards each.
    // Refs:    1) https://stackoverflow.com/questions/70149719/how-to-split-list-in-chunk-of-list-item-in-kotlin
    //          2) ChatGPT (01/01/2025) helped fix bugs in my first implementation
    val currentPageOfDisplayedCards = displayedCards.chunked(maxCardsPerPage).getOrNull(page - 1) ?: emptyList()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().height(60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick =  { if (page > 1) page-- },
                modifier = Modifier.width(100.dp),
                enabled = (page > 1)
            ) {
                Image(
                    painter = painterResource(R.drawable.page_left_arrow),
                    contentDescription = "Change page to the left",
                )
            }

            Text(
                text = "Page $page of $totalPages",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = colorResource(R.color.standard_fawn)
            )

            IconButton(
                onClick =  { if (page < totalPages) page++ },
                modifier = Modifier.width(100.dp),
                enabled = (page < totalPages)
            ) {
                Image(
                    painter = painterResource(R.drawable.page_right_arrow),
                    contentDescription = "Change page to the right",
                )
            }
        }

        OutlinedTextField(
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = colorResource(R.color.standard_fawn),
                unfocusedBorderColor = colorResource(R.color.standard_fawn),
                focusedTextColor = colorResource(R.color.white),
                unfocusedTextColor = colorResource(R.color.standard_fawn),
            ),
            value = filterText,
            onValueChange = {
                filterText = it
                // Resets to page one if the inputted text changes
                page = 1
            },
            label = { Text(stringResource(id = R.string.filter_cards)) },
            keyboardOptions = KeyboardOptions.Default.copy(capitalization = KeyboardCapitalization.Sentences),
            modifier = Modifier
                .width(200.dp)
                .padding(bottom = 7.dp, start = 10.dp)
                .wrapContentHeight()
        )

        if (currentPageOfDisplayedCards.isEmpty()) {
            Centre(Modifier.fillMaxWidth()) {
                Text(
                    color = colorResource(R.color.white),
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp,
                    lineHeight = 34.sp,
                    textAlign = TextAlign.Center,
                    text = stringResource(R.string.empty_card_list)
                )
            }
        } else {
            MiniCardList(
                cards = currentPageOfDisplayedCards,
                onClickCard = onClickCard,
                onSwipeDelete = { card: CardModel
                    ->
                    browseViewModel.deleteCard(card)
                }
            )
        }
    }
}