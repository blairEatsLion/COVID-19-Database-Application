SELECT COUNT(DISTINCT o.pid) FROM
    administer ad INNER JOIN observe o ON ad.pid = o.pid
    
WHERE ad.dname='tocilizumab'
  AND o.text LIKE '%dizz%'
  AND o.date > (SELECT ad.date AS firstDose FROM administer ad
                WHERE ad.dname='tocilizumab'
                ORDER BY ad.date ASC
                LIMIT 1);

