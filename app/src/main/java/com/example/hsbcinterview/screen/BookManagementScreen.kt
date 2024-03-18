package com.example.hsbcinterview.screen
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hsbcinterview.room.Book
import com.example.hsbcinterview.viewmodel.BookViewModel


@Composable
fun BookManagementScreen(navController: NavHostController, viewModel: BookViewModel) {
    val books by viewModel.allBooks.collectAsState(emptyList())
    var selectedBook by remember { mutableStateOf<Book?>(null) }

    Column {
        Row(Modifier.padding(16.dp, 8.dp).fillMaxWidth()) {
            Button(onClick = { navController.navigate("addBook")  }, modifier = Modifier.weight(1f)) {
                Text(text = "增加")
            }
            Button(onClick = { navController.navigate("searchBook") }, modifier = Modifier.weight(1f)) {
                Text(text = "查找")
            }
        }
        if (books.isEmpty()) {
            Text(
                text = "数据库中暂无内容",
                modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.Center)
            )
        } else {
            Log.i("aaa",books.toString())
            LazyColumn(Modifier.fillMaxSize()) {
                items(books) { book ->
                     BookItem(book, onClick = {
                         Log.i("aaa","updata")
                         viewModel.setSelectedBook(book)
                         navController.navigate("updataBook")
                     }){
                         // 长按 BookItem 触发删除操作
                         selectedBook = book
                     }
                }
            }
        }

        // 确认删除弹窗
        selectedBook?.let { book ->
            AlertDialog(
                onDismissRequest = {
                    selectedBook = null
                },
                title = {
                    Text("确认删除")
                },
                text = {
                    Text("确定要删除编号 ${book.id} 吗？")
                },
                confirmButton = {
                    Button(onClick = {
                        viewModel.deleteBook(book)
                        selectedBook = null
                    }) {
                        Text("确定")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        selectedBook = null
                    }) {
                        Text("取消")
                    }
                }
            )
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Composable
fun BookItem(book: Book,onClick:() -> Unit,onLongPress: () -> Unit) {
    Card(Modifier.padding(16.dp, 8.dp).fillMaxWidth().pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                onClick()
            },
            onPress = { /* 暂时无操作 */ },
            onLongPress = {
                onLongPress()
            }
        )
    }) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "书籍ID: ${book.id}", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "书名: ${book.title}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "作者: ${book.author}")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "详情: ${book.details}")
        }
    }
}