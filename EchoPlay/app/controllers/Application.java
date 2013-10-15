package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import play.libs.F;
import play.libs.Json;
import play.mvc.BodyParser;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.SimpleResult;

public class Application extends Controller {
    
    public static Result index() {
        return ok(views.html.index.render("Hello Play Framework"));
    }
    @BodyParser.Of(BodyParser.Json.class)
    public static Result echo()
    {
        JsonNode json = request().body().asJson();
        String name = json.findPath("target").textValue();
        String message = json.findPath("message").textValue();
        if(name == null)
        {
            return badRequest("Missing parameter [name]");
        }
        if(message == null)
        {
            return badRequest("Missing parameter [message]");
        }
        else {
            ObjectNode result = Json.newObject();
            result.put("status", "ack");
            result.put("message", name + " says " + message);
            return ok(result);
        }
    }

    @BodyParser.Of(BodyParser.Json.class)
    public static F.Promise<Result> asyncEcho()
    {
        F.Promise echoPromise = F.Promise.promise(new F.Function0<ObjectNode>() {
            @Override
            public ObjectNode apply() throws Throwable {
                JsonNode json = request().body().asJson();
                String name = json.findPath("target").textValue();
                String message = json.findPath("message").textValue();
                if(name == null || message == null)
                {
                    return null;
                }
                else {
                    ObjectNode result = Json.newObject();
                    result.put("status", "ack");
                    result.put("message", name + " says " + message);
                    return result;
                }
            }
        });
        return echoPromise.map(new F.Function<ObjectNode, SimpleResult>() {
            @Override
            public SimpleResult apply(ObjectNode computedValue) throws Throwable {
                return ok(computedValue);
            }
        });
    }
}
