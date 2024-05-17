package com.midterm.jetpacknote

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.midterm.jetpacknote.ui.theme.JetPackNoteTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.lifecycle.viewmodel.compose.viewModel
import com.midterm.jetpacknote.model.Note
import com.midterm.jetpacknote.viewmodel.NoteViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackNoteTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val noteViewModel = viewModel<NoteViewModel>()

                    Column(
                        modifier = Modifier
                            .padding(5.dp)
                            .fillMaxWidth()
                    ) {
                        topBar()
                        mainForm(noteViewModel = noteViewModel)
                        Divider(modifier = Modifier.padding(10.dp))
                        listOfNote(noteViewModel = noteViewModel)
                    }
                }
            }
        }
    }

    @Composable
    fun listOfNote(noteViewModel : NoteViewModel) {
        val data by noteViewModel.noteList.collectAsState()
        LazyColumn {
            items(data) {
                items ->
                Surface (modifier = Modifier
                    .padding(5.dp)
                    .clip(RoundedCornerShape(topStart = 30.dp, bottomEnd = 30.dp))
                    .fillMaxWidth(),
                    color = Color(0x873e23)
                ){
                    Row {
                        Column (modifier = Modifier
                            .padding(10.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                modifier = Modifier.padding(3.dp),
                                text = items.title,
                                style = MaterialTheme.typography.titleLarge
                            )
                            Text(
                                modifier = Modifier.padding(3.dp),
                                text = items.description,
                                style = MaterialTheme.typography.titleMedium
                            )
                            Text(
                                modifier = Modifier.padding(3.dp),
                                text = SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z").format(items.entryDate),
                                style = MaterialTheme.typography.labelMedium
                            )
                        }
                    }
                }
            }
        }

    }

    @Composable
    fun topBar() {
        Surface(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(50.dp),
            color = Color(0x873e23)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(start = 10.dp)
            ) {
                Text(
                    text = "Notes",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notification"
                    )
                }
            }
        }
    }

    @Composable
    fun mainForm(noteViewModel: NoteViewModel) {

        val title = remember {
            mutableStateOf("")
        }

        val content = remember {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            TextField(
                value = title.value,
                modifier = Modifier.padding(5.dp),
                onValueChange = { text ->
                    title.value = text
                },
                label = { Text(text = "Title")},
            )
            TextField(
                value = content.value,
                modifier = Modifier.padding(5.dp),
                onValueChange = { text ->
                    content.value = text
                },
                label = { Text(text = "Add a note") }
            )
            Button(
                onClick = {
                    noteViewModel.addNote(
                        Note(title = title.value,
                            description = content.value,
                            entryDate = Date()
                        )
                    )
                },
                modifier = Modifier.padding(5.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(text = "Save")
            }
        }
    }

    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        Text(
            text = "Hello $name!",
            modifier = modifier
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        JetPackNoteTheme {
            Greeting("Android")
        }
    }
}