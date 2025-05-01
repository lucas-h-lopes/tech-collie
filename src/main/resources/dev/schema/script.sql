create table if not exists log(
performed_by varchar(200),
action varchar(15),
description TEXT,
event_date timestamp,
affected_table varchar(100),
primary key(performed_by, action, event_date)
);

create or replace procedure insert_into_log(userEmail varchar(200), action varchar(15), description text, affected_table varchar(100))
begin atomic
    insert into log (performed_by, action, description, event_date, affected_table) values (userEmail, action, description, now(), affected_table);
end;
