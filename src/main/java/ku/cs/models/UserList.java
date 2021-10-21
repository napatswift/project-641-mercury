package ku.cs.models;

import ku.cs.models.io.CSVFile;
import ku.cs.strategy.FromMostRecentLoginUserComparator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;

public class UserList implements CSVFile {
    private final Collection<User> users;

    private User currUser;

    public UserList() {
        users = new TreeSet<>();
    }

    public boolean addUser(User user){
        return users.add(user);
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
        newList.sort(new FromMostRecentLoginUserComparator());
        return newList;
    }

    @Override
    public String toCSV(){
        StringBuilder stringBuilder = new
                StringBuilder("username,role,name,password,picture_path," +
                "last_login,is_banned,login_attempt,has_store,store");
        stringBuilder.append("\n");
        for(User acc: users){
            stringBuilder.append(acc.toCSV());
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
