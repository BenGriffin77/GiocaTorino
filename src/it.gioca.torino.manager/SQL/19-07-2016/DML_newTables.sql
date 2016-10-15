CREATE TABLE `giocatorino`.`alternativenames` (
  `ID_GAME` INT NOT NULL,
  `NAME` VARCHAR(1000) NOT NULL,
  PRIMARY KEY (`ID_GAME`),
  INDEX `A_NOME_IND` (`NAME`(6) ASC),
  CONSTRAINT `ID_GAME_AN`
    FOREIGN KEY (`ID_GAME`)
    REFERENCES `giocatorino`.`boardgames` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

ALTER TABLE `giocatorino`.`alternativenames` 
DROP FOREIGN KEY `ID_GAME_AN`;
ALTER TABLE `giocatorino`.`alternativenames` 
DROP PRIMARY KEY;

CREATE TABLE `giocatorino`.`boardgame_designer` (
  `ID_DESIGNER` INT NOT NULL,
  `DESIGNER_NAME` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`ID_DESIGNER`),
  INDEX `DESIGNER_INDEX` (`DESIGNER_NAME` ASC));
  
  
CREATE TABLE `giocatorino`.`designer_fkey` (
  `ID_GAME` INT NOT NULL,
  `ID_DESIGNER` INT NOT NULL,
  UNIQUE INDEX `DESIGNER_KEY` (`ID_GAME` ASC, `ID_DESIGNER` ASC),
  INDEX `DESIGNER_ID_idx` (`ID_DESIGNER` ASC),
  CONSTRAINT `DESIGNER_ID_GAME`
    FOREIGN KEY (`ID_GAME`)
    REFERENCES `giocatorino`.`boardgames` (`ID`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `DESIGNER_ID`
    FOREIGN KEY (`ID_DESIGNER`)
    REFERENCES `giocatorino`.`boardgame_designer` (`ID_DESIGNER`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
	
	ALTER TABLE `giocatorino`.`boardgame_designer` 
DROP INDEX `DESIGNER_INDEX` ,
ADD INDEX `DESIGNER_INDEX` (`DESIGNER_NAME`(6) ASC);