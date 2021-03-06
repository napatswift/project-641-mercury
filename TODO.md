# TODO

# To-do list

- หัวข้อจะมี “(`x` คะแนน)” ขึ้นต้น
- หัวข้อใหญ่เป็นตัวหนา แปลว่า**ต้องทำ**ทั้งหัวข้อและ child
    - บางหัวข้อเป็นตัวหนา แต่ child ขึ้นต้นด้วย `(extra)` แปลว่าไม่จำเป็น
- หัวข้อใหญ่ขึ้นต้นด้วย `(extra)` แปลว่าไม่จำเป็น

## ทั่วไป

- [ ]  **13. (5 คะแนน) ต้องมีส่วนของ GUI ที่แสดงถึงข้อมูลต่อไปนี้ โดยอาจเข้าถึงข้อมูลนี้ผ่านเมนู หรือมีปุ่มกดที่หน้าแรกข้อมูลนิสิตผู้จัดทำโปรแกรม**
    - [ ]  รูปที่แสดงหน้าตา (หน้ายิ้ม) ที่ชัดเจน ชื่อ นามสกุล ชื่อเล่น รหัสนิสิต
    - [ ]  ข้อมูลคำสั่ง หรือคำแนะนำ ในการใช้โปรแกรมที่นิสิตสร้างขึ้นมา

## 14. ระบบของผู้ดูแลระบบ

- [x]  **ระบบส่วนนี้ถูกจำกัดสิทธิ์สำหรับผู้ดูแลระบบเท่านั้น**
- [x]  **ผู้ใช้ที่ไม่ใช่ผู้ดูแลระบบต้องไม่สามารถเข้าใช้งานส่วนนี้ได้**
- [x]  **Application ไม่ต้องมีระบบการสร้างบัญชีสำหรับผู้ดูแลระบบ**
- [ ]  **ให้นิสิตระบุ username และ password ของผู้ดูแลระบบในไฟล์ pdf** (ข้อ 11 หัวข้อ “ตัวอย่างข้อมูลผู้ใช้ระบบ”)
- [x]  14.1 (5 คะแนน) ผู้ดูแลระบบสามารถเปลี่ยนรหัสผ่านของตนเองได้ รหัสผ่านใหม่ต้องใช้ได้
- [x]  **14.2 (5 คะแนน) มีหน้าแสดงรายชื่อของผู้ใช้ระบบ**
    - [x]  แสดงภาพผู้ใช้ username
    - [x]  ชื่อของผู้ใช้ระบบ
    - [x]  ชื่อร้านค้า (กรณีผู้ใช้ระบบเปิดร้านค้าของตนเอง)
    - [x]  วันเวลาที่เข้าใช้ล่าสุดของผู้ใช้ระบบคนนั้น
    - [x]  เรียงลำดับตามวันเวลาที่ผู้ใช้ระบบเข้าใช้งานล่าสุดก่อน
- [ ]  *14.3 (5 คะแนน) หน้าแสดงรายการของรายงานความไม่เหมาะสมของผู้ใช้ระบบ*
    - [x]  มีหน้าแสดงรายการของรายงานความไม่เหมาะสมของผู้ใช้ระบบ
    - (เช่น เนื้อหาไม่เหมาะสม ความคิดเห็นไม่เหมาะสม ข่าวปลอม เนื้อหาล่อแหลม เนื้อหาอันตรายมีความรุนแรง ฯลฯ)
    - [x]  ผู้ดูแลระบบสามารถระงับสิทธิ์การเข้าใช้งานของผู้ใช้ระบบได้
    - [x]  ผู้ใช้ระบบที่ถูกระงับสิทธิ์จะไม่สามารถเข้าใช้งานโปรแกรมได้ มีข้อความแจ้งเตือนการถูกระงับสิทธิ์
    - [ ]  ผู้ดูแลระบบสามารถทราบถึงจำนวนครั้งในการพยายามเข้าใช้โปรแกรมในขณะที่ผู้ใช้ระบบถูกระงับสิทธิ์
    - [x]  ผู้ดูแลระบบสามารถคืนสิทธิ์การเข้าใช้งานของผู้ใช้ระบบได้
