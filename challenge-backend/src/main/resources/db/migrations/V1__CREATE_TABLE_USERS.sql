CREATE TABLE IF NOT EXISTS TBLUSERS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    IDSUPERIOR INT,
    NAME VARCHAR(100) NOT NULL,
    PASSWORDSTRENGTH INT NOT NULL,
    FOREIGN KEY (IDSUPERIOR) REFERENCES TBLUSERS(ID)
);
