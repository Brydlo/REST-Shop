package com.example.tcrestserwer;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import sklep.db.DBConnection;
import sklep.db.DBException;
import sklep.db.ProductDAO;
import sklep.db.RecordNotFound;
import sklep.model.Product;

@Path("/products.html")
public class RHtmlZaPomocaWritera {
//    Ta klasa służy do przetestowania własnego MessageBodyWritera, który potrafi zamieniać pojedyncze produkty na HTML
    @GET
    @Produces("text/html")
    @Path("{id}")
    public Product readOne(@PathParam("id") int productId) throws DBException, RecordNotFound {
        try(DBConnection db = DBConnection.open()) {
            ProductDAO productDAO = db.productDAO();
            return productDAO.findById(productId);
        }
    }
}
