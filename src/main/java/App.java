import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("tasks", request.session().attribute("tasks")); //retrieving the task from the session, and placing it in model under the key "task"
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/tasks", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      // include an if conditional that attempts to retrieve an ArrayList from the session saved under the key "tasks". If that ArrayList does not exist yet, we create a new one and add it to the session
      ArrayList<Task> tasks = request.session().attribute("tasks");
        if (tasks == null) {
          tasks = new ArrayList<Task>();
          request.session().attribute("tasks", tasks);
        }
      // create our Task object and add it into the tasks ArrayList with: tasks.add(newTask);
      String description = request.queryParams("description");
      Task newTask = new Task(description);
      tasks.add(newTask); // adding the task to the arrayList
      model.put("template", "templates/success.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
