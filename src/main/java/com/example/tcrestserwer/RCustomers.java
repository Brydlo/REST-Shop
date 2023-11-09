package com.example.tcrestserwer;

import java.net.URI;
import java.util.List;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
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

    @POST
    public Response save(final Customer customer) throws DBException {
        try (DBConnection db = DBConnection.open()) {
            CustomerDAO customerDAO = db.customerDAO();
            customerDAO.save(customer);
            db.commit();
            URI uri = UriBuilder
                    .fromResource(RCustomers.class)
                    .path(String.valueOf(customer.getEmail()))
                    .build();
            return Response.created(uri).build();
        }
    }
}


