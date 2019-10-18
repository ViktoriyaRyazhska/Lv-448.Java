INSERT INTO public.audiences(name)
VALUES ('Leonardo da Vinci'),
       ('Pictures of the Middle Ages'),
       ('Sculptures');


INSERT INTO public.autors(first_name, last_name)
VALUES ('Leonardo', 'da Vinci'),
       ('Francesco', 'Melzi'),
       ('Bernardino', 'Luini'),
       ('Auguste', 'Rodin');


INSERT INTO public.exhibits(id, type, material, techic, audience_id, name)
VALUES (1, 'PAINTING', null, 'Oils', '1', 'Mona Lisa'),
       (2, 'PAINTING', null, 'Oils', '1', 'Madonna Litta'),
       (3, 'PAINTING', null, 'Watercolor', '2', 'Female Saint'),
       (4, 'PAINTING', null, 'Pencil', '2', 'Flora'),
       (5, 'SCULPTURE', 'Bronze', null, '3', 'A man with a broken nose'),
       (6, 'SCULPTURE', 'Stone', null, '3', 'Bronze Age'),
       (7, 'SCULPTURE', 'Clay', null, '2', 'Stone Age Gold'),
       (8, 'SCULPTURE', 'Bronze', null, '3', 'Clay Bronze Age');


INSERT INTO public.autor_exhibit(
    autor_id, exhibit_id)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (1, 3),
       (3, 4),
       (4, 5),
       (4, 6);


INSERT INTO public.employees(id, first_name, last_name, "position", login, password, audience_id)
VALUES
(1, 'Anna', 'Kentor', 'MANAGER', 'a_kentor', 'anna1230', null),
(2, 'Bogdan', 'Korty', 'AUDIENCE_MANAGER', 'Bogdan_Korty', '_Korty59', 1),
(3, 'Bill', 'Kell', 'AUDIENCE_MANAGER', 'Kell_007', 'Bond_007', 2),
(4, 'Jeck', 'Loper', 'AUDIENCE_MANAGER', 'loper_79', '123456qwe', 3),
(5, 'Tom', 'Vagik', 'TOUR_GUIDE', 'Vagik2005', '1Best', null),
(6, 'Angela', 'Sadik', 'TOUR_GUIDE', 'angel', 'IamAngel', null),
(7, 'Julia', 'Karenge', 'TOUR_GUIDE', 'Karenge_j', 'hello_world', null);


INSERT INTO public.excursion(id, name)
VALUES (1, 'Golden Spring'),
       (2, 'Da Vinci Demons'),
       (3, 'Inferno'),
       (4, 'Angels and Deamons');

INSERT INTO public.timetable(id, employee_id, excursion_id, date_start, date_end)
VALUES
(1, 1, 1, '2019-07-02 12:10:00', '2019-07-02 13:10:00'),
(2, 2, 2, '2019-07-02 12:10:00', '2019-07-02 13:10:00'),
(3, 3, 3, '2019-07-02 12:10:00', '2019-07-02 13:10:00'),
(4, 4, 4, '2019-07-03 14:10:00', '2019-07-03 15:10:00'),
(5, 5, 1, '2019-07-03 14:30:00', '2019-07-03 15:30:00'),
(6, 6, 2, '2019-07-03 14:30:00', '2019-07-03 15:30:00'),
(7, 7, 3, '2019-07-04 19:30:00', '2019-07-04 20:30:00'),
(8, 5, 4, '2019-07-05 17:30:00', '2019-07-05 18:30:00');