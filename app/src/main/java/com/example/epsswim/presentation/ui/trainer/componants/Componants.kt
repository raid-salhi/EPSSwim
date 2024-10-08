package com.example.epsswim.presentation.ui.trainer.componants

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.epsswim.R
import com.example.epsswim.data.model.app.competition.Competition
import com.example.epsswim.data.model.app.competition.Participant
import com.example.epsswim.data.model.app.participation.swimmingtypes.Eventtype
import com.example.epsswim.data.model.app.swimmer.Swimmer
import com.example.epsswim.data.model.requestBody.absences.SwimmerId
import com.example.epsswim.data.model.requestBody.competition.CompetitionData
import com.example.epsswim.data.model.requestBody.competition.Data
import com.example.epsswim.data.model.requestBody.competition.Participants
import com.example.epsswim.presentation.ui.theme.MyBackground
import com.example.epsswim.presentation.ui.theme.MyPrimary
import com.example.epsswim.presentation.ui.theme.MyPrimaryDark
import com.example.epsswim.presentation.ui.theme.MyRed
import com.example.epsswim.presentation.ui.theme.MySecondary
import com.example.epsswim.presentation.utils.calculateAge
import com.example.epsswim.presentation.utils.formatDate
import com.example.epsswim.presentation.utils.getArabicDate
import com.example.epsswim.presentation.utils.getArabicWeekDay
import com.example.epsswim.presentation.utils.getFullName
import com.kizitonwose.calendar.compose.WeekCalendar
import com.kizitonwose.calendar.compose.weekcalendar.rememberWeekCalendarState
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun FullScreenDialogContent(
    competitionData: MutableState<CompetitionData>,
    participants: List<Swimmer>,
    onDone: (CompetitionData) -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Surface (color = MyBackground) {

            Column (
                modifier = Modifier
                    .padding(horizontal = 24.dp, vertical = 16.dp)
                    .fillMaxSize()
            ) {
                DialogHeader(
                    competitionData =competitionData.value,
                    onDismiss =onDismiss,
                    onDone = onDone
                )
                DialogBody(participants,competitionData)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DialogBody(participants: List<Swimmer>, competitionData: MutableState<CompetitionData>) {
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }
    var isCardSelected by remember {mutableStateOf(false)}
    val participantList = remember {
        mutableStateOf(participants.filter {
            Data(it.swimmerid) in (competitionData.value.participants?.data ?: emptyList())
        })
    }
    Column (
        modifier = Modifier
            .clickable(
                indication = null,
                interactionSource = interactionSource
            ) {
                isCardSelected = false
                focusManager.clearFocus(true)
            }
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ){
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.personal_info),
            fontSize = 16.sp,
            color = MyPrimary,
            fontFamily = FontFamily(listOf(Font(R.font.cairo_regular))),
        )
        var name by rememberSaveable { mutableStateOf(competitionData.value.event) }
        var nameTyped by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
            value = name,
            onValueChange = {
                name = it
                nameTyped = true
                competitionData.value = competitionData.value.copy(event = it)
            },
            label = { Text(stringResource(R.string.competition_name)) },
            isError = name.isEmpty() && nameTyped,
            modifier = Modifier
                .onFocusChanged { isCardSelected = false }
                .padding(bottom = 12.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MyPrimary,
                focusedContainerColor = MyBackground ,
                unfocusedContainerColor = MyBackground ,
                focusedLabelColor = MyPrimary
            )
        )
        var place by rememberSaveable { mutableStateOf(competitionData.value.location) }
        var placeTyped by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
            value = place,
            onValueChange = {
                place = it
                placeTyped = true
                competitionData.value = competitionData.value.copy(location = it)
            },
            label = { Text(stringResource(R.string.competition_place)) },
            isError = name.isEmpty() && nameTyped,
            modifier = Modifier
                .onFocusChanged { isCardSelected = false }
                .padding(bottom = 12.dp)
                .fillMaxWidth(),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = MyPrimary,
                focusedContainerColor = MyBackground ,
                unfocusedContainerColor = MyBackground ,
                focusedLabelColor = MyPrimary
            )
        )
        val initialDate = Calendar
            .getInstance()
            .apply {
                set(
                    competitionData.value.competitiondate.split('-')[0].toInt(),
                    competitionData.value.competitiondate.split('-')[1].toInt()-1,
                    competitionData.value.competitiondate.split('-')[2].toInt(),
                    1,
                    0,
                    0
                )
            }
            .timeInMillis
        val competitionDate =  rememberSaveable { mutableStateOf(competitionData.value.competitiondate) }

        CustomDatePicker(
            modifier = Modifier
                .onFocusChanged { isCardSelected = false }
                .padding(bottom = 12.dp),
            competitionData = competitionData,
            datePickerState = rememberDatePickerState(
                initialSelectedDateMillis = initialDate,
                yearRange = 2024..2034
            ),
            label = stringResource(R.string.competition_date),
            isIllegalInput = false,
            dateState = competitionDate
        )
        var isBrevet by remember {mutableStateOf(competitionData.value.isbrevet)}
        OutlinedCard(
            modifier = Modifier
                .selectable(
                    indication = null,
                    interactionSource = interactionSource,
                    selected = isCardSelected,
                    onClick = {
                        focusManager.clearFocus(true)
                        isCardSelected = !isCardSelected

                    }
                )
                .padding(bottom = 16.dp)
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(4.dp),
            border = if (isCardSelected) BorderStroke(2.dp, MyPrimary) else BorderStroke(1.dp, Color.DarkGray),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MyBackground
            ),
        ) {
            Row (
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(stringResource(R.string.brevet), fontSize = 16.sp)
                Switch(
                    checked = isBrevet,
                    onCheckedChange = {
                        isBrevet = it
                        competitionData.value = competitionData.value.copy(isbrevet = it)
                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = MyBackground,
                        checkedTrackColor = MyPrimary,
                        checkedBorderColor = MyPrimary,
                        uncheckedTrackColor = MyBackground
                    )
                )
            }
        }
        Text(
            modifier = Modifier.padding(bottom = 12.dp),
            text = stringResource(R.string.the_participants),
            fontSize = 16.sp,
            color = MyPrimary,
            fontFamily = FontFamily(listOf(Font(R.font.cairo_regular))),
        )
        EditableParticipantExposedDropdownMenu(
            participants = participants,
            modifier = Modifier
                .onFocusChanged { isCardSelected = false }
                .padding(bottom = 12.dp)
        ){
            if (!participantList.value.contains(it))
                participantList.value += it
        }

        ParticipantsContainer(
            participants = participantList,
            modifier = Modifier
                .onFocusChanged { isCardSelected = false }
                .padding(bottom = 12.dp)
        )
        LaunchedEffect(key1 = participantList.value) {
            competitionData.value= competitionData.value
                .copy(participants=Participants(participantList.value.map { Data(swimmerid = it.swimmerid) }))
        }
    }
}