- [x]  14.4 (extra 5 คะแนน) มีเมนูจัดการหมวดหมู่สินค้า
    - [x]  สามารถสร้างหมวดหมู่ของสินค้าที่แต่ละร้านค้าจะนำมาขายได้
    - [x]  สามารถกำหนดให้หมวดหมู่ที่ต่างกัน มีคุณลักษณะของสินค้าที่ต่างกัน เช่น หมวดเสื้อผ้า จะให้เพิ่มข้อมูลสี ขนาด แต่หมวดโทรศัพท์มือถือ จะให้เพิ่มข้อมูลรุ่น และความจุ และจะต้องนำไปใช้ในข้อ 16.2 ได้ด้วย
    - หากการจัดการหมวดหมู่สินค้าเป็นแบบ hard code จะไม่ได้คะแนนในส่วนนี้
    - ไม่สามารถเพิ่มหมวดหมู่ใหม่ได้จากใน application จะไม่ได้คะแนนในส่วนนี้
## 15. การสร้างบัญชีของผู้ใช้ระบบ
- [ ]  **15.1 (5 คะแนน) ระบบลงทะเบียน (registration)**
    - [x]  มีระบบลงทะเบียน (registration)
    - [x]  โดยผู้ใช้ระบบที่ลงทะเบียนนี้จะเป็นผู้ซื้อเท่านั้น
    - ข้อมูลที่ใช้ในการลงทะเบียน ได้แก่
        - ชื่อของผู้ใช้ระบบ
        - ชื่อสำหรับเข้าสู่ระบบ (username)
        - รหัสผ่าน ยืนยันรหัสผ่าน
    - [x]  โดยมีการตรวจสอบ username จะต้องไม่ซ้ำกับผู้ใช้ระบบที่มีอยู่แล้ว
- [ ]  15.2 (5 คะแนน) ผู้ใช้ระบบสามารถเปลี่ยนรหัสผ่านของตนเองได้
    - [ ]  และรหัสผ่านใหม่ต้องใช้ได้
- [ ]  **15.3 (5 คะแนน) ผู้ใช้ระบบ upload รูปภาพ เพื่อใช้เป็นภาพของผู้ใช้ระบบ**
    - [x]  upload ได้
    - [ ]  และสามารถเปลี่ยนรูปได้
    - [x]  หากผู้ใช้ระบบยังไม่กำหนดภาพ ระบบจะให้ใช้ภาพ default แทนไปก่อน
## 16. ระบบสำหรับผู้ขายสินค้า
- [ ]  **16.1 (5 คะแนน) เมนูเปิดร้าน**
    - [x]  มีเมนูเปิดร้านค้า
    - [x]  ให้ผู้ใช้ระบบสามารถสร้างร้านค้าของตนเอง
    - [x]  ซึ่งจะทำให้ผู้ใช้ระบบมีสถานะเป็นผู้ขายด้วย
    - [x]  โดยการเปิดร้านค้าจะใช้ข้อมูลชื่อร้าน
- [ ]  **16.2 (5 คะแนน) เมนูเพิ่มสินค้า**
    - [x]  มีเมนูเพิ่มสินค้า
    - เพื่อลงขายสินค้าโดยใช้
        - [ ]  ข้อมูลชื่อสินค้า
        - [ ]  ภาพสินค้า
        - [ ]  รายละเอียดสินค้า
        - [ ]  ราคาสินค้า
        - [ ]  จำนวนสินค้าในคลัง
        - [ ]  (extra 14.4) หมวดหมู่สินค้า
        - [ ]  (extra 14.4) คุณลักษณะสินค้าเพิ่มเติมตามหมวดหมู่
    - [ ]  มีการตรวจสอบรูปแบบของข้อมูลก่อนจะบันทึกการลงขายสินค้า
- [ ]  **16.3 (5 คะแนน) เมนูดูรายการสินค้าทั้งหมด**
    - [ ]  มีเมนูดูรายการสินค้าทั้งหมด ที่แสดงรายการสินค้าทั้งหมด
        - [ ]  แสดงภาพสินค้า
        - [ ]  ชื่อสินค้า
        - [ ]  ราคาสินค้า
        - [ ]  จำนวนสินค้าในคลัง
        - [ ]  มีสัญลักษณ์และสีที่ทำให้สังเกตได้ว่าสินค้าใดมีจำนวนน้อยในคลังซึ่งระบุจาก *เมนูตั้งค่าร้านค้า 16.6*
    - [ ]  *16.4 (5 คะแนน) หน้ารายการสินค้าทั้งหมด*
        - [ ]  เลือกสินค้าเพื่อแก้ไขข้อมูลสินค้าได้
        - โดยสามารถแก้ไข
            - [ ]  ภาพสินค้า ได้ถูกต้อง
            - [ ]  ชื่อสินค้า ได้ถูกต้อง
            - [ ]  ราคาสินค้า ได้ถูกต้อง
            - [ ]  มีการตรวจสอบรูปแบบของข้อมูลก่อนจะบันทึกการแก้ไข
        - [ ]  *16.5 (5 คะแนน) ในหน้ารายการสินค้าทั้งหมดเลือกสินค้าเพื่อเพิ่มจำนวนสินค้าได้*
            - [ ]  เพิ่มจำนวนสินค้าได้
            - [ ]  จำนวนสินค้าเพิ่มขึ้นอย่างถูกต้อง
