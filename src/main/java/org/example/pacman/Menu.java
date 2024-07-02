package org.example.pacman;

import org.example.pacman.User;

abstract public class Menu {
    public enum MenuType {Profile, Main, Shop, Login, Play, SignUp}
    public static User currentUser = new User();
    public static MenuType currentMenu = MenuType.Login;
}
