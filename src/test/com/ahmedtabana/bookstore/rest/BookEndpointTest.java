package com.ahmedtabana.bookstore.rest;

import com.ahmedtabana.bookstore.model.Book;
import com.ahmedtabana.bookstore.model.Language;
import com.ahmedtabana.bookstore.repository.BookRepository;
import com.ahmedtabana.bookstore.util.IsbnGenerator;
import com.ahmedtabana.bookstore.util.NumberGenerator;
import com.ahmedtabana.bookstore.util.TextUtil;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
@RunAsClient
public class BookEndpointTest {

    @Deployment(testable = false)
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addClass(BookRepository.class)
                .addClass(Book.class)
                .addClass(TextUtil.class)
                .addClass(Language.class)
                .addClass(NumberGenerator.class)
                .addClass(IsbnGenerator.class)
                .addClass(BookEndpoint.class)
                .addClass(JAXRSConfiguration.class)
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
                .addAsManifestResource("META-INF/test-persistence.xml", "persistence.xml");

    }
    @Test
    public void createBook( WebTarget webTarget) {

        // Test counting books
        Response response = webTarget.path("api/books/count").request().get();
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // Test find all
        response = webTarget.path("api/books").request(MediaType.APPLICATION_JSON_TYPE).get();
        assertEquals(Status.NO_CONTENT.getStatusCode(), response.getStatus());

        // Create book
        Book book = new Book("isbn", "a   title", 12F, 123, Language.ENGLISH, new Date(), "imageURL", "description");
        response = webTarget.path("api/books").request(MediaType.APPLICATION_JSON_TYPE).post(Entity.entity(book, MediaType.APPLICATION_JSON_TYPE));
        assertEquals(Status.CREATED.getStatusCode(), response.getStatus());

    }
}
