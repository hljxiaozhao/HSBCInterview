package com.example.hsbcinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hsbcinterview.room.BookDatabase
import com.example.hsbcinterview.repository.BookRepository
import com.example.hsbcinterview.room.Book
import com.example.hsbcinterview.screen.AddBookScreen
import com.example.hsbcinterview.viewmodel.BookViewModel
import com.example.hsbcinterview.screen.BookManagementScreen
import com.example.hsbcinterview.screen.SearchPage
import com.example.hsbcinterview.screen.UpdataScreen

class MainActivity : ComponentActivity() {

    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory(BookRepository(BookDatabase.getDatabase(applicationContext).bookDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "main") {
                composable("main") {
                    BookManagementScreen(navController =navController,viewModel = bookViewModel)
                }
                composable("addBook") {
                    AddBookScreen(navController =navController,viewModel = bookViewModel)
                }
                composable("searchBook") {
                    SearchPage(navController =navController,viewModel = bookViewModel)
                }
                composable("updataBook") {
                    val selectedBook = bookViewModel.selectedBook.value
                    selectedBook?.let { it1 -> UpdataScreen(navController =navController,viewModel = bookViewModel,selectedBook = it1) }
                }
            }
        }
    }
}


@Suppress("UNCHECKED_CAST")
class BookViewModelFactory(private val repository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}