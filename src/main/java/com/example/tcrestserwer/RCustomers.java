package com.example.tcrestserwer;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import sklep.db.CustomerDAO;
import sklep.db.DBConnection;
import sklep.db.DBException;
import sklep.db.RecordNotFound;
import sklep.model.Customer;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class RCustomers {

    @GET
    @Path("/{id}")
    public Customer findById(@PathParam("id") final String email) throws DBException, RecordNotFound {
        try(DBConnection db = DBConnection.open()) {
            CustomerDAO customerDAO = db.customerDAO();
            return customerDAO.findByEmail(email);
        }
    }

    @GET
    public List<Customer> listAll() throws DBException {
        try(DBConnection db = DBConnection.open()) {
            CustomerDAO customerDAO = db.customerDAO();
            return customerDAO.readAll();
        }
    }
}


