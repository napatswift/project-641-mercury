package ku.cs.models;

import java.util.Collection;
import java.util.TreeSet;

public class Accounts {
    private Collection<User> accounts;

    private User currAccount;

    public Accounts() {
        accounts = new TreeSet<>();
    }

    public boolean addAccount(User account){
        return accounts.add(account);
    }

    public void removeAccount(String name){
        accounts.removeIf(userAccount -> userAccount.getName().equals(name));
    }

    public boolean isExist(String username){
        for(User acc : accounts){
            if(acc.getUsername().equals(username))
                return true;
        }
        return false;
    }

    public boolean login(String username, String password){
        for(User user: accounts){
            if(user.getUsername().equals(username)){
                if (user.login(password) == 2){
                    currAccount = user;
                    return true;
                }
            }
        }
        return false;
    }

    public User getUserAccount(String username){
        for(User user: accounts){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public boolean checkAccount(String username, String password){
        return true;
    }

    public User getCurrAccount() {
        return currAccount;
    }

    public Collection<User> toList() {
        return accounts;
    }

}
