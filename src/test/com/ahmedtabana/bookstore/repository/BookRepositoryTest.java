package com.ahmedtabana.bookstore.repository;

import com.ahmedtabana.bookstore.model.Book;
import com.ahmedtabana.bookstore.util.IsbnGenerator;
import com.ahmedtabana.bookstore.util.NumberGenerator;
import com.ahmedtabana.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class BookRepositoryTest {

    @Inject
    private BookRepository bookRepository;

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(TextUtil.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");

    }

    @Test(expected = Exception.class)
    public void findWithInvalidId(){
        bookRepository.find(null);
    }
    @Test(expected = Exception.class)
    public void createInvalidBook(){
        //Book book = new Book("isbn", "a   title", 12F, 123, Language.ENGLISH, new Date(), "imageURL", "description");
       Book book = new Book("isbn", null, 12F, 123, "ENGLISH", new Date(), "imageURL", "description");

       bookRepository.create(book);
    }
    @Test
    public void create() throws Exception{

        // Test counting books
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());

        // Create a book
        Book book = new Book("isbn", "a   title", 12F, 123,  "ENGLISH", new Date(), "imageURL", "description");
        bookRepository.create(book);
        Long bookId = book.getId();

        // Check created book
        assertNotNull(bookId);

        assertEquals(book.getTitle(),"a title");
        assertTrue(book.getIsbn().startsWith("13"));
        // Find created book
        Book bookFound = bookRepository.find(bookId);

        // Test counting books
        assertEquals(Long.valueOf(1), bookRepository.countAll());
        assertEquals(1, bookRepository.findAll().size());

        // Delete the book
        bookRepository.delete(bookId);
        // Test counting books
        assertEquals(Long.valueOf(0), bookRepository.countAll());
        assertEquals(0, bookRepository.findAll().size());

    }
}
