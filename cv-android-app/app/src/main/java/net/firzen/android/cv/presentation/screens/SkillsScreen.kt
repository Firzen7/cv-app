package net.firzen.android.cv.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.firzen.android.cv.R
import net.firzen.android.cv.domain.model.*
import net.firzen.android.cv.presentation.models.SkillsScreenState
import net.firzen.android.cv.presentation.models.SkillsViewModel
import net.firzen.android.cv.ui.theme.CvAndroidAppTheme

// Entry point called from navigation -- reads ViewModel state and delegates to content
@Composable
fun SkillsScreen(viewModel: SkillsViewModel) {
    SkillsScreenContent(state = viewModel.state.value)
}

// Stateless content composable -- can be used in @Preview with sample data
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
        // -- Programming Languages section ------------------------------------
        SectionCard(title = stringResource(R.string.section_programming_languages)) {
            state.programmingLanguages.forEach { language ->
                LanguageLevelRow(name = language.name, level = language.level, maxLevel = 5)
                if (language != state.programmingLanguages.last()) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }

        // -- Android Technologies section -------------------------------------
        SectionCard(title = stringResource(R.string.section_android_technologies)) {
            state.technologyCategories.forEach { category ->
                CategoryChipsBlock(categoryName = category.name, items = category.technologies)
                if (category != state.technologyCategories.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        // -- Other Skills section ---------------------------------------------
        SectionCard(title = stringResource(R.string.section_other_skills)) {
            state.otherSkillCategories.forEach { category ->
                CategoryChipsBlock(categoryName = category.name, items = category.skills)
                if (category != state.otherSkillCategories.last()) {
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        // -- Education section ------------------------------------------------
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

// -- Programming language row with dot level indicators -----------------------

@Composable
private fun LanguageLevelRow(name: String, level: Int, maxLevel: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Language name
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

        // Level text (e.g. "5/5")
        Text(
            text = stringResource(R.string.skill_level_format, level, maxLevel),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// -- Category with chip tags --------------------------------------------------

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun CategoryChipsBlock(categoryName: String, items: List<String>) {
    Text(
        text = categoryName,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(6.dp))

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
                    TechnologyCategory(1, "UI", listOf("Jetpack Compose", "XML layouts", "Material Design")),
                    TechnologyCategory(2, "Networking", listOf("OkHttp", "Retrofit", "REST APIs")),
                    TechnologyCategory(3, "Data", listOf("Room DB", "SQLite", "Shared Preferences"))
                ),
                otherSkillCategories = listOf(
                    OtherSkillCategory(1, "Versioning", listOf("Git", "GitLab", "GitHub")),
                    OtherSkillCategory(2, "CI/CD", listOf("Jenkins", "Teamcity"))
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
