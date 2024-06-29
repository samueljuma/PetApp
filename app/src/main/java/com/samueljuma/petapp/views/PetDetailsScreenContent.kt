package com.samueljuma.petapp.views

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.samueljuma.petapp.data.Cat

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetDetailsScreenContent(
    modifier: Modifier,
    cat: Cat,
) {
    var imageClicked by rememberSaveable { mutableStateOf(false) }

    // Animate the height based on imageClicked
    val height by animateDpAsState(
        targetValue = if (imageClicked) 500.dp else 250.dp,
        animationSpec =
            spring(
                // Adjust bounciness
                dampingRatio = Spring.DampingRatioMediumBouncy,
                // Adjust speed
                stiffness = Spring.StiffnessLow,
            ),
    )
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        // Add vertical scrolling
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        ElevatedCard(
            onClick = {
                imageClicked = !imageClicked
            },
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.elevatedCardElevation(10.dp),
            content = {
                AsyncImage(
                    model = "https://cataas.com/cat/${cat.id}",
                    contentDescription = "Cute Cat",
                    modifier =
                        Modifier
                            .fillMaxWidth()
                            .height(height),
                    contentScale = ContentScale.Crop,
                )
            },
        )

        FlowRow(
            modifier =
                Modifier
                    .padding(top = 10.dp, start = 6.dp, end = 6.dp),
        ) {
            repeat(cat.tags.size) {
                ElevatedSuggestionChip(
                    modifier =
                        Modifier
                            .padding(start = 3.dp, end = 3.dp),
                    onClick = { /*TODO*/ },
                    label = { Text(text = cat.tags[it]) },
                    colors =
                        SuggestionChipDefaults.suggestionChipColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            labelColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        ),
                )
            }
        }
    }
}