package net.firzen.android.cv.presentation.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.firzen.android.cv.R
import net.firzen.android.cv.domain.model.*
import net.firzen.android.cv.presentation.models.SkillsScreenState
import net.firzen.android.cv.presentation.models.SkillsViewModel
import net.firzen.android.cv.ui.theme.CvAndroidAppTheme

// Entry point called from navigation - reads ViewModel state and delegates to content
@Composable
fun SkillsScreen(viewModel: SkillsViewModel) {
    SkillsScreenContent(state = viewModel.state.collectAsState().value)
}

// Stateless content composable - can be used in @Preview with sample data
@Composable
fun SkillsScreenContent(state: SkillsScreenState) {
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
            .padding(bottom = 16.dp)
    ) {
        ProgrammingLanguagesSection(state)
        EducationSection(state)
        AndroidTechnologiesSection(state)
        OtherTechnologiesSection(state)
    }
}

@Composable
private fun EducationSection(state: SkillsScreenState) {
    SectionCard(title = stringResource(R.string.section_education)) {
        state.education.forEach { edu ->
            EducationRow(education = edu)
            if (edu != state.education.last()) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = MaterialTheme.colorScheme.outlineVariant
                )
            }
        }
    }
}

@Composable
private fun ProgrammingLanguagesSection(state: SkillsScreenState) {
    SectionCard(title = stringResource(R.string.section_programming_languages)) {
        state.programmingLanguages.forEach { language ->
            LanguageLevelRow(name = language.name, level = language.level, maxLevel = 5)
            if (language != state.programmingLanguages.last()) {
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
private fun AndroidTechnologiesSection(state: SkillsScreenState) {
    SectionCard(title = stringResource(R.string.section_android_technologies)) {
        state.technologyCategories.forEachIndexed { index, category ->
            CollapsibleCategoryRow(
                categoryName = category.name,
                items = category.technologies
            )
            if (index < state.technologyCategories.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                )
            }
        }
    }
}

@Composable
private fun OtherTechnologiesSection(state: SkillsScreenState) {
    SectionCard(title = stringResource(R.string.section_other_skills)) {
        state.otherSkillCategories.forEachIndexed { index, category ->
            CollapsibleCategoryRow(
                categoryName = category.name,
                items = category.skills
            )
            if (index < state.otherSkillCategories.lastIndex) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 4.dp),
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
                )
            }
        }
    }
}

// -- Section Card (reusable wrapper) ------------------------------------------

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

// -- Collapsible category row with preview chips and expand/collapse ----------

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CollapsibleCategoryRow(categoryName: String, items: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    // Whether the chips overflow the available width (null = not yet measured)
    var overflows by remember { mutableStateOf<Boolean?>(null) }
    val chevronRotation by animateFloatAsState(
        targetValue = if (expanded) 180f else 0f,
        label = "chevron"
    )
    val isCollapsible = overflows ?: false

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .then(
                if (isCollapsible) Modifier.clickable { expanded = !expanded }
                else Modifier
            )
            .padding(vertical = 4.dp)
    ) {
        // Header row: category name + chevron (chevron only if collapsible)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = categoryName,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.weight(1f)
            )
            if (isCollapsible) {
                Icon(
                    imageVector = Icons.Default.ExpandMore,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier
                        .size(20.dp)
                        .rotate(chevronRotation)
                )
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        if (expanded) {
            // Expanded: show all chips in a wrapped flow layout
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items.forEach { item ->
                    SuggestionChip(
                        onClick = { },
                        label = {
                            Text(text = item, style = MaterialTheme.typography.bodySmall)
                        },
                        shape = RoundedCornerShape(8.dp)
                    )
                }
            }
        } else {
            // Collapsed: measure overflow, then show gradient only if needed
            val surfaceColor = MaterialTheme.colorScheme.surface
            val scrollState = rememberScrollState()

            // Detect overflow after composition
            LaunchedEffect(scrollState.maxValue) {
                overflows = scrollState.maxValue > 0
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .horizontalScroll(scrollState),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items.forEach { item ->
                        SuggestionChip(
                            onClick = { if (isCollapsible) expanded = true },
                            label = {
                                Text(text = item, style = MaterialTheme.typography.bodySmall)
                            },
                            shape = RoundedCornerShape(8.dp)
                        )
                    }
                }

                // Gradient overlay only when items overflow
                if (isCollapsible) {
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colorStops = arrayOf(
                                        0.85f to Color.Transparent,
                                        1f to surfaceColor
                                    )
                                )
                            )
                    )
                }
            }
        }
    }
}

// -- Programming language row with dot level indicators -----------------------

@Composable
private fun LanguageLevelRow(name: String, level: Int, maxLevel: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = name,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f)
        )

        // Dot indicators (filled for acquired, outlined for remaining)
        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            for (i in 1..maxLevel) {
                Box(
                    modifier = Modifier
                        .size(12.dp)
                        .clip(CircleShape)
                        .background(
                            if (i <= level) MaterialTheme.colorScheme.primary
                            else MaterialTheme.colorScheme.outlineVariant
                        )
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = stringResource(R.string.skill_level_format, level, maxLevel),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// -- Education row ------------------------------------------------------------

@Composable
private fun EducationRow(education: Education) {
    Column {
        Text(
            text = education.institution,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = education.degree,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "${education.startYear} – ${education.endYear}",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// -- Preview ------------------------------------------------------------------

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SkillsScreenPreview() {
    CvAndroidAppTheme {
        SkillsScreenContent(
            state = SkillsScreenState(
                programmingLanguages = listOf(
                    ProgrammingLanguage(1, "Kotlin", 5),
                    ProgrammingLanguage(2, "Java", 5),
                    ProgrammingLanguage(3, "Bash", 4),
                    ProgrammingLanguage(4, "C/C++", 3),
                    ProgrammingLanguage(5, "Swift", 2)
                ),
                technologyCategories = listOf(
                    TechnologyCategory(1, "Basics", listOf("Android SDK", "Activity", "Services", "Fragments", "Composable functions", "Lifecycle")),
                    TechnologyCategory(2, "UI", listOf("Jetpack Compose", "XML layouts", "Custom Views (Canvas)", "Material Design")),
                    TechnologyCategory(3, "Networking", listOf("OkHttp", "Retrofit", "REST APIs", "JSON", "GSON", "Base64")),
                    TechnologyCategory(4, "Data", listOf("Room DB", "SQLite", "Shared Preferences"))
                ),
                otherSkillCategories = listOf(
                    OtherSkillCategory(1, "Versioning", listOf("Git", "GitLab", "GitHub")),
                    OtherSkillCategory(2, "CI/CD", listOf("Jenkins", "Teamcity")),
                    OtherSkillCategory(3, "Backend", listOf("Ktor", "MariaDB", "MySQL", "PostgreSQL", "Elasticsearch"))
                ),
                education = listOf(
                    Education(1, "Palacký University Olomouc", "BSc Applied Computer Science", 2011, 2015),
                    Education(2, "Gymnázium Havlíčkův Brod", "Maturita", 2003, 2011)
                ),
                isLoading = false
            )
        )
    }
}
