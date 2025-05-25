package app.conjure.creatorv2.ui.components.browse

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.conjure.creatorv2.R
import app.conjure.creatorv2.ui.theme.ConjureTheme

// TODO: At some point cut out simple text and replace with a filter interface of some kind
@Composable
fun BrowseText(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(
            top = 20.dp,
            bottom = 20.dp
        ),
        verticalArrangement = Arrangement.spacedBy(20.dp)) {
        Text(
            text = stringResource(R.string.title_browse),
            fontWeight = FontWeight.Bold,
            fontSize = 28.sp,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ReportPreview() {
    ConjureTheme  {
        BrowseText()
    }
}