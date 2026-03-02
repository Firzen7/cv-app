package net.firzen.android.cv.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.*
import javax.inject.Inject

/**
 * Use case that loads all skill-related data for the Skills screen.
 * Combines programming languages, technology categories, other skill categories,
 * and education into a single reactive [Flow].
 */
class GetSkillsDataUseCase @Inject constructor(
    private val repository: CvRepository
) {

    data class SkillsData(
        val programmingLanguages: List<ProgrammingLanguage>,
        val technologyCategories: List<TechnologyCategory>,
        val otherSkillCategories: List<OtherSkillCategory>,
        val education: List<Education>
    )

    operator fun invoke(): Flow<SkillsData> {
        return combine(
            repository.getAllProgrammingLanguages(),
            repository.getAllTechnologyCategories(),
            repository.getAllOtherSkillCategories(),
            repository.getAllEducation()
        ) { progLangs, techCategories, otherSkills, education ->
            SkillsData(
                programmingLanguages = progLangs,
                technologyCategories = techCategories,
                otherSkillCategories = otherSkills,
                education = education
            )
        }
    }
}
