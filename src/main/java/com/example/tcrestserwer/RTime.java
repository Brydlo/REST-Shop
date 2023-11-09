package com.example.tcrestserwer;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Path("/dt")
public class RTime {
//    Obiekt tej klasy jest tworzony za każdym razem do obsługi każdego pojedynczego zapytania.
    private final LocalDateTime dt = LocalDateTime.now();
    {
        System.out.println("Jest tworzony obiekt Rtime : dt = " + dt);
    }

    @GET
    public String getDataTime() {
        return dt.toString();
    }
//    metody poniżej znajdują się pod adresem /dt/...
    @GET
    @Path("/date")
    public String getData() {
        return dt.toLocalDate().toString();
    }
    @GET
    @Path("/time")
    public String getTime() {
        return dt.toLocalTime().toString();
    }

    @GET
    @Path("/time/second")
    public int getSecond() {
        return dt.getSecond();
    }

}
