use mydb;

INSERT INTO Users(user_account_number,user_password,user_name,SIN,user_type,user_address,date_of_birth,occupation,payment_info)
VALUES ("111","111","host01","111","1","1st Rd","2001-01-01","job1","Credit01");

INSERT INTO Users(user_account_number,user_password,user_name,SIN,user_type,user_address,date_of_birth,occupation,payment_info)
VALUES ("222","222","host02","222","1","2st Rd","2001-01-01","job2","Credit02");

INSERT INTO Users(user_account_number,user_password,user_name,SIN,user_type,user_address,date_of_birth,occupation,payment_info)
VALUES ("333","333","host03","222","1","3st Street","2001-01-01","job3","Credit03");

INSERT INTO Users(user_account_number,user_password,user_name,SIN,user_type,user_address,date_of_birth,occupation,payment_info) 
VALUES ("101","101","renter1","101","2","1st Rd","2001-01-01","job1","Debit 01");

INSERT INTO Users(user_account_number,user_password,user_name,SIN,user_type,user_address,date_of_birth,occupation,payment_info) 
VALUES ("202","202","renter2","202","2","Second Trail","2001-01-01","job2","Debit 02");

INSERT INTO Users(user_account_number,user_password,user_name,SIN,user_type,user_address,date_of_birth,occupation,payment_info) 
VALUES ("303","303","renter3","303","2","Third Trail","2001-01-01","job3","Debit 03");

INSERT INTO Shelters(s_name,s_host,property_type,longitude,latitude,s_address,characteristic,price,city,country,zip_code) 
SELECT "VeryLargeHouse",user_account_number,"1","100.0","0","Test1 Rd","With wifi, TV and a King size bed!","999.9","city1","country1","A1A1A1"
FROM Users
WHERE user_account_number = "111";

INSERT INTO Shelters(s_name,s_host,property_type,longitude,latitude,s_address,characteristic,price,city,country,zip_code) 
SELECT "VerySmallRoom",user_account_number,"2","100.2","-0.1","Test1 Rd","No wifi, no funitures","0.9","city1","country1","A1A1A1"
FROM Users
WHERE user_account_number = "111";

INSERT INTO Shelters(s_name,s_host,property_type,longitude,latitude,s_address,characteristic,price,city,country,zip_code) 
SELECT "A Gusthouse in another country",user_account_number,"3","99.9","0.1","Test2 Rd","Basic Condition","22.2","city2","country2","B2B2B2"
FROM Users
WHERE user_account_number = "222";

INSERT INTO Shelters(s_name,s_host,property_type,longitude,latitude,s_address,characteristic,price,city,country,zip_code) 
SELECT "TestHotel",user_account_number,"4","179.9","30","Test3 Rd","Basic Condition","333.3","city3","country3","333333"
FROM Users
WHERE user_account_number = "333";

INSERT INTO Activity(a_date,a_host,a_shelter,a_renter,a_status) 
SELECT "2024-01-01",s_host,s_name,"101",0
FROM Shelters
WHERE s_host = "111" AND s_name = "VeryLargeHouse";

INSERT INTO Activity(a_date,a_host,a_shelter,a_renter,a_status) 
SELECT "2023-01-02",s_host,s_name,"101",1
FROM Shelters
WHERE s_host = "111" AND s_name = "VerySmallRoom";

INSERT INTO Activity(a_date,a_host,a_shelter,a_renter,a_status) 
SELECT "2023-01-01",s_host,s_name,"202",1
FROM Shelters
WHERE s_host = "111" AND s_name = "VeryLargeHouse";

INSERT INTO Comments(content, rate, commented,poster)
VALUES ("Very good!", "5", "222", "303");

INSERT INTO Comments(content, rate, commented,poster)
VALUES ("pretty good!  -By Renter3", "3", "222", "202");

INSERT INTO Comments(content, rate, commented,poster)
VALUES ("The worst renter I've ever met!", "1", "202", "222");



