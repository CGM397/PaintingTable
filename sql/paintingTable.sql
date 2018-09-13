DROP DATABASE IF EXISTS `paintingTable`;
CREATE DATABASE `paintingTable` DEFAULT CHARACTER SET utf8;
USE `paintingTable`;

DROP TABLE IF EXISTS `picture`;
CREATE TABLE `picture` (
  `pictureId` VARCHAR(64) NOT NULL,
  `allPoints` TEXT NOT NULL,
  `tags` TEXT,
  PRIMARY KEY (`pictureId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `pictureTag`;
CREATE TABLE `pictureTag` (
  `pictureId` VARCHAR(64) NOT NULL,
  `tagId` VARCHAR(64) NOT NULL,
  `border` TEXT NOT NULL,
  `drawingType` VARCHAR(64),
  PRIMARY KEY (`pictureId`, `tagId`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;