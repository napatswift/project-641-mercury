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
        Organizer chang = new Organizer("ณัฐพงศ์ พิมพิสาร", "ช้าง", "6310401327", "changatKU", "/ku/cs/image/chang.jpg");
        Organizer bank  = new Organizer("ณัฐดนัย ตันวาณิชกุล", "แบงค์", "6310403974", "Natdadai", "/ku/cs/image/media-cup-holder.png");
        Organizer feel  = new Organizer("ณภัทร ดลภาวิจิต", "ฟีล", "6310400967", "napatswift", "/ku/cs/image/media-cup-holder.png");
        Organizer mek   = new Organizer("พีรพัฒน์ ตันตระกูล", "เมฆ", "6310401084", "Mekpearaphat", "/ku/cs/image/mek.jpg");

        list.add(chang);
        list.add(bank);
        list.add(feel);
        list.add(mek);

    }

    public ArrayList<Organizer> getList() {
        return list;
    }
}
