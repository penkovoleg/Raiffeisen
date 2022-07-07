# Raiffeisenbank
Всем привет! Это мой вариант решения тестового задания для стажеров в Raiffeisenbank  
Задание: https://github.com/Raiffeisen-DGTL/cib-interns-test-task   
Стек: Java 11, Spring Framework (Boot, Data JPA, Web), Lombok, PostgreSQL, Liquibase, Docker.   
Коротко по заданию: реализовать CRUD приложение на основе Java + Spring для автоматизации учёта носков на складе магазина. Кладовщик должен иметь возможность: учесть приход и отпуск носков; узнать общее количество носков определенного цвета и состава в данный момент времени. Для данного задания реализовать URL HTTP-методы: POST /api/socks/income, POST /api/socks/outcome, GET /api/socks и дополнительно был добавлен GET /api/socks/all для просмотра всех доступных носков на складе в формате Json.   
Реализация:   
В качестве базы данных используется PostgreSQL, схема БД:   
Для версионирования используется Liquibase, конфигурационный файл располагается по пути: src/main/resources/liquibase/changelog.yml
Приложение и база данных поднимаются в Docker контейнере по средству Dockerfile (создает image приложения) и docker-compose (конфигурирует image базы данных и приложения). База данных разворачивается по портам 5333:5432, приложение 8085:8082 (внешний/внутренний), локально, не используя Docker, База данных 5432 и приложение 8082. Оба файла Dockerfile и docker-compose.yml размещены в корне.   
Приложение и база данных в виде образов Docker задеплоины на Heroku. Ссылка на приложение: https://raiffeisenbank.herokuapp.com/   
Список доступных запросов:   
https://raiffeisenbank.herokuapp.com/api/socks/all - получение списка всех доступных носков в формате Json   
https://raiffeisenbank.herokuapp.com/api/socks/income - учет прихода носков, пример запроса JSON:   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleIncomeRequest.png)   
https://raiffeisenbank.herokuapp.com/api/socks/outcome - учет отпуска носков, пример запроса JSON:   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleOutcomeRequest.png)   
https://raiffeisenbank.herokuapp.com/api/socks?color=red&operation=moreThan&cottonPart=90 - возвращает общее количество носков на складе, соответствующих переданным в параметрах критериям запроса (color, operation, cottonPart).   
В приложении реализована валидация всех передаваемых пользователем параметров, если поле не соответсвует требованиям, то в качестве ответа будет получен Json в формате:   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleBadRequest.png)   
Схожие Json будут получены и при других исключительных ситуациях (400, 404), где будет указан возникший Exception и сообщение с деталями о причине его возникновения:   
![image](https://github.com/penkovoleg/Raiffeisen/raw/main/image/ExampleNoSuchElement.png)   
