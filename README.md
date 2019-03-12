# ChatRoom
Running environment: Java version 1.8

Running platform: Windows 10

## How to run the jar files
As a user open cmd to run jar file, he or she needs to open server jar first, enter command as java -jar [FilePath] to open server jar.Then user can run client jar, and the command is java -jar [FilePath] [IP number] [Port],because server default work in port 20666, so [port] is 20666 and IP number is server's IP number.

For example: <br/>
java -jar ChatServer.jar<br/>
java -jar Client.jar 127.0.0.1 20666

## Functions
This is a chatting room application for both group and private communication. When jar run successfully, a login GUI will appear with three buttons, namely "register", "log in" and "quit" pops up, in which users can do the accordingly functions in the chatting room. While the user click "register", a register GUI popped up, allowing users to type in username, nickname, password and choose the file. After registering and logging in, users come in the group chat GUI, where users are able to read the unread messages from other users and chat with the online users chosen from the user-list. The user list shows all the registered users among which the online users are specified by the red nickname. Additionally, users can send emoji, files and check the chat records as well. Meantime, when another offline-registered user log in the system, the user list will be updated.

If a user wants to chat with another user individually, he or she could click the username in the user list. Then a pair chat window will pop up. In this window, all the functions are the same with that in group chatting room except the user list part is replaced by the self image up to the gender.
