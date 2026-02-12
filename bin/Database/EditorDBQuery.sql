CREATE DATABASE realeditor;
Use realeditor

CREATE TABLE `files` (
	`fileid` INT(11) NOT NULL AUTO_INCREMENT,
	`fileName` VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
	`fileHash` VARCHAR(255) NOT NULL COLLATE 'utf8mb4_general_ci',
	`dateCreated` TIMESTAMP NOT NULL DEFAULT current_timestamp(),
	`lastModified` TIMESTAMP NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
	PRIMARY KEY (`fileid`) USING BTREE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=70
;
CREATE TABLE `pages` (
	`pageId` INT(11) NOT NULL AUTO_INCREMENT,
	`fileId` INT(11) NOT NULL,
	`pageNumber` INT(11) NOT NULL,
	`pageContent` LONGTEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`pageId`) USING BTREE,
	UNIQUE INDEX `file_id` (`fileId`, `pageNumber`) USING BTREE,
	CONSTRAINT `pages_ibfk_1` FOREIGN KEY (`fileId`) REFERENCES `files` (`fileid`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=1363
;
CREATE TABLE `transliteratedpages` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pageId` INT(11) NOT NULL,
	`transliteratedText` LONGTEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `page_id` (`pageId`) USING BTREE,
	CONSTRAINT `transliteratedpages_ibfk_1` FOREIGN KEY (`pageId`) REFERENCES `pages` (`pageId`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=32
;

CREATE TABLE `lemmatization` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pageId` INT(11) NOT NULL,
	`word` LONGTEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`lemma` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `page_id` (`id`, `pageId`) USING BTREE,
	INDEX `lemmatization_ibfk_1` (`pageId`) USING BTREE,
	CONSTRAINT `lemmatization_ibfk_1` FOREIGN KEY (`pageId`) REFERENCES `pages` (`pageId`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14573
;

CREATE TABLE `pos` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pageId` INT(11) NOT NULL,
	`word` LONGTEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`pos` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `page_id` (`id`, `pageId`) USING BTREE,
	INDEX `pos_ibfk_1` (`pageId`) USING BTREE,
	CONSTRAINT `pos_ibfk_1` FOREIGN KEY (`pageId`) REFERENCES `pages` (`pageId`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=613
;

CREATE TABLE `rootextraction` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pageId` INT(11) NOT NULL,
	`word` LONGTEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`root` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `page_id` (`id`, `pageId`) USING BTREE,
	INDEX `rootExtraction_ibfk_1` (`pageId`) USING BTREE,
	CONSTRAINT `rootExtraction_ibfk_1` FOREIGN KEY (`pageId`) REFERENCES `pages` (`pageId`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14506
;

CREATE TABLE `stemmation` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pageId` INT(11) NOT NULL,
	`word` LONGTEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`stem` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `page_id` (`id`, `pageId`) USING BTREE,
	INDEX `stemmation_ibfk_1` (`pageId`) USING BTREE,
	CONSTRAINT `stemmation_ibfk_1` FOREIGN KEY (`pageId`) REFERENCES `pages` (`pageId`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14460
;

CREATE TABLE `wordsegementation` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pageId` INT(11) NOT NULL,
	`word` LONGTEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`segment` LONGTEXT NULL DEFAULT NULL COLLATE 'utf8mb4_general_ci',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `page_id` (`id`, `pageId`) USING BTREE,
	INDEX `wordSegementation_ibfk_1` (`pageId`) USING BTREE,
	CONSTRAINT `wordSegementation_ibfk_1` FOREIGN KEY (`pageId`) REFERENCES `pages` (`pageId`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14475
;

CREATE TABLE `pkl` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pageId` INT(11) NOT NULL,
	`word` LONGTEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`pklScore` DOUBLE NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `page_id` (`id`, `pageId`) USING BTREE,
	INDEX `pkl_ibfk_1` (`pageId`) USING BTREE,
	CONSTRAINT `pkl_ibfk_1` FOREIGN KEY (`pageId`) REFERENCES `pages` (`pageId`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14394
;
CREATE TABLE `pmi` (
	`id` INT(11) NOT NULL AUTO_INCREMENT,
	`pageId` INT(11) NOT NULL,
	`word` LONGTEXT NOT NULL COLLATE 'utf8mb4_general_ci',
	`pmiScore` DOUBLE NOT NULL DEFAULT '0',
	PRIMARY KEY (`id`) USING BTREE,
	UNIQUE INDEX `page_id` (`id`, `pageId`) USING BTREE,
	INDEX `pmi_ibfk_1` (`pageId`) USING BTREE,
	CONSTRAINT `pmi_ibfk_1` FOREIGN KEY (`pageId`) REFERENCES `pages` (`pageId`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14386
;
CREATE TABLE `tfidf` (
	`tfidfId` INT(11) NOT NULL AUTO_INCREMENT,
	`fileId` INT(11) NOT NULL,
	`tfidfScore` DOUBLE NOT NULL DEFAULT '0',
	PRIMARY KEY (`tfidfId`) USING BTREE,
	INDEX `tfidf_fk` (`fileId`) USING BTREE,
	CONSTRAINT `tfidf_fk` FOREIGN KEY (`fileId`) REFERENCES `files` (`fileid`) ON UPDATE RESTRICT ON DELETE CASCADE
)
COLLATE='utf8mb4_general_ci'
ENGINE=InnoDB
AUTO_INCREMENT=14517
;
