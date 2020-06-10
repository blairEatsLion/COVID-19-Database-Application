
SELECT p.dname DRUGNAME, COALESCE(p.numRec,0) RECOVERED, COALESCE(q.numNot,0) NOTRECOVERED FROM
     (SELECT dname, COUNT(DISTINCT ad.pid) AS numRec, 0 AS numNot
      FROM administer ad
      WHERE ad.pid IN (SELECT pid FROM patient WHERE status = 'recovered')
      GROUP BY ad.dname) p
    FULL OUTER JOIN
     (SELECT dname, 0 AS numRec, COUNT(DISTINCT ad.pid) AS numNot
      FROM administer ad
      WHERE ad.pid IN
            (SELECT pid FROM patient WHERE status = 'deceased' OR status = 'infected')
      GROUP BY ad.dname) q
     ON p.dname = q.dname;