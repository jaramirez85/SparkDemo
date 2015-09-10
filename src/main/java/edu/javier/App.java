package edu.javier;

import edu.javier.bl.PersonService;
import edu.javier.bl.PersonServiceImpl;
import edu.javier.model.Person;
import edu.javier.util.JsonUtil;
import spark.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static spark.Spark.*;
import static edu.javier.util.JsonUtil.*;

/**
 * Created by JavierAlonso on 08/09/2015.
 */
public class App {

    private static final int HTTP_BAD_REQUEST = 500;
    private static PersonService service = new PersonServiceImpl();

    public static void main(String[] args) {

        port(8000);

        get("/persons"
                , (request, response) -> {
            response.type("application/json");
            return service.getAll();
        }
                , json());

        get("/persons/:id"
                , (request, response) -> {
            response.type("application/json");
            return service.findById(Integer.valueOf(request.params(":id")));
        }
                , json());

        post("/persons", "application/json", (request, response) -> RestExecutor.execute(response, () -> {
                service.create(JsonUtil.toObject(request.body(), Person.class));
                response.status(201);
                return "";
            })
        );

        put("/persons/:id", "application/json", (request, response) -> RestExecutor.execute(response, () -> {
                Person p = JsonUtil.toObject(request.body(), Person.class);
                p.setId(Integer.valueOf(request.params(":id")));
                service.edit(p);
                return "";
            })
        );

        delete("/persons/:id", (request, response) -> RestExecutor.execute(response, () -> {
            service.delete(Integer.valueOf(request.params(":id")));
            return "";
        }));

    }


    private static class RestExecutor {
        public static Object execute(Response response, Supplier<Object> supplier) {
            try {
                return supplier.get();
            } catch (Exception ex) {
                response.status(HTTP_BAD_REQUEST);
                return "";
            }
        }
    }
}

