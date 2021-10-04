drop database if exists farm;
create database if not exists farm;
show databases;


use farm;


create table login(
	userId varchar(5) primary key,
	userName varchar(100) not null,
	password varchar(100) not null,
	email varchar(30) not null,
	role varchar(10) not null
);
show tables;
desc login;


create table supplier(
	supId varchar(5),
	supName varchar(100) not null,
	supAddress varchar(30)not null,
	supContact varchar(12)not null,
	constraint primary key(supId)
);
show tables;
desc supplier;


create table buy(
	buyingId varchar(5),
	supId varchar(5),
	buyingDate date,
	buyingCost DECIMAL(10, 2),
	constraint primary key(buyingId),
	constraint foreign key(supId) references supplier(supId)
	on delete cascade on update cascade
);
show tables;
desc buy;


create table supplyItem(
	supItemCode varchar(5),
	supItemName varchar(100) not null,
	supItemType varchar(30)not null,
	unitPrice DECIMAL (6, 2),
	constraint primary key(supItemCode)
);
show tables;
desc supplyItem;


create table buyingDetails(
	buyingId varchar(5),
	supItemCode varchar(5),
	buyingQty INT(11),
 	constraint foreign key(buyingId) references buy(buyingId)
	on delete cascade on update cascade,
	constraint foreign key(supItemCode) references supplyItem(supItemCode)
	on delete cascade on update cascade
);
show tables;
desc buyingDetails;


create table garden(
	gardenId varchar(5),
	gardenType varchar(50) not null,
	gardenLocation varchar(100)not null,
	extendOfLand varchar(30)not null,
	description varchar(100)not null,
	constraint primary key(gardenId)
);
show tables;
desc garden;


create table farmer(
	farmerId varchar(5),
	farmerName varchar(100)not null,
	farmerAddress varchar(30)not null,
	farmerContact varchar(12)not null,
	gardenId varchar(5),
	constraint primary key(farmerId),
	constraint foreign key(gardenId) references garden(gardenId)
	on delete cascade on update cascade
);
show tables;
desc farmer;


create table finalProduct(
	finalProductId varchar(5),
	finalProductName varchar(100) not null,
	finalProductType varchar(50) not null,
	qtyOnHand int not null,
	unitPrice DECIMAL (6, 2),
	constraint primary key(finalProductId)
);
show tables;
desc finalProduct;

create table collect(
	collectId varchar(5),
	collectDate date,
	gardenId varchar(5),
	constraint primary key(collectId),
	constraint foreign key(gardenId) references garden(gardenId)
	on delete cascade on update cascade
);
show tables;
desc collect;

create table finalProductDetails(
	finalProductId varchar(5),
	collectId varchar(5),
	productQty INT(11),
	constraint foreign key(finalProductId) references finalProduct(finalProductId)
	on delete cascade on update cascade,
	constraint foreign key(collectId) references collect(collectId)
	on delete cascade on update cascade
);
show tables;
desc finalProductDetails;


create table customer(
   	customerId VARCHAR(6),
   	customerName VARCHAR(30)not null,
   	customerAddress VARCHAR(30)not null,
   	customerContact varchar(12)not null,
	constraint primary key(customerId)
);
show tables;
desc customer;


create table `order`(
	orderId varchar(5),
	orderDate date not null,
	customerId varchar(6),
	orderCost DECIMAL(10, 2),
	constraint primary key(orderId),
	constraint foreign key(customerId) references customer(customerId)
	on delete cascade on update cascade
);
show tables;
desc `order`;


create table orderDetails(
   	finalProductId varchar(5),
   	orderId varchar(5),
  	orderQty int(11),
   	discount double(6, 2),
   	itemTotal decimal(10 ,2),
   	constraint foreign key(finalProductId) references finalProduct(finalProductId)
	on delete cascade on update cascade,
	constraint foreign key(orderId) references `order`(orderId)
	on delete cascade on update cascade
);
show tables;
desc orderDetails;


insert into login values('M001','Dulan',md5('1234'),'dulan@gmail.com','Manager');

insert into login values('R001','Sanda',md5('1234'),'sanda@gmail.com','Reception');


