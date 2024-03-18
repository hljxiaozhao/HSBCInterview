package com.example.hsbcinterview.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hsbcinterview.viewmodel.BookViewModel

@Composable
fun SearchPage (navController: NavHostController, viewModel: BookViewModel){
    var searchText by remember { mutableStateOf("") }
    val book by viewModel.bookResult.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Row (modifier = Modifier.padding(5.dp),verticalAlignment = Alignment.CenterVertically){
            Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.weight(1f).clickable { navController.popBackStack() })
            OutlinedTextField(
                value = searchText,
                onValueChange = {
                    if (it.all { char -> char.isDigit() }) {
                        searchText = it
                    }
                },
                label = { Text("请输入搜索内容") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .weight(6f)
            )

            Button(
                onClick = {
                    if (searchText.isNotBlank()) {
                        viewModel.searchBookById(searchText.toInt())
                        searchText = ""
                    }
                },
                modifier = Modifier.weight(2f)
            ) {
                Text("搜索")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))


        if (book!=null){
            Card(
                Modifier
                    .padding(16.dp, 8.dp)
                    .fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "书籍ID: ${book?.id}", fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = "书名: ${book?.title}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "作者: ${book?.author}")
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "详情: ${book?.details}")
                }
            }
        }else{
            Text(text = "暂无书籍")
        }

    }
}

