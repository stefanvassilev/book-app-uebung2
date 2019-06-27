package com.ovgu.book.server.bookserver.repository;

import com.ovgu.book.server.bookserver.entity.BookEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BookRepository extends PagingAndSortingRepository<BookEntity, String> {

    Page<BookEntity> findAllByAuthor(String author, Pageable pageable);
}
