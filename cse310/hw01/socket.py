from socket import *
import ssl

mailserver = "smtp.gmail.com"#use gmail server

clientSocket=socket(AF_INET, SOCK_STREAM)
clientSocketSSL=ssl.wrap_socket(clientSocket)

clientSocketSSL.connect((mailserver, 465))#TLS on 587
recv=clientSocketSSL.recv(1024).decode()
print(recv)

if recv[:3] != '220':
    print("220 reply not received from server")


heloCmd="HELO gmail.com\r\n"
clientSocketSSL.send(heloCmd.encode())
recv1=clientSocketSSL.recv(1024).decode()

print(recv1)

if recv1[:3] != '250':
    print("250 reply not reeived from server")
