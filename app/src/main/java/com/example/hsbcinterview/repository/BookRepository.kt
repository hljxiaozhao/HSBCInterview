package com.example.hsbcinterview.repository

import com.example.hsbcinterview.room.Book
import com.example.hsbcinterview.room.BookDao
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    
    val allBooks: Flow<List<Book>> = bookDao.getAllBooks()
    
    fun getBookById(id: Int): Flow<Book> {
        return bookDao.findBookById(id)
    }
    
    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }
    
    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }
    
    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }
}