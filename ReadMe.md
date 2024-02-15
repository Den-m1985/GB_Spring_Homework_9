Базовое задание:
Добавить в один из Ваших проектов сделанных ранее ApiGateWay и Eureka.\
В проекте обязательно должна быть Spring Data.


Возможно при клонировании репозитория не будет модулей. Их надо подключть вручную:\
Зайти в File -> Project Structure -> Modules -> + -> Import Module\
Далее выбираем любой pom.xml\
Можно выбрать там же все модули,\
а можно добавлять модули в вкладке справа Maven -> + -> pom.xml

Eurika server запускаются на порту:
http://localhost:8761

## Запуск из конфигурации "run all" в idea
Запустятся 4 модуля.

API микросервисов:

Главная страница (get запрос):  http://localhost:8080/shop/

Переход по товарам (get запрос): http://localhost:8080/shop/product/1

Покупка товара (post запрос): http://localhost:8080/shop/buyProduct

_________________________________start Homework 11_________________________________________________
Homework 11:
Задание: По примерам показанным на семинаре:
1) Подключить к своему проекту зависимости actuator, registry-prometheus и micrometer.
2) Установить и подключить к проекту prometheus
3) Установить и подключить Grafana. В Grafana добавить пару точеу контроля( Например: процессоное время приложения и количество запросов)
   Формат сдачи: проект с добавленными зависимостями, файл настройки prometheus и скриншот Grafana с добавленными контрольными точками.
   Задание со звездочкой:
- Проделать, то же самое с многомодульным проектом(добавить под контроль несколько модулей)
- Добавить собственную метрику.

скачиваем:
nssm-2.24
prometheus-2.49.1.windows-amd64
grafana-enterprise-10.3.1.windows-amd64.msi

командная строка от имени администратора:
    переходим на диск где распакован nssm-2.24 
    cd e:\Программы\IT\Spring\nssm-2.24\win64\
далее устанавливаем:
    nssm.exe install prometheus e:\Программы\IT\Spring\prometheus-2.49.1.windows-amd64\prometheus.exe


собираем из нескольких сервисов:
global:
scrape_interval: 15s
evaluation_interval: 15s

scrape_configs:
# The job name is added as a label `job=<job_name>` to any timeseries scraped from this config.
- job_name: "prometheus"

  # metrics_path defaults to '/metrics'
  # scheme defaults to 'http'.

  static_configs:
    - targets: ['localhost:9090']

- job_name: 'microservice-shop'
  metrics_path: /actuator/prometheus
  #scheme: http
  static_configs:
    - targets: ['localhost:8080']

- job_name: 'microservice-warehouse'
  metrics_path: /actuator/prometheus
  #scheme: http
  static_configs:
    - targets: ['localhost:8081']
________________________________end Homework 11________________________________________