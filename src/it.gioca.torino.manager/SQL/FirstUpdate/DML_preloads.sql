ALTER TABLE `giocatorino`.`preloads` 
ADD COLUMN `LANGUAGE` VARCHAR(45) NULL AFTER `QUANTITY`;

ALTER TABLE `giocatorino`.`preloads` 
ADD COLUMN `ID_MAINGAME` INT(11) NOT NULL AFTER `LANGUAGE`;