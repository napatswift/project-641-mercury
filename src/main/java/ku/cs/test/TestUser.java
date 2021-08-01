package ku.cs.test;

import ku.cs.models.User;

public class TestUser {
    public static void main(){
        User napat = new User("napatswift", "Napat");

        // test set password
        System.out.println(napat.getName() + " " + napat.getUsername() + " " + napat.getRole());
        String [] testPasswords = {
                "4251koanorb",
                "32",
                "คภๅฟน",
                "abcd",
                "}+M<YQJB2_u~wqQ:",
                "*#%W9wzU:W+dvWJA",
                "1345#abr",
                "1234",
                "123456",
                "1234ab"
        };

        for(String tp: testPasswords){
            System.out.println(tp + " " + napat.setPassword(tp));
        }

        System.out.println(napat.login("1234ab"));
    }
}
