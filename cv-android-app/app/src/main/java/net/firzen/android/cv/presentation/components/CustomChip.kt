package net.firzen.android.cv.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun CustomChip(name: String, hasDescription: Boolean, isDark: Boolean, onClick: () -> Unit) {
    val darkChipBorder = if (isDark) {
        BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    } else {
        BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    }

    SuggestionChip(
        onClick = {
            onClick()
        },
        label = {
            Text(
                text = name,
                style = MaterialTheme.typography.bodySmall
            )
        },
        shape = RoundedCornerShape(8.dp),
        colors = if (hasDescription) {
            SuggestionChipDefaults.suggestionChipColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                labelColor = if (isDark) {
                    MaterialTheme.colorScheme.onSurface
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                }
            )
        } else {
            SuggestionChipDefaults.suggestionChipColors()
        },
        border = darkChipBorder
    )
}