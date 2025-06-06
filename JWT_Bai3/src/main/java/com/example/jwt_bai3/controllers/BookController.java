package com.example.jwt_bai3.controllers;

import com.example.jwt_bai3.entities.Book;
import com.example.jwt_bai3.entities.ResposeObject;
import com.example.jwt_bai3.services.BookServices;
import jakarta.annotation.security.RolesAllowed;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/books")
@RolesAllowed({"User","Admin"})
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookController {
    @Autowired
    BookServices bookServices;

    @GetMapping
    public ResponseEntity<ResposeObject> getBooks(){
        List<Book> books = bookServices.getBooks();

        if (books.isEmpty() || books == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResposeObject("204", "Không có sách nào trong hệ thống.", null));
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResposeObject("200","Lấy thành công danh sách các cuốn sách.",books));
    }
    @RolesAllowed({"Admin"})
    @PostMapping("/addBook")
    public ResponseEntity<ResposeObject> addBook(@RequestBody Book book){
        Book newBook = bookServices.postBook(book);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResposeObject("200","Thêm sách thành công.",newBook));
    }
    @RolesAllowed({"Admin"})
    @PutMapping("/updateBook")
    public ResponseEntity<ResposeObject> updateBook(@RequestBody Book book){
        boolean isUpdated = bookServices.updateBook(book);
        return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResposeObject("200","Sửa đôi thành công.",isUpdated));
    }
    @RolesAllowed({"Admin"})
    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<ResposeObject> deleteBook(@PathVariable Long id){
        boolean isDeleted = bookServices.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResposeObject("200","Xóa thành công",isDeleted));
    }
}
