package com.sangjae.section01.autowired.subsection03.setter;

import com.sangjae.section01.autowired.common.BookDAO;
import com.sangjae.section01.autowired.common.BookDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

// @Service 를 붙혀 component scan 을 통해 bean 등록
@Service("bookServiceSetter")
public class BookService {

    private BookDAO bookDAO;

    @Autowired // setter 주입
    public void setBookDAO(BookDAO bookDAO){
        this.bookDAO = bookDAO;
    };

    public List<BookDTO> selectAllBooks(){
        return bookDAO.selectBookList();
    }

    public BookDTO searchBookBySequence(int sequence){
        return bookDAO.selectOneBook(sequence);
    }
}
