package net.firzen.android.cv.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.firzen.android.cv.R
import net.firzen.android.cv.domain.model.Project
import net.firzen.android.cv.other.projectIconResId
import net.firzen.android.cv.presentation.models.ProjectsScreenState
import net.firzen.android.cv.presentation.models.ProjectsViewModel
import net.firzen.android.cv.ui.theme.CvAndroidAppTheme

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

// Entry point called from navigation - reads ViewModel state and delegates to content
@Composable
fun ProjectsScreen(viewModel: ProjectsViewModel, onProjectClick: (Int) -> Unit) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { viewModel.refreshLocale() }
    ProjectsScreenContent(state = viewModel.state.collectAsState().value, onProjectClick = onProjectClick)
}

// Stateless content composable - can be used in @Preview with sample data
@Composable
fun ProjectsScreenContent(state: ProjectsScreenState, onProjectClick: (Int) -> Unit = {}) {
    if (state.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 16.dp)
    ) {
        state.projects.forEach { project ->
            ProjectCard(
                project = project,
                iconResId = projectIconResId(project.name),
                onClick = { onProjectClick(project.id) }
            )
        }
    }
}

// -- Project Card -------------------------------------------------------------

@Composable
private fun ProjectCard(project: Project, iconResId: Int, onClick: () -> Unit) {
    val darkBorder = if (isSystemInDarkTheme()) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = darkBorder
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Project icon
            Image(
                painter = painterResource(id = iconResId),
                contentDescription = stringResource(R.string.cd_project_icon),
                modifier = Modifier
                    .size(56.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Project name and description
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = project.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Forward chevron
            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = stringResource(R.string.cd_show_details),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

// -- Icon mapping: project name -> drawable resource --------------------------



// -- Preview ------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProjectsScreenPreview() {
    CvAndroidAppTheme {
        ProjectsScreenContent(
            state = ProjectsScreenState(
                projects = listOf(
                    Project(
                        id = 1, name = "WattsUp",
                        description = "Assistant for EV drivers which allows to find nearest " +
                                "rapid chargers, plan route, select charging stops.",
                        googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.WattsUp",
                        ordinal = 1
                    ),
                    Project(
                        id = 2, name = "Sanctuary First",
                        description = "Christian spiritual community with modern approach. " +
                                "Videos, podcasts, likes, comments, live streams.",
                        googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.sanctuaryfirst",
                        ordinal = 2
                    ),
                    Project(
                        id = 3, name = "CrossReach",
                        description = "Digital version of annual publication with added features.",
                        googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.crossreach",
                        ordinal = 3
                    ),
                    Project(
                        id = 4, name = "Sanctus Tools",
                        description = "AAR library with Kotlin extensions and utility functions " +
                                "for Android development.",
                        googlePlayUrl = null,
                        ordinal = 4
                    ),
                    Project(
                        id = 5, name = "Sanctuary First for Amazon Alexa",
                        description = "Alexa Skill that allows user to listen to daily prayers " +
                                "or readings from Bible.",
                        googlePlayUrl = "https://www.amazon.co.uk/Santus-Media-Ltd-Sanctuary-First/dp/B0811W4GFK",
                        ordinal = 5
                    )
                ),
                isLoading = false
            )
        )
    }
}
