package net.firzen.android.cv.presentation.dialogs

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import net.firzen.android.cv.R

/**
 * Reusable dialog that shows additional information about a chip item.
 *
 * @param title   the display name of the chip (used as dialog title)
 * @param description  the detail text to show in the dialog body
 * @param onDismiss    callback when the dialog is dismissed
 */
@Composable
fun ChipDetailDialog(
    title: String,
    description: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.chip_dialog_close))
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
