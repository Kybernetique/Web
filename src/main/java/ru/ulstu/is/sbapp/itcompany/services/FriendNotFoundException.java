package ru.ulstu.is.sbapp.itcompany.services;

public class FriendNotFoundException extends RuntimeException{
    public FriendNotFoundException(Long id) {
        super(String.format("Project with id [%s] is not found", id));
    }
}
