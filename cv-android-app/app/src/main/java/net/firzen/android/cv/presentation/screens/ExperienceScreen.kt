package net.firzen.android.cv.presentation.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.firzen.android.cv.R
import net.firzen.android.cv.domain.model.WorkExperience
import net.firzen.android.cv.presentation.models.ExperienceScreenState
import net.firzen.android.cv.presentation.models.ExperienceViewModel
import net.firzen.android.cv.ui.theme.CvAndroidAppTheme

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect

// Entry point called from navigation -- reads ViewModel state and delegates to content
@Composable
fun ExperienceScreen(viewModel: ExperienceViewModel) {
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) { viewModel.refreshLocale() }
    ExperienceScreenContent(state = viewModel.state.collectAsState().value)
}

// Stateless content composable -- can be used in @Preview with sample data
@Composable
fun ExperienceScreenContent(state: ExperienceScreenState) {
    if (state.isLoading) {
        Box(Modifier.fillMaxSize(), contentAlignment = androidx.compose.ui.Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        state.experiences.forEachIndexed { index, experience ->
            val isLast = index == state.experiences.lastIndex
            TimelineItem(experience = experience, isLast = isLast)
        }
    }
}

// -- Timeline item with circle marker, vertical line, and experience card -----

@Composable
private fun TimelineItem(experience: WorkExperience, isLast: Boolean) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val outlineColor = MaterialTheme.colorScheme.outlineVariant

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min)
            .padding(start = 24.dp, end = 16.dp)
    ) {
        // Timeline indicator: circle + vertical line
        Canvas(
            modifier = Modifier
                .width(24.dp)
                .fillMaxHeight()
        ) {
            val centerX = size.width / 2
            val circleRadius = 8.dp.toPx()
            val strokeWidth = 2.dp.toPx()

            // Circle marker (outlined)
            drawCircle(
                color = primaryColor,
                radius = circleRadius,
                center = Offset(centerX, circleRadius + 4.dp.toPx()),
                style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
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

        // Experience card
        ExperienceCard(
            experience = experience,
            modifier = Modifier
                .weight(1f)
                .padding(bottom = 12.dp)
        )
    }
}

// -- Experience card with header and description ------------------------------

@Composable
private fun ExperienceCard(experience: WorkExperience, modifier: Modifier = Modifier) {
    val presentLabel = stringResource(R.string.experience_date_present)
    val dateRange = "${experience.startDate} – ${experience.endDate ?: presentLabel}"

    val darkBorder = if (isSystemInDarkTheme()) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = darkBorder
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Company - Position header
            Text(
                text = "${experience.company} – ${experience.position}",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Date range
            Text(
                text = dateRange,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Description
            Text(
                text = experience.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// -- Preview ------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ExperienceScreenPreview() {
    CvAndroidAppTheme {
        ExperienceScreenContent(
            state = ExperienceScreenState(
                experiences = listOf(
                    WorkExperience(
                        id = 1,
                        company = "Sanctus Media, Ltd.",
                        position = "Android Developer",
                        startDate = "2/2019",
                        endDate = null,
                        description = "Development of 6 Android applications, 2 Android libraries, " +
                                "2 REST API microservices, 1 Alexa skill, and 9 side projects.",
                        ordinal = 1
                    ),
                    WorkExperience(
                        id = 2,
                        company = "Redcroft Care Homes, Ltd.",
                        position = "Support Worker",
                        startDate = "3/2017",
                        endDate = "3/2018",
                        description = "Support and protection of adults suffering from " +
                                "Learning Disability, ASD and Prader-Willi Syndrome.",
                        ordinal = 2
                    ),
                    WorkExperience(
                        id = 3,
                        company = "FCR Tech, spol. s.r.o.",
                        position = "Java Developer",
                        startDate = "6/2016",
                        endDate = "1/2017",
                        description = "Development of an application for printing yellow pages. " +
                                "Leading team of four Turkish internship students.",
                        ordinal = 3
                    )
                ),
                isLoading = false
            )
        )
    }
}
