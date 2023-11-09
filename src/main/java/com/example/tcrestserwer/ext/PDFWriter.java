package com.example.tcrestserwer.ext;


import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import jakarta.servlet.ServletContext;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.MessageBodyWriter;
import jakarta.ws.rs.ext.Provider;
import sklep.model.Product;
import sklep.model.ProductList;

@Provider
public class PDFWriter implements MessageBodyWriter<Object> {
    private static final MediaType PDF_TYPE = new MediaType("application", "pdf");

    @Context
    private ServletContext servletContext;

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return (type == ProductList.class || type == Product.class) && PDF_TYPE.isCompatible(mediaType);
    }

    @Override
    public void writeTo(Object obj, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType,
                        MultivaluedMap<String, Object> httpHeaders, OutputStream output)
            throws IOException, WebApplicationException {

        String fileName = "products.pdf";
        if (obj instanceof Product) {
            Product product = (Product) obj;
            // fileName = product.getProductName().replace(' ', '_') + ".pdf";
            fileName = String.format("product%04d.pdf", product.getProductId());
        }
        // httpHeaders.add("Content-Disposition", "attachment;filename=" + fileName);
        httpHeaders.add("Content-Disposition", "inline;filename=" + fileName);
        ObslugaXSL obslugaXSL = new ObslugaXSL(servletContext);
        obslugaXSL.wypiszPDF(obj, output);

    }

}


