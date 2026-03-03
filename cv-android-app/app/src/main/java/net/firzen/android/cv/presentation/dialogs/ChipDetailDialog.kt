package net.firzen.android.cv.presentation.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import net.firzen.android.cv.R

/**
 * Reusable dialog that shows additional information about a chip item.
 * URLs in the description are automatically detected and rendered as
 * clickable links that open in the external browser.
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
    val uriHandler = LocalUriHandler.current
    val linkColor = MaterialTheme.colorScheme.primary
    val textColor = MaterialTheme.colorScheme.onSurfaceVariant
    val textStyle = MaterialTheme.typography.bodyMedium.copy(color = textColor)

    // Build annotated string with clickable URL spans
    val annotated = buildAnnotatedString {
        val urlRegex = Regex("""https?://\S+""")
        var lastIndex = 0

        urlRegex.findAll(description).forEach { match ->
            // Append text before the URL
            append(description.substring(lastIndex, match.range.first))

            // Append the URL with annotation and styling
            val url = match.value
            pushStringAnnotation(tag = "URL", annotation = url)
            pushStyle(SpanStyle(color = linkColor, textDecoration = TextDecoration.Underline))
            append(url)
            pop() // pop style
            pop() // pop annotation
            lastIndex = match.range.last + 1
        }

        // Append remaining text after the last URL
        if (lastIndex < description.length) {
            append(description.substring(lastIndex))
        }
    }

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
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                ClickableText(
                    text = annotated,
                    style = textStyle,
                    onClick = { offset ->
                        annotated.getStringAnnotations(tag = "URL", start = offset, end = offset)
                            .firstOrNull()?.let { annotation ->
                                uriHandler.openUri(annotation.item)
                            }
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.chip_dialog_close))
            }
        },
        shape = RoundedCornerShape(16.dp)
    )
}
