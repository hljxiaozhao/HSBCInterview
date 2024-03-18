@file:Suppress("NAME_SHADOWING")

package com.example.hsbcinterview.screen

import android.content.Context
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hsbcinterview.room.Book
import com.example.hsbcinterview.viewmodel.BookViewModel

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext

@Composable
fun AddBookScreen(navController: NavHostController, viewModel: BookViewModel) {
    val context = LocalContext.current
    var id by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var details by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        TitleBar(title = "创建一本新书"){
            navController.popBackStack()
        }

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = id,
            onValueChange = { if (it.all { char -> char.isDigit() }) {
                id = it
            } },
            label = { Text("编号") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = { title = it },
            label = { Text("书名") }
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = author,
            onValueChange = { author = it },
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
            if (id.isNotBlank() && title.isNotBlank() && author.isNotBlank() && details.isNotBlank()) {
                val book = Book(id.toInt(), title, author, details)
                viewModel.insertBook(book)
                navController.popBackStack()
            } else {
                showToast(context, "请输入内容")
            }
        }) {
            Text("添加")
        }
    }


}
fun showToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}


@Composable
fun TitleBar(title: String, onBackClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding( top = 8.dp,  bottom = 8.dp)
    ) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.clickable { onBackClick() })
        Text(
            text = title,
        )
    }
}