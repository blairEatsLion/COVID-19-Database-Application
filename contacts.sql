SELECT DISTINCT name,email,phone FROM person_focused p WHERE pid IN
(
SELECT pid FROM contacted c,person_focused p
WHERE p.pid IN (SELECT c.pida FROM contacted c,person_focused p, patient pt WHERE c.pidb = pt.pid AND pt.pid = p.pid AND p.email = 'dub.vizer@br.com')
UNION
SELECT pid FROM contacted c,person_focused p
WHERE p.pid IN (SELECT c.pidb FROM contacted c,person_focused p, patient pt WHERE c.pida = pt.pid AND pt.pid = p.pid AND p.email = 'dub.vizer@br.com')
)
