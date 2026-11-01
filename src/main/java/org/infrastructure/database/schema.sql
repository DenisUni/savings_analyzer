CREATE TABLE User(
   id varchar primary key,
   name varchar not null,
   email varchar not null,
);


CREATE TABLE SaveProfile(
    save_id varchar not null ,
    id varchar not null ,
    name varchar not null,
    savings_rate DECIMAL(4,3) check ( savings_rate between 0 and 1 ) not null,
    primary key (save_id,id),
    FOREIGN KEY (id) references User(id)
);

CREATE TABLE SavingsCategory(
    category_id varchar primary key,
    save_id varchar not null,
    id varchar not null,

    name varchar not null,
    percent DECIMAL(4,3) check ( percent between 0 and 1) not null,
    description TEXT,
    foreign key (save_id,id) references  SaveProfile(save_id,id)
);

CREATE TABLE IncomeSource(
    income_id varchar primary key,
    save_id varchar not null ,
    id varchar not null ,

    name varchar not null,
    amount DECIMAL (10,2) check ( Amount >= 0 ),
    description TEXT,
    active BOOLEAN not null,
    foreign key (save_id,id) references  SaveProfile(save_id,id)
);