@Composable
fun ActionsMenu(
    modifier: Modifier,
    isEditable: Boolean = true,
    expanded: MutableState<Boolean>,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit = { }
) {
    Box(modifier = modifier.background(MyBackground)){
        DropdownMenu(
            modifier = Modifier,
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            if (isEditable){
                DropdownMenuItem(
                    text = {  Text(stringResource(R.string.edit))  },
                    onClick = {
                        onEditClick.invoke()
                        expanded.value = false
                    },
                )
                HorizontalDivider()
            }

            DropdownMenuItem(
                text = { Text(stringResource(R.string.delete)) },
                onClick = { onDeleteClick.invoke() }
            )
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ParticipantsContainer(
    participants: MutableState<List<Swimmer>>,
    modifier: Modifier
) {

    OutlinedCard(
        modifier = modifier
            .padding(bottom = 16.dp)
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        shape = RoundedCornerShape(4.dp),
        border = if (participants.value.isNotEmpty()) BorderStroke(2.dp, MyPrimary) else BorderStroke(1.dp, Color.DarkGray),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MyBackground
        ),
    ){
        FlowRow (
            modifier = Modifier
                .padding(0.dp)
                .fillMaxWidth()
        ){
            participants.value.forEach{ participant ->
                Tag(
                    modifier=Modifier.padding(8.dp),
                    text = getFullName(participant.firstname,participant.lastname)
                ){
                    participants.value -= participant
                }
            }
        }
    }
}

@Composable
fun Tag(modifier: Modifier,text: String,onClick: () -> Unit) {
    Box(
        modifier = modifier
            .clickable { onClick.invoke() }
            .background(MyPrimary, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
    ){
        Row (
            modifier=Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                modifier = Modifier.padding(end = 8.dp),
                text = text,
                fontSize = 16.sp,
                color = MyBackground,
            )
            Icon(
                imageVector =  Icons.Default.Close ,
                contentDescription = stringResource(id = R.string.close),
                tint = MyBackground,
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

@Composable
private fun DialogHeader(competitionData: CompetitionData,onDismiss: () -> Unit, onDone: (CompetitionData) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .height(56.dp)
            .padding(bottom = 16.dp)
    ) {
        IconButton(onClick = { onDismiss.invoke() }) {
            Icon(imageVector = Icons.Filled.Close, contentDescription = stringResource(R.string.close))
        }
        Spacer(modifier = Modifier.width(24.dp))
        Text(
            text = stringResource(id = R.string.add_competition),
            style = MaterialTheme.typography.titleLarge,
            fontFamily = FontFamily(listOf(Font(R.font.cairo_semi_bold))),
        )
        Box(modifier = Modifier.fillMaxWidth()){
            OutlinedButton(
                modifier =  Modifier.align(Alignment.CenterEnd),
                onClick = {
                    onDone(competitionData)
                }
                ,
            ) {
                Text(text = stringResource(R.string.save), fontSize = 16.sp, color = MyPrimary)
            }
        }
    }
}

@Composable
fun ParticipantCard(modifier: Modifier, participant: Participant, onClick: () -> Unit) {
    OutlinedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, MySecondary)
    ){
        Row (
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            AsyncImage(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .size(65.dp)
                    .border(0.2.dp, Black, RoundedCornerShape(12.dp)),
                model =  participant.swimmer.pfpUrl ,
                error = painterResource(id = R.drawable.img),
                fallback =  painterResource(id = R.drawable.img),
                contentDescription = stringResource(R.string.profile_img),
                contentScale = ContentScale.Crop
            )
            Column  {
                Text(
                    text = getFullName(participant.swimmer.firstname,participant.swimmer.lastname),
                    fontWeight = FontWeight.Medium,
                    fontSize = 18.sp,
                    color = Black
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "مستوى "+ participant.swimmer.level.levelname +" ( ${calculateAge(participant.swimmer.birthday)} سنة)",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun CompetitionDetailsCard(modifier: Modifier, competition: Competition) {
    Row (
        modifier= modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(
                text = competition.event,
                color = Black,
                fontWeight = FontWeight.Medium,
                fontSize = 22.sp,
            )
            Text(
                text = competition.competitiondate.replace('-','/'),
                color = Color.Gray,
                fontSize = 16.sp,
            )
            Text(
                text = competition.location,
                color = Color.Gray,
                fontSize = 16.sp,
            )
        }
        val competitionBadge = if (competition.isbrevet) R.drawable.competition_badge1 else R.drawable.competition_badge
        Image(
            modifier = Modifier.height(50.dp),
            painter = painterResource(id = competitionBadge),
            contentDescription = "competition badge",
            contentScale = ContentScale.Crop
        )
    }
}

@ExperimentalMaterial3Api
@Composable
private fun CustomDatePickerDialog (
    state: DatePickerState,
    confirmButtonText: String = stringResource(R.string.ok),
    dismissButtonText: String = stringResource(R.string.cancel),
    onDismissRequest: () -> Unit,
    onConfirmButtonClicked: (Long?) -> Unit
) {
    DatePickerDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = { //OK
            TextButton(onClick = { onConfirmButtonClicked(state.selectedDateMillis) }) {
                Text(text = confirmButtonText)
            }
        },
        dismissButton = { //cancel
            TextButton(onClick = onDismissRequest) {
                Text(text = dismissButtonText)
            }
        },
        content = {
            DatePicker(
                state = state,
                showModeToggle = false,
                headline = null,
                title = null,
            )
        },
        colors = DatePickerDefaults.colors(
            containerColor = MyBackground,

            )
    )
}
@Composable
private fun DateTextField (
    modifier: Modifier = Modifier,
    text: String,
    trailingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit,
    isEnabled: Boolean = true,
    label: String,
    isIllegalInput: Boolean, //error state
    readOnly: Boolean,
) {
    OutlinedTextField(
        value = text,
        onValueChange = onChange,
        modifier = modifier.fillMaxWidth(),
        textStyle = TextStyle(fontSize = 18.sp),
        enabled = isEnabled,
        trailingIcon = trailingIcon,
        label = {
            Text(text = label, style = TextStyle(fontSize = 18.sp))
        },
        singleLine = true,
        isError = isIllegalInput,
        readOnly = readOnly,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = MyPrimary,
            focusedContainerColor = MyBackground ,
            unfocusedContainerColor = MyBackground ,
            focusedLabelColor = MyPrimary
        )
    )
}
@ExperimentalMaterial3Api
@Composable
fun CustomDatePicker (
    modifier: Modifier,
    datePickerState: DatePickerState,
    label: String,
    isIllegalInput: Boolean,
    dateState: MutableState<String>,
    competitionData: MutableState<CompetitionData>
) {
    val pattern = "dd/MM/yyyy"
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())
    val formattedDate = formatter.format(Date(datePickerState.selectedDateMillis!!))

    dateState.value = formattedDate
    val isOpen = rememberSaveable { mutableStateOf(false) }

    DateTextField(
        text = dateState.value,
        modifier = modifier,
        label = label,
        onChange = {
            dateState.value = it
        },
        trailingIcon = {
            IconButton(onClick = { isOpen.value = true}) {
                Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date Picker")
                if (isOpen.value) {
                    CustomDatePickerDialog(
                        state = datePickerState,
                        onConfirmButtonClicked = {
                            isOpen.value = false
                            if (it != null) {
                                dateState.value = formatter.format(Date(it))
                                competitionData.value = competitionData.value.copy(competitiondate = formatDate(dateState.value))

                            }
                        },
                        onDismissRequest = { isOpen.value = false },
                    )
                }
            }
        },
        isIllegalInput = isIllegalInput,
        readOnly = true
    )
}




@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun EditableParticipantExposedDropdownMenu(
    modifier: Modifier,
    participants: List<Swimmer>,
    onParticipantSelected: (Swimmer) -> Unit
    ) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var text by remember { mutableStateOf(TextFieldValue()) }
    val filteredOptions = participants.filter {
        getFullName(it.firstname,it.lastname).contains(text.text,ignoreCase = true)
    }
    val (allowExpanded, setExpanded) = remember { mutableStateOf(false) }
    val expanded = allowExpanded && filteredOptions.isNotEmpty()
    LaunchedEffect(expanded) {
        if (!expanded)
            keyboardController!!.hide()
    }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = setExpanded,
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(),
            value = text,
            onValueChange = { text = it },
            singleLine = true,
            label = { Text(stringResource(id = R.string.the_participator)) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded,
                )
            },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = MyPrimary,
                focusedContainerColor = MyBackground ,
                unfocusedContainerColor = MyBackground ,
                focusedLabelColor = MyPrimary
            ),
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(MyBackground),
            expanded = expanded,
            onDismissRequest = { setExpanded(false) },
        ) {
            filteredOptions.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            getFullName(option.firstname,option.lastname),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    },
                    onClick = {
                        onParticipantSelected(option)
                        text = TextFieldValue()
//                        text = TextFieldValue(text = getFullName(option.firstname,option.lastname), selection = TextRange(getFullName(option.firstname,option.lastname).length))
                        setExpanded(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    colors = MenuDefaults.itemColors(
                    )
                )
            }
        }
    }
}

