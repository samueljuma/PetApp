package com.samueljuma.petapp.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.samueljuma.petapp.data.Cat
import com.samueljuma.petapp.viewmodel.PetsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PetList(
    onPetClicked: (Cat) -> Unit,
    pets: List<Cat>,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier,
    ) {
        items(items = pets){ pet->
            PetListItem(
                cat = pet,
                onPetClicked = onPetClicked
            )
        }
    }





}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetListItem(
    cat: Cat,
    onPetClicked: (Cat) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
                .clickable {
                    onPetClicked(cat)
                }
        ) {
            AsyncImage(
                model = "https://cataas.com/cat/${cat.id}",
                contentDescription = "Cute Cat",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                contentScale = ContentScale.FillWidth,
            )
            FlowRow(
                modifier = Modifier
                    .padding(start = 6.dp, end = 6.dp)
            ) {
                repeat(cat.tags.size) {
                    SuggestionChip(
                        modifier = Modifier
                            .padding(start = 3.dp, end = 3.dp),
                        onClick = { /*TODO*/ },
                        label = { Text(text = cat.tags[it]) }
                    )
                }
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun PetListItemPreview() {
    val tags = listOf("cute", "black", "fluffy", "cool")
    val cat = Cat(
        "1",
        "_1",
        "sam",
        tags = tags,
        "1"

    )
    PetListItem(cat = cat, onPetClicked = {})
}