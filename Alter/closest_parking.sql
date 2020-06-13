DELIMITER $$

USE `vpark`$$

DROP PROCEDURE IF EXISTS `closest_parking`$$

CREATE DEFINER=`gauss`@`::1` PROCEDURE `closest_parking`(IN units VARCHAR(5), IN lat DECIMAL(9,6), IN lon DECIMAL(9,6), 
    IN max_distance SMALLINT, IN limit_rows MEDIUMINT)
BEGIN
   SELECT latitude,longitude,park_name,
          ROUND((CASE units WHEN 'miles' 
             THEN 3959
             ELSE 6371 
           END * ACOS( COS( RADIANS(lat) ) 
               * COS( RADIANS(latitude) ) 
               * COS( RADIANS(longitude) - RADIANS(lon)) + SIN(RADIANS(lat)) 
               * SIN( RADIANS(latitude)))
          ), 3) AS distance
   FROM parking_locations HAVING distance <= max_distance
   ORDER BY distance ASC   LIMIT limit_rows;
 END$$

DELIMITER ;