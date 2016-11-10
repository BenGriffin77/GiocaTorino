ALTER TABLE `giocatorino`.`boardgame_status` 
DROP FOREIGN KEY `OWNERID`;
ALTER TABLE `giocatorino`.`boardgame_status` 
ADD CONSTRAINT `OWNERID`
  FOREIGN KEY (`OWNERID`)
  REFERENCES `giocatorino`.`users` (`USERID`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
