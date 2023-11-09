package com.example.tcrestserwer;
//Aplikacja RESTowa
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
/**
 * Ten Projekt jest zrobiony w technologii JAX-RS - implementacja usług RESTowych
 * w ramach Java EE

 * Ta klasa pełni rolę "aktywatora" tej technologii na serwerze. Gdy serwer
 * (Wildfly/ Glassfish / WebLogic) zauważy w projekcie taką klasę, to wie,
 * że ma skonfigurować usługę REST pod podanym adresem.

 * Adres / (albo pusty string) oznacza, że cały projekt jest "jedną wielką aplikacją RESTową"
 * ale można też podać jakiś podkatalog na serwerze.

 *   Domyślnie w skład aplikacji RESTowej będą wchodzić wszystkie klasy w projekcie,
 *   które mają adnotację @Path (+ rozszerzenia @Provider i @Interceptor, ale to inny temat...)

 *   To zachowanie można zmienić nadpisując metody getClasses i getSingletons
 *   - pokażę to w innym projekcie...
 */
@ApplicationPath("/")
public class HelloApplication extends Application {

}