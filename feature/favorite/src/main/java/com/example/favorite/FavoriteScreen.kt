package com.example.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.mappers.reverseVacancyMapper
import com.example.data.mappers.vacancyMapper
import com.example.database.entities.Address
import com.example.database.entities.Experience
import com.example.database.entities.Favorite
import com.example.database.entities.Salary
import com.example.database.entities.Vacancy
import com.example.designsystem.MyColors
import com.example.designsystem.MyTypes
import com.example.designsystem.VacancyCard
import com.example.network.model.VacancyFromNetwork

@Composable
fun FavoriteRoute(
    onVacancyClick: (String) -> Unit,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    val allFavorite by viewModel.allFavoriteUiState.collectAsState()
    if (allFavorite is FavoriteUiState.Success) {
        FavoriteScreen(
            allFavorite = (allFavorite as FavoriteUiState.Success).allFavorite,
            onVacancyClick = onVacancyClick,
            insertVacancy = viewModel::insertVacancy,
            insertFavorite = viewModel::insertFavorite,
            deleteFavorite = viewModel::deleteFavorite,
        )
    }
}

@Composable
fun FavoriteScreen(
    allFavorite: List<Vacancy>,
    onVacancyClick: (String) -> Unit,
    insertVacancy: (Vacancy) -> Unit,
    insertFavorite: (Vacancy) -> Unit,
    deleteFavorite: (Vacancy) -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MyColors.black)
        .padding(12.dp))
    {
        Text(text = "Избранное",
            color = MyColors.white,
            style = MyTypes.title1,
            modifier = Modifier.padding(top = 40.dp))
        Text(text = "${allFavorite.size} вакансии",
            color = MyColors.grey3,
            style = MyTypes.title4,
            modifier = Modifier.padding(top = 20.dp, bottom = 10.dp))
        LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
            items(allFavorite) { item ->
                val vacancy = reverseVacancyMapper(item)
                VacancyCard(
                    vacancy = vacancy,
                    onVacancyClick = { onVacancyClick(vacancy.id!!) },
                    insertVacancy = { insertVacancy(vacancyMapper(vacancy))},
                    insertFavorite = { insertFavorite(vacancyMapper(vacancy)) },
                    deleteFavorite = { deleteFavorite(vacancyMapper(vacancy)) },
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewSearchScreen() {
    val vacancies = listOf(
        Vacancy(
            id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
            lookingNumber = 2,
            title = "UI/UX дизайнер",
            address = Address(town = "Минск", street = "улица Бирюзова", house = "4/5"),
            company = "Мобирикс",
            experience = Experience(previewText = "Опыт от 1 до 3 лет", text = "1–3 года"),
            publishedDate = "2024-02-20",
            isFavorite = false,
            salary = Salary(full = "Уровень дохода не указан"),
            schedules = listOf("полная занятость", "полный день"),
            appliedNumber = 147,
            description = "Мы ищем специалиста на позицию UX/UI Designer, который вместе с коллегами будет заниматься проектированием пользовательских интерфейсов внутренних и внешних продуктов компании.",
            responsibilities = "- проектирование пользовательских сценариев и создание прототипов;\n- разработка интерфейсов для продуктов компании (Web+App);\n- работа над созданием и улучшением Дизайн-системы;\n- взаимодействие с командами frontend-разработки;\n- контроль качества внедрения дизайна;\n- ситуативно: создание презентаций и других материалов на основе фирменного стиля компании",
            questions = listOf(
                "Где располагается место работы?",
                "Какой график работы?",
                "Вакансия открыта?",
                "Какая оплата труда?"
            )
        ),
        Vacancy(
            id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
            lookingNumber = 2,
            title = "UI/UX дизайнер",
            address = Address(town = "Минск", street = "улица Бирюзова", house = "4/5"),
            company = "Мобирикс",
            experience = Experience(previewText = "Опыт от 1 до 3 лет", text = "1–3 года"),
            publishedDate = "2024-02-20",
            isFavorite = false,
            salary = Salary(full = "Уровень дохода не указан"),
            schedules = listOf("полная занятость", "полный день"),
            appliedNumber = 147,
            description = "Мы ищем специалиста на позицию UX/UI Designer, который вместе с коллегами будет заниматься проектированием пользовательских интерфейсов внутренних и внешних продуктов компании.",
            responsibilities = "- проектирование пользовательских сценариев и создание прототипов;\n- разработка интерфейсов для продуктов компании (Web+App);\n- работа над созданием и улучшением Дизайн-системы;\n- взаимодействие с командами frontend-разработки;\n- контроль качества внедрения дизайна;\n- ситуативно: создание презентаций и других материалов на основе фирменного стиля компании",
            questions = listOf(
                "Где располагается место работы?",
                "Какой график работы?",
                "Вакансия открыта?",
                "Какая оплата труда?"
            )
        ),
        Vacancy(
            id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
            lookingNumber = 2,
            title = "UI/UX дизайнер",
            address = Address(town = "Минск", street = "улица Бирюзова", house = "4/5"),
            company = "Мобирикс",
            experience = Experience(previewText = "Опыт от 1 до 3 лет", text = "1–3 года"),
            publishedDate = "2024-02-20",
            isFavorite = false,
            salary = Salary(full = "Уровень дохода не указан"),
            schedules = listOf("полная занятость", "полный день"),
            appliedNumber = 147,
            description = "Мы ищем специалиста на позицию UX/UI Designer, который вместе с коллегами будет заниматься проектированием пользовательских интерфейсов внутренних и внешних продуктов компании.",
            responsibilities = "- проектирование пользовательских сценариев и создание прототипов;\n- разработка интерфейсов для продуктов компании (Web+App);\n- работа над созданием и улучшением Дизайн-системы;\n- взаимодействие с командами frontend-разработки;\n- контроль качества внедрения дизайна;\n- ситуативно: создание презентаций и других материалов на основе фирменного стиля компании",
            questions = listOf(
                "Где располагается место работы?",
                "Какой график работы?",
                "Вакансия открыта?",
                "Какая оплата труда?"
            )
        )
    )
    FavoriteScreen(
        allFavorite = vacancies,
        onVacancyClick = {},
        insertVacancy = {},
        insertFavorite = {},
        deleteFavorite = {},
    )
}