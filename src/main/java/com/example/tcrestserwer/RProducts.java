package com.example.tcrestserwer;

import java.math.BigDecimal;
import java.util.List;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import sklep.db.*;
import sklep.model.Product;
import sklep.model.ProductList;

@Path("/products")
public class RProducts {
/*
    @GET
    @Produces({"application/json", "application/xml", "text/plain"}) // WildFly zwraca jako domyślny pierwszy wpisany paramtr
    public List<Product> readAll() throws DBException {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            return productDAO.readAll();
        }
    }
  */
    @GET
    @Produces({"application/xml", "text/plain"})
    public ProductList readAll() throws DBException {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            return new ProductList(productDAO.readAll());
        }
    }

    // Żeby w JSON nie było dodatkowego poziomu w strukturze, zwracam bezpośrednio listę rekordów:
    @GET
    @Produces({"application/json"})
    public List<Product> readAllJSON() throws DBException {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            return productDAO.readAll();
        }
    }

    // Może też być tak, że kilka metod działa pod tym samym adresem, ale służą one do tworzenia odpowiedzi w różnych formatach.
    // Przykład: tworzenie HTML w oddzielnej metodzie
    @GET
    @Produces("text/html;charset=UTF-8")
    public String readAllHTML() throws DBException {
        List<Product> products = readAll().getProducts();
        StringBuilder txt = new StringBuilder("<!DOCTYPE html>\n<html><body>\n");
        txt.append("<h1>Lista produktów</h1>\n");
        for(Product product : products) {
            txt.append(product.toHtml()).append('\n');
        }
        txt.append("</body></html>");
        return txt.toString();
    }

    @GET
    @Path("/{id}")
    @Produces({"application/json", "application/xml", "text/plain"})
    public Product readOne(@PathParam("id") int productId) throws DBException, RecordNotFound {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            return productDAO.findById(productId);
        }
    }

    @POST
    @Consumes({"application/json", "application/xml"})
    // W metodach typu POST i PUT powinien znajdować się dokładnie jeden parametr nieozanczony żadną adnotacją.
    // Do tego parametru zostanie przekazana wartość utworzona na podstawie treści zapytania (content / body / entity).
    // W adnotacji @Consumes określamy format, w jakim te dane mają być przysłane.
    public void saveProduct(Product product) throws DBException {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            productDAO.save(product);
            db.commit();
        }
    }


    // Ta metoda zwraca wartość wybranego pola w rekordzie.
    // W praktyce rzadko kiedy twozy się takie metody, ale gdybyśmy wiedzieli, że klient akurat takiej rzeczy może potrzebować,
    // to można taką dodatkową meotdę stworzyć.
    // Właściwą strukturą adresu będzie wtedy np. products/3/price
    @GET
    @Path("/{id}/price")
    @Produces({"application/json", "text/plain"})
    public BigDecimal getPrice(@PathParam("id") int productId) throws DBException, RecordNotFound {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            return productDAO.findById(productId).getPrice();
        }
    }

    // Metoda PUT służy w HTTP do zapisywania danych DOKŁADNIE POD PODANYM ADRESEM
    @PUT
    @Path("/{id}/price")
    @Consumes({"application/json", "text/plain"})
    public void setPrice(@PathParam("id") int productId, BigDecimal newPrice) throws DBException, RecordNotFound {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            Product product = productDAO.findById(productId);
            product.setPrice(newPrice);
            productDAO.update(product);
            db.commit();
        }
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int productId) throws DBException {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            productDAO.delete(productId);
            db.commit();
        }
    }

    @GET
    @Path("/{id}/photo")
    @Produces("image/jpeg")
    public byte[] getPhoto(@PathParam("id") int productId) throws DBException, RecordNotFound {
        return PhotoUtil.readBytes(productId);
    }
}


