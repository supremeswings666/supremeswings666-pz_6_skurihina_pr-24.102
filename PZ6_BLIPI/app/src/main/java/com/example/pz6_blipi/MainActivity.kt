
package com.example.pz6_blipi

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pz6_blipi.ui.theme.PZ6_BLIPITheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemDarkTheme = isSystemInDarkTheme()

            var darkTheme by remember {
                mutableStateOf(systemDarkTheme)
            }

            val counter = remember {
                mutableStateOf(0)
            }

            var courseName by remember {
                mutableStateOf("")
            }

            var isLearning by remember {
                mutableStateOf(false)
            }

            PZ6_BLIPITheme {
                AppTheme(darkTheme) {
                    StudyScreen(
                        counter = counter,
                        courseName = courseName,
                        isLearning = isLearning,
                        darkTheme = darkTheme,
                        onCounterClick = {
                            counter.value++
                        },
                        onCourseNameChange = {
                            courseName = it
                        },
                        onLearningChange = {
                            isLearning = it
                        },
                        onThemeChange = {
                            darkTheme = it
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun StudyScreen(
    counter: State<Int>,
    courseName: String,
    isLearning: Boolean,
    darkTheme: Boolean,
    onCounterClick: () -> Unit,
    onCourseNameChange: (String) -> Unit,
    onLearningChange: (Boolean) -> Unit,
    onThemeChange: (Boolean) -> Unit
) {
    Log.d("ComposeLessons", "StudyScreen")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Мой учебный кабинет",
            style = MaterialTheme.typography.headlineSmall
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (darkTheme) {
                        "Тёмное оформление"
                    } else {
                        "Светлое оформление"
                    }
                )

                Switch(
                    checked = darkTheme,
                    onCheckedChange = onThemeChange
                )
            }
        }

        CounterCard(
            counter = counter,
            onCounterClick = onCounterClick
        )

        Card(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Урок 7. Поле ввода и флажок",
                    style = MaterialTheme.typography.titleLarge
                )

                OutlinedTextField(
                    value = courseName,
                    onValueChange = onCourseNameChange,
                    label = {
                        Text("Какой курс ты изучаешь?")
                    },
                    modifier = Modifier.fillMaxWidth()
                )

                Text(
                    text = if (courseName.isBlank()) {
                        "Напиши название курса в поле выше"
                    } else {
                        "Твой выбор: $courseName"
                    }
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = isLearning,
                        onCheckedChange = onLearningChange
                    )

                    Text("Я изучаю мобильную разработку")
                }

                Text(
                    text = if (isLearning) {
                        "Здорово! Продолжай обучение."
                    } else {
                        "Отметь флажок, когда будешь готов."
                    }
                )
            }
        }

        ExtraInfoCard()
    }
}

@Composable
fun CounterCard(
    counter: State<Int>,
    onCounterClick: () -> Unit
) {
    var uppercase by remember {
        mutableStateOf(false)
    }

    val parityHelper = remember(uppercase) {
        ParityHelper(uppercase)
    }

    val currentCount = counter.value

    Log.d("ComposeLessons", "CounterCard: $currentCount")

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Уроки 6, 8 и 9. Счётчик",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Количество нажатий: $currentCount",
                fontSize = 20.sp
            )

            Text(
                text = "Число ${parityHelper.check(currentCount)}"
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uppercase,
                    onCheckedChange = {
                        uppercase = it
                    }
                )

                Text("Показывать заглавными буквами")
            }

            Button(
                onClick = onCounterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Увеличить счётчик")
            }
        }
    }
}

class ParityHelper(
    private val uppercase: Boolean
) {

    fun check(value: Int): String {
        val result = if (value % 2 == 0) {
            "чётное"
        } else {
            "нечётное"
        }

        return if (uppercase) {
            result.uppercase()
        } else {
            result
        }
    }
}

@Composable
fun ExtraInfoCard() {
    var showDetails by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Урок 10. Дополнительные сведения",
                style = MaterialTheme.typography.titleLarge
            )

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = showDetails,
                    onCheckedChange = {
                        showDetails = it
                    }
                )

                Text("Открыть пояснение")
            }

            if (showDetails) {
                Text(
                    text = "remember сохраняет состояние между повторными " +
                            "вызовами Composable-функции, а mutableStateOf " +
                            "позволяет отслеживать его изменения."
                )
            }
        }
    }
}

@Composable
fun AppTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFA7D7C5),
            secondary = Color(0xFFF1B89F),
            tertiary = Color(0xFFD1B9F0),
            background = Color(0xFF121B18),
            surface = Color(0xFF1C2924),
            onPrimary = Color(0xFF18342B),
            onSecondary = Color(0xFF49291E),
            onTertiary = Color(0xFF35264A),
            onBackground = Color(0xFFE3EEE8),
            onSurface = Color(0xFFE3EEE8)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF3F6B5B),
            secondary = Color(0xFF9C5B42),
            tertiary = Color(0xFF65558D),
            background = Color(0xFFF3F6F1),
            surface = Color(0xFFFFFEFC),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onTertiary = Color.White,
            onBackground = Color(0xFF1B241F),
            onSurface = Color(0xFF1B241F)
        )
    }

    val appTypography = Typography(
        headlineSmall = TextStyle(
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        ),
        titleLarge = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.SemiBold,
            fontSize = 21.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 18.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = FontFamily.SansSerif,
            fontSize = 15.sp
        )
    )

    MaterialTheme(
        colorScheme = colors,
        typography = appTypography,
        content = content
    )
}
