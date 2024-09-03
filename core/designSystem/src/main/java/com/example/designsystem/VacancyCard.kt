package com.example.designsystem

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.data.mappers.intToMonth
import com.example.network.model.AddressFromNetwork
import com.example.network.model.ExperienceFromNetwork
import com.example.network.model.SalaryFromNetwork
import com.example.network.model.VacancyFromNetwork

@Composable
fun VacancyCard(
    vacancy: VacancyFromNetwork,
    onVacancyClick: () -> Unit,
    insertVacancy: () -> Unit,
    insertFavorite: () -> Unit,
    deleteFavorite: () -> Unit,
) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(bottom = 16.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MyColors.grey2)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Column(modifier = Modifier.clickable {
                onVacancyClick()
                insertVacancy()
            }) {
                Row(modifier = Modifier.padding(bottom = 3.dp)) {
                    Column {
                        if (vacancy.lookingNumber != null) {
                            Text(text = "Сейчас просматривает ${vacancy.lookingNumber} человек",
                                color = MyColors.green,
                                style = MyTypes.text1,
                                modifier = Modifier.padding(bottom = 5.dp)
                            )
                        }
                        Text(text = vacancy.title ?: "", color = MyColors.white, style = MyTypes.title3, modifier = Modifier.width(300.dp))
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    // FIXME: функциональный эл-т, по нажатию должен сохранять в избранное и удалять
                    Icon(painter = painterResource(if (vacancy.isFavorite!!) R.drawable.favorite_clicked else R.drawable.favorite_default) ,
                        contentDescription = "Icon",
                        modifier = Modifier.clickable { if (vacancy.isFavorite!!) deleteFavorite() else insertFavorite() })
                }
                Text(text = vacancy.address?.town ?: "", color = MyColors.white, style = MyTypes.title4, modifier = Modifier.padding(bottom = 3.dp))
                Row(modifier = Modifier.padding(bottom = 3.dp)) {
                    Text(text = vacancy.company ?: "", color = MyColors.white, style = MyTypes.title4, modifier = Modifier.padding(end = 5.dp))
                    Icon(painter = painterResource(R.drawable.check_mark), contentDescription = "Icon", tint = MyColors.grey3)
                }
                Row(modifier = Modifier.padding(bottom = 3.dp)) {
                    Icon(painter = painterResource(R.drawable.exp), contentDescription = "", modifier = Modifier.padding(end = 5.dp), tint = MyColors.white)
                    Text(text = vacancy.experience?.previewText ?: "", color = MyColors.white, style = MyTypes.title4)
                }

                val day = vacancy.publishedDate?.substring(8,10)  ?: ""
                val month = vacancy.publishedDate?.substring(5,7)?.trimStart { it == '0'} ?: ""
                Text(text = "Опубликовано $day ${ if (month.isNotEmpty()) intToMonth(month.toInt()) else ""}", color = MyColors.grey4)
            }
            Button(
                onClick = { },
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                colors = ButtonDefaults.buttonColors()
                    .copy(containerColor = MyColors.green)
            ) {
                Text(
                    text = "Откликнуться",
                    modifier = Modifier.padding(horizontal = 20.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    VacancyCard(
        vacancy = VacancyFromNetwork(
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
        onVacancyClick = {},
        insertVacancy = {},
        insertFavorite = {},
        deleteFavorite = {}
    )
}