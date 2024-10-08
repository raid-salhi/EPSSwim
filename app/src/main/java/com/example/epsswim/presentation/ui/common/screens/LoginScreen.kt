package com.example.epsswim.presentation.ui.common.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withLink
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.epsswim.R
import com.example.epsswim.data.model.auth.LoginBody
import com.example.epsswim.presentation.navigation.Screen
import com.example.epsswim.presentation.ui.common.componants.MyOutlinedTextField
import com.example.epsswim.presentation.ui.common.viewmodels.AuthViewmodel
import com.example.epsswim.presentation.ui.theme.MyBackground
import com.example.epsswim.presentation.ui.theme.MyPrimary
import com.example.epsswim.presentation.ui.theme.MyRed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun LoginScreen(
    authViewmodel: AuthViewmodel ,
    navController: NavHostController,
    isTrainer: MutableState<Boolean?>
) {
    val role = authViewmodel.role.collectAsState()
    val token = authViewmodel.token.collectAsState()
    val isIncorrectCredentials by authViewmodel.isIncorrectCredentials
    val isNotConnected by authViewmodel.isNotConnected
    var enabled by remember {
        mutableStateOf(true)
    }
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    LaunchedEffect(key1 = token.value, key2 = role.value) {
        Log.d("ROLE", "Login: $role ")
        if ((token.value != null) && (role.value != null)){
            enabled = true
            isTrainer.value =  role.value=="coach"
            when (isTrainer.value) {
                true ->{
                    navController.popBackStack()
                    navController.navigate(Screen.AbsenceScreen)
                }
                false ->{
                    navController.popBackStack()
                    navController.navigate(Screen.ParentHome)
                }
                else -> Log.e("login", "LoginScreen: role is null")
            }
        }
    }
    LaunchedEffect(enabled) {
        if (!enabled){
            delay(8000)
            enabled = true
            if (isNotConnected)
                Toast.makeText(context, "تحقق من شبكة الانترنت", Toast.LENGTH_SHORT).show()
            if (isIncorrectCredentials)
                Toast.makeText(context, "تحقق من معلوماتك الشخصية", Toast.LENGTH_SHORT).show()
        }
    }
    Surface (modifier = Modifier.fillMaxSize()) {
        Column (
            modifier = Modifier.padding(start = 20.dp, end = 20.dp,top = 50.dp, bottom = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Image(
                painter = painterResource(id = R.drawable.logo_login) ,
                contentDescription = "Logo login Screen",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(256.dp)
            )
            Spacer(modifier = Modifier.height(50.dp))
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                    fontFamily = FontFamily(listOf(Font(R.font.cairo_extra_bold))),
                    fontSize = 32.sp,
                    color = MyPrimary
                    )) {
                    append(stringResource(R.string.eps))
                }
                    withStyle(style = SpanStyle(
                        fontFamily = FontFamily(listOf(Font(R.font.cairo_extra_bold))),
                        fontSize = 32.sp,
                        color = Color.Black
                    )) {
                        append(stringResource(R.string.welcome))
                    }

                },
                modifier = Modifier
                    .padding(bottom = 10.dp)
            )
            Text(
                text = stringResource(R.string.login),
                fontFamily = FontFamily(listOf(Font(R.font.cairo_regular))),
                fontSize = 24.sp,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.height(50.dp))
            var username by rememberSaveable {
                mutableStateOf("")
            }
            MyOutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = { Text(stringResource(R.string.username)) },
                modifier = Modifier
                    .padding(bottom = 18.dp)
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
            )
            var password by rememberSaveable {
                mutableStateOf("")
            }
            var passwordVisible by rememberSaveable {
                mutableStateOf(false)
            }
            MyOutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            painter = if (passwordVisible) painterResource(id = R.drawable.eye_open) else painterResource(id =R.drawable.eye_close),
                            contentDescription = stringResource(id = R.string.password))
                    }
                },
                label = { Text(stringResource(R.string.password)) },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 18.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password,imeAction = ImeAction.Done),
            )

            Button(
                modifier = Modifier
                    .height(56.dp)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                enabled = enabled,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MyPrimary,
                    contentColor = MyBackground
                ),
                onClick = {
                    authViewmodel.login(LoginBody(username, password))
                    enabled = false
                    focusManager.clearFocus()
                }
            ) {
                if (!enabled)
                    CircularProgressIndicator(modifier = Modifier
                        .padding(end = 12.dp)
                        .size(26.dp))
                Text(
                    text = stringResource(R.string.login),
                    fontFamily = FontFamily(listOf(Font(R.font.cairo_bold))),
                    fontSize = 16.sp,
                    color = MyBackground
                )
            }
            Box(modifier = Modifier.fillMaxSize()){
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )) {
                            append("By ")
                        }
                        withLink(LinkAnnotation.Url(url = "https://mtc.dz")){
                            withStyle(style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = MyRed
                            )) {
                                append("MTC Company. ")
                            }
                        }
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.LightGray
                        )) {
                            append("All rights reserved.")
                        }
                    },
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

