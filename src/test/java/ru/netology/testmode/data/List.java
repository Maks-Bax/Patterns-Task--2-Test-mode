package ru.netology.testmode.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

@Data
@Value
@AllArgsConstructor
public class List {
    private String login;
    private String password;
    private String status;

}