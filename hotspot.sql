SELECT pf.bname, count(*) ct 
FROM person_focused pf INNER JOIN patient pt ON pt.pid = pf.pid
GROUP BY pf.bname
ORDER BY ct DESC, bname ASC
