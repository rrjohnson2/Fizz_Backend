insert into member (id, email, first_name, last_name, salt, salty_password, username) values
(102, 'email@email.com', 'Roice', 'Johnson', 'HeA9ekya8FQ7TlQSs9sYgmNTgxfOwN+UJvht/P0Sri8=', 'HeA9ekya8FQ7TlQSs9sYgmNTgxfOwN+UJvht/P0Sri8=HpeaAs9sekya8FQ7TlQSs9sYgmNTgxfOwN+UJvht/P0Sri8=HeA9ekya8FQ7TlQSs9sYgmNTgxfOwN+UJvht/P0Sri8=', 'appleseed'),
(152, 'k@j.com', 'Grace', 'Johnson', 'ut5rJRo+0dRmuWJlgigPinJyq3y7lD0lPwzaCT/Tp78=', 'ut5rJRo+0dRmuWJlgigPinJyq3y7lD0lPwzaCT/Tp78=upta5srsJRo+0dRmuWJlgigPinJyq3y7lD0lPwzaCT/Tp78=ut5rJRo+0dRmuWJlgigPinJyq3y7lD0lPwzaCT/Tp78=', 'pomseed');

insert into preference (id, category, weight, owner_id) values
(1001, 'MOVIES', 0, 102),
(1002, 'MOVIES', 0, 152);

insert into idea( id, description, timestamp, title, creator) values
(2001, 'A man has to travel tru a wormhole to save his moms dog for an broken avenger', '2019-12-28 14:33:45', 'Movie Idea', 102),
(2002, 'How to get away eith lifer ', '2019-12-28 14:46:21', 'Sum Flick',152);

insert into focus (id, category, idea_id) values
(301, 'MOVIES', 2001),
(302, 'TECHNOLOGY', 2001),
(304, 'MOVIES', 2002),
(305, 'TECHNOLOGY', 2002);

insert into retort (id,content,timestamp,creator,idea) values
(4001,'some of the pit holes people fall in to are blah blah blah so watch out for those, other than that good idea','2019-12-28 14:50:02',102,2002);

insert into rating (id,vote,creator_id,idea_id) values
(5001,'UP',102,2002);

insert into message (id,content,timestamp,creator_id,retort_id) values
(4001,'Thanks you for the feedback','2019-12-28 14:56:35',102,4001);
