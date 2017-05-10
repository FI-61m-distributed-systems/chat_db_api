# chat_db_api

# Get Start
1. Запустить Redis сервер.
2. В новой вкладке/окне терминала ввести команду: 
$ python subscriber.py имя-чата 
имя-чата - параметр необходимый для идентификации "канала"
3. В новой вкладке/окне терминала ввести команду:
$ python publisher.py имя имя-чата
имя - параметр идентифицирующий "ползователя"
имя-чата - должен быть такой же как и пункте 2.
В этом терминале можно писать сообщения которые будут отображаться во вкладке с subscriber.py
4. Можно запустить несколько publisher.py с разными "именами" и с одинаковыми названием "канала" и во вкладке subscriber.py будет отображаться последовательность сообщений.
# Пример
1. Вкладка 1
$ python sub.py PYTHON
Listening to PYTHON
2. Вкладка 2
$ python pub.py Dan PYTHON
Welcome to PYTHON
Enter a message:
4. Вкладка 3
$ python pub.py Jesse PYTHON
Welcome to PYTHON
Enter a message:
5. После ввода сообщений во ВКЛАДКЕ 1 будет отображаться сообщения от Dan и Jesse.
