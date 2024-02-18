Базовое задание:
Добавить в один из Ваших проектов сделанных ранее ApiGateWay и Eureka.<br>
В проекте обязательно должна быть Spring Data.


Возможно при клонировании репозитория не будет модулей. Их надо подключть вручную:<br>
Зайти в File -> Project Structure -> Modules -> + -> Import Module<br>
Далее выбираем любой pom.xml<br>
Можно выбрать там же все модули,<br>
а можно добавлять модули в вкладке справа Maven -> + -> pom.xml

Eurika server запускаются на порту:
http://localhost:8761

## Запуск из конфигурации "run all" в idea
Запустятся 4 модуля.

API микросервисов:

Главная страница (get запрос):  http://localhost:8080/shop/

Переход по товарам (get запрос): http://localhost:8080/shop/product/1

Покупка товара (post запрос): http://localhost:8080/shop/buyProduct

___ 
    start Homework 11
___
#### Homework 11:
Задание: По примерам показанным на семинаре:<br>
-[x] Подключить к своему проекту зависимости actuator, registry-prometheus и micrometer.<br>
-[x] Установить и подключить к проекту prometheus<br>
-[x] Установить и подключить Grafana. В Grafana добавить пару точеу контроля( Например: процессоное время приложения и количество запросов)
 
#### Формат сдачи: 
проект с добавленными зависимостями, файл настройки prometheus и скриншот Grafana с добавленными контрольными точками.

#### Задание со звездочкой:
-[x] Проделать, то же самое с многомодульным проектом(добавить под контроль несколько модулей)<br>
-[ ] Добавить собственную метрику.

скачиваем:
- nssm-2.24
- prometheus-2.49.1.windows-amd64
- grafana-enterprise-10.3.1.windows-amd64.msi

командная строка от имени администратора:
    переходим на диск где распакован nssm-2.24
    если это другой диск то:
```PwerShell
    e:

    cd e:\{Путь до файла}\nssm-2.24\win64\
```    
далее устанавливаем:
```PwerShell
    nssm.exe install prometheus e:\{Путь до файла}\prometheus-2.49.1.windows-amd64\prometheus.exe
```

собираем из нескольких сервисов:
```yml
global:
scrape_interval: 15s
evaluation_interval: 15s

scrape_configs:
- job_name: "prometheus"
  static_configs:
    - targets: ['localhost:9090']

- job_name: 'microservice-shop'
  metrics_path: /actuator/prometheus
  static_configs:
    - targets: ['localhost:8080']

- job_name: 'microservice-warehouse'
  metrics_path: /actuator/prometheus
  static_configs:
    - targets: ['localhost:8081']
```
___
    end Homework 11
___

___ 
    start Homework 12
___
Урок 12. Паттерны проектирония и GoF паттерны в Spring приложении
Задание:
1) На базе первого примера разобранного на семинаре, добавить в 
один из проектов разработанных ранее spring Integration. 
Сохранять запросы от пользователя в файл.
2) Добавить в проект один из паттернов разобранных на лекции.
___
    end Homework 12
___