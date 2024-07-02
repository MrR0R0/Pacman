package org.example.pacman;

import org.example.pacman.User;

abstract public class Menu {
    public enum MenuType {Profile, Main, Shop, Authentication, Play, SignUp}
    public static User currentUser = new User();
    public static MenuType currentMenu = MenuType.Authentication;
}
