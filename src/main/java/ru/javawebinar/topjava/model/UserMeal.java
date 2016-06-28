package ru.javawebinar.topjava.model;


import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
@NamedQueries(value = {
        @NamedQuery(name = UserMeal.DELETE, query = "DELETE FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId"),
        @NamedQuery(name = UserMeal.BETWEEN, query = "SELECT m FROM UserMeal m WHERE m.dateTime BETWEEN ?1 AND ?2 ORDER BY m.dateTime DESC "),
        @NamedQuery(name = UserMeal.ALL_SORTED, query = "SELECT m FROM UserMeal m WHERE m.user.id=:userId ORDER BY m.dateTime DESC "),
        @NamedQuery(name = UserMeal.UPDATE, query = "UPDATE UserMeal m SET m.dateTime=:dateTime, m.description=:description, m.calories=:calories WHERE m.id=:id AND m.user.id=:userId "),
        @NamedQuery(name = UserMeal.GET_MEAL, query = "SELECT m FROM UserMeal m WHERE m.id=:id AND m.user.id=:userId "),
})
@Entity
@Table(name = "meals", uniqueConstraints = {@UniqueConstraint(columnNames = {"date_time" , "user_id"}, name = "meals_unique_user_datetime_idx") })
public class UserMeal extends BaseEntity{

    public static final String DELETE = "UserMeal.delete";
    public static final String ALL_SORTED = "UserMeal.getAllSorted";
    public static final String BETWEEN = "UserMeal.getBetween";
    public static final String UPDATE = "UserMeal.update";
    public static final String GET_MEAL = "UserMeal.get";

    @Column(name = "date_time", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDateTime dateTime;

    @Column(name = "description", nullable = false)
    @NotNull
    private String description;

    @Column(name = "calories", nullable = false)
    @Digits(fraction = 0, integer = 4)
    protected int calories;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public UserMeal() {
    }

    public UserMeal(LocalDateTime dateTime, String description, int calories) {
        this(null,dateTime,description,calories);
    }

    public UserMeal( Integer id, LocalDateTime dateTime, String description, int calories) {
        super(id);
        this.description = description;
        this.calories = calories;
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isNew(){
        return id == null;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserMeal{" +
                "id=" + id +
                ", dateTime=" + dateTime +
                ", description='" + description + '\'' +
                ", calories=" + calories +
                '}';
    }
}
