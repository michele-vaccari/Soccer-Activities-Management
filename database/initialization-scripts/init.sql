CREATE TABLE IF NOT EXISTS SAM.User (
	Id INT CHECK (Id > 0) NOT NULL,
	Email VARCHAR(255) NOT NULL,
	Password VARCHAR(255) NOT NULL,
	Name VARCHAR(255) NOT NULL,
	Surname VARCHAR(255) NOT NULL,
	Role VARCHAR(255) NOT NULL,
	Active VARCHAR(1) NOT NULL DEFAULT 'Y',
	PRIMARY KEY (Id)
);

CREATE TABLE IF NOT EXISTS SAM.Admin_User (
	Id INT CHECK (Id > 0) NOT NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY (Id) REFERENCES SAM.User(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Registered_User (
	Id INT CHECK (Id > 0) NOT NULL,
	Admin_user_id INT CHECK (Admin_user_id > 0) NOT NULL,
	Phone VARCHAR(15) NOT NULL,
	Address VARCHAR(255) NOT NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY (Id) REFERENCES SAM.User(Id),
	FOREIGN KEY (Admin_user_id) REFERENCES SAM.Admin_User(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Referee (
	Id INT CHECK (Id > 0) NOT NULL,
	Birth_date VARCHAR(10) NOT NULL,
	Citizenship VARCHAR(255) NOT NULL,
	Resume CLOB,
	PRIMARY KEY (Id),
	FOREIGN KEY (Id) REFERENCES SAM.Registered_User(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Team_Manager (
	Id INT CHECK (Id > 0) NOT NULL,
	PRIMARY KEY (Id),
	FOREIGN KEY (Id) REFERENCES SAM.Registered_User(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Tournament (
	Id INT CHECK (Id > 0) NOT NULL,
	Admin_user_id INT CHECK (Admin_user_id > 0) NOT NULL,
	Type CHAR(1) NOT NULL,
	Name VARCHAR(255) NOT NULL,
	Description CLOB,
	PRIMARY KEY (Id),
	FOREIGN KEY (Admin_user_id) REFERENCES SAM.Admin_User(Id)
);


CREATE TABLE IF NOT EXISTS SAM.Match (
	Id INT CHECK (Id > 0) NOT NULL,
	Type VARCHAR(255) NOT NULL,
	Place VARCHAR(255) NOT NULL,
	Date VARCHAR(255) NOT NULL,
	Time VARCHAR(255) NOT NULL,
	PRIMARY KEY (Id)
);

CREATE TABLE IF NOT EXISTS SAM.Report (
	Id INT CHECK (Id > 0) NOT NULL,
	Match_id INT CHECK (Match_id > 0) NOT NULL,
	Referee_id INT CHECK (Referee_id > 0) NOT NULL,
	Match_start_time VARCHAR(5),
	Match_end_time VARCHAR(5),
	Result VARCHAR(10),
	PRIMARY KEY (Id),
	FOREIGN KEY (Match_id) REFERENCES SAM.Match(Id),
	FOREIGN KEY (Referee_id) REFERENCES SAM.Referee(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Team (
	Id INT CHECK (Id > 0) NOT NULL,
	Team_manager_id INT CHECK (Team_manager_id > 0) NOT NULL,
	Name VARCHAR(255) NOT NULL,
	Description CLOB,
	Headquarters VARCHAR(255),
	Sponsor_name VARCHAR(255),
	PRIMARY KEY (Id),
	FOREIGN KEY (Team_manager_id) REFERENCES SAM.Team_Manager(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Ranking (
	Tournament_id INT CHECK (Tournament_id > 0) NOT NULL,
	Team_id INT CHECK (Team_id > 0) NOT NULL,
	Score INT CHECK (Score >= 0) DEFAULT 0,
	Won_matches INT CHECK (Won_matches >= 0) DEFAULT 0,
	Lost_matches INT CHECK (Lost_matches >= 0) DEFAULT 0,
	Tied_matches INT CHECK (Tied_matches >= 0) DEFAULT 0,
	Played_matches INT CHECK (Played_matches >= 0) DEFAULT 0,
	Goals_made INT CHECK (Goals_made >= 0) DEFAULT 0,
	Goals_suffered INT CHECK (Goals_suffered >= 0) DEFAULT 0,
	PRIMARY KEY (Tournament_id, Team_id),
	FOREIGN KEY (Tournament_id) REFERENCES SAM.Tournament(Id),
	FOREIGN KEY (Team_id) REFERENCES SAM.Team(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Player (
	Id INT CHECK (Id > 0) NOT NULL,
	Team_id INT CHECK (Team_id > 0) NOT NULL,
	Active VARCHAR(1) DEFAULT 'Y',
	Jersey_number INT CHECK (Jersey_number > 0),
	Role VARCHAR(255) NOT NULL,
	Name VARCHAR(255) NOT NULL,
	Surname VARCHAR(255) NOT NULL,
	Birth_date VARCHAR(10) NOT NULL,
	Citizenship VARCHAR(255) NOT NULL,
	Description CLOB,
	Goal INT CHECK (Goal >= 0) DEFAULT 0,
	Admonitions INT CHECK (Admonitions >= 0) DEFAULT 0,
	Ejections INT CHECK (Ejections >= 0) DEFAULT 0,
	PRIMARY KEY (Id),
	FOREIGN KEY (Team_id) REFERENCES SAM.Team(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Player_Report (
	Report_id INT CHECK (Report_id > 0) NOT NULL,
	Player_id INT CHECK (Player_id > 0) NOT NULL,
	Goal INT CHECK (Goal >= 0) DEFAULT 0,
    Admonitions INT CHECK (Admonitions >= 0) DEFAULT 0,
	Ejection VARCHAR(1),
	PRIMARY KEY (Report_id, Player_id),
	FOREIGN KEY (Report_id) REFERENCES SAM.Report(Id),
	FOREIGN KEY (Player_id) REFERENCES SAM.Player(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Tournament_Team (
	Team_id INT CHECK (Team_id > 0) NOT NULL,
	Tournament_id INT CHECK (Tournament_id > 0) NOT NULL,
	PRIMARY KEY (Team_id, Tournament_id),
	FOREIGN KEY (Team_id) REFERENCES SAM.Team(Id),
	FOREIGN KEY (Tournament_id) REFERENCES SAM.Tournament(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Admin_User_Referee_Match (
	Match_id INT CHECK (Match_id > 0) NOT NULL,
	Referee_id INT CHECK (Referee_id > 0) NOT NULL,
	Admin_user_id INT CHECK (Admin_user_id > 0) NOT NULL,
	PRIMARY KEY (Match_id, Referee_id, Admin_user_id),
	FOREIGN KEY (Match_id) REFERENCES SAM.Team(Id),
	FOREIGN KEY (Referee_id) REFERENCES SAM.Referee(Id),
	FOREIGN KEY (Admin_user_id) REFERENCES SAM.Admin_User(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Team_Player_Report (
	Report_id INT CHECK (Report_id > 0) NOT NULL,
	Team_id INT CHECK (Team_id > 0) NOT NULL,
	Player_id INT CHECK (Player_id > 0) NOT NULL,
	Reserve VARCHAR(1) NOT NULL,
	PRIMARY KEY (Report_id, Team_id, Player_id),
	FOREIGN KEY (Report_id) REFERENCES SAM.Report(Id),
	FOREIGN KEY (Team_id) REFERENCES SAM.Team(Id),
	FOREIGN KEY (Player_id) REFERENCES SAM.Player(Id)
);

CREATE TABLE IF NOT EXISTS SAM.Tournament_Team_Match (
	Match_id INT CHECK (Match_id > 0) NOT NULL,
	Team_id INT CHECK (Team_id > 0) NOT NULL,
	Other_team_id INT CHECK (Other_team_id > 0) NOT NULL,
	Tournament_id INT CHECK (Tournament_id > 0) NOT NULL,
	Match_name VARCHAR(30) NOT NULL,
	PRIMARY KEY (Match_id, Team_id, Other_team_id, Tournament_id),
	FOREIGN KEY (Match_id) REFERENCES SAM.Match(Id),
	FOREIGN KEY (Team_id) REFERENCES SAM.Team(Id),
	FOREIGN KEY (Other_team_id) REFERENCES SAM.Team(Id),
	FOREIGN KEY (Tournament_id) REFERENCES SAM.Tournament(Id)
);