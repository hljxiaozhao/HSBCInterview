package com.example.hsbcinterview.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hsbcinterview.repository.BookRepository
import com.example.hsbcinterview.room.Book
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    val allBooks: Flow<List<Book>> = bookRepository.allBooks

    fun getBookById(id: Int): Flow<Book> {
        return bookRepository.getBookById(id)
    }
    private val _selectedBook = MutableLiveData<Book>()
    val selectedBook: LiveData<Book> get() = _selectedBook

    fun setSelectedBook(book: Book) {
        _selectedBook.value = book
    }

    private val _bookResult = MutableStateFlow<Book?>(null)
    val bookResult: StateFlow<Book?> = _bookResult

    fun searchBookById(id: Int) {
        viewModelScope.launch {
            val book = getBookByIdFromRepository(id)
            _bookResult.value = book
        }
    }

    private suspend fun getBookByIdFromRepository(id: Int): Book? {
        return bookRepository.getBookById(id).firstOrNull()
    }

    fun insertBook(book: Book) {
        viewModelScope.launch {
            bookRepository.insertBook(book)
        }
    }
    
    fun updateBook(book: Book) {
        viewModelScope.launch {
            bookRepository.updateBook(book)
        }
    }
    
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            bookRepository.deleteBook(book)
        }
    }
}