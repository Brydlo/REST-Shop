package com.example.tcrestserwer;

import java.util.List;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import sklep.db.DBConnection;
import sklep.db.DBException;
import sklep.db.OrderDAO;
import sklep.db.RecordNotFound;
import sklep.model.Order;

@Path("/orders")
@Produces(MediaType.APPLICATION_JSON)
public class ROrders {
    @GET
    public List<Order> listAll() throws DBException {
        try(DBConnection db = DBConnection.open()) {
            OrderDAO orderDAO = db.orderDAO();
            return orderDAO.readAll();
        }
    }

    @GET
    @Path("/{id:[0-9]+}")
    public Response findById(@PathParam("id") final Integer id) {
        // Klasa Response pozwala nam z pełną precyzją przygotować odpowiedź, która ma zostać odesłana klientowi.
        // W przypadku pozytywnym (ok) zostanie odesłany obiekt przetłumaczony na XML lub JSON, a kod wynikowy to będzie 200.
        // Ale w przypadku błędów możemy sami zdecydować co odsyłami (tutaj odpowiednie kody HTTP).
        try(DBConnection db = DBConnection.open()) {
            OrderDAO orderDAO = db.orderDAO();
            Order order = orderDAO.findById(id);
            return Response.ok(order).build();
        } catch (DBException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } catch (RecordNotFound e) {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

}



