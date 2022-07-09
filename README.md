# Raiffeisenbank
Всем привет! Это мой вариант решения тестового задания для стажеров в Raiffeisenbank  
**Задание:** https://github.com/Raiffeisen-DGTL/cib-interns-test-task   
**Стек:** Java 11, Spring Framework (Boot, Data JPA, Web), Lombok, PostgreSQL, Liquibase, Docker.   
**Коротко по заданию:** реализовать CRUD приложение на основе Java + Spring для автоматизации учёта носков на складе магазина. Кладовщик должен иметь возможность: учесть приход и отпуск носков; узнать общее количество носков определенного цвета и состава в данный момент времени.    
Для данного задания реализовать URL HTTP-методы:   
+ POST /api/socks/income   
+ POST /api/socks/outcome   
+ GET /api/socks   
     
**Результаты:**   
+ HTTP 200 — удалось добавить приход/отпуск;   
+ HTTP 400 — параметры запроса отсутствуют или имеют некорректный формат;   
+ HTTP 500 — произошла ошибка, не зависящая от вызывающей стороны (например, база данных недоступна).   
   
**Реализация:**   
В качестве базы данных используется PostgreSQL, для версионирования используется Liquibase, конфигурационный файл располагается по пути: ***src/main/resources/liquibase/changelog.yml***, схема создаваемой таблицы:   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ShemeDBSocks.png)   
Приложение и база данных поднимаются в Docker контейнере по средству Dockerfile (создает image приложения) и docker-compose (конфигурирует image базы данных и приложения). База данных разворачивается по портам 5333:5432, приложение 8085:8082 (внешний/внутренний); локально, не используя Docker, база данных использует 5432, а приложение 8082. Файлы Dockerfile и docker-compose.yml размещены в корне. Для запуска проекта через Docker достаточно воспользоваться командой: `docker compose up`, перед этим выполнив сборку проекта в `.jar`. Приложение было размещено на Heroku.   
   
**Ссылка на приложение в Heroku:** **https://raiffeisenbank.herokuapp.com/**   
   
**Список доступных запросов:**   
+ `GET` https://raiffeisenbank.herokuapp.com/api/socks/all - дополнительный метод для просмотра всех доступных носков на складе в формате JSON   
+ `POST` https://raiffeisenbank.herokuapp.com/api/socks/income   
+ `POST` https://raiffeisenbank.herokuapp.com/api/socks/outcome   
+ `GET` https://raiffeisenbank.herokuapp.com/api/socks?color=red&operation=moreThan&cottonPart=90   
   
**Более подробно с примерами:**   
+ `GET` https://raiffeisenbank.herokuapp.com/api/socks/all - получение списка всех доступных носков в формате JSON   
   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleAllRequest.png)   
+ `POST` https://raiffeisenbank.herokuapp.com/api/socks/income - учет прихода носков, пример запроса JSON:  
```
{   
    "color":"red",
    "cottonPart": 70,
    "quantity": 20   
}
```   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleIncomeRequest.png)   
   
+ `POST` https://raiffeisenbank.herokuapp.com/api/socks/outcome - учет отпуска носков, пример запроса JSON:   
```
{   
    "color":"red",
    "cottonPart": 70,
    "quantity": 10   
}
```   
   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleOutcomeRequest.png)   
   
+ `GET` https://raiffeisenbank.herokuapp.com/api/socks?color=red&operation=moreThan&cottonPart=90 - возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса (color, operation, cottonPart).   
   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleGetRequest.png)   
   
В приложении реализована валидация всех передаваемых пользователем параметров, если поле не соответсвует требованиям, то в качестве ответа будет получен JSON в формате:   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleBadRequest.png)   
   
Схожие JSON будут получены и при других исключительных ситуациях (400, 404), где будет указан возникший Exception и сообщение с деталями о причине его возникновения:   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleNoSuchElement.png)   
   
