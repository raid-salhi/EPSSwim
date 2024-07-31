package com.example.epsswim.presentation.ui.common.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.epsswim.R
import com.example.epsswim.presentation.ui.common.componants.MyOutlinedTextField
import com.example.epsswim.presentation.ui.theme.MyBackground
import com.example.epsswim.presentation.ui.theme.MyPrimary
import com.example.epsswim.presentation.ui.theme.MyRed

@Preview
@Composable
fun LoginScreen() {
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
                onClick = { /*TODO*/ }
            ) {
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
                        withStyle(style = SpanStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = MyRed
                        )) {
                            append("MTC Company. ")
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
                        .align(Alignment.BottomCenter)
                )
            }
        }
    }
}

