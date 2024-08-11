# Task Management System

Task Management System — это система управления задачами, разработанная на базе Spring Framework и использующая PostgreSQL в качестве базы данных.

## Установка

### Предварительные требования

- **Java 11** или более новая версия
- **Maven** или **Gradle** (для сборки проекта)
- **PostgreSQL** (для базы данных)
- **SMTP-сервер** (для отправки email)

### Установка

1. **Клонируйте репозиторий**:

   ```bash
   git clone https://github.com/Sanjardasturchi/Task_Management_System.git
   cd Task_Management_System


2. Установите зависимости с помощью Maven:
mvn install

Или используйте Gradle:
./gradlew build

3. Настройте базу данных:

Создайте базу данных PostgreSQL с именем TaskManagementSystemDB и настройте пользователя и пароль:

URL: jdbc:postgresql://localhost:5432/databasename
Имя пользователя: username
Пароль: password


4. Настройте файл application.properties:

Откройте src/main/resources/application.properties и убедитесь, что у вас указаны правильные настройки для подключения к базе данных и почтового сервера:

# Настройки базы данных
spring.datasource.url=jdbc:postgresql://localhost:5432/databasename
spring.datasource.username=username
spring.datasource.password=password

# Настройки почтового сервера
spring.mail.host=smtp.mail.ru
spring.mail.port=465
spring.mail.username=*****************@mail.ru
spring.mail.password=***************
spring.mail.protocol=smtps
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.connectiontimeout=50000
spring.mail.properties.mail.smtp.timeout=50000
spring.mail.properties.mail.smtp.writetimeout=50000


Запуск
1. Запустите приложение с помощью Maven:
  mvn spring-boot:run
Или используйте Gradle:
  ./gradlew bootRun 
2. Откройте веб-интерфейс приложения в браузере по адресу:
  http://localhost:8080
Использование
Управление задачами: Создание, редактирование и удаление задач.
Email-уведомления: Отправка уведомлений по электронной почте.
Тестирование
Для выполнения тестов используйте:
mvn test
Или с помощью Gradle:
./gradlew test
Внесение изменений
Если вы хотите внести изменения в проект, выполните следующие шаги:

Сделайте форк репозитория и создайте новую ветку.
Внесите необходимые изменения.
Создайте Pull Request.
Решение проблем
Если вы столкнулись с проблемами:

Проверьте журналы: logs/log-file.log
Проверьте настройки в application.properties
Лицензия
Этот проект распространяется под лицензией MIT.

Контакты
Если у вас есть вопросы или вам нужна помощь, вы можете связаться с нами по следующему адресу:

Email: sanjarbeksultanov2023@gmail.com
GitHub Issues: Issues проекта


### Объяснение:

1. **Оглавление**: Включает название проекта и краткое описание.
2. **Установка**: Описание шагов по установке и настройке проекта.
3. **Запуск**: Инструкции по запуску приложения.
4. **Использование**: Как использовать основные функции приложения.
5. **Тестирование**: Как выполнять тесты.
6. **Внесение изменений**: Инструкции для разработчиков, желающих внести изменения в проект.
7. **Решение проблем**: Как устранять потенциальные проблемы.
8. **Лицензия**: Информация о лицензии.
9. **Контакты**: Как связаться с автором проекта для получения помощи.

Этот шаблон README должен быть настроен в соответствии с вашими потребностями и дополнительной информацией, которую вы можете хотеть включить.
 











