package com.samueljuma.petapp

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.samueljuma.petapp.data.Cat
import com.samueljuma.petapp.views.PetListItem
import org.junit.Rule
import org.junit.Test

class PetListItemTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testPestListItem() {
        with(composeTestRule) {
            setContent {
                PetListItem(
                    cat =
                        Cat(
                            id = "1",
                            owner = "Test Owner",
                            tags = listOf("cute", "fluffy"),
                            createdAt = "2023-08-01T00:00:00Z",
                            updatedAt = "2023-08-02T00:00:00Z",
                            isFavorite = false,
                        ),
                    onPetClicked = {},
                    onFavoriteClicked = {},
                )
            }

            // Assertions using tags
            onNodeWithTag("PetListItemCard").assertExists()
            onNodeWithTag("PetListItemColumn").assertExists()
            onNodeWithTag("PetListItemFavoriteIcon").assertExists()

            // Assertions using text
            onNodeWithText("fluffy").assertIsDisplayed()
            onNodeWithContentDescription("Cute Cat").assertIsDisplayed()
            onNodeWithContentDescription("Favorite").assertIsDisplayed()

            // Actions
            onNodeWithTag("PetListItemFavoriteIcon").performClick()
        }
    }
}