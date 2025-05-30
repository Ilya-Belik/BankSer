# Система управления банковскими картами

Описать данный файл по ТЗ

Создание администратора
curl --location 'http://localhost:9090/auth/sign-up' \
--header 'Content-Type: application/json' \
--data-raw '{
"username": "admin",
"password": "P@ssw0rd!2025",
"roleEnum": "ROLE_ADMIN"
}'

eyJhbGciOiJIUzI1NiJ9.eyJyb2xlIjpbXSwiaWQiOiI2MTFhNTMzMi0yMTQzLTQzNjYtYTdkMS01ZWM5MDJhMDFiOTciLCJzdWIiOiJhZG1pbiIsImlhdCI6MTc0ODYzMTM4NCwiZXhwIjoxNzQ4Nzc1Mzg0fQ.Ko3Q1oyyNtJGGpNmK6J5ueUXoZdr1NngtS0nIiLnLIM