- [ ]  **16.6 (5 คะแนน) เมนูตั้งค่าร้านค้า**
    - [ ]  มีเมนูตั้งค่าร้านค้า
    - กำหนดจำนวนที่ถือว่าสินค้ามีจำนวนน้อยในคลัง เพื่อใช้ในการกำหนดสัญลักษณ์หรือสีในข้อ *16.3*
- [ ]  *16.7 (5 คะแนน) เมนูดูรายการสั่งซื้อ*
    - [ ]  มีเมนูดูรายการสั่งซื้อ
    - [ ]  แสดงรายการสินค้าที่มีผู้ซื้อสั่งซื้อ
    - [ ]  แยกรายการสั่งซื้อใหม่ และรายการสั่งซื้อที่จัดส่งแล้ว
    - โดยแสดง
        - [ ]  ภาพสินค้า
        - [ ]  ชื่อสินค้า
        - [ ]  จำนวนสินค้า
        - [ ]  ยอดซื้อ
    - [ ]  และมีส่วนของการจัดส่งสินค้า ซึ่งจะสมมติว่าผู้ขายส่งสินค้าเรียบร้อยแล้ว
    - [ ]  โดยต้องระบุหมายเลขติดตาม (tracking number) ในรายการสั่งซื้อ
    - [ ]  จากนั้นต้องลดจำนวนสินค้าได้ถูกต้อง
    - [ ]  และรายการสั่งซื้อจะเปลี่ยนสถานะเป็นจัดส่งแล้ว
- [ ]  16.8 (extra 5 คะแนน) เมนูจัดการโปรโมชัน
    - [ ]  มีเมนูจัดการโปรโมชัน เพื่อใช้เป็นส่วนลดในการซื้อสินค้าในร้านค้า
    - [ ]  โดยให้ระบุเป็นโค้ดส่วนลด
        - [ ]  ซึ่งโค้ดจะต้องไม่ซ้ำกับโปรโมชันอื่นในร้านค้าเดียวกัน
        - [ ]  และไม่ซ้ำกับโค้ดของโปรโมชันของร้านค้าอื่นด้วย
    - [ ]  ผู้ขายสามารถสร้างโปรโมชันได้หลายรูปแบบ เช่น โปรโมชันส่วนลด X เปอร์เซ็นต์เมื่อซื้อตั้งแต่ Y บาทขึ้นไป โปรโมชันส่วนลด X บาท เมื่อซื้อสินค้า Y ชิ้นขึ้นไป เป็นต้น (คำใบ้ ออกแบบคลาสโปรโมชันโดยอาศัยคุณสมบัติ polymorphism)

## 17. ระบบสำหรับผู้ซื้อสินค้า

- [x]  ผู้ใช้ระบบที่ลงทะเบียนตามข้อ 15.1 จะเป็นผู้ซื้อสินค้าโดยปริยาย ซึ่งจะเข้าดูหน้า marketplace และเลือกซื้อสินค้าได้
- [ ]  **17.1 (5 คะแนน) หน้า marketplace ที่แสดงสินค้าทั้งหมดจากทุกร้าน**
    - [x]  มีหน้า marketplace
    - [x]  ที่แสดงสินค้าทั้งหมดจากทุกร้าน
    - [x]  เรียงลำดับโดยให้สินค้าที่วางขายล่าสุดขึ้นแสดงก่อน
    - โดยแสดง
        - [x]  ภาพสินค้า
        - [x]  ชื่อสินค้า
        - [x]  และราคาสินค้า
    - [x]  **17.2 มีส่วนให้ผู้ซื้อเลือกเรียงลำดับสินค้าจากราคา**
        - [x]  ราคามากที่สุดขึ้นแสดงก่อน
        - [x]  ราคาน้อยที่สุดขึ้นแสดงก่อน
    - [x]  *17.3 (5 คะแนน) เลือกการแสดงเฉพาะสินค้าตามหมวดหมู่ที่สนใจ*
        - [x]  มีส่วนให้ผู้ซื้อเลือกการแสดงเฉพาะสินค้าตามหมวดหมู่ที่สนใจ
        - [x]  โดยยังคงเลือกเรียงลำดับการแสดงสินค้าจากราคา (17.2) ได้
    - [x]  *17.4 (5 คะแนน) เลือกการแสดงเฉพาะสินค้าที่มีช่วงราคา*
        - [x]  มีส่วนให้ผู้ซื้อเลือกการแสดงผลเฉพาะสินค้าที่มีช่วงราคาที่ผู้ซื้อกำหนดได้เอง
        - [x]  โดยยังคงเลือกเรียงลำดับการแสดงสินค้าจากราคา (17.2) ได้
