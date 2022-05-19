delete from hardware;

insert into hardware(id,name,article_Code,article_Type,stock,price)
values(1,'Test','01234','OTHER',100,3000);

insert into hardware(id,name,article_Code,article_Type,stock,price)
values(2,'RTX 2060','13234','GPU',20,4000);

insert into hardware(id,name,article_Code,article_Type,stock,price)
values(3,'Ryzen 5 2600x','56974','CPU',7,2400);

insert into hardware(id,name,article_Code,article_Type,stock,price)
values(4,'Gigabyte Z370P','11224','MBO',9,750.34);


insert into review(id,naslov,tekst,ocjena,hardware_id)
values(1,'Dosta dobro!','Veoma korisna komponenta',5,2);

insert into review(id,naslov,tekst,ocjena,hardware_id)
values(2,'Katastrofa','Nemojte nikada ovo kupiti',1,1);

insert into review(id,naslov,tekst,ocjena,hardware_id)
values(3,'OK','Okej',3,1);

insert into review(id,naslov,tekst,ocjena,hardware_id)
values(4,'Jako zadovoljan','Najbolja komponenta na tržištu',5,3);

insert into review(id,naslov,tekst,ocjena,hardware_id)
values(5,'Uredu','Dosta dobro ali može bolje',4,4);

insert into review(id,naslov,tekst,ocjena,hardware_id)
values(6,'Razočaran','Nikada više ne kupujem ovo smeće',1,4);


insert into authority(id,authority_name)
values(1,'ROLE_ADMIN');

insert into authority(id,authority_name)
values(2,'ROLE_USER');


insert into user(id,username,password)
values(1,'user','$2a$12$yPNffr.6xkWglTmLTGuxNejd0EFkb6hgZ8riRtfqO1Yvv.BPY7jT2');

insert into user(id,username,password)
values(2,'admin','$2a$12$dX65einkf0VEVtm90beid.lL399Ez3xzl84SJR.i4YtvtGiZ1l6U6');


insert into user_authority(user_id,authority_id)
values(1,2);

insert into user_authority(user_id,authority_id)
values(2,1);