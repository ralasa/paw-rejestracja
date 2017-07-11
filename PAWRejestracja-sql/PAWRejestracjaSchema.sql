drop table studenci;
CREATE TABLE studenci(
id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
created_at timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
updated_at timestamp,
imie text NOT NULL,
nazwisko text NOT NULL,
obywatelstwo text NOT NULL,
dataurodzenia Date NOT NULL,
pesel VARCHAR(11) NOT NULL,
nip VARCHAR(10) NOT NULL,
adreszamieszkania text,
adreskor text,
telefon VARCHAR(18) NOT NULL,
email varchar(40) NOT NULL,
wyksztalcenie text,
zatrudnienie text,
uprawnienia text,
rodzina text,
komentarz text,
page_id varchar(128)
) default charset=utf8;



drop table users;
CREATE TABLE users(
  id int NOT NULL AUTO_INCREMENT PRIMARY KEY,
  username varchar(45),
  password varchar(128)
) DEFAULT CHARSET=utf8;
insert into users(username,password) values('root','8398ce6fbfc95daa8b1fc594d4605a16a13605ee995abc405aa7185eaa4d77b7a28467535935cd87fe9183d3c9e5800c93a40432271e1522b9270aa7149e94a7');






