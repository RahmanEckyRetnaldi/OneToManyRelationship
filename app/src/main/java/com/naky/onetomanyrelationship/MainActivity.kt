package com.naky.onetomanyrelationship

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.naky.onetomanyrelationship.entity.Library
import com.naky.onetomanyrelationship.entity.User
import com.naky.onetomanyrelationship.entity.UserAndLibrary
import com.naky.onetomanyrelationship.ui.theme.OneToManyRelationshipTheme
import com.naky.onetomanyrelationship.viewmodel.UserViewModel
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OneToManyRelationshipTheme {
                // A surface container using the 'background' color from the theme
                OneToManyDb()
            }
        }
    }
}

val userData = listOf(
    User(1,"User 1",22),
    User(2,"User 2",22),
    User(3,"User 3",22),
    User(4,"User 4",22),
    User(5,"User 5",22),
)

val libraryData = listOf(
    Library(1,"Library 1",1),
    Library(2,"Library 2",2),
    Library(3,"Library 3",1),
    Library(4,"Library 4",2),
    Library(5,"Library 5",3),
    Library(6,"Library 6",4),
    Library(7,"Library 7",4),
    Library(8,"Library 8",5),
)

@Composable
fun OneToManyDb(
    modifier: Modifier = Modifier
){
    val scope = rememberCoroutineScope()
    val contex = LocalContext.current
    val userViewModel : UserViewModel = viewModel(
        factory = ViewModelProvider.AndroidViewModelFactory(contex.applicationContext as Application)
    )

    //add user
    userViewModel.addUser(userData)

    //add library
    userViewModel.addLibrary(libraryData)

    val getUserrecord = userViewModel.readAllData.observeAsState(listOf()).value

    val userId = remember {
        mutableStateOf("")
    }

    Scaffold(
        topBar = {
            Column(
                modifier = modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "One to One Relationship",
                    style = MaterialTheme.typography.h1.copy(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(600)
                    )
                )
            }
        }
    ) {
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            OutlinedTextField(
                value = userId.value,
                onValueChange = {
                    userId.value = it
                },
                label = { Text(text = "Enter User Id") },
                singleLine = true,
                modifier = modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(25.dp))
            Button(
                onClick = {
                    scope.launch {
                        userViewModel.getUser(userId.value.toInt())
                    }
                },
                modifier = modifier
                    .fillMaxWidth(0.5f)
                    .height(44.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Start Collect")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "User id",
                    modifier = modifier.fillMaxWidth(0.3f),
                    color = Color.White,
                    fontWeight = FontWeight(500)
                )
                Text(
                    text = "User Name",
                    modifier = modifier.fillMaxWidth(0.3f),
                    color = Color.White,
                    fontWeight = FontWeight(500)
                )
                Text(
                    text = "Age",
                    modifier = modifier.fillMaxWidth(0.3f),
                    color = Color.White,
                    fontWeight = FontWeight(500)
                )
            }

            if(getUserrecord.isNotEmpty()){
                LazyColumn{
                    items(getUserrecord.size){
                        index ->
                        UserDataList(userRecord = getUserrecord[index])
                    }
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .padding(15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Library id",
                                modifier = modifier.fillMaxWidth(0.3f),
                                color = Color.White,
                                fontWeight = FontWeight(500)
                            )
                            Text(
                                text = "Title",
                                modifier = modifier.fillMaxWidth(0.3f),
                                color = Color.White,
                                fontWeight = FontWeight(500)
                            )
                        }
                    }

                    items(getUserrecord[0].library.size) { index ->
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .background(Color.LightGray)
                                .padding(15.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = getUserrecord[0].library[index].id.toString(),
                                modifier = modifier.fillMaxWidth(0.3f),
                                color = Color.White,
                                fontWeight = FontWeight(500)
                            )
                            Text(
                                text = getUserrecord[0].library[index].title,
                                modifier = modifier.fillMaxWidth(0.3f),
                                color = Color.White,
                                fontWeight = FontWeight(500)
                            )
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun UserDataList(
    modifier: Modifier = Modifier,
    userRecord : UserAndLibrary
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Blue)
            .padding(15.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = userRecord.user.userId.toString(),
            modifier = modifier.fillMaxWidth(0.3f),
            color = Color.White,
            fontWeight = FontWeight(500)
        )
        Text(
            text = userRecord.user.name,
            modifier = modifier.fillMaxWidth(0.3f),
            color = Color.White,
            fontWeight = FontWeight(500)
        )
        Text(
            text = userRecord.user.age.toString(),
            modifier = modifier.fillMaxWidth(0.3f),
            color = Color.White,
            fontWeight = FontWeight(500)
        )
    }
}