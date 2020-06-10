SELECT pro.name_prof, o.date, o.time, o.text FROM observe o, professional pro 
WHERE o.text LIKE '%breathing%' 
AND pro.hid = o.hid
AND o.pid IN (SELECT pf.pid FROM person_focused pf,patient pt WHERE pf.pid = pt.pid AND pf.name = 'Samantha Adam')
ORDER by o.date DESC, o.time DESC;
