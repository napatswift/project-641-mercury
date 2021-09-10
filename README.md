# cs211-641-project

## รายชื่อสมาชิก
* 6310401327 นาย ณัฐพงศ์ พิมพิสาร github : changatKU
* 6310403974 นาย ณัฐดนัย ตันวาณิชกุล github : Natdadai  
* 6310401084 นาย พีรพัฒน์ ตันตระกูล github : Mekpearaphat
* 6310400967 นาย ณภัทร ดลภาวิจิต github : napatswift


## วิธีการติดตั้งหรือรันโปรแกรม
ระบุวิธีการติดตั้งระบบ เช่น งานโปรเจคที่สำเร็จอยู่ที่ directory ใด ต้องใช้ command เพื่อเปิดหน้าโปรแกรมอย่างไร


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

|Role|username|password|
|---|----|---|
|Admin|admin|admin|

## สรุปสิ่งที่พัฒนาแต่ละครั้งที่นำเสนอความก้าวหน้าของระบบ

### ครั้งที่ 1 

วันที่เสนอ 10 Aug

|สิ่งที่พัฒนา                      |ผู้พัฒนา              |
|-----------------------------|-------------------|
|โครงหน้าต่างวิธีการใช้งาน (how-to)| นาย ณัฐพงศ์ พิมพิสาร  |
|โครงหน้าต่างส่วนของ Admin       | นาย ณัฐดนัย ตันวาณิชกุล|
|โครงหน้าต่างแนะนำผู้ทำระบบ       | นาย พีรพัฒน์ ตันตระกูล |
|การสมัครเข้าใช้งานระบบ          | นาย ณภัทร ดลภาวิจิต  |

### ครั้งที่ 2

วันที่เสนอ 8 Sep

[สิ่งที่พัฒนา](https://github.com/CS211-641/project-641-mercury/milestone/1)

|สิ่งที่พัฒนา|ผู้พัฒนา|
|---|---|
|ระบบของผู้ดูแลระบบ ระบบส่วนนี้ถูกจำกัดสิทธิ์สำหรับผู้ดูแลระบบเท่านั้น|นาย ณัฐดนัย ตันวาณิชกุล|
|มีหน้าแสดงรายชื่อของผู้ใช้ระบบ โดยต้องแสดงภาพผู้ใช้ username ชื่อของผู้ใช้ระบบ ชื่อร้านค้า|นาย ณัฐดนัย ตันวาณิชกุล|
|มีหน้าแสดงรายชื่อของผู้ใช้ระบบ เรียงลำดับตามวันเวลาที่ผู้ใช้ระบบเข้าใช้งานล่าสุดก่อน|นาย ณัฐดนัย ตันวาณิชกุล|
มีเมนูเปิดร้านค้า ให้ผู้ใช้ระบบสามารถสร้างร้านค้าของตนเอง ซึ่งจะทำให้ผู้ใช้ระบบมีสถานะเป็นผู้ขายด้วย โดยการเปิดร้านค้าจะใช้ข้อมูลชื่อร้าน|นาย ณัฐพงศ์ พิมพิสาร|
|ต้องมีส่วนของ GUI ที่แสดงถึงข้อมูลต่อไปนี้ โดยอาจเข้าถึงข้อมูลนี้ผ่านเมนูหรือมีปุ่มกดที่หน้าแรก 1. รูปที่แสดงหน้าตา (หน้ายิ้ม) ที่ชัดเจน ชื่อ นามสกุล ชื่อเล่น รหัสนิสิต 2. ข้อมูลคำสั่ง หรือคำแนะนำ ในการใช้โปรแกรมที่นิสิตสร้างขึ้นมา|นาย พีรพัฒน์ ตันตระกูล|
|มีหน้า marketplace ที่แสดงสินค้าทั้งหมดจากทุกร้าน เรียงลำดับโดยให้สินค้าที่วางขายล่าสุดขึ้นแสดงก่อน โดยแสดงภาพสินค้า ชื่อสินค้า และราคาสินค้า|นาย ณภัทร ดลภาวิจิต|
|ในหน้า marketplace ที่แสดงสินค้าทั้งหมดจากทุกร้าน มีส่วนให้ผู้ซื้อเลือกเรียงลำดับสินค้าจากราคามากที่สุดขึ้นแสดงก่อน หรือเลือกเรียงลำดับสินค้าจากราคาน้อยที่สุดขึ้นแสดงก่อน|นาย ณภัทร ดลภาวิจิต|
|เมื่อเปิดโปรแกรมใหม่ หรือเมื่อโปรแกรมโหลดไฟล์ csv จะต้องแสดงข้อมูลที่ได้บันทึกไว้อย่างถูกต้อง|นาย ณภัทร ดลภาวิจิต|

### ครั้งที่ 3 

วันที่เสนอ - -

|สิ่งที่พัฒนา|ผู้พัฒนา|
|---|---|
| - | - |