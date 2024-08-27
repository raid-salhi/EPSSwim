package com.example.epsswim.presentation.ui.trainer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.epsswim.R
import com.example.epsswim.presentation.navigation.Screen
import com.example.epsswim.presentation.ui.common.componants.MyAppBar
import com.example.epsswim.presentation.ui.theme.MyBackground
import com.example.epsswim.presentation.ui.theme.MyPrimary
import com.example.epsswim.presentation.ui.theme.MyPrimaryDark
import com.example.epsswim.presentation.ui.theme.MySecondary
import com.example.epsswim.presentation.ui.trainer.componants.AbsenceSwimmerCard
import com.example.epsswim.presentation.ui.trainer.componants.MyWeekCalendar
import com.mohamedrejeb.richeditor.model.RichTextState
import com.mohamedrejeb.richeditor.model.rememberRichTextState
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditor
import com.mohamedrejeb.richeditor.ui.material3.RichTextEditorDefaults
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelScreen(navController: NavHostController) {
    Scaffold (
        topBar = {
            MyAppBar(
                title = "المستوى 1",
                navigationIcon = {
                    IconButton( onClick = {
                        navController.popBackStack()
                    }){
                        Icon(
                            painter = painterResource(id = R.drawable.chevron_left),
                            contentDescription = "back button"
                        )
                    }
                },
                actions = {
                    IconButton( onClick = {
                        navController.popBackStack()
                    }){
                        Icon(
                            painter = painterResource(id = R.drawable.done_ic),
                            contentDescription = "done button"
                        )
                    }
                }
            )
        },
        contentWindowInsets = WindowInsets(0,0,0,)


    ){
        val sheetState = rememberModalBottomSheetState()
        var showBottomSheet by remember { mutableStateOf(false) }
        val scope = rememberCoroutineScope()
        var note by remember {
            mutableStateOf("")
        }
        val state = rememberRichTextState()
        val titleSize = MaterialTheme.typography.displaySmall.fontSize


        Surface(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ){
            Column {
                MyWeekCalendar()
                Column (
                    horizontalAlignment = Alignment.End,
                    modifier = Modifier
                        .padding(bottom = 30.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        modifier = Modifier.padding(vertical = 16.dp, horizontal = 12.dp),
                        text = stringResource(R.string.number_of_presence) +"23/23",
                        fontFamily = FontFamily(listOf(Font(R.font.cairo_semi_bold))),
                        fontSize = 20.sp,
                    )
                    Box (modifier = Modifier.fillMaxWidth()){
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .clickable { }
                                .width(28.dp)
                                .height(180.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.CenterStart)
                                    .padding(start = 10.dp),
                                painter = painterResource(id = R.drawable.chevron_left) ,
                                contentDescription ="back",
                                tint = MyPrimaryDark
                            )
                        }
                        Box(
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .clickable { }
                                .width(28.dp)
                                .height(180.dp)
                        ) {
                            Icon(
                                modifier = Modifier
                                    .align(Alignment.CenterEnd)
                                    .padding(end = 10.dp),
                                painter = painterResource(id = R.drawable.chevron_right) ,
                                contentDescription ="next",
                                tint = MyPrimaryDark
                            )
                        }
                        AbsenceSwimmerCard(
                            modifier = Modifier
                                .padding(horizontal = 36.dp)
                                .align(Alignment.Center)
                        ){
                            navController.popBackStack()
                            navController.navigate(Screen.SwimmerProfile)
                        }
                    }
                    Button(
                        onClick = {
                            showBottomSheet = true
                        },
                        modifier = Modifier
                            .padding(vertical = 30.dp, horizontal = 36.dp)
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = MyPrimaryDark, contentColor = MyBackground),
                        elevation = ButtonDefaults.buttonElevation(10.dp),
                    ) {
                        Text(
                            text = stringResource(R.string.add_note),
                            fontWeight = FontWeight.Medium,
                            fontSize = 20.sp,
                            color = MyBackground
                        )
                    }
                }

                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            showBottomSheet = false
                        },
                        sheetState = sheetState,
                        containerColor = MyBackground
                    ) {
                        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                            Column {
                                TextButton(
                                    onClick = {
                                        scope.launch {
                                            note = state.toMarkdown()
                                            sheetState.hide()
                                        }.invokeOnCompletion {
                                            showBottomSheet= false
                                        }
                                    },
                                    modifier = Modifier
                                        .padding(top = 16.dp, end = 16.dp)
                                        .align(Alignment.End)
                                ) {
                                    Text(
                                        text ="حفظ",
                                        fontWeight = FontWeight.Medium,
                                        fontSize = 16.sp,
                                        color = MyPrimary
                                    )
                                }

                                MarkDownController(
                                    modifier = Modifier.weight(1f),
                                    onBoldClick = {
                                        state.toggleSpanStyle(SpanStyle(fontWeight = FontWeight.Bold))
                                    },
                                    onItalicClick = {
                                        state.toggleSpanStyle(SpanStyle(fontStyle = FontStyle.Italic))
                                    },
                                    onUnderlineClick = {
                                        state.toggleSpanStyle(SpanStyle(textDecoration = TextDecoration.Underline))
                                    },
                                    onTitleClick = {
                                        state.toggleSpanStyle(SpanStyle(fontSize = titleSize))
                                    },

                                    onTextColorClick = {
                                        state.toggleSpanStyle(SpanStyle(color = Color.Red))
                                    }
                                )
                                RichTextEditor(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .fillMaxWidth(),
                                    state = state,
                                    placeholder = {
                                        Text(text = "أضف ملاحظة...")
                                    },
                                    minLines = 10,
                                    colors = RichTextEditorDefaults.richTextEditorColors(
                                        containerColor = MyBackground,
                                        cursorColor = MyPrimaryDark,
                                        focusedIndicatorColor = Color.Transparent,
                                        unfocusedIndicatorColor = Color.Transparent
                                    )
                                )


                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun MarkDownController(
    modifier: Modifier = Modifier,
    onBoldClick: () -> Unit,
    onItalicClick: () -> Unit,
    onUnderlineClick: () -> Unit,
    onTitleClick: () -> Unit,
    onTextColorClick: () -> Unit,
) {
    var boldSelected by rememberSaveable { mutableStateOf(false) }
    var italicSelected by rememberSaveable { mutableStateOf(false) }
    var underlineSelected by rememberSaveable { mutableStateOf(false) }
    var titleSelected by rememberSaveable { mutableStateOf(false) }
    var textColorSelected by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(all = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        ControlWrapper(
            selected = boldSelected,
            onChangeClick = { boldSelected = it },
            onClick = onBoldClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.format_bold),
                contentDescription = "Bold Control",
                tint = MyPrimary
            )
        }
        ControlWrapper(
            selected = italicSelected,
            onChangeClick = { italicSelected = it },
            onClick = onItalicClick
        ) {
            Icon(
                painter = painterResource(id =R.drawable.format_italic ),
                contentDescription = "Italic Control",
                tint = MyPrimary
            )
        }
        ControlWrapper(
            selected = underlineSelected,
            onChangeClick = { underlineSelected = it },
            onClick = onUnderlineClick
        ) {
            Icon(
                painter = painterResource(id =R.drawable.format_underlined ),
                contentDescription = "Underline Control",
                tint = MyPrimary
            )
        }
        ControlWrapper(
            selected = titleSelected,
            onChangeClick = { titleSelected = it },
            onClick = onTitleClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.format_title ),
                contentDescription = "Title Control",
                tint = MyPrimary
            )
        }
        ControlWrapper(
            selected = textColorSelected,
            onChangeClick = { textColorSelected = it },
            onClick = onTextColorClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.format_text_xolor ),
                contentDescription = "TextColor Control",
                tint = MyPrimary
            )
        }
    }
}

@Composable
fun ControlWrapper(
    selected: Boolean,
    selectedColor: Color = MySecondary,
    unselectedColor: Color = MyBackground,
    onChangeClick: (Boolean) -> Unit,
    onClick: () -> Unit,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(size = 6.dp))
            .clickable {
                onClick()
                onChangeClick(!selected)
            }
            .background(
                if (selected) selectedColor
                else unselectedColor
            )
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(size = 6.dp)
            )
            .padding(all = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}
