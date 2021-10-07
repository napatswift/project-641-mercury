package ku.cs.models;

import ku.cs.strategy.MostRecentLoginUserComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeSet;

public class UserList {
    private Collection<User> users;

    private User currUser;

    public UserList() {
        users = new TreeSet<>();
    }

    public boolean addUser(User user){
        return users.add(user);
    }

    public void removeUser(String name){
        users.removeIf(userAccount -> userAccount.getName().equals(name));
    }

    public boolean isExist(String username){
        for(User acc : users){
            if(acc.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public boolean login(String username, String password){
        for(User user : users){
            if(user.getUsername().equals(username)){
                if (user.login(password) == 2){
                        currUser = user;
                    return true;
                }
            }
        }
        return false;
    }

    public User getUser(String username){
        for(User user : users){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public User getCurrUser() {
        return currUser;
    }

    public Collection<User> toListOnlyRoleUser(){
        ArrayList<User> newList = new ArrayList<>(users);
        for(User temp : users){
            if(temp.role == User.Role.ADMIN)
                newList.remove(temp);
        }
        newList.sort(new MostRecentLoginUserComparator());
        return newList;
    }

    public String toCsv(){
        StringBuilder stringBuilder = new
                StringBuilder("username,role,name,password,picture_path," +
                "last_login,is_banned,login_attempt,has_store,store");
        stringBuilder.append("\n");
        for(User acc: users){
            stringBuilder.append(acc.toCsv());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
