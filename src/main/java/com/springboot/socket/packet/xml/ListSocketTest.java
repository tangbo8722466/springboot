package com.springboot.socket.packet.xml;

import java.util.ArrayList;
import java.util.List;

public class ListSocketTest {
    public static void main(String[] args) {
        objectToXml();
        xmlToObject();
    }

    public static void objectToXml() {
        ListSocket listSocket = new ListSocket();
        listSocket.setName("张三");
        listSocket.setAge("26");
        List<Book> books = new ArrayList<Book>();
        Book book1 = new Book();
        book1.setAuthor("作者1");
        book1.setTime("2014-12-28");
        book1.setBookName("How to use JAXB");
        Book book2 = new Book();
        book2.setAuthor("作者2");
        book2.setTime("2014-06-06");
        book2.setBookName("How to use SOCKET");
        books.add(book1);
        books.add(book2);
        listSocket.setBooks(books);
        String xml = JaxbUtil.convertToXml(listSocket);
        System.out.println(xml);
    }

    public static void xmlToObject() {
        String xml = "<?xml version=\"1.0\" encoding=\"GBK\"?><ROOT><Name>张三</Name><Age>26</Age><ROWS><LIST>"
                + "<BookName>How to use JAXB</BookName><Time>2014-12-28</Time><Author>作者1</Author>"
                + "</LIST><LIST><BookName>How to use SOCKET</BookName><Time>2014-06-06</Time><Author>作者2</Author></LIST></ROWS></ROOT>";
        ListSocket listSocket = JaxbUtil.converyToJavaBean(xml, ListSocket.class);
        System.out.println(listSocket);
    }
}