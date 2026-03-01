package net.firzen.android.cv.presentation.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.firzen.android.cv.data.local.entities.LanguageEntity
import net.firzen.android.cv.presentation.models.ProfileViewModel

@Composable
fun ProfileScreen(viewModel: ProfileViewModel) {
    val state = viewModel.state.value
    val context = LocalContext.current

    if (state.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val profile = state.profile ?: return

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // -- Profile Header ---------------------------------------------------
        ProfileHeader(
            name = profile.name,
            title = profile.title,
            initials = profile.name
                .split(" ")
                .filter { it.first().isUpperCase() && it.first() != '.' && !it.endsWith(".") }
                .take(2)
                .joinToString("") { it.first().toString() }
        )

        // -- Contact Icons Row ------------------------------------------------
        ContactIconsRow(
            phone = profile.phone,
            email = profile.email,
            linkedInUsername = profile.linkedInUsername,
            githubUsernames = profile.githubUsernames,
            stackOverflowId = profile.stackOverflowId,
            context = context
        )

        Spacer(modifier = Modifier.height(16.dp))

        // -- About Card -------------------------------------------------------
        // TODO move hardcoded string to strings.xml
        SectionCard(title = "About") {
            InfoRow(
                icon = Icons.Outlined.CalendarToday,
                text = profile.dateOfBirth
            )
            Spacer(modifier = Modifier.height(8.dp))
            InfoRow(
                icon = Icons.Outlined.LocationOn,
                text = profile.address
            )
        }

        // -- Languages Card ---------------------------------------------------
        // TODO move hardcoded string to strings.xml
        SectionCard(title = "Languages") {
            state.languages.forEach { language ->
                LanguageRow(language)
                if (language != state.languages.last()) {
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }

        // -- Personality Card -------------------------------------------------
        // TODO move hardcoded string to strings.xml
        SectionCard(title = "Personality") {
            ChipsRow(items = state.personalityTraits.map { it.trait })
        }

        // -- Interests Card ---------------------------------------------------
        // TODO move hardcoded string to strings.xml
        SectionCard(title = "Interests") {
            ChipsRow(items = state.interests.map { it.name })
        }
    }
}

// -- Profile Header with avatar circle, name, and title -----------------------

@Composable
private fun ProfileHeader(name: String, title: String, initials: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Circular avatar with initials
        Box(
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = initials,
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// -- Contact Icons Row --------------------------------------------------------

@Composable
private fun ContactIconsRow(phone: String,
                            email: String,
                            linkedInUsername: String,
                            githubUsernames: String,
                            stackOverflowId: String,
                            context: Context) {

    Row(modifier = Modifier.padding(top = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)) {

        // Phone contact button
        ContactIconButton(
            icon = Icons.Default.Phone,
            // TODO move hardcoded string to strings.xml
            contentDescription = "Call",
            onClick = {
                context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$phone")))
            }
        )

        // Email contact button
        ContactIconButton(
            icon = Icons.Default.Email,
            // TODO move hardcoded string to strings.xml
            contentDescription = "Email",
            onClick = {
                context.startActivity(Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$email")))
            }
        )

        // Linkedin button
        ContactIconButton(
            icon = Icons.Default.Person,
            // TODO move hardcoded string to strings.xml
            contentDescription = "LinkedIn",
            onClick = {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://linkedin.com/in/$linkedInUsername"))
                )
            }
        )

        // Github accounts button
        GitHubDropdownButton(
            githubUsernames = githubUsernames,
            context = context
        )

        // Stackoverflow profile button
        ContactIconButton(
            icon = Icons.Default.QuestionAnswer,
            // TODO move hardcoded string to strings.xml
            contentDescription = "StackOverflow",
            onClick = {
                context.startActivity(
                    Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://stackoverflow.com/users/$stackOverflowId"))
                )
            }
        )
    }
}

@Composable
private fun ContactIconButton(icon: ImageVector,
                              contentDescription: String,
                              onClick: () -> Unit) {
    FilledIconButton(
        onClick = onClick,
        modifier = Modifier.size(48.dp),
        colors = IconButtonDefaults.filledIconButtonColors(containerColor =
            MaterialTheme.colorScheme.primary)) {

        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(24.dp)
        )
    }
}

// GitHub button with dropdown menu for selecting between Work and Personal profiles
@Composable
private fun GitHubDropdownButton(githubUsernames: String, context: Context) {
    var expanded by remember { mutableStateOf(false) }
    val usernames = githubUsernames.split(",").map { it.trim() }

    // Labels for each GitHub profile
    // TODO move hardcoded strings to strings.xml
    val labels = listOf("Work", "Personal")

    Box {
        ContactIconButton(
            icon = Icons.Default.Code,
            // TODO move hardcoded string to strings.xml
            contentDescription = "GitHub",
            onClick = { expanded = true }
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            usernames.forEachIndexed { index, username ->
                DropdownMenuItem(
                    text = {
                        // TODO move hardcoded string to strings.xml
                        Text("${labels.getOrElse(index) { "Profile" }} ($username)")
                    },
                    onClick = {
                        expanded = false
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW,
                                Uri.parse("https://github.com/$username"))
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Code,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                )
            }
        }
    }
}

// -- Reusable Section Card composable -----------------------------------------

@Composable
private fun SectionCard(title: String, content: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

// -- Info row with icon + text ------------------------------------------------

@Composable
private fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

// -- Language row with name, level, and optional note --------------------------

@Composable
private fun LanguageRow(language: LanguageEntity) {
    Column {
        Text(
            text = "${language.name} - ${language.level}",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        language.note?.let { note ->
            Text(
                text = note,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// -- Chip/tag flow row --------------------------------------------------------

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ChipsRow(items: List<String>) {
    FlowRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items.forEach { item ->
            SuggestionChip(
                onClick = { },
                label = {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                shape = RoundedCornerShape(8.dp)
            )
        }
    }
}
