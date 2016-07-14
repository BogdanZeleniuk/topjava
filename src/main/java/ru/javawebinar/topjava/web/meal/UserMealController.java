package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * Created by Admin on 14.07.2016.
 */
@Controller
public class UserMealController extends AbstractUserMealController {
    @RequestMapping(value = "/meals", method = RequestMethod.GET)
    public String getAll(Model model){
        model.addAttribute("mealList", super.getAll());
        return "mealList";
    }

    @RequestMapping(value = "/meals/delete", method = RequestMethod.GET)
    public String delete(HttpServletRequest request){
        int id = Integer.valueOf(request.getParameter("id"));
        super.delete(id);
        return "redirect:/meals";
    }

    @RequestMapping(value = "/meals/create", method = RequestMethod.GET)
    public String create(Model model){
        model.addAttribute("meal", new UserMeal(LocalDateTime.now(), "", 1000));
        return "mealEdit";
    }

    @RequestMapping(value = "/meals/update", method = RequestMethod.GET)
    public String update(HttpServletRequest request, Model model){
        int id = Integer.valueOf(request.getParameter("id"));
        model.addAttribute("meal", super.get(id));
        return "mealEdit";
    }

    @RequestMapping(value = "/meals", method = RequestMethod.POST)
    public String edit(HttpServletRequest request){
        int id = Integer.valueOf(request.getParameter("id"));
        final UserMeal meal = new UserMeal(LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        if (meal.isNew()){
            super.create(meal);
        }
        else
        {
            super.update(meal,id);
        }
        return "redirect:/meals";
    }
    @RequestMapping(value = "/meals/filter", method = RequestMethod.POST)
    public String filter(HttpServletRequest request, Model model){
        LocalDate startDate = TimeUtil.parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = TimeUtil.parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = TimeUtil.parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = TimeUtil.parseLocalTime(request.getParameter("endTime"));
        model.addAttribute("mealList", super.getBetween(startDate, startTime, endDate, endTime));
        return "mealList";
    }
}
