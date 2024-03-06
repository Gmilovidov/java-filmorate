# java-filmorate
Template repository for Filmorate project.
![ ](./filmorate.png)

Пример: получить первые 10 фильмов по жанру "комедия"

SELECT f.name 
FROM films AS f
WHERE f.genre_id IN (SELECT g.genre_id
                     FROM genre AS g)
                     WHERE g.genre_name = 'комедия'
                     LIMIT 10)

