package edu.javier;

import edu.javier.bl.PersonService;
import edu.javier.bl.PersonServiceImpl;
import edu.javier.model.Person;
import spark.Request;
import spark.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

import static edu.javier.App.RestExecutor.execute;
import static edu.javier.util.JsonUtil.json;
import static edu.javier.util.JsonUtil.toJson;
import static edu.javier.util.JsonUtil.toObject;
import static spark.Spark.*;

/**
 * Created by JavierAlonso on 08/09/2015.
 */
public class App {

    private static final int HTTP_BAD_REQUEST = 500;
    private static PersonService service = new PersonServiceImpl();

    public static void main(String[] args) {

        port(8000);

        get("/api/persons", (request, response) ->
                execute(response, () -> {
                    response.type("application/json");
                    return service.getAll();
                }), json());

        get("/api/persons/:id", (request, response) ->
                execute(response, () -> {
                    UUID idPerson = UUID.fromString(request.params(":id"));
                    Optional<Person> person = getPerson(idPerson);
                    if (!person.isPresent()) {
                        response.status(404);
                        return null;
                    }
                    response.type("application/json");
                    return person.get();
                })
                , json());

        post("/api/persons", "application/json", (request, response) -> execute(response, () -> {
                    UUID newId = service.create(toObject(request.body(), Person.class));
                    response.type("application/json");
                    response.status(201);
                    Map<String, UUID> map = new HashMap<>();
                    map.put("id", newId);
                    return toJson(map);
                }));

        put("/api/persons/:id", "application/json", (request, response) -> execute(response, () -> {
                    UUID idPerson = UUID.fromString(request.params(":id"));
                    Optional<Person> person = getPerson(idPerson);
                    if (!person.isPresent()) {
                        response.status(404);
                        return null;
                    } else {
                        Person p = toObject(request.body(), Person.class);
                        p.setId(idPerson);
                        service.edit(p);
                        return "";
                    }
                })
        );

        delete("/api/persons/:id", (request, response) -> execute(response, () -> {
            UUID idPerson = UUID.fromString(request.params(":id"));
            Optional<Person> person = getPerson(idPerson);
            if (!person.isPresent()) {
                response.status(404);
                return null;
            } else {
                service.delete(idPerson);
                return "";
            }
        }));

    }

    private static Optional<Person> getPerson(UUID idPerson) {
        return service.findById(idPerson);
    }


    public static class RestExecutor {
        public static Object execute(Response response, Supplier<Object> supplier) {
            try {
                return supplier.get();
            } catch (Exception ex) {
                response.type("application/json");
                response.status(HTTP_BAD_REQUEST);
                Map<String, String> map = new HashMap<>();
                map.put("message", ex.getMessage());
                return toJson(map);
            }
        }
    }
}

