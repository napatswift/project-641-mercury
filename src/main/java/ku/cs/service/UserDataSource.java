package ku.cs.service;

import ku.cs.models.AccountList;
import ku.cs.models.CsvReader;
import ku.cs.models.User;

import java.io.IOException;

public class UserDataSource {
    private String filePath;
    private AccountList accountList;

    public UserDataSource(String filePath) {
        this.filePath = filePath;
        this.accountList = new AccountList();
    }

    public AccountList getAccounts() {
        return accountList;
    }

    public void parse(String sep) throws IOException{
        String [] lines = CsvReader.getLines(filePath);
        for(String line: lines){
            String [] entries = line.split(",");
            User newUser = new User(entries[0], entries[1] , entries[2],
                    entries[3], entries[4], entries[5], entries[6], entries[7], entries[8], entries[9]);
            accountList.addAccount(newUser);
        }
    }

    public void parse() throws IOException {
        parse(",");
    }

}