@Composable
fun LevelCard(modifier: Modifier, title: String, onClick: () -> Unit) {
    ElevatedCard(
        onClick = { onClick.invoke() },
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = MySecondary
        ),
        elevation = CardDefaults.cardElevation(5.dp)
    ){
        Text(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            text = title,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            color = Black
        )
    }
}
@Composable
fun MyWeekCalendar(selectedDate : MutableState<LocalDate>){
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        val currentDate = remember { LocalDate.now() }
        val currentMonth = remember { YearMonth.now() }
        val startDate = remember { currentMonth.atStartOfMonth() } // Adjust as needed
        val endDate = remember { currentMonth.plusMonths(1).atEndOfMonth() } // Adjust as needed
        val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library


        val state = rememberWeekCalendarState(
            startDate = startDate,
            endDate = endDate,
            firstVisibleWeekDate = currentDate,
            firstDayOfWeek = firstDayOfWeek
        )
        selectedDate.value = currentDate
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp.dp *0.95f
        val itemDp = screenWidth/7

       Surface (color = MyPrimaryDark) {
           Column (
               modifier = Modifier.padding(10.dp)
           ){
               Text(
                   modifier = Modifier.padding(bottom = 25.dp),
                   text = getArabicDate(selectedDate.value),
                   fontFamily = FontFamily(listOf(Font(R.font.cairo_bold))),
                   fontSize = 18.sp,
                   color = MyBackground
               )
               WeekCalendar(
                   state = state,
                   calendarScrollPaged = false,
                   dayContent = {
                       Day(
                           day = it,
                           selected = it.date == selectedDate.value,
                           dp = itemDp
                       ){
                           selectedDate.value = it.date
                       }
                   }
               )
           }
       }
    }


}
@Composable
fun Day(day: WeekDay, selected: Boolean = false, dp: Dp,onClick: () -> Unit) {
    Box(
        modifier =
        Modifier
            .clickable { onClick() }
            .width(dp)
            .padding(end = 5.dp)
            .background(if (!selected) MyPrimaryDark else MyBackground, RoundedCornerShape(12.dp)),
//            .aspectRatio(1f), // This is important for square sizing!
        contentAlignment = Alignment.Center
    ) {
        Column (
            Modifier.padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val colorText = if (!selected) MyBackground else MyPrimaryDark
            Text(
                text = getArabicWeekDay(day.date.dayOfWeek.name),
                color = colorText,
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp,
                maxLines = 1,
                overflow = TextOverflow.Clip
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = day.date.dayOfMonth.toString(),
                color = colorText,
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
            )
//
        }
    }
}


