CREATE TABLE Scanner(
    id INT AUTO_INCREMENT PRIMARY KEY,
    sname VARCHAR(64) UNIQUE NOT NULL,
    pass CHAR(64) NOT NULL,
    status INT
    );

-- Represent a single word (name part, word, ...)
-- in lower case
CREATE TABLE Word(
    id INT AUTO_INCREMENT PRIMARY KEY,          -- unique id
    word VARCHAR(64) UNIQUE                     -- word value
    );

-- Represent fb person
CREATE TABLE Pers (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,	-- unique id
    idstr VARCHAR(64) UNIQUE,			-- fb identifier
    rname VARCHAR(64),				-- person name
    sexe BOOL,					-- sexe (true=M)
    birth DATETIME,				-- birth date
    lastscan DATETIME                           -- last time this person have been scanned (NULL if not)
    );

-- person name as a list of words
CREATE TABLE Namewpart(
    id INT,                                     -- person id
    word INT,                                   -- word id
    place INT,                                  -- place in the name
    PRIMARY KEY (id, word, place),
    FOREIGN KEY (id) REFERENCES Pers(id)
        ON DELETE CASCADE,
    FOREIGN KEY (word) REFERENCES Word(id)
        ON DELETE CASCADE
    );

-- Account in scan
CREATE TABLE InScan(
    acc INT PRIMARY KEY,                        -- account id
    start DATETIME,                             -- date time of the begening of the scan
    scanner INT,                                -- scanner account
    FOREIGN KEY (acc) REFERENCES Pers(id)
        ON DELETE CASCADE,
    FOREIGN KEY (scanner) REFERENCES Scanner(id)
        ON DELETE CASCADE
    );

-- Represent a language
CREATE TABLE Lang(
    id INT AUTO_INCREMENT PRIMARY KEY,          -- language ID
    lname VARCHAR(64) UNIQUE                    -- language name
    );

-- Language spoken by a person
CREATE TABLE Speak(
    idpers INT,                                 -- person id
    idlang INT,                                 -- language id
    PRIMARY KEY (idpers, idlang),
    FOREIGN KEY (idpers) REFERENCES Pers(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idlang) REFERENCES Lang(id)
        ON DELETE CASCADE
    );

-- Friend relation between two persons
CREATE TABLE Friend (
    idpers INTEGER,				-- first peson
    idfrd INTEGER,				-- second person
    PRIMARY KEY (idpers, idfrd),
    FOREIGN KEY (idpers) REFERENCES Pers(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idfrd) REFERENCES Pers(id)
        ON DELETE CASCADE
    );

-- a comment
CREATE TABLE Comments(
    id INT AUTO_INCREMENT PRIMARY KEY,          -- unique comment id
    postby INT,                                 -- comment poster
    datep DATETIME,                             -- date of the post
    txt TEXT,                                   -- comment value
    FOREIGN KEY (postby) REFERENCES Pers(id)
        ON DELETE CASCADE
    );

-- word list of comment value
CREATE TABLE Comwpart(
    id INT,                                     -- comment id
    word INT,                                   -- word id
    place INT,                                  -- rank in the comment
    PRIMARY KEY (id, word, place),
    FOREIGN KEY (id) REFERENCES Comments(id)
        ON DELETE CASCADE,
    FOREIGN KEY (word) REFERENCES Word(id)
        ON DELETE CASCADE
    );

-- a like on a comment
CREATE TABLE Comlike(
    id INT,                                     -- comment id
    pers INT,                                   -- person id
    PRIMARY KEY (id, pers),
    FOREIGN KEY (id) REFERENCES Comments(id)
        ON DELETE CASCADE,
    FOREIGN KEY (pers) REFERENCES Pers(id)
        ON DELETE CASCADE
    );

-- image posted
CREATE TABLE Photo(
    id INT AUTO_INCREMENT PRIMARY KEY,          -- unique id
    postby INT,                                 -- poster id
    datep DATETIME,                             -- date of the post
    img	VARCHAR(255) UNIQUE,                    -- image url
    descr TEXT,                                 -- image description
    FOREIGN KEY (postby) REFERENCES Pers(id)
        ON DELETE CASCADE
    );

-- word list of photo description
CREATE TABLE Phwpart(
    id INT,                                     -- photo id
    word INT,                                   -- word id
    place INT,                                  -- rank in the description
    PRIMARY KEY (id, word, place),
    FOREIGN KEY (id) REFERENCES Photo(id)
        ON DELETE CASCADE,
    FOREIGN KEY (word) REFERENCES Word(id)
        ON DELETE CASCADE
    );

-- person who like a photo
CREATE TABLE Phlike(
    id INTEGER,                                 -- image id
    pers INTEGER,                               -- person id
    PRIMARY KEY (id, pers),
    FOREIGN KEY (id) REFERENCES Photo(id)
        ON DELETE CASCADE,
    FOREIGN KEY (pers) REFERENCES Pers(id)
        ON DELETE CASCADE
    );

-- person referenced in a photo
CREATE TABLE Phrefs(
    id INT,                                     -- image id
    pers INT,                                   -- person id
    PRIMARY KEY (id, pers),
    FOREIGN KEY (id) REFERENCES Photo(id)
        ON DELETE CASCADE,
    FOREIGN KEY (pers) REFERENCES Pers(id)
        ON DELETE CASCADE
    );

-- comment on a photo
CREATE TABLE Phcoms(
    id INT,                                     -- photo id
    com INT,                                    -- comment id
    PRIMARY KEY (id, com),
    FOREIGN KEY (id) REFERENCES Photo(id)
        ON DELETE CASCADE,
    FOREIGN KEY (com) REFERENCES Comments(id)
        ON DELETE CASCADE
    );

-- place in the world
CREATE TABLE Place(
    id INTEGER AUTO_INCREMENT PRIMARY KEY,	-- unique id
    pname VARCHAR(64) UNIQUE,                   -- place name
    typeent VARCHAR(64)                         -- type
    );
		
-- place where person live(ed)
CREATE TABLE Live(
    idpers INTEGER,                             -- person id
    idplace INTEGER,                            -- place id
    PRIMARY KEY (idpers, idplace),
    FOREIGN KEY (idpers) REFERENCES Pers(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idplace) REFERENCES Place(id)
        ON DELETE CASCADE
    );
		
-- Place where people work
CREATE TABLE Workat(
    idpers INTEGER,                             -- person id
    idplace INTEGER,                            -- workplace id
    PRIMARY KEY (idpers, idplace),
    FOREIGN KEY (idpers) REFERENCES Pers(id)
        ON DELETE CASCADE,
    FOREIGN KEY (idplace) REFERENCES Place(id)
        ON DELETE CASCADE
    );