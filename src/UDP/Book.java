/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package UDP;

import java.io.Serializable;

/**
 *
 * @author dinhh
 */
public class Book implements Serializable{

    public String id;
    public String title;
    public String author;
    public String isbn;
    public String publishDate;
    private static final long serialVersionUID = 20251107L;

    public Book(String id, String tilte, String author, String isbn, String publishDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishDate = publishDate;
    }
    
    
    
}
