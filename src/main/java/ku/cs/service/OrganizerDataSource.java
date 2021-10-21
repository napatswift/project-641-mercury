package ku.cs.service;

import ku.cs.models.Organizer;

import java.util.ArrayList;

public class OrganizerDataSource {
    private ArrayList<Organizer> list;

    public OrganizerDataSource(){
        list = new ArrayList<>();
        readData();
    }

    public void readData() {

        Organizer chang = new Organizer();
        Organizer bank = new Organizer();
        Organizer feel = new Organizer();
        Organizer mek = new Organizer();

        chang.setName("ณัฐพงศ์ พิมพิสาร");
        chang.setNickname("ช้าง");
        chang.setStudentId("6310401327");
        chang.setGithubId("changatKU");
        chang.setImagePath("/ku/cs/image/changnoi.jpg");

        bank.setName("ณัฐดนัย ตันวาณิชกุล");
        bank.setNickname("แบงค์");
        bank.setStudentId("6310403974");
        bank.setGithubId("Natdadai");
        bank.setImagePath("/ku/cs/image/bank.jpg");

        feel.setName("ณภัทร ดลภาวิจิต");
        feel.setNickname("ฟีล");
        feel.setStudentId("6310400967");
        feel.setGithubId("napatswift");
        feel.setImagePath("/ku/cs/image/feen.png");

        mek.setName("พีรพัฒน์ ตันตระกูล");
        mek.setNickname("เมฆ");
        mek.setStudentId("6310401084");
        mek.setGithubId("Mekpearaphat");
        mek.setImagePath("/ku/cs/image/mek.jpeg");


        list.add(chang);
        list.add(bank);
        list.add(feel);
        list.add(mek);

    }

    public ArrayList<Organizer> getList() {
        return list;
    }
}