- [ ]  **17.5 (5 คะแนน) เมื่อผู้ซื้อกดเลือกสินค้าจากหน้า marketplace**
    - ให้แสดงหน้ารายละเอียดสินค้า (โดยแสดงข้อมูลสินค้าที่ผู้ขายกรอกข้อมูลการวางขายสินค้าข้อ 16.2)
        - [x]  ข้อมูลชื่อสินค้า
        - [x]  ภาพสินค้า
        - [x]  รายละเอียดสินค้า
        - [x]  ราคาสินค้า
        - [x]  จำนวนสินค้าในคลัง
        - [x]  (extra) หมวดหมู่สินค้า
        - [x]  (extra) คุณลักษณะสินค้าเพิ่มเติมตามหมวดหมู่
        - [x]  ชื่อร้าน
        - [x]  *17.8 (5 คะแนน) ส่วนแจ้งรายงานความไม่เหมาะสมของสินค้า*
            - [x]  มีส่วนที่ให้ผู้ซื้อแจ้งรายงานความไม่เหมาะสม
            - โดยต้องระบุ
                - [x]  ประเภทของความไม่เหมาะสม
                - [x]  ข้อความเพิ่มเติม
            - [x]  ซึ่งเจ้าของร้านอาจถูกระงับสิทธิ์การเข้าใช้ระบบจากรายงานนี้เมื่อผู้ดูแลระบบเห็นว่าไม่เหมาะสมจริง
        - [ ]  *17.9 (5 คะแนน) เขียนรีวิวสินค้า*
            - [x]  มีส่วนที่ให้ผู้ซื้อเขียนรีวิวสินค้า
            - [ ]  และให้คะแนนความนิยมเป็นคะแนนเต็มในช่วง 1 ถึง 5 คะแนน bug***
            - จะต้องแสดง
                - [x]  คะแนนความนิยมเฉลี่ย
                - [x]  และจำนวนผู้ให้คะแนน
            - [x]  *17.10 (5 คะแนน) ส่วนที่แสดงความคิดเห็นต่อการรีวิวสินค้า*
                - [x]  มีส่วนที่แสดงความคิดเห็นของการรีวิวสินค้า และคะแนนความนิยมที่ให้จากข้อ 17.9
                - [x]  และมีส่วนให้ผู้ใช้ระบบแจ้งรายงานความไม่เหมาะสมของรีวิวิสินค้า
                    - [x]  ซึ่งจะไปแสดงผลในข้อ 14.3
                - โดยต้องระบุ
                    - [x]  ประเภทของความไม่เหมาะสม
                    - [x]  และข้อความเพิ่มเติม
                - [x]  ซึ่งเจ้าของรีวิวอาจถูกระงับสิทธิ์การเข้าใช้ระบบจากรายงานนี้เมื่อผู้ดูแลระบบเห็นว่าไม่เหมาะสมจริง
    - [ ]  เชื่อมโยงไปยังหน้าร้านค้าได้
- [ ]  **17.6 (5 คะแนน) ผู้ซื้อเลือกซื้อสินค้าจากหน้า marketplace แล้วจากข้อก่อนหน้า (17.5)**
    - [ ]  สามารถสร้างรายการสั่งซื้อได้เลย (ไม่ต้องทำระบบตะกร้าสินค้า)
    - [x]  โดยให้ระบุจำนวน
    - [ ]  มีการสรุปราคาให้ก่อนยืนยันการสร้างรายการสั่งซื้อ
        - [ ]  ซึ่งจะมีสถานะเป็นรายการสั่งซื้อใหม่
    - [ ]  หากจำนวนสินค้ามีไม่เพียงพอ
        - [ ]  มีข้อความแจ้งเตือนเพื่อให้ผู้ซื้อทราบอย่างเหมาะสม
