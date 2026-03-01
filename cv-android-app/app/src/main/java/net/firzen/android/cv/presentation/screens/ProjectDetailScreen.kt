package net.firzen.android.cv.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalUriHandler
import net.firzen.android.cv.R
import net.firzen.android.cv.domain.model.Project
import net.firzen.android.cv.domain.model.ProjectMilestone
import net.firzen.android.cv.presentation.models.ProjectDetailScreenState
import net.firzen.android.cv.presentation.models.ProjectDetailViewModel
import net.firzen.android.cv.ui.theme.CvAndroidAppTheme

// Entry point called from navigation - reads ViewModel state and delegates to content
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProjectDetailScreen(
    viewModel: ProjectDetailViewModel,
    onNavigateBack: () -> Unit
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(R.string.project_details_title))
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_navigate_back)
                        )
                    }
                },
                windowInsets = WindowInsets(0)
            )
        }
    ) { innerPadding ->
        ProjectDetailContent(
            state = state,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

// Stateless content composable - can be used in @Preview with sample data
@Composable
fun ProjectDetailContent(state: ProjectDetailScreenState, modifier: Modifier = Modifier) {
    if (state.isLoading) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val project = state.project ?: return

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(bottom = 24.dp)
    ) {
        // -- Project header card with icon and name ---------------------------
        ProjectHeaderCard(project)

        Spacer(modifier = Modifier.height(8.dp))

        // -- Milestones timeline ----------------------------------------------
        if (project.milestones.isNotEmpty()) {
            MilestonesSection(milestones = project.milestones)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // -- Store link button ------------------------------------------------
        if (project.googlePlayUrl != null) {
            StoreLinkButton(url = project.googlePlayUrl)
        }
    }
}

// -- Project header card (icon + name + description) --------------------------

@Composable
private fun ProjectHeaderCard(project: Project) {
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
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Project icon
                Image(
                    painter = painterResource(id = projectIconResId(project.id)),
                    contentDescription = stringResource(R.string.cd_project_icon),
                    modifier = Modifier
                        .size(56.dp)
                        .clip(RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.width(16.dp))

                // Project name
                Text(
                    text = project.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Full description
            Text(
                text = project.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// -- Milestones section -------------------------------------------------------

@Composable
private fun MilestonesSection(milestones: List<ProjectMilestone>) {
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
                text = stringResource(R.string.section_milestones),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(12.dp))

            milestones.forEachIndexed { index, milestone ->
                MilestoneTimelineItem(
                    milestone = milestone,
                    isLast = index == milestones.lastIndex
                )
            }
        }
    }
}

// -- Milestone timeline item with circle + vertical connector -----------------

@Composable
private fun MilestoneTimelineItem(milestone: ProjectMilestone, isLast: Boolean) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outlineVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
    ) {
        // Timeline indicator: circle + vertical line
        Canvas(
            modifier = Modifier
                .width(24.dp)
                .fillMaxHeight()
        ) {
            val centerX = size.width / 2
            val circleRadius = 6.dp.toPx()
            val strokeWidth = 2.dp.toPx()

            // Filled circle marker
            drawCircle(
                color = primaryColor,
                radius = circleRadius,
                center = Offset(centerX, circleRadius + 4.dp.toPx())
            )

            // Vertical connector line below the circle (unless last item)
            if (!isLast) {
                drawLine(
                    color = outlineColor,
                    start = Offset(centerX, circleRadius * 2 + 8.dp.toPx()),
                    end = Offset(centerX, size.height),
                    strokeWidth = strokeWidth
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Milestone content
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = if (isLast) 0.dp else 16.dp)
        ) {
            Text(
                text = "${milestone.year} — ${milestone.title}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = milestone.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// -- Store link button --------------------------------------------------------

@Composable
private fun StoreLinkButton(url: String) {
    val isAmazon = url.contains("amazon")
    val label = if (isAmazon) {
        stringResource(R.string.view_on_amazon)
    } else {
        stringResource(R.string.view_on_google_play)
    }
    val uriHandler = LocalUriHandler.current

    Button(
        onClick = { uriHandler.openUri(url) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(48.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}

// -- Icon mapping (reused from ProjectsScreen) --------------------------------

private fun projectIconResId(projectId: Int): Int = when (projectId) {
    1 -> R.drawable.ic_project_wattsup
    2 -> R.drawable.ic_project_sanctuary_first
    3 -> R.drawable.ic_project_crossreach
    4 -> R.drawable.ic_project_sanctus_tools
    5 -> R.drawable.ic_project_sf_alexa
    else -> R.drawable.ic_project_sanctus_tools
}

// -- Preview ------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProjectDetailScreenPreview() {
    CvAndroidAppTheme {
        ProjectDetailContent(
            state = ProjectDetailScreenState(
                project = Project(
                    id = 1, name = "WattsUp",
                    description = "Assistant for EV drivers which allows to find nearest " +
                            "rapid chargers, plan route, select charging stops along the " +
                            "chosen route, and also view the live status of charging stations.",
                    googlePlayUrl = "https://play.google.com/store/apps/details?id=com.sanctusmedia.android.WattsUp",
                    ordinal = 1,
                    milestones = listOf(
                        ProjectMilestone(1, 1, "2019", "Android version design",
                            "Design and development of Android version.", 1),
                        ProjectMilestone(2, 1, "2020", "Discovery Mode feature",
                            "Design and development of the first paid feature.", 2),
                        ProjectMilestone(3, 1, "2022", "Dynamic operator logos",
                            "Added dynamic logos for charger operators.", 3),
                        ProjectMilestone(4, 1, "2024", "Advanced filtering",
                            "Implementation of advanced filtering and price calculation.", 4),
                        ProjectMilestone(5, 1, "2025", "Major redesign",
                            "The beginning of a major redesign and transition to Mapbox 11.", 5)
                    )
                ),
                isLoading = false
            )
        )
    }
}
