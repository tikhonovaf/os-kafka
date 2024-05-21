package ru.openschool.aop.backend.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * Created by Tikhonov Arkadiy
 */
@Entity
@Getter
@Setter
public class MigrUser {

    /**
     * Идентификатор
     */
    @Id
    @GeneratedValue
    Long id;

    /**
     * Роль
     */
    @ManyToOne
    Role role;

    /**
     * имя
     */
    String name;

    /**
     * Логин
     */
    String login;

    /**
     * Пароль
     */
    String password;

    /**
     * Дата начала действия
     */
    LocalDate startDate;

    /**
     * Дата окончания действия
     */
    LocalDate endDate;

    /**
     * email
     */
    String email;

    /**
     * phone
     */
    String phone;


    /**
     * telegram
     */
    String telegram;

}
