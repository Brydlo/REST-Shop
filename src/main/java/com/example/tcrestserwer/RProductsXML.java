package com.example.tcrestserwer;

import jakarta.ws.rs.*;
import sklep.db.*;
import sklep.model.Product;

import java.math.BigDecimal;
import java.util.List;

@Path("/products.xml")
@Produces("application/xml")
@Consumes("application/xml")
public class RProductsXML {

    @GET
    public List<Product> readAll() throws DBException {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            return productDAO.readAll();
        }
    }

    @GET
    @Path("/{id}")
    public Product readOne(@PathParam("id") int productId) throws DBException, RecordNotFound {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            return productDAO.findById(productId);
        }
    }

    @POST
    // ta metoda zadziałą jeśli klient wyśle zapytanie typu post
    /*
    W metodach tego Typu (PUT i POST) powinien znajdować się dokładnie jeden
    parametr nieoznaczony żadną adnotacją.
    Do tego parametru zoastanie przekazana wartość utworzona na podstawie
    treści zapytania (content  / body / entity).
    W adnotacji @Consumes określamy format, w jakim te dane mają być przysłane.
     */
    public void saveProduct(Product product) throws DBException {
        try (DBConnection db = DBConnection.open()) {
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
    public BigDecimal getPrice(@PathParam("id") int productId) throws DBException, RecordNotFound {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            return productDAO.findById(productId).getPrice();
        }
    }


    // Metoda PUT służy w HTTP do zapisywania danych DOKŁADNIE POD PODANYM ADRESEM
    @PUT
    @Path("/{id}/price")
    public void setPrice(@PathParam("id") int productId, BigDecimal newPrice) throws DBException, RecordNotFound {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            Product product = productDAO.findById(productId);
            product.setPrice(newPrice);
            productDAO.update(product);
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
