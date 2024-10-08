package com.example.vacancy

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.database.entities.Address
import com.example.database.entities.Experience
import com.example.database.entities.Salary
import com.example.database.entities.Vacancy
import com.example.designsystem.MyColors
import com.example.designsystem.MyTypes
import kotlinx.coroutines.launch

@Composable
fun VacancyRoute(
    onUpClicked: () -> Unit,
    viewModel: VacancyViewModel = hiltViewModel(),
) {
    val vacancy by viewModel.currentVacancy.collectAsState()
    VacancyScreen(
        onUpClicked = {
            viewModel.deleteVacancy()
            onUpClicked() },
        vacancy = vacancy ?: Vacancy(""),
        insertFavorite = viewModel::insertFavorite,
        deleteFavorite = viewModel::deleteFavorite
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VacancyScreen(
    onUpClicked: () -> Unit,
    vacancy: Vacancy,
    insertFavorite: (Vacancy) -> Unit,
    deleteFavorite: (Vacancy) -> Unit,
) {
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MyColors.black)
        .padding(start = 12.dp, end = 12.dp, top = 12.dp)
        .verticalScroll(state = rememberScrollState())
    ) {
        Row(modifier = Modifier.padding(bottom = 10.dp),
            verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onUpClicked) {
                Icon(
                    painter = painterResource(id = com.example.designsystem.R.drawable.arrow_left),
                    contentDescription = "down",
                    tint = MyColors.white
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                painter = painterResource(id = com.example.designsystem.R.drawable.eye),
                contentDescription = "down",
                tint = MyColors.white,
                modifier = Modifier
                    .padding(end = 9.dp)
                    .width(25.dp)
                    .height(25.dp)
            )
            Icon(
                painter = painterResource(id = com.example.designsystem.R.drawable.share),
                contentDescription = "down",
                tint = MyColors.white,
                modifier = Modifier
                    .width(25.dp)
                    .height(25.dp)
            )
            IconButton(onClick = {
                if (vacancy.isFavorite == null || vacancy.isFavorite == true)
                    deleteFavorite(vacancy)
                else insertFavorite(vacancy)
            }) {
                Icon( // FIXME: при нажатии не происходит изменение сердечка, так как не observable, нет рекомпозиции
                    painter = painterResource(id =
                    if (vacancy.isFavorite == null || vacancy.isFavorite == true)
                        com.example.designsystem.R.drawable.favorite_clicked
                    else com.example.designsystem.R.drawable.favorite_default),
                    contentDescription = "down",
                    tint = MyColors.white
                )
            }
        }
        Text(text = "${vacancy.title}", color = MyColors.white,
            style = MyTypes.title1, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "${vacancy.salary?.full}", color = MyColors.white,
            style = MyTypes.text1, modifier = Modifier.padding(bottom = 8.dp))
        Text(text = "Требуемый опыт ${vacancy.experience?.text}", color = MyColors.white,
            style = MyTypes.text1, modifier = Modifier.padding(bottom = 4.dp))
        val schedules = vacancy.schedules.toString()
        Text(text = "${schedules[1].uppercaseChar()}${schedules.substring(2, schedules.length - 1)}",
            color = MyColors.white, style = MyTypes.text1, modifier = Modifier.padding(bottom = 20.dp))

        Row {
            Card(modifier = Modifier.weight(10f),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors().copy(containerColor = MyColors.dark_green)
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    if (vacancy.appliedNumber != null) {
                        Text(text = "${vacancy.appliedNumber} человек уже откликнулось",
                            color = MyColors.white,
                            style = MyTypes.text1,
                            modifier = Modifier.weight(7f)
                        )
                    }
                    if (vacancy.lookingNumber != null) {
                        Icon(
                            // FIXME: иконка не отображается как надо
                            painter = painterResource(id = com.example.designsystem.R.drawable.person),
                            contentDescription = "down",
                            modifier = Modifier.weight(2f),
//                        tint = MyColors.white
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.weight(0.4f))
            Card(modifier = Modifier.weight(10f),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors().copy(containerColor = MyColors.dark_green)
            ) {
                Row(modifier = Modifier.padding(8.dp)) {
                    Text(text = "${vacancy.lookingNumber} человека сейчас смотрят",
                        color = MyColors.white,
                        style = MyTypes.text1,
                        modifier = Modifier.weight(7f))
                    Icon(
                        // FIXME: иконка не отображается как надо
                        painter = painterResource(id = com.example.designsystem.R.drawable.big_eye),
                        contentDescription = "down",
                        modifier = Modifier.weight(2f)
                    )
                }
            }
        }
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
            shape = RoundedCornerShape(8.dp),
            colors = CardDefaults.cardColors().copy(containerColor = MyColors.grey2)
        ) {
            Column(modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()) {
                Row {
                    Text(text = "${vacancy.company}",
                        color = MyColors.white,
                        style = MyTypes.title3,
                        modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Icon(
                        painter = painterResource(id = com.example.designsystem.R.drawable.check_mark),
                        contentDescription = "down",
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
                Image(painter = painterResource(id = com.example.designsystem.R.drawable.useless_map2),
                    contentDescription = "",
                    modifier = Modifier
                        .width(400.dp)
                        .padding(bottom = 5.dp),
                    contentScale = ContentScale.FillWidth
                    )
                Text(text = "${vacancy.address?.town} ${vacancy.address?.street} ${vacancy.address?.house}",
                    color = MyColors.white,
                    style = MyTypes.text1,
                )
            }
        }
        if (vacancy.description != null) {
            Text(
                text = vacancy.description!!,
                color = MyColors.white,
                style = MyTypes.text1,
                modifier = Modifier.padding(top = 20.dp)
            )
        }
        Text(
            text = "Ваши задачи",
            color = MyColors.white,
            style = MyTypes.title2,
            modifier = Modifier.padding(top = 20.dp)
        )
        if (vacancy.responsibilities != null) {
            Text(
                text = vacancy.responsibilities!!,
                color = MyColors.white,
                style = MyTypes.text1,
                modifier = Modifier.padding(top = 10.dp)
            )
        }
        Text(
            text = "Задайте вопрос работодателю",
            color = MyColors.white,
            style = MyTypes.title4,
            modifier = Modifier.padding(top = 30.dp)
        )
        Text(
            text = "Он получит его с откликом на вакансию",
            color = MyColors.grey4,
            style = MyTypes.text1,
            modifier = Modifier.padding(top = 10.dp)
        )
        var text by remember { mutableStateOf("") }
        var isShownTextField by remember { mutableStateOf(false) }

        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()
        var showBottomSheet by remember { mutableStateOf(false) }
        
        if (vacancy.questions != null) {
            LazyColumn(modifier = Modifier.height((vacancy.questions!!.size  * 40).dp)) {
                items(vacancy.questions!!) {
                    Card(
                        shape = RoundedCornerShape(25.dp),
                        colors = CardDefaults.cardColors().copy(containerColor = MyColors.grey2),
                        modifier = Modifier
                            .padding(top = 10.dp)
                            .clickable {
                                text = it
                                isShownTextField = true
                                showBottomSheet = true
                            }
                            .height(28.dp)
                    ) {
                        Text(
                            text = it,
                            color = MyColors.white,
                            style = MyTypes.title4,
                            modifier = Modifier.padding(vertical = 5.dp, horizontal = 10.dp)
                        )
                    }
                }
            }
        }

        if (showBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = {
                    showBottomSheet = false
                    isShownTextField = false
                    text = ""
                },
                sheetState = sheetState
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row {
                        Image(painter = painterResource(id = com.example.designsystem.R.drawable.ava),
                            contentDescription = "",
                            modifier = Modifier
                                .width(60.dp)
                                .height(60.dp))
                        Column(modifier = Modifier.padding(start = 10.dp, bottom = 20.dp)) {
                            Text(
                                text = "Резюме для отклика",
                                color = MyColors.grey3,
                                style = MyTypes.text1,
                                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                            )
                            Text(
                                text = "UI/UX дизайнер",
                                color = MyColors.white,
                                style = MyTypes.title4,
                            )
                        }
                    }
                    HorizontalDivider(modifier = Modifier.padding(bottom = 20.dp))
                    if (isShownTextField) {
                        TextField(
                            value = text, 
                            onValueChange = { text = it },
                            placeholder = {
                                Text(text = "Ваше сопроводительное письмо")
                            },
                            colors = TextFieldDefaults.colors(
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                // FIXME: подобрать норм цвет
                                unfocusedContainerColor = BottomSheetDefaults.ContainerColor,
                                focusedContainerColor = BottomSheetDefaults.ContainerColor,
                                disabledContainerColor = BottomSheetDefaults.ContainerColor,
                            ),
                            textStyle = MyTypes.title4,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else {
                        Text(
                            text = "Добавить сопроводительное",
                            color = MyColors.green,
                            style = MyTypes.title4,
                            modifier = Modifier
                                .clickable { isShownTextField = true }
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 20.dp)
                        )
                    }
                    
                    Button(onClick = {
                        scope.launch { sheetState.hide() }.invokeOnCompletion {
                            if (!sheetState.isVisible) {
                                showBottomSheet = false
                            }
                        } },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors()
                            .copy(containerColor = MyColors.green),
                        shape = RoundedCornerShape(8.dp)) {
                        Text(
                            text = "Откликнуться",
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }
            }
        }
        Button(
            onClick = { showBottomSheet = true },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, bottom = 10.dp),
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

@Preview
@Composable
fun PreviewVacancyScreen() {
    val vacancy = Vacancy(
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
    VacancyScreen(
        onUpClicked = {},
        vacancy = vacancy,
        insertFavorite = {},
        deleteFavorite = {},
    )
}