# cs211-641-project

## รายชื่อสมาชิก


## วิธีการติดตั้งหรือรันโปรแกรม

ให้ [กดดาวโหลด](https://drive.google.com/drive/folders/1gE6iwR3MOJ-FiloXg5_QRr5LlXTDTsBx?usp=sharing) ไฟล์โปรแกรม

### windowsOS

หากผู้ใช้ใช้ windows OS ให้กด double-click *Mercury-windowsOS.jar* หรือ

```
java -jar Mercury-windowsOS.jar 
```

### macOS

หากผู้ใช้ใช้ macOS ให้กด double-click *Mercury-macOS.jar* หรือ

```
java -jar Mercury-macOS.jar
```

## การวางโครงสร้างไฟล์

ภาพรวม
```
root
└───data
│   │   ...
│
└───images
│   │   ...
│
└───src
    │   ...
```

ข้อมูลที่บันทึกเป็นฐานข้อมูล

```
data
│   *.csv
│
└───dev
    │   *.csv
```

รูปภาพที่ uploead โดยผู้ใช้

```
images
│   ...
│
└───product_images
    │   ...
```

source code

```
src
└───java/ku/cs
│   │   module-info.java
│   │
│   └───controllers
│   │   │
│   │   └───signup
│   │       │   *.java
│   │
│   └───models
│   │       │   *.java
│   │
│   └───service
│   │       │   *.java
│   │
│   └───`outsource package`
│       │
│       └─── ...
│
└───resources
    │   *.fxml
    │
    └───fonts
    │   │   *.ttf
    │
    └───image
    │   │   *
    │
    └───style
        │   *.css
```

## ตัวอย่างข้อมูลผู้ใช้ระบบ

username|role|name|password|has_store|store
---|---|---|---|---|---
admin|ADMIN|Admin|admin|false|null
adoughilla6|USER|Aubrette Doughill|lwvOJkG|false|null
anethercott3|USER|Abeu Nethercott|M687uDhVlw|true|Schulist, Collier and Christiansen
bshapterct|USER|Bud Shapter|cFz8r7FL6th|false|null
ctoffulbq|USER|Charo Tofful|8dopPtEbzu|false|null
fcardillo2|USER|Frederik Cardillo|gOY5sbrdwn18|false|null
gbratch2q|USER|Gilbertine Bratch|g4ZzgQcIu|false|null
kbrabenderbn|USER|Kamillah Brabender|fryL5McNwuMe|false|null
kskelington54|USER|Kitti Skelington|m6MjBLPGEy|false|null
lrensd9|USER|Leopold Rens|5y78Ht|false|null
mgorinib4|USER|Malinda Gorini|ygI1TvG|false|null
msolway4|USER|Melina Solway|ty5o6nD9|true|Collier, Ankunding and Kunde
sgrimstead0|USER|Silvanus Grimstead|ewE8lxpfq|false|null
tbuttress9r|USER|Tally Buttress|xH7VdFgD|false|null
tzylbermann1|USER|Tony Zylbermann|mgqk6SyPxB|true|Durgan-Graham
wcleyburn5w|USER|Warden Cleyburn|5FILBR8Ebt|false|null


## สรุปสิ่งที่พัฒนาแต่ละครั้งที่นำเสนอความก้าวหน้าของระบบ

### ครั้งที่ 1 

วันที่เสนอ 10 Aug

### ครั้งที่ 2

วันที่เสนอ 8 Sep

[สิ่งที่พัฒนา](https://github.com/CS211-641/project-641-mercury/milestone/1?closed=1)

### ครั้งที่ 3 

วันที่เสนอ 30 Sep

[สิ่งที่พัฒนา](https://github.com/CS211-641/project-641-mercury/milestone/2?closed=1)
