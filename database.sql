-- create database Ecommerce;
USE Ecommerce;

CREATE TABLE Category (
    categoryID VARCHAR(36) PRIMARY KEY,
    categoryName VARCHAR(255) NOT NULL
);

CREATE TABLE Product (
    productID VARCHAR(36) PRIMARY KEY,         
    productName VARCHAR(255) NOT NULL,      
    description TEXT,                 
    country VARCHAR(100),                   
    rating FLOAT,                         
    discount INT,                            
    price FLOAT NOT NULL,                 
    stock INT,                             
    categoryID VARCHAR(36),                    
    FOREIGN KEY (categoryID) REFERENCES Category(categoryID)  
);

CREATE TABLE ProductImage (
    productID VARCHAR(36),
    imageLink VARCHAR(255) NOT NULL,
    PRIMARY KEY (productID, imageLink),
    FOREIGN KEY (productID) REFERENCES Product(productID)
);

CREATE TABLE User (
    userID VARCHAR(36) PRIMARY KEY,
    FName VARCHAR(255) NOT NULL,
    MName VARCHAR(255),
    LName VARCHAR(255) NOT NULL,
    dob DATE NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    gender VARCHAR(10),
    phone VARCHAR(20) NOT NULL
);

CREATE TABLE Address (
    addressID VARCHAR(36),
    personID VARCHAR(36),
    streetName VARCHAR(255) NOT NULL,
    cityName VARCHAR(255) NOT NULL,
    districtName VARCHAR(255),
    pincode VARCHAR(20) NOT NULL,
    countryName VARCHAR(255) NOT NULL,
    PRIMARY KEY (addressID, personID),
    FOREIGN KEY (personID) REFERENCES User(userID)
);

CREATE TABLE Notifications (
    notificationsID VARCHAR(36),
    userID VARCHAR(36),
    message VARCHAR(255) NOT NULL,
    PRIMARY KEY(userID,notificationsID),
    FOREIGN KEY (userID) REFERENCES User(userID)
);

CREATE TABLE CustomerCare (
    customerCareID VARCHAR(36),
    userID VARCHAR(36),
    priority ENUM('HIGH', 'MEDIUM', 'LOW') NOT NULL,
    issue VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    status ENUM('SUBMITTED', 'IN_PROGRESS', 'RESOLVED') NOT NULL,
    filedTime DATETIME NOT NULL,
	PRIMARY KEY(customerCareID,userID),
    FOREIGN KEY (userID) REFERENCES User(userID)
);

CREATE TABLE Orders (
    orderID VARCHAR(36) PRIMARY KEY,
    productID VARCHAR(36),
    userID VARCHAR(36),
    orderDate DATE NOT NULL,
    FOREIGN KEY (productID) REFERENCES Product(productID),
    FOREIGN KEY (userID) REFERENCES User(userID)
);

CREATE TABLE Tracking (
    trackingID VARCHAR(36) PRIMARY KEY,
    orderID VARCHAR(36),
    trackingStatus ENUM('SHIPPED', 'IN_TRANSIT', 'OUT_FOR_DELIVERY', 'DELIVERED') NOT NULL,
    FOREIGN KEY (orderID) REFERENCES Orders(orderID)
);

CREATE TABLE Payment (
    paymentID VARCHAR(36) PRIMARY KEY,
    orderID VARCHAR(36),
    paymentTime DATETIME NOT NULL,
    paymentMode ENUM('CASH_ON_DELIVERY', 'UPI', 'NET_BANKING') NOT NULL,
    FOREIGN KEY (orderID) REFERENCES Orders (orderID)
);

CREATE TABLE Refund (
    refundID VARCHAR(36) PRIMARY KEY,
    refundTime DATETIME NOT NULL,
    reason VARCHAR(255) NOT NULL,
    amount FLOAT NOT NULL,
    paymentID VARCHAR(36),
    FOREIGN KEY (paymentID) REFERENCES Payment(paymentID)
);

CREATE TABLE WishList (
    wishlistID VARCHAR(36),
    productID VARCHAR(36),
    addTime DATETIME NOT NULL,
    userID VARCHAR(36),
    PRIMARY KEY(wishlistID,productID),
    FOREIGN KEY (productID) REFERENCES Product(productID),
    FOREIGN KEY (userID) REFERENCES User(userID)
);

CREATE TABLE Cart (
    cartID VARCHAR(36),
    productID VARCHAR(36),
    addTime DATE NOT NULL,
    userID VARCHAR(36),
    quantity INT NOT NULL,
    PRIMARY KEY(cartID,productID),
    FOREIGN KEY (productID) REFERENCES Product(productID),
    FOREIGN KEY (userID) REFERENCES User(userID)
);

CREATE TABLE Review (
    reviewID VARCHAR(36) PRIMARY KEY,
    productID VARCHAR(36),
    comment VARCHAR(255),
    rating INT CHECK (rating >= 1 AND rating <= 5),
    reviewTime DATETIME NOT NULL,
    userID VARCHAR(36),
    FOREIGN KEY (productID) REFERENCES Product(productID),
    FOREIGN KEY (userID) REFERENCES User(userID)
);

CREATE TABLE ReviewImage (
    reviewID VARCHAR(36),
    image VARCHAR(255),
    PRIMARY KEY (reviewID,image),
    FOREIGN KEY (reviewID) REFERENCES Review(reviewID)
);

CREATE TABLE Coupons (
    couponID VARCHAR(36) PRIMARY KEY,
    usageLimit INT NOT NULL,
    couponCode VARCHAR(50) NOT NULL UNIQUE,
    validFrom DATE NOT NULL,
    validTo DATE NOT NULL,
    discount INT NOT NULL
);

CREATE TABLE CouponUsability (
    couponID VARCHAR(36),
    productID VARCHAR(36),
    PRIMARY KEY (couponID, productID),
    FOREIGN KEY (couponID) REFERENCES Coupons(couponID),
    FOREIGN KEY (productID) REFERENCES Product(productID)
);

CREATE TABLE Supplier (
    supplierID VARCHAR(36) PRIMARY KEY,
    FName VARCHAR(50) NOT NULL,
    MName VARCHAR(50),
    LName VARCHAR(50) NOT NULL,
    phone VARCHAR(15) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    addressID VARCHAR(36)
);

CREATE TABLE SuppliesList (
    supplierID VARCHAR(36),
    productID VARCHAR(36),
    PRIMARY KEY (supplierID, productID),
    FOREIGN KEY (supplierID) REFERENCES Supplier(supplierID),
    FOREIGN KEY (productID) REFERENCES Product(productID)
);