@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun MySearchBar(
    text : MutableState<String> = mutableStateOf(""),
    onSearchClicked: (String) -> Unit = {},
    onTextChange: (String) -> Unit = {},
) {
    SearchBar(
        modifier = Modifier
            .fillMaxWidth(),
        query = text.value,
        onQueryChange = { onTextChange(it) },
        onSearch = {
            onSearchClicked(it)
        },
        active = false,
        onActiveChange = {},
        placeholder = { Text(text = stringResource(R.string.search)) },
        leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = stringResource(id = R.string.search)) },
        trailingIcon = {
            IconButton(onClick = { text.value = ""}) {
                Icon(imageVector = Icons.Default.Clear, contentDescription = "clear")
            }
        },
        shadowElevation = 10.dp,
        shape = SearchBarDefaults.inputFieldShape,
        windowInsets = WindowInsets(top = 0),
        colors = SearchBarDefaults.colors(containerColor = Color.White),
        content = {},
    )
}
@Composable
fun AbsenceSwimmerCard(
    modifier: Modifier,
    swimmer: Swimmer,
    absentList: MutableList<SwimmerId>,
    presentList: MutableList<String>,
    enabled: Boolean,
    onClick: () -> Unit,
){

    var selected by remember {
        mutableStateOf(false)
    }
    selected= absentList.contains(SwimmerId(swimmer.swimmerid))
    Box(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
    ){
        Surface(
            modifier = Modifier
                .padding(top = 65.dp)
                .align(Alignment.TopCenter),
            shape = RoundedCornerShape(12.dp),
            tonalElevation = 10.dp,
            shadowElevation = 10.dp,
            color = MySecondary,
        ) {
            Column (
                Modifier
                    .padding(top = 65.dp)
                    .fillMaxWidth()) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(bottom = 12.dp),
                    text = getFullName(swimmer.firstname,swimmer.lastname),
                    fontFamily = FontFamily(listOf(Font(R.font.cairo_bold))),
                    fontSize = 20.sp,
                    color = Color.Black
                )

                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 40.dp, bottom = 12.dp),
                    text = stringResource(R.string.age) + calculateAge(swimmer.birthday),
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                Text(
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(end = 40.dp, bottom = 12.dp),
                    text = stringResource(id = R.string.absence_number) + swimmer.totalAbsences.aggregate.count.toString(),
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                    color = Color.Black
                )
                val buttonColor = if (!selected) MyRed else MyBackground
                val disabledButtonColor = if (!selected) MyRed.copy(alpha = 0.5f) else MyBackground.copy(alpha = 0.5f)
                val buttonContainerColor = if (selected) MyRed else Color.White
                val disabledButtonContainerColor = if (selected) MyRed.copy(alpha = 0.5f) else Color.White.copy(0.5f)
                OutlinedButton(
                    modifier = Modifier
                        .padding(start = 40.dp, bottom = 40.dp)
                        .align(Alignment.Start),
                    onClick = {
                        selected = !selected
                        if (selected){
                            absentList.add(SwimmerId(swimmer.swimmerid))
                            presentList.remove(swimmer.swimmerid)
                        }else{
                            absentList.remove(SwimmerId(swimmer.swimmerid))
                            presentList.add(swimmer.swimmerid)
                        }
                              },
                    enabled = enabled,
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = buttonColor,
                        containerColor = buttonContainerColor,
                        disabledContentColor = disabledButtonColor,
                        disabledContainerColor = disabledButtonContainerColor
                    ),
                    border = BorderStroke(1.dp, buttonColor)
                ) {
                    Text(
                        text = stringResource(R.string.absent),
                        fontWeight = FontWeight.Normal,
                        fontSize = 18.sp,
                    )
                }
            }
        }
        AsyncImage(
            error = painterResource(id = R.drawable.img),
            fallback = painterResource(id = R.drawable.img),
            model =  swimmer.pfpUrl,
            modifier = Modifier
                .background(MySecondary, RoundedCornerShape(12.dp))
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.TopCenter)
                .size(125.dp),
            contentDescription = "swimmer image",
            contentScale = ContentScale.Crop
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownMenuParticipationType(
    modifier: Modifier,
    swimmingTypes: List<Eventtype>,
    onSelectItem: (Eventtype) -> Unit
){
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf(swimmingTypes[0].eventname) }
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {
        OutlinedTextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth(),
            value = text,
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = { Text(stringResource(id = R.string.the_participation)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(
                focusedBorderColor = MyPrimary,
                focusedContainerColor = MyBackground ,
                unfocusedContainerColor = MyBackground ,
                focusedLabelColor = MyPrimary
            ),
        )
        ExposedDropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .background(MyBackground),
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            swimmingTypes.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option.eventname, style = MaterialTheme.typography.bodyLarge) },
                    onClick = {
                        text = option.eventname
                        expanded = false
                        onSelectItem(option)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
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
//    onTitleClick: () -> Unit,
//    onTextColorClick: () -> Unit,
    onUnorderedListClick : () -> Unit
) {
    var boldSelected by rememberSaveable { mutableStateOf(false) }
    var italicSelected by rememberSaveable { mutableStateOf(false) }
    var underlineSelected by rememberSaveable { mutableStateOf(false) }
    var titleSelected by rememberSaveable { mutableStateOf(false) }
    var textColorSelected by rememberSaveable { mutableStateOf(false) }
    var unorderedListSelected by rememberSaveable { mutableStateOf(false) }
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
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
            selected = unorderedListSelected,
            onChangeClick = { unorderedListSelected = it },
            onClick = onUnorderedListClick
        ) {
            Icon(
                painter = painterResource(id =R.drawable.unordered_list ),
                contentDescription = "unorderedList Control",
                tint = MyPrimary
            )
        }
//        ControlWrapper(
//            selected = titleSelected,
//            onChangeClick = { titleSelected = it },
//            onClick = onTitleClick
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.format_title ),
//                contentDescription = "Title Control",
//                tint = MyPrimary
//            )
//        }
//        ControlWrapper(
//            selected = textColorSelected,
//            onChangeClick = { textColorSelected = it },
//            onClick = onTextColorClick
//        ) {
//            Icon(
//                painter = painterResource(id = R.drawable.format_text_xolor ),
//                contentDescription = "TextColor Control",
//                tint = MyPrimary
//            )
//        }
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
