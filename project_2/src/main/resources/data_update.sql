INSERT IGNORE INTO `project2`.`user` (`id`, `username`,
`password`, `algorithm`) VALUES 
('1', 'john','{bcrypt}$2a$10$xn3LI/AjqicFYZFruSwve.681477XaVNaUQbr1gioaWPn4t1KsnmG', 'BCRYPT'),
('2', 'jon' ,'{bcrypt}$2a$10$/fUIjDLEJuZBdj/OmD5/tusA2LrLJH5o2nzMXzBwSZdZWzSyfuPC2', 'BCRYPT');


INSERT IGNORE INTO `project2`.`authority` (`id`, `name`, `user`)
VALUES ('1', 'READ', '1');
INSERT IGNORE INTO `project2`.`authority` (`id`, `name`, `user`)
VALUES ('2', 'WRITE', '1');
INSERT IGNORE INTO `project2`.`product` (`id`, `name`, `price`,
`currency`) VALUES ('1', 'Chocolate', '10', 'USD'),
				   ('2', 'Milk', '2', 'USD'), 
				   ('3', 'Cheese', '15', 'USD');