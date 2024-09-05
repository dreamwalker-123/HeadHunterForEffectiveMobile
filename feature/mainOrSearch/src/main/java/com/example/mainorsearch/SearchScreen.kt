package com.example.mainorsearch

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.data.mappers.correctEndingOfANoun
import com.example.data.mappers.vacancyMapper
import com.example.database.entities.Favorite
import com.example.database.entities.Vacancy
import com.example.designsystem.MyColors
import com.example.designsystem.MyTypes
import com.example.designsystem.VacancyCard
import com.example.network.model.AddressFromNetwork
import com.example.network.model.Button
import com.example.network.model.ExperienceFromNetwork
import com.example.network.model.NetworkData
import com.example.network.model.OfferFromNetwork
import com.example.network.model.SalaryFromNetwork
import com.example.network.model.VacancyFromNetwork

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchRoute(
    onVacancyClick: (String) -> Unit,
    viewModel: SearchViewModel = hiltViewModel(),
) {
    val dataUiState by viewModel.dataUiState.collectAsState()
    SearchScreen(
        dataUiState = dataUiState,
        onVacancyClick = onVacancyClick,
        insertVacancy = { viewModel.insertVacancy(it) },
        insertFavorite = { viewModel.insertFavorite(it) },
        deleteFavorite = { viewModel.deleteFavorite(it) },
    )
}// bator@gmail.com


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchScreen(
    dataUiState: DataUiState,
    onVacancyClick: (String) -> Unit,
    insertVacancy: (Vacancy) -> Unit,
    insertFavorite: (Vacancy) -> Unit,
    deleteFavorite: (Vacancy) -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MyColors.black)) {
        when (dataUiState) {
            is DataUiState.Success -> {
                var blueButton by rememberSaveable { mutableStateOf(false) }

                Column(modifier = Modifier) {
                    // Поле поиска и фильтр
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)) {
                        Card(modifier = Modifier
                            .height(45.dp)
                            .weight(1f),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors().copy(containerColor = MyColors.grey2)
                        ) {
                            Row(modifier = Modifier
                                .fillMaxSize()
                                .padding(start = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { if (!blueButton) blueButton = true }) {
                                    Icon(
                                        painter = painterResource(id = if (blueButton) com.example.designsystem.R.drawable.arrow_left else com.example.designsystem.R.drawable.search_black),
                                        contentDescription = "Email",
                                        tint = MyColors.grey3,
                                        modifier = Modifier.padding(5.dp)
                                    )
                                }
                                Text(text = "Должность, ключевые слова", color = MyColors.grey3, style = MyTypes.text1)
                            }
                        }

                        // фильтр
                        Card(modifier = Modifier
                            .padding(start = 8.dp)
                            .width(45.dp)
                            .height(45.dp),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors().copy(containerColor = MyColors.grey2)
                        ) {
                            Icon(
                                painter = painterResource(id = com.example.designsystem.R.drawable.filter_default2),
                                contentDescription = "Email",
                                modifier = Modifier
                                    .align(Alignment.CenterHorizontally)
                                    .padding(top = 15.dp),
                                tint = MyColors.grey3
                            )
                        }
                    }

                    // Блок рекомендаций
                    if (!blueButton) {
                        LazyRow(modifier = Modifier.padding(start = 4.dp)) {
                            val offers = dataUiState.data.offers
                            items(offers) { offer ->
                                // FIXME: при клике переход по ссылке из поля link
                                Card(modifier = Modifier
                                    .padding(start = 8.dp)
                                    .width(150.dp)
                                    .height(110.dp),
                                    shape = RoundedCornerShape(8.dp),
                                    colors = CardDefaults.cardColors().copy(containerColor = MyColors.grey2)
                                ) {
                                    Column(modifier = Modifier
                                        .padding(8.dp)) {
                                        when (offer.id) {
                                            "near_vacancies" -> {
                                                Card(modifier = Modifier,
                                                    shape = RoundedCornerShape(80.dp),
                                                    colors = CardDefaults.cardColors().copy(containerColor = MyColors.dark_blue)) {
                                                    Icon(painter = painterResource(com.example.designsystem.R.drawable.near_vacancy),
                                                        contentDescription = "Icon",
                                                        tint = MyColors.blue,
                                                        modifier = Modifier.padding(5.dp)
                                                    )
                                                }
                                            }
                                            "level_up_resume" -> {
                                                Card(modifier = Modifier,
                                                    shape = RoundedCornerShape(80.dp),
                                                    colors = CardDefaults.cardColors().copy(containerColor = MyColors.dark_green)) {
                                                    Icon(painter = painterResource(com.example.designsystem.R.drawable.small_star),
                                                        contentDescription = "Icon",
                                                        tint = MyColors.green,
                                                        modifier = Modifier.padding(7.dp)
                                                    )
                                                }
                                            }
                                            else -> {
                                                Card(modifier = Modifier,
                                                    shape = RoundedCornerShape(80.dp),
                                                    colors = CardDefaults.cardColors().copy(containerColor = MyColors.dark_green)) {
                                                    Icon(painter = painterResource(com.example.designsystem.R.drawable.vacancy),
                                                        contentDescription = "Icon",
                                                        tint = MyColors.green,
                                                        modifier = Modifier.padding(7.dp)
                                                    )
                                                }
                                            }
                                        }
                                        Text(text = offer.title!!,
                                            color = MyColors.white,
                                            style = MyTypes.title4,
                                            modifier = Modifier.padding(top = 10.dp),
                                            maxLines = if (offer.button == null) 3 else 2
                                        )
                                        if (offer.button != null) {
                                            Text(text = offer.button!!.text!!, color = MyColors.green, style = MyTypes.title4)
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        val size = dataUiState.data.vacancies.size
                        val str = correctEndingOfANoun(size)
                        Row(modifier = Modifier.padding(start = 12.dp, end = 12.dp)) {
                            Text(text = "$size $str", color = MyColors.white, style = MyTypes.title4)
                            Spacer(modifier = Modifier.weight(1f))
                            Row {
                                Text(text = "По соответствию", color = MyColors.blue, style = MyTypes.title4)
                                Column(modifier = Modifier.padding(start = 5.dp)) {
                                    Icon(
                                        painter = painterResource(id = com.example.designsystem.R.drawable.arrow_up),
                                        contentDescription = "up",
                                        tint = MyColors.blue,
                                        modifier = Modifier.padding(top = 3.dp)
                                    )
                                    Icon(
                                        painter = painterResource(id = com.example.designsystem.R.drawable.arrow_down),
                                        contentDescription = "down",
                                        tint = MyColors.blue
                                    )
                                }
                            }
                        }
                    }

                    // Вакансии
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 12.dp, top = 12.dp, end = 12.dp)
                        .padding(top = 25.dp))
                    {
                        Text(text = "Вакансии для вас", color = MyColors.white, style = MyTypes.title1)

                        LazyColumn(modifier = Modifier.padding(top = 10.dp)) {
                            itemsIndexed(dataUiState.data.vacancies) { index, vacancy  ->
                                if (index == 3 && !blueButton) {
                                    Button(
                                        onClick = { blueButton = !blueButton },
                                        shape = RoundedCornerShape(8.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(bottom = 5.dp),
                                        colors = ButtonDefaults.buttonColors()
                                            .copy(containerColor = MyColors.blue)
                                    ) {
                                        val size = dataUiState.data.vacancies.size
                                        val str = correctEndingOfANoun(size)
                                        Text(
                                            text = "Еще ${dataUiState.data.vacancies.size - 3} $str",
                                            color = MyColors.white, style = MyTypes.title3,
                                            modifier = Modifier.padding(horizontal = 20.dp)
                                        )
                                    }
                                }

                                when {
                                    index < 3 -> VacancyCard(
                                        vacancy = vacancy,
                                        onVacancyClick = { onVacancyClick(vacancy.id!!) },
                                        insertVacancy = { insertVacancy(vacancyMapper(vacancy)) },
                                        insertFavorite = { insertFavorite(vacancyMapper(vacancy)) },
                                        deleteFavorite = { deleteFavorite(vacancyMapper(vacancy)) },
                                    )
                                    index >= 3 && blueButton -> VacancyCard(
                                        vacancy = vacancy,
                                        onVacancyClick = { onVacancyClick(vacancy.id!!) },
                                        insertVacancy = { insertVacancy(vacancyMapper(vacancy)) },
                                        insertFavorite = { insertFavorite(vacancyMapper(vacancy)) },
                                        deleteFavorite = { deleteFavorite(vacancyMapper(vacancy)) },
                                    )
                                }
                            }
                        }
                    }
                }
            }
            else -> {
                Column(modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun PreviewSearchScreen() {
    val offers = listOf(
        OfferFromNetwork(id = "near_vacancies", title = "Вакансии рядом с вами", link = "https://hh.ru/"),
        OfferFromNetwork(id = "level_up_resume", title = "Поднять резюме в поиске", button = Button(text= "Поднять"), link = "https://hh.ru/mentors?from\u003dfooter_new\u0026hhtmFromLabel\u003dfooter_new\u0026hhtmFrom\u003dmain\u0026purposeId\u003d1"),
        OfferFromNetwork(id = "temporary_job", title = "Временная работа или подработка", link = "https://hh.ru/"),
        OfferFromNetwork(title = "Полезные статьи и советы", link = "https://hh.ru/articles?hhtmFrom\\u003dmain")
    )
    val vacancies = listOf(
        VacancyFromNetwork(
            id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
            lookingNumber = 2,
            title = "UI/UX дизайнер",
            address = AddressFromNetwork(town = "Минск", street = "улица Бирюзова", house = "4/5"),
            company = "Мобирикс",
            experience = ExperienceFromNetwork(previewText = "Опыт от 1 до 3 лет", text = "1–3 года"),
            publishedDate = "2024-02-20",
            isFavorite = false,
            salary = SalaryFromNetwork(full = "Уровень дохода не указан"),
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
        VacancyFromNetwork(
            id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
            lookingNumber = 2,
            title = "UI/UX дизайнер",
            address = AddressFromNetwork(town = "Минск", street = "улица Бирюзова", house = "4/5"),
            company = "Мобирикс",
            experience = ExperienceFromNetwork(previewText = "Опыт от 1 до 3 лет", text = "1–3 года"),
            publishedDate = "2024-02-20",
            isFavorite = false,
            salary = SalaryFromNetwork(full = "Уровень дохода не указан"),
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
        VacancyFromNetwork(
            id = "cbf0c984-7c6c-4ada-82da-e29dc698bb50",
            lookingNumber = 2,
            title = "UI/UX дизайнер",
            address = AddressFromNetwork(town = "Минск", street = "улица Бирюзова", house = "4/5"),
            company = "Мобирикс",
            experience = ExperienceFromNetwork(previewText = "Опыт от 1 до 3 лет", text = "1–3 года"),
            publishedDate = "2024-02-20",
            isFavorite = false,
            salary = SalaryFromNetwork(full = "Уровень дохода не указан"),
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
    SearchScreen(
        dataUiState = DataUiState.Success(
            NetworkData(
                offers = offers,
                vacancies = vacancies)
        ),
        onVacancyClick = {},
        insertVacancy = {},
        insertFavorite = {},
        deleteFavorite = {},
    )
}