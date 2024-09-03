package com.example.entry

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.designsystem.MyColors
import com.example.designsystem.MyTypes

@Composable
fun EntryRoute(
    onMainScreenClick: () -> Unit,
    viewModel: EntryViewModel = hiltViewModel(),
) {
    val emailAddress by viewModel.emailAddress.collectAsState()
    val emailIsValid by viewModel.emailIsValid.collectAsState()
    val shouldTheScreenAppear by viewModel.shouldTheScreenAppear.collectAsState()
    val password by viewModel.password.collectAsState()

    EntryScreen(
        emailAddress = emailAddress,
        enterEmail = viewModel::enterEmail,
        emailIsValid = emailIsValid,
        changeEmailIsValid = viewModel::changeEmailIsValid,
        shouldTheScreenAppear = shouldTheScreenAppear,
        changeToPasswordScreenAppear = viewModel::changeScreenAppear,
        password = password,
        enterPassword = viewModel::enterPassword,
        onMainScreenClick = onMainScreenClick,
    )
}
@Composable
fun EntryScreen(
    emailAddress: String,
    enterEmail: (String) -> Unit,
    emailIsValid: Boolean,
    changeEmailIsValid: (Boolean) -> Unit,
    shouldTheScreenAppear: Boolean,
    changeToPasswordScreenAppear: (Boolean) -> Unit,
    password: String,
    enterPassword: (String) -> Unit,
    onMainScreenClick: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (shouldTheScreenAppear) {
            // экран ввода пароля, появляется после правильного ввода емейла
            // и нажатии на "Продолжить"
            Column(modifier = Modifier
                .padding(12.dp)
                .padding(top = 200.dp))
            {
                Text(text = "Отправили код на $emailAddress",
                    style = MyTypes.title1,
                    color = MyColors.white,)
                Text(text = "Напишите его, чтобы подтвердить, что это вы, а не кто-то другой входит в личный кабинет",
                    style = MyTypes.title3,
                    color = MyColors.white,
                    modifier = Modifier.padding(top = 15.dp))
                BasicTextField(
                    value = password,
                    onValueChange = { if (it.length <= 4) enterPassword(it) },
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.NumberPassword
                    ),
                    decorationBox = {
                        Row(horizontalArrangement = Arrangement.Center) {
                            repeat(4) { index ->
                                val char = when {
                                    index == 0 && password.isEmpty() -> "|"
                                    index >= password.length -> "*"
                                    else -> password[index].toString()
                                }
                                Text(
                                    modifier = Modifier
                                        .width(40.dp)
                                        .height(40.dp)
                                        .border(3.dp, MyColors.grey2, RoundedCornerShape(8.dp))
                                        .padding(2.dp)
                                        .background(MyColors.grey2),
                                    text = char,
                                    style = MyTypes.title1,
                                    color = MyColors.grey3,
                                    textAlign = TextAlign.Center
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                        }
                    }
                )
                Button(
                    onClick = onMainScreenClick,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors()
                        .copy(containerColor = if (password.length == 4) MyColors.blue else MyColors.dark_blue),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Продолжить",
                        modifier = Modifier
                    )
                }
            }
        } else {
            // экран входа
            Text(
                text = "Вход в личный кабинет",
                color = Color.White,
                style = MyTypes.title1,
                modifier = Modifier.padding(start = 12.dp, top = 40.dp)
            )

            Spacer(modifier = Modifier.height(200.dp))

            Card(
                modifier = Modifier
                    .padding(12.dp),
                colors = CardDefaults.cardColors(MyColors.grey1)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(
                        text = "Поиск работы",
                        style = MyTypes.title3,
                        color = MyColors.white,
                        modifier = Modifier.padding(top = 15.dp)
                    )

                    BasicTextField(
                        value = emailAddress,
                        onValueChange = { changeEmailIsValid(true); enterEmail(it) },
                        decorationBox = { innerTextField ->
                            Row(
                                Modifier
                                    .border(
                                        1.dp,
                                        if (emailIsValid) MyColors.grey3 else MyColors.red,
                                        RoundedCornerShape(8.dp)
                                    )
                                    .background(MyColors.grey2)
                                    .padding(start = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    Icons.Default.Email,
                                    contentDescription = "Email",
                                    tint = MyColors.grey3
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Box(modifier = Modifier.weight(1f)) {
                                    if (emailAddress.isEmpty()) {
                                        Text(
                                            text = "Enter email",
                                            style = TextStyle(color = Color.Gray)
                                        )
                                    }
                                    innerTextField()
                                }
                                if (emailAddress.isNotEmpty()) {
                                    IconButton(onClick = { enterEmail("") }) {
                                        Icon(
                                            Icons.Default.Clear,
                                            contentDescription = "Clear text",
                                            tint = MyColors.grey3
                                        )
                                    }
                                }
                            }
                        },
                        textStyle = MyTypes.text1.copy(color = MyColors.white),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .height(40.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(top = 20.dp)
                    ) {
                        Button(
                            onClick = {
                                // FIXME: есть непонятки в работе, проверить в разных комбинациях
                                Log.d("regex", "emailIsValid = $emailIsValid")
                                val qwe = emailAddress.matches("""([a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\.[a-zA-Z0-9_-]+)""".toRegex())
                                Log.d("regex", "qwe = $qwe")
                                changeEmailIsValid(qwe)
                                Log.d("regex", "emailIsValid = $emailIsValid")
                                if (qwe) changeToPasswordScreenAppear(true)
                            },
                            shape = RoundedCornerShape(8.dp),
                            colors = ButtonDefaults.buttonColors()
                                .copy(containerColor = if (emailAddress.isNotEmpty()) MyColors.blue else MyColors.dark_blue)
                        ) {
                            Text(
                                text = "Продолжить",
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                        }
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "Войти с паролем", color = MyColors.blue)
                        }
                    }
                }
            }

            Card(
                modifier = Modifier
                    .padding(12.dp),
                colors = CardDefaults.cardColors(MyColors.grey1)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {

                    Text(
                        text = "Поиск сотрудников",
                        style = MyTypes.title3,
                        color = MyColors.white,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Text(
                        text = "Размещение вакансий и доступ к базе резюме",
                        style = MyTypes.title4,
                        color = MyColors.white,
                        modifier = Modifier.padding(top = 10.dp)
                    )
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
                            text = "Я ищу сотрудников",
                            modifier = Modifier.padding(horizontal = 20.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun EntryScreenPreview() {
    EntryScreen(
        emailAddress = "example@mail.ru",
        enterEmail = {},
        emailIsValid = true,
        changeEmailIsValid = {},
        shouldTheScreenAppear = false,
        changeToPasswordScreenAppear = {},
        password = "1234",
        enterPassword = {},
        onMainScreenClick = {}
    )
}