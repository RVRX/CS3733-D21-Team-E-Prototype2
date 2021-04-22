-- Data Definition Language: Create, Alter, Drop, Rename, Truncate, Comment

Drop Table node;

Drop View patientAccount;

Create Table node
(
	nodeID    varchar(31) Primary Key,
	xCoord    int        Not Null,
	yCoord    int        Not Null,
	floor     varchar(5) Not Null,
	building  varchar(20),
	nodeType  varchar(10),
	longName  varchar(100),
	shortName varchar(100),
	Unique (xCoord, yCoord, floor),
	Constraint floorLimit Check (floor In ('1', '2', '3', 'L1', 'L2')),
	Constraint buildingLimit Check (building In ('BTM', '45 Francis', 'Tower', '15 Francis', 'Shapiro', 'Parking')),
	Constraint nodeTypeLimit Check (nodeType In ('PARK', 'EXIT', 'WALK', 'HALL', 'CONF', 'DEPT', 'ELEV', 'INFO',
	                                             'LABS', 'REST', 'RETL', 'STAI', 'SERV', 'ELEV', 'BATH'))
);


Create Table hasEdge
(
	edgeID    varchar(63) Primary Key,
	startNode varchar(31) Not Null References node (nodeID) On Delete Cascade,
	endNode   varchar(31) Not Null References node (nodeID) On Delete Cascade,
	length    float,
	Unique (startNode, endNode)
);

Create Table userAccount
(
	userID    Int Primary Key,
	email     Varchar(31) Unique Not Null,
	password  Varchar(31)        Not Null,
	userType  Varchar(31),
	firstName Varchar(31),
	lastName  Varchar(31),
	Constraint userIDLimit Check ( userID != 0 ),
	Constraint passwordLimit Check (Length(password) >= 8 ),
	Constraint userTypeLimit Check (userType In ('visitor', 'patient', 'doctor', 'admin'))
);



Create View visitorAccount As
Select *
From userAccount
Where userType = 'visitor';

Create View patientAccount As
Select *
From userAccount
Where userType = 'patient';

Create View doctorAccount As
Select *
From userAccount
Where userType = 'doctor';

Create View adminAccount As
Select *
From userAccount
Where userType = 'admin';


Create Table requests
(
	requestID     int Primary Key,
	creatorID     int References userAccount On Delete Cascade,
--                          creationTime timestamp,
	requestType   varchar(31),
	requestStatus varchar(10),
	assignee      varchar(31),
	Constraint requestTypeLimit Check (requestType In
	                                   ('floral', 'medDelivery', 'sanitation', 'security', 'extTransport')),
	Constraint requestStatusLimit Check (requestStatus In ('complete', 'canceled', 'inProgress'))
);


Create Table floralRequests
(
	requestID     int Primary Key References requests On Delete Cascade,
	roomID        varchar(31) References node On Delete Cascade,
	recipientName varchar(31),
	flowerType    varchar(31),
	flowerAmount  int,
	vaseType      varchar(31),
	message       varchar(5000),
	Constraint flowerTypeLimit Check (flowerType In ('Roses', 'Tulips', 'Carnations', 'Assortment')),
	Constraint flowerAmountLimit Check (flowerAmount In (1, 6, 12)),
	Constraint vaseTypeLimit Check (vaseType In ('Round', 'Square', 'Tall', 'None'))
);


Create Table sanitationRequest
(
	requestID      int Primary Key References requests On Delete Cascade,
	roomID         varchar(31) Not Null References node On Delete Cascade,
	signature      varchar(31) Not Null,
	description    varchar(5000),
	sanitationType varchar(31),
	urgency        varchar(31) Not Null,
	Constraint sanitationTypeLimit Check (sanitationType In
	                                      ('Urine Cleanup', 'Feces Cleanup', 'Preparation Cleanup', 'Trash Removal')),
	Constraint urgencyTypeLimit Check (urgency In ('Low', 'Medium', 'High', 'Critical'))
);


Create Table extTransport
(
	requestID   int Primary Key References requests On Delete Cascade,
	roomID      varchar(31)  Not Null References node On Delete Cascade,
	requestType varchar(100) Not Null,
	severity    varchar(30)  Not Null,
	patientID   int          Not Null,
	ETA         varchar(100),
	description varchar(5000),
	Constraint requestTypeLimitExtTrans Check (requestType In ('Ambulance', 'Helicopter', 'Plane'))
);


Create Table medDelivery
(
	requestID           int Primary Key References requests On Delete Cascade,
	roomID              varchar(31) Not Null References node On Delete Cascade,
	medicineName        varchar(31) Not Null,
	quantity            int         Not Null,
	dosage              varchar(31) Not Null,
	specialInstructions varchar(5000),
	signature           varchar(31) Not Null
);

Create Table securityServ
(
	requestID int Primary Key References requests On Delete Cascade,
	roomID    varchar(31) Not Null References node On Delete Cascade,
	level     varchar(31),
	urgency   varchar(31) Not Null,
	Constraint urgencyTypeLimitServ Check (urgency In ('Low', 'Medium', 'High', 'Critical'))
);