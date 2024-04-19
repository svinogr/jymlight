package info.upump.jymlight.ui.screens.loginscreens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import info.upump.jymlight.R
import info.upump.jymlight.ui.screens.navigation.botomnavigation.NavigationItem
import info.upump.jymlight.ui.screens.viewmodel.web.LoginVM
import info.upump.jymlight.ui.theme.MyTextTitleLabelWithColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    navHost: NavHostController,
    paddingValues: PaddingValues
) {
    val loginVM: LoginVM = viewModel()
    val login = remember {
        loginVM.login
    }

    val password = remember {
        loginVM.password
    }

    val visible = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = true) {
        loginVM.getLoginAndPassword()
    }
    Scaffold(
        modifier = Modifier.padding(top = 0.dp),
        content = { it ->
            Column(
                modifier = Modifier.padding(top = paddingValues.calculateTopPadding()),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Image(
                        modifier = Modifier.align(Alignment.Center),
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "logo"
                    )
                }

                Column(
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.background)
                        .weight(1f),
                    verticalArrangement = Arrangement.SpaceAround

                ) {
                    val modifierValue = Modifier.padding(top = 4.dp, end = 0.dp, bottom = 4.dp)
                    TextField(modifier = modifierValue.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            containerColor = MaterialTheme.colorScheme.background,
                            textColor = MaterialTheme.colorScheme.onBackground
                        ),
                        readOnly = false,
                        value = login.collectAsState().value,
                        onValueChange = {
                            loginVM.updateLogin(it)
                        },
                        label = {
                            Text(
                                text = "Логин",
                                style = MyTextTitleLabelWithColor
                            )
                        }
                    )
                    TextField(modifier = modifierValue.fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            disabledTextColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            containerColor = MaterialTheme.colorScheme.background,
                            textColor = MaterialTheme.colorScheme.onBackground
                        ),
                        readOnly = false,
                        value = password.collectAsState().value,
                        onValueChange = {
                            loginVM.updatePassword(it)
                        },
                        visualTransformation = if (visible.value) VisualTransformation.None else PasswordVisualTransformation(),

                        label = {
                            Text(
                                text = "Пароль",
                                style = MyTextTitleLabelWithColor
                            )
                        },
                        trailingIcon = {
                            val image = if (visible.value)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Please provide localized description for accessibility services
                            val description =
                                if (visible.value) "Hide password" else "Show password"

                            IconButton(onClick = { visible.value = !visible.value }) {
                                Icon(imageVector = image, description)
                            }
                        }
                    )
                    OutlinedButton(
                        onClick = {
                                  navHost.navigate(NavigationItem.DefaultCycleNavigationItem.route)
                        },
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        Text(
                            text = "Войти",
                            color = colorResource(id = R.color.colorBackgroundCardView)
                        )
                    }

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp, end = 8.dp, bottom = 16.dp)
                    ) {
                        Text(text = "Востановить", modifier = Modifier.clickable { })
                        Text(text = "Зарегистрироваться", modifier = Modifier.clickable { })
                    }
                }
            }
        })
}
@PreviewLightDark
@Preview(showBackground = true, showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun LoginScreenPreview() {
    LoginScreen(
        navHost = NavHostController(LocalContext.current),
        paddingValues = PaddingValues(8.dp)
    )
}