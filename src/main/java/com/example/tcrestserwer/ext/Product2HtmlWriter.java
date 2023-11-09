package com.example.tcrestserwer.ext;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import sklep.model.Product;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import static java.lang.System.out;

@Provider
public class Product2HtmlWriter implements MessageBodyWriter<Product> {
    @Override
    public boolean isWriteable(Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType) {
//        na podstawie informacji odczytanych z kodu związanego z metodą zwracającą wynik mamy odpowiedzieć na pytanie
//        czy ten writer sobie poradzi
        return type == Product.class && mediaType.isCompatible(MediaType.TEXT_HTML_TYPE);
    }

    @Override
    public void writeTo(Product product, Class<?> aClass, Type type, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream outputStream)
                            throws IOException, WebApplicationException {
//        Dla konkretnego obiektu mamy go wypisać w podanym formacie przez przekazany nam OutputStream
        String html = "<!DOCTYPE html>\n<html><body>" + product.toHtml() + "</body></html>";
        httpHeaders.add("Content-Type", "text/html;charset=utf-8");
        out.write(html.getBytes());
    }
}