- [ ]  17.7 (extra 5 คะแนน) กรณีที่ทำเมนูจัดการโปรโมชันข้อ 16.8
    - [ ]  จะต้องให้ผู้ซื้อกรอกโค้ดโปรโมชันเพื่อใช้เป็นส่วนลด
    - [ ]  และมีการสรุปราคาให้ก่อนยืนยันการสร้างรายการสั่งซื้อ
    - [ ]  หากโปรโมชันที่ระบุไม่สอดคล้องกับการเงื่อนไขในการใช้โปรโมชัน
        - จะต้อง
            - [ ]  มีข้อความแจ้งเตือนที่เหมาะสม
            - [ ]  มีข้อความแจ้งเตือนที่เข้าใจได้ว่าทำไมจึงใช้โปรโมชันที่กรอกโค้ดนี้ไม่ได้ 

## 18. ความสวยงามและประสบการณ์ของผู้ใช้งาน

- [ ]  *18.1 (5 คะแนน) ใช้โทนสีและองค์ประกอบต่าง ๆ ของ GUI ที่ดูได้ชัดเจน น่าใช้ ไม่แสบตา ไม่ลวงตา โดยลองนึกถึงว่าหากเราทำโปรแกรมนี้ให้คนทั่วไปใช้จริง ควรแสดงองค์ประกอบเหล่านี้อย่างไร*
- 18.2 (caution) โปรดตรวจสอบความถูกต้องของข้อความที่ปรากฏในส่วนต่าง ๆ ของโปรแกรม หรือเอกสารประกอบ หากพบว่ามีคำผิด หักคำที่ผิดจุดละ 5 คะแนน
- [ ]  **18.3 (10 คะแนน) Graphic User Interface (GUI)**
    - ควรมีรูปแบบที่เข้าใจง่าย มีการแสดงข้อมูลที่ชัดเจน
    - [ ]  มี Label กำกับข้อมูลที่ชัดเจน เพื่อให้เข้าใจว่าค่าที่แสดงนั้นคือค่าอะไร
    - มีการแสดงองค์ประกอบต่าง ๆ ในขนาดที่เหมาะสม ไม่เล็กหรือไม่ใหญ่จนเกินไป
    - [ ]  ขนาดหน้าจอของโปรแกรมต้องมีความสูงไม่เกิน 1024 pixel
- [ ]  18.4 (extra 5 คะแนน) GUI มี effect ที่น่าสนใจ
    - เมื่อมี action ต่าง ๆ เช่น มีการ highlight button เมื่อคลิกปุ่ม
    - มี effect การ transition เมื่อเปลี่ยน scene เป็นต้น
- [ ]  18.5 (extra 10 คะแนน) Theme
    - [ ]  ผู้ใช้โปรแกรมสามารถปรับเปลี่ยน Theme ของ Application ได้
        - เปลี่ยนโทนสีหรือ
        - เปลี่ยนขนาดอักษรหรือ
        - เปลี่ยนฟอนต์ของตัวอักษรได้
    - [ ]  โดยจะต้องเปลี่ยนให้สอดคล้องกันทุก Scene

## 19. บันทึก

โปรแกรมต้องสามารถบันทึกสถานะของข้อมูลต่าง ๆ ในรูปแบบของไฟล์ csv (comma-separated values) และสามารถโหลดไฟล์ที่บันทึกไว้นั้นมาแสดงผลได้อย่างถูกต้อง

- [x]  19.1 (caution) ต้องมีการบันทึกสถานะและข้อมูลที่จำเป็นในรูปแบบไฟล์ csv
- [x]  **19.2 (5 คะแนน) เมื่อเปิดโปรแกรมใหม่ หรือเมื่อโปรแกรมโหลดไฟล์ csv จะต้องแสดงข้อมูลที่ได้บันทึกไว้อย่างถูกต้อง**

## 20. ข้อมูลในที่ใช้การตรวจสอบ

ต้องมีข้อมูลเริ่มต้นในการทดสอบระบบ เสมือนว่าระบบถูกใช้งานมาประมาณ 1 เดือน เป็นอย่างน้อย 

- [ ]  ระบบถูกใช้งานมาประมาณ 1 เดือน
- [x]  มีข้อมูลผู้ดูแลระบบ ข้อมูลบัญชีผู้ใช้งานอย่างน้อย 5 บัญชี
- [ ]  มีร้านค้าอย่างน้อย 3 ร้านค้า
- [x]  มีสินค้าอย่างน้อย 10 รายการ

หากมีข้อมูลเริ่มต้นระบบน้อยกว่าที่กำหนด จะไม่ตรวจให้