package com.example.hsbcinterview.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hsbcinterview.room.Book
import com.example.hsbcinterview.viewmodel.BookViewModel

@Composable
fun UpdataScreen (navController: NavHostController, viewModel: BookViewModel,selectedBook:Book){
    val context = LocalContext.current
    var details by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        TitleBar(title = "修改一本新书"){
            navController.popBackStack()
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = selectedBook.id.toString(),
            onValueChange = {  },
            readOnly = true, // 将此属性设置为true
            label = { Text("编号") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = selectedBook.title,
            onValueChange = {  },
            readOnly = true, // 将此属性设置为true
            label = { Text("书名") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = selectedBook.author,
            onValueChange = { },
            readOnly = true, // 将此属性设置为true
            label = { Text("作者") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = details,
            onValueChange = { details = it },
            label = { Text("详情") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(modifier = Modifier.fillMaxWidth(),onClick = {
            if (details.isNotBlank()) {
                val book = Book(selectedBook.id, selectedBook.author, selectedBook.title, details)
                viewModel.updateBook(book)
                navController.popBackStack()
            } else {
                showToast(context, "请输入内容")
            }
        }) {
            Text("修改")
        }
    }


}