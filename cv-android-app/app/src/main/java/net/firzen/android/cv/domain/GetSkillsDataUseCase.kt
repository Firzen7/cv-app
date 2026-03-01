package net.firzen.android.cv.domain

import net.firzen.android.cv.data.repository.CvRepository
import net.firzen.android.cv.domain.model.*
import javax.inject.Inject

/**
 * Use case that loads all skill-related data for the Skills screen.
 * Combines programming languages, technology categories, other skill categories,
 * and education into a single result.
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

    suspend operator fun invoke(): SkillsData {
        return SkillsData(
            programmingLanguages = repository.getAllProgrammingLanguages(),
            technologyCategories = repository.getAllTechnologyCategories(),
            otherSkillCategories = repository.getAllOtherSkillCategories(),
            education = repository.getAllEducation()
        )
    }
}